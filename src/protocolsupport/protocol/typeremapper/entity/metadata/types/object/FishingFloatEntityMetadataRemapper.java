package protocolsupport.protocol.typeremapper.entity.metadata.types.object;

import protocolsupport.protocol.typeremapper.entity.metadata.object.value.IndexValueRemapperNoOp;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.BaseEntityMetadataRemapper;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class FishingFloatEntityMetadataRemapper extends BaseEntityMetadataRemapper {

	public static final FishingFloatEntityMetadataRemapper INSTANCE = new FishingFloatEntityMetadataRemapper();

	protected FishingFloatEntityMetadataRemapper() {
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.FishingFloat.HOOKED_ENTITY, 7), ProtocolVersionsHelper.UP_1_14);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.FishingFloat.HOOKED_ENTITY, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addRemap(new IndexValueRemapperNoOp(NetworkEntityMetadataObjectIndex.FishingFloat.HOOKED_ENTITY, 5), ProtocolVersionsHelper.ALL_1_9);
	}

}
