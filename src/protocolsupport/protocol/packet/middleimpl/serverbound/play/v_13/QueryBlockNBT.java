package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleQueryBlockNBT;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class QueryBlockNBT extends MiddleQueryBlockNBT {

	public QueryBlockNBT(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void readClientData(ByteBuf clientdata) {
		id = VarNumberSerializer.readVarInt(clientdata);
		PositionSerializer.readLegacyPositionLTo(clientdata, position);
	}

}
