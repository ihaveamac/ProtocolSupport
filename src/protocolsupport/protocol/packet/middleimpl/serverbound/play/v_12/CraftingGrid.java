package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_12;

import java.util.function.Function;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleCraftingGrid;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;

public class CraftingGrid extends MiddleCraftingGrid {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		windowId = clientdata.readByte();
		actionNumber = clientdata.readShort();
		Function<ByteBuf, Entry> elementReader = (from) -> {
			return new Entry(ItemStackSerializer.readItemStack(from, connection.getVersion(), cache.getLocale(), true), from.readByte(), from.readByte());
		};
		returnEntries = ArraySerializer.readShortTArray(clientdata, Entry.class, elementReader);
		prepareEntries = ArraySerializer.readShortTArray(clientdata, Entry.class, elementReader);
	}

}
