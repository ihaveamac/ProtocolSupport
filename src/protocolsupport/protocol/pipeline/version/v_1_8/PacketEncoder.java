package protocolsupport.protocol.pipeline.version.v_1_8;

import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.PacketDataCodecImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.LoginDisconnect;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.LoginSuccess;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.EncryptionRequest;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.SetCompression;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopAdvancements;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopAdvanementsTab;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopBossBar;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopCraftRecipeConfirm;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopDeclareCommands;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopDeclareRecipes;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopDeclareTags;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopEntitySound;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopLookAt;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopSetCooldown;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopSetViewCenter;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopStatistics;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopStopSound;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopUnlockRecipes;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopUpdateViewDistance;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.noop.NoopVehicleMove;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.WorldCustomSound;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8.WorldSound;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.InventoryClose;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.InventoryConfirmTransaction;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.InventoryData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.InventorySetItems;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.InventorySetSlot;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.ScoreboardDisplay;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.TimeUpdate;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6_7_8.EntityLeash;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6_7_8.EntityPassengers;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.PlayerAbilities;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8.Explosion;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8.SpawnExpOrb;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8.SpawnGlobal;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8.SpawnLiving;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8_9r1_9r2_10_11_12r1_12r2.TabComplete;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8_9r1_9r2_10_11_12r1_12r2_13.ChangeDimension;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.EntityAnimation;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.GameStateChange;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.HeldSlot;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.KickDisconnect;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.BookOpen;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.Chunk;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.ChunkLight;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.ChunkUnload;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.EntityEquipment;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.EntityMetadata;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.EntityRelMove;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.EntityRelMoveLook;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.EntityStatus;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.EntityTeleport;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.ScoreboardTeam;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.SetPosition;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.SpawnNamed;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.SpawnObject;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.SpawnPainting;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8.UpdateMap;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2.EntityEffectAdd;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10.CollectEffect;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10.Title;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2.BlockAction;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2.BlockBreakConfirm;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2.BlockChangeMulti;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2.BlockChangeSingle;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2.CustomPayload;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2.InventoryHorseOpen;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2.InventoryOpen;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2.MerchantTradeList;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2.ScoreboardObjective;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2.WorldParticle;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.BlockBreakAnimation;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.BlockOpenSignEditor;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.BlockTileUpdate;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.ServerDifficulty;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.SpawnPosition;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.StartGame;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13.WorldEvent;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.Camera;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.Chat;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.CombatEvent;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.Entity;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.EntityDestroy;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.EntityEffectRemove;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.EntityHeadRotation;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.EntityLook;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.EntitySetAttributes;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.EntityVelocity;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.KeepAlive;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.PlayerListHeaderFooter;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.PlayerListSetEntry;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.ResourcePack;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.ScoreboardScore;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.SetExperience;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.SetHealth;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.WorldBorder;
import protocolsupport.protocol.packet.middleimpl.clientbound.status.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.Pong;
import protocolsupport.protocol.packet.middleimpl.clientbound.status.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.ServerInfo;
import protocolsupport.protocol.pipeline.version.util.encoder.AbstractPacketEncoder;
import protocolsupport.protocol.typeremapper.packet.ChunkSendIntervalPacketQueue;

public class PacketEncoder extends AbstractPacketEncoder {

	public PacketEncoder(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void init(PacketDataCodecImpl codec) {
		super.init(codec);
		codec.addClientboundPacketProcessor(new ChunkSendIntervalPacketQueue());
	}

	{
		registry.register(NetworkState.LOGIN, PacketType.CLIENTBOUND_LOGIN_SUCCESS, LoginSuccess::new);
		registry.register(NetworkState.LOGIN, PacketType.CLIENTBOUND_LOGIN_ENCRYPTION_BEGIN, EncryptionRequest::new);
		registry.register(NetworkState.LOGIN, PacketType.CLIENTBOUND_LOGIN_DISCONNECT, LoginDisconnect::new);
		registry.register(NetworkState.LOGIN, PacketType.CLIENTBOUND_LOGIN_SET_COMPRESSION, SetCompression::new);
		registry.register(NetworkState.STATUS, PacketType.CLIENTBOUND_STATUS_SERVER_INFO, ServerInfo::new);
		registry.register(NetworkState.STATUS, PacketType.CLIENTBOUND_STATUS_PONG, Pong::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_KEEP_ALIVE, KeepAlive::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_START_GAME, StartGame::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_CHAT, Chat::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_UPDATE_TIME, TimeUpdate::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_ENTITY_EQUIPMENT, EntityEquipment::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_SPAWN_POSITION, SpawnPosition::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_SET_HEALTH, SetHealth::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_RESPAWN, ChangeDimension::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_POSITION, SetPosition::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_HELD_SLOT, HeldSlot::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_ENTITY_ANIMATION, EntityAnimation::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_SPAWN_NAMED, SpawnNamed::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_COLLECT_EFFECT, CollectEffect::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_SPAWN_OBJECT, SpawnObject::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_SPAWN_LIVING, SpawnLiving::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_SPAWN_PAINTING, SpawnPainting::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_SPAWN_EXP_ORB, SpawnExpOrb::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_ENTITY_VELOCITY, EntityVelocity::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_ENTITY_DESTROY, EntityDestroy::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_ENTITY_NOOP, Entity::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_ENTITY_REL_MOVE, EntityRelMove::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_ENTITY_LOOK, EntityLook::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_ENTITY_REL_MOVE_LOOK, EntityRelMoveLook::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_ENTITY_TELEPORT, EntityTeleport::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_ENTITY_HEAD_ROTATION, EntityHeadRotation::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_ENTITY_STATUS, EntityStatus::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_ENTITY_LEASH, EntityLeash::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_ENTITY_METADATA, EntityMetadata::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_ENTITY_EFFECT_ADD, EntityEffectAdd::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_ENTITY_EFFECT_REMOVE, EntityEffectRemove::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_SET_EXPERIENCE, SetExperience::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_ENTITY_ATTRIBUTES, EntitySetAttributes::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE, Chunk::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_BLOCK_CHANGE_MULTI, BlockChangeMulti::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_BLOCK_CHANGE_SINGLE, BlockChangeSingle::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_BLOCK_BREAK_CONFIRM, BlockBreakConfirm::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_BLOCK_ACTION, BlockAction::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_BLOCK_BREAK_ANIMATION, BlockBreakAnimation::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_EXPLOSION, Explosion::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_WORLD_EVENT, WorldEvent::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_WORLD_SOUND, WorldSound::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_WORLD_CUSTOM_SOUND, WorldCustomSound::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_WORLD_PARTICLES, WorldParticle::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_GAME_STATE_CHANGE, GameStateChange::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_SPAWN_GLOBAL, SpawnGlobal::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_WINDOW_OPEN, InventoryOpen::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_WINDOW_HORSE_OPEN, InventoryHorseOpen::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_WINDOW_CLOSE, InventoryClose::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_WINDOW_SET_SLOT, InventorySetSlot::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_WINDOW_SET_ITEMS, InventorySetItems::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_WINDOW_DATA, InventoryData::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_WINDOW_TRANSACTION, InventoryConfirmTransaction::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_UPDATE_MAP, UpdateMap::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_BLOCK_TILE, BlockTileUpdate::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_SIGN_EDITOR, BlockOpenSignEditor::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_PLAYER_INFO, PlayerListSetEntry::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_PLAYER_ABILITIES, PlayerAbilities::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_TAB_COMPLETE, TabComplete::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_SCOREBOARD_OBJECTIVE, ScoreboardObjective::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_SCOREBOARD_SCORE, ScoreboardScore::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_SCOREBOARD_DISPLAY_SLOT, ScoreboardDisplay::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_SCOREBOARD_TEAM, ScoreboardTeam::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_CUSTOM_PAYLOAD, CustomPayload::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_RESOURCE_PACK, ResourcePack::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_KICK_DISCONNECT, KickDisconnect::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_CAMERA, Camera::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_PLAYER_LIST_HEADER_FOOTER, PlayerListHeaderFooter::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_ENTITY_PASSENGERS, EntityPassengers::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_TITLE, Title::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_WORLD_BORDER, WorldBorder::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_CHUNK_UNLOAD, ChunkUnload::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_SERVER_DIFFICULTY, ServerDifficulty::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_COMBAT_EVENT, CombatEvent::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_CHUNK_LIGHT, ChunkLight::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_MERCHANT_TRADE_LIST, MerchantTradeList::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_BOOK_OPEN, BookOpen::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_SET_COOLDOWN, NoopSetCooldown::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_BOSS_BAR, NoopBossBar::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_VEHICLE_MOVE, NoopVehicleMove::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_UNLOCK_RECIPES, NoopUnlockRecipes::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_ADVANCEMENTS, NoopAdvancements::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_ADVANCEMENTS_TAB, NoopAdvanementsTab::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_CRAFT_RECIPE_CONFIRM, NoopCraftRecipeConfirm::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_STATISTICS, NoopStatistics::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_DECLARE_COMMANDS, NoopDeclareCommands::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_DECLARE_RECIPES, NoopDeclareRecipes::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_DECLARE_TAGS, NoopDeclareTags::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_STOP_SOUND, NoopStopSound::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_LOOK_AT, NoopLookAt::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_SET_VIEW_CENTER, NoopSetViewCenter::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_UPDATE_VIEW_DISTANCE, NoopUpdateViewDistance::new);
		registry.register(NetworkState.PLAY, PacketType.CLIENTBOUND_PLAY_ENTITY_SOUND, NoopEntitySound::new);
	}

}
