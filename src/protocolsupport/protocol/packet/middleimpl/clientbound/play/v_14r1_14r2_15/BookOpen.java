package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBookOpen;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;

public class BookOpen extends MiddleBookOpen {

	public BookOpen(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData bookopen = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_BOOK_OPEN);
		MiscSerializer.writeVarIntEnum(bookopen, hand);
		codec.write(bookopen);
	}

}
