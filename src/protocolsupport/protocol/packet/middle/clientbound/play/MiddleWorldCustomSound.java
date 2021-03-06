package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.types.SoundCategory;

public abstract class MiddleWorldCustomSound extends ClientBoundMiddlePacket {

	public MiddleWorldCustomSound(ConnectionImpl connection) {
		super(connection);
	}

	protected String id;
	protected SoundCategory category;
	protected int x;
	protected int y;
	protected int z;
	protected float volume;
	protected float pitch;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		id = StringSerializer.readVarIntUTF8String(serverdata);
		category = MiscSerializer.readVarIntEnum(serverdata, SoundCategory.CONSTANT_LOOKUP);
		x = serverdata.readInt();
		y = serverdata.readInt();
		z = serverdata.readInt();
		volume = serverdata.readFloat();
		pitch = serverdata.readFloat();
	}

}
