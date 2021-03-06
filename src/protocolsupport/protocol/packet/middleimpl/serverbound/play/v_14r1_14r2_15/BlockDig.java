package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_14r1_14r2_15;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockDig;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;

public class BlockDig extends MiddleBlockDig {

	public BlockDig(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void readClientData(ByteBuf clientdata) {
		status = MiscSerializer.readVarIntEnum(clientdata, Action.CONSTANT_LOOKUP);
		PositionSerializer.readPositionTo(clientdata, position);
		face = clientdata.readUnsignedByte();
	}

}
