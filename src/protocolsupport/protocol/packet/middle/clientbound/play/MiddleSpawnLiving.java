package protocolsupport.protocol.packet.middle.clientbound.play;

import java.text.MessageFormat;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.ProtocolSupport;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.protocol.types.networkentity.NetworkEntity;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.zplatform.ServerPlatform;

public abstract class MiddleSpawnLiving extends ClientBoundMiddlePacket {

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	public MiddleSpawnLiving(ConnectionImpl connection) {
		super(connection);
	}

	protected NetworkEntity entity;
	protected double x;
	protected double y;
	protected double z;
	protected byte yaw;
	protected byte pitch;
	protected byte headPitch;
	protected int motX;
	protected int motY;
	protected int motZ;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		int entityId = VarNumberSerializer.readVarInt(serverdata);
		UUID uuid = MiscSerializer.readUUID(serverdata);
		int typeId = VarNumberSerializer.readVarInt(serverdata);
		NetworkEntityType type = NetworkEntityType.getMobByNetworkTypeId(typeId);
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		yaw = serverdata.readByte();
		pitch = serverdata.readByte();
		headPitch = serverdata.readByte();
		motX = serverdata.readShort();
		motY = serverdata.readShort();
		motZ = serverdata.readShort();

		if (type == NetworkEntityType.NONE) {
			if (ServerPlatform.get().getMiscUtils().isDebugging()) {
				ProtocolSupport.logWarning(MessageFormat.format(
					"Attempted to spawn unknown mob entity type id {0} at {1},{2},{3}",
					typeId, x, y, z
				));
			}
			throw CancelMiddlePacketException.INSTANCE;
		}

		entity = NetworkEntity.createMob(uuid, entityId, type);
	}

	@Override
	protected void handleReadData() {
		entityCache.addEntity(entity);
	}

}
