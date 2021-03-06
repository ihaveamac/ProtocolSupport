package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_10_11_12r1_12r2_13_14r1_14r2_15;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldCustomSound;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.basic.SoundRemapper;
import protocolsupport.protocol.types.SoundCategory;

public class WorldCustomSound extends MiddleWorldCustomSound {

	public WorldCustomSound(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		codec.write(create(version, x, y, z, id, category, volume, pitch));
	}

	public static ClientBoundPacketData create(
		ProtocolVersion version,
		int x, int y, int z,
		String sound, SoundCategory category, float volume, float pitch
	) {
		ClientBoundPacketData worldcustomsound = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_WORLD_CUSTOM_SOUND);
		StringSerializer.writeVarIntUTF8String(worldcustomsound, SoundRemapper.getSoundName(version, sound));
		MiscSerializer.writeVarIntEnum(worldcustomsound, category);
		worldcustomsound.writeInt(x);
		worldcustomsound.writeInt(y);
		worldcustomsound.writeInt(z);
		worldcustomsound.writeFloat(volume);
		worldcustomsound.writeFloat(pitch);
		return worldcustomsound;
	}

}
