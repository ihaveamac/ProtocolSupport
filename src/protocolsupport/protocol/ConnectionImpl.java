package protocolsupport.protocol;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.logging.Level;

import org.bukkit.entity.Player;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoop;
import io.netty.util.AttributeKey;
import io.netty.util.Recycler;
import io.netty.util.Recycler.Handle;
import protocolsupport.ProtocolSupport;
import protocolsupport.api.Connection;
import protocolsupport.api.Connection.PacketListener.PacketEvent;
import protocolsupport.api.Connection.PacketListener.RawPacketEvent;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.packet.handler.IPacketListener;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPacketIdCodec;
import protocolsupport.protocol.pipeline.version.util.decoder.AbstractPacketDecoder;
import protocolsupport.protocol.pipeline.version.util.encoder.AbstractPacketEncoder;
import protocolsupport.protocol.storage.ProtocolStorage;
import protocolsupport.protocol.storage.netcache.NetworkDataCache;
import protocolsupport.protocol.utils.authlib.LoginProfile;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class ConnectionImpl extends Connection {

	protected static final AttributeKey<ConnectionImpl> key = AttributeKey.valueOf("PSConnectionImpl");

	protected final NetworkManagerWrapper networkmanager;

	public ConnectionImpl(NetworkManagerWrapper networkmanager) {
		this.networkmanager = networkmanager;
		this.networkmanager.getChannel().attr(key).set(this);
	}

	public static ConnectionImpl getFromChannel(Channel channel) {
		return channel.attr(key).get();
	}

	public void destroy() {
		Object listener = networkmanager.getPacketListener();
		if (listener instanceof IPacketListener) {
			((IPacketListener) listener).destroy();
		}
		if (codec != null) {
			codec.release();
		}
	}

	public void setVersion(ProtocolVersion version) {
		this.version = version;
	}

	public NetworkManagerWrapper getNetworkManagerWrapper() {
		return networkmanager;
	}

	@Override
	public Object getNetworkManager() {
		return networkmanager.unwrap();
	}

	@Override
	public boolean isConnected() {
		return networkmanager.isConnected();
	}

	@Override
	public void close() {
		networkmanager.close("Force connection close");
	}

	@Override
	public void disconnect(String message) {
		Player player = getPlayer();
		if (player != null) {
			player.kickPlayer(message);
			return;
		}
		Object packetListener = networkmanager.getPacketListener();
		if (packetListener instanceof IPacketListener) {
			((IPacketListener) packetListener).disconnect(message);
			return;
		}
		close();
	}

	@Override
	public InetSocketAddress getVirtualHost() {
		return networkmanager.getVirtualHost();
	}

	@Override
	public InetSocketAddress getRawAddress() {
		return networkmanager.getRawAddress();
	}

	@Override
	public InetSocketAddress getAddress() {
		return networkmanager.getAddress();
	}

	@Override
	public void changeAddress(InetSocketAddress newRemote) {
		SocketAddress primaryaddr = networkmanager.getRawAddress();
		ProtocolStorage.addAddress(primaryaddr, newRemote);
		networkmanager.setAddress(newRemote);
	}

	@Override
	public Player getPlayer() {
		return networkmanager.getBukkitPlayer();
	}

	public LoginProfile getLoginProfile() {
		return (LoginProfile) profile;
	}

	public void setWrappedProfile(Player player) {
		profile = ServerPlatform.get().getMiscUtils().createWrappedProfile(getLoginProfile(), player);
	}

	@Override
	public NetworkState getNetworkState() {
		return networkmanager.getNetworkState();
	}

	@Override
	public EventLoop getIOExecutor() {
		return networkmanager.getChannel().eventLoop();
	}

	public void submitIOTask(Runnable task) {
		getIOExecutor().submit(() -> {
			try {
				if (!isConnected()) {
					return;
				}
				task.run();
			} catch (Exception e) {
				ProtocolSupport.getInstance().getLogger().log(Level.WARNING, "Unhandled exception occured in task submitted to event loop", e);
			}
		});
	}

	protected PacketDataCodecImpl codec;
	protected ChannelHandlerContext logicCtx;
	protected ChannelHandlerContext rawSendCtx;
	protected ChannelHandlerContext rawRecvCtx;

	public PacketDataCodecImpl getCodec() {
		return codec;
	}

	public void initCodec(IPacketIdCodec packetIdCodec, AbstractPacketEncoder encoder, AbstractPacketDecoder decoder) {
		ChannelPipeline pipeline = networkmanager.getChannel().pipeline();
		this.logicCtx = pipeline.context(ChannelHandlers.LOGIC);
		this.rawSendCtx = pipeline.context(ChannelHandlers.RAW_CAPTURE_SEND);
		this.rawRecvCtx = pipeline.context(ChannelHandlers.RAW_CAPTURE_RECEIVE);
		this.codec = new PacketDataCodecImpl(this, packetIdCodec, pipeline.context(encoder), pipeline.context(decoder));
		encoder.init(codec);
		decoder.init(codec);
	}

	@Override
	public void sendPacket(Object packet) {
		submitIOTask(() -> logicCtx.writeAndFlush(packet, logicCtx.voidPromise()));
	}

	@Override
	public void receivePacket(Object packet) {
		submitIOTask(() -> {
			logicCtx.fireChannelRead(packet);
			logicCtx.fireChannelReadComplete();
		});
	}

	@Override
	public void sendRawPacket(byte[] data) {
		ByteBuf dataInst = Unpooled.wrappedBuffer(data);
		submitIOTask(() -> rawSendCtx.writeAndFlush(dataInst));
	}

	@Override
	public void receiveRawPacket(byte[] data) {
		ByteBuf dataInst = Unpooled.wrappedBuffer(data);
		submitIOTask(() -> {
			rawRecvCtx.fireChannelRead(dataInst);
			rawRecvCtx.fireChannelReadComplete();
		});
	}


	protected static class LPacketEvent extends PacketEvent implements AutoCloseable {

		protected static final Recycler<LPacketEvent> recycler = new Recycler<LPacketEvent>() {
			@Override
			protected LPacketEvent newObject(Handle<LPacketEvent> handle) {
				return new LPacketEvent(handle);
			}
		};

		public static LPacketEvent create(Object packet) {
			LPacketEvent packetevent = recycler.get();
			packetevent.mainpacket = packet;
			packetevent.packets.add(packet);
			return packetevent;
		}

		protected final Handle<LPacketEvent> handle;
		protected LPacketEvent(Handle<LPacketEvent> handle) {
			this.handle = handle;
		}

		public void recycle() {
			this.mainpacket = null;
			this.packets.clear();
			this.cancelled = false;
			this.handle.recycle(this);
		}

		@Override
		public void close() {
			recycle();
		}

	}

	public void handlePacketSend(Object packet, Collection<Object> storeTo) {
		try (LPacketEvent packetevent = LPacketEvent.create(packet)) {
			for (PacketListener listener : packetlisteners) {
				try {
					listener.onPacketSending(packetevent);
				} catch (Throwable t) {
					System.err.println("Error occured while handling packet sending");
					t.printStackTrace();
				}
			}
			if (!packetevent.isCancelled()) {
				storeTo.addAll(packetevent.getPackets());
			}
		}
	}

	public void handlePacketReceive(Object packet, Collection<Object> storeTo) {
		try (LPacketEvent packetevent = LPacketEvent.create(packet)) {
			for (PacketListener listener : packetlisteners) {
				try {
					listener.onPacketReceiving(packetevent);
				} catch (Throwable t) {
					System.err.println("Error occured while handling packet receiving");
					t.printStackTrace();
				}
			}
			if (!packetevent.isCancelled()) {
				storeTo.addAll(packetevent.getPackets());
			}
		}
	}

	protected static class LRawPacketEvent extends RawPacketEvent implements AutoCloseable {

		protected static final Recycler<LRawPacketEvent> recycler = new Recycler<LRawPacketEvent>() {
			@Override
			protected LRawPacketEvent newObject(Handle<LRawPacketEvent> handle) {
				return new LRawPacketEvent(handle);
			}
		};

		public static LRawPacketEvent create(ByteBuf data) {
			LRawPacketEvent packetevent = recycler.get();
			packetevent.data = data;
			packetevent.cancelled = false;
			return packetevent;
		}

		protected final Handle<LRawPacketEvent> handle;
		protected LRawPacketEvent(Handle<LRawPacketEvent> handle) {
			this.handle = handle;
		}

		public void recycle() {
			this.handle.recycle(this);
		}

		public ByteBuf getDirectData() {
			return this.data;
		}

		@Override
		public void close() {
			recycle();
		}

	}

	public ByteBuf handleRawPacketSend(ByteBuf data) {
		try (LRawPacketEvent rawpacketevent = LRawPacketEvent.create(data)) {
			for (PacketListener listener : packetlisteners) {
				try {
					listener.onRawPacketSending(rawpacketevent);
				} catch (Throwable t) {
					System.err.println("Error occured while handling raw packet sending");
					t.printStackTrace();
				}
			}
			if (rawpacketevent.isCancelled()) {
				rawpacketevent.getDirectData().release();
				return null;
			} else {
				return rawpacketevent.getDirectData();
			}
		}
	}

	public ByteBuf handleRawPacketReceive(ByteBuf data) {
		try (LRawPacketEvent rawpacketevent = LRawPacketEvent.create(data)) {
			for (PacketListener listener : packetlisteners) {
				try {
					listener.onRawPacketReceiving(rawpacketevent);
				} catch (Throwable t) {
					System.err.println("Error occured while handling raw packet receiving");
					t.printStackTrace();
				}
			}
			if (rawpacketevent.isCancelled()) {
				rawpacketevent.getDirectData().release();
				return null;
			} else {
				return rawpacketevent.getDirectData();
			}
		}
	}

	protected final NetworkDataCache cache = new NetworkDataCache();

	public NetworkDataCache getCache() {
		return cache;
	}

	@Override
	public String toString() {
		return MessageFormat.format(
			"{0}(profile: {1}, player: {2}, address: {3}, rawaddress: {4}, version: {5}, metadata: {6})",
			getClass().getName(), getProfile(), getPlayer(), getAddress(), getRawAddress(), getVersion(), metadata
		);
	}

}
