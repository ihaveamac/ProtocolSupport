package protocolsupport.protocol.packet.middleimpl.serverbound.login.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.login.MiddleLoginStart;
import protocolsupport.protocol.serializer.StringSerializer;

public class LoginStart extends MiddleLoginStart {

	public LoginStart(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void readClientData(ByteBuf clientdata) {
		name = StringSerializer.readVarIntUTF8String(clientdata, 16);
	}

}
