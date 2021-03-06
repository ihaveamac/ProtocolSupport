package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;

public abstract class MiddleEntityTeleport extends MiddleEntity {

	public MiddleEntityTeleport(ConnectionImpl connection) {
		super(connection);
	}

	protected double x;
	protected double y;
	protected double z;
	protected byte yaw;
	protected byte pitch;
	protected boolean onGround;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		super.readServerData(serverdata);
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		yaw = serverdata.readByte();
		pitch = serverdata.readByte();
		onGround = serverdata.readBoolean();
	}

}
