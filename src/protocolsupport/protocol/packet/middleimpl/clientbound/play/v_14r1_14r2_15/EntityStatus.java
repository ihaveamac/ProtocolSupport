package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.AbstractKnownEntityStatus;

public class EntityStatus extends AbstractKnownEntityStatus {

	public EntityStatus(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData entitystatus = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_ENTITY_STATUS);
		entitystatus.writeInt(entityId);
		entitystatus.writeByte(status);
		codec.write(entitystatus);
	}

}
