package net.hycrafthd.headless_minecraft.network;

import com.mojang.authlib.GameProfile;

import io.netty.buffer.Unpooled;
import net.hycrafthd.headless_minecraft.Constants;
import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.hycrafthd.headless_minecraft.impl.HeadlessLevel;
import net.hycrafthd.headless_minecraft.impl.HeadlessMultiPlayerGameMode;
import net.hycrafthd.headless_minecraft.impl.HeadlessPlayer;
import net.hycrafthd.headless_minecraft.mixin.accessor.ClientPacketListenerAccessorMixin;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.multiplayer.ClientLevel.ClientLevelData;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundAddExperienceOrbPacket;
import net.minecraft.network.protocol.game.ClientboundAddMobPacket;
import net.minecraft.network.protocol.game.ClientboundAddPaintingPacket;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundAwardStatsPacket;
import net.minecraft.network.protocol.game.ClientboundBlockBreakAckPacket;
import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockEventPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ClientboundChatPacket;
import net.minecraft.network.protocol.game.ClientboundCommandSuggestionsPacket;
import net.minecraft.network.protocol.game.ClientboundCommandsPacket;
import net.minecraft.network.protocol.game.ClientboundContainerAckPacket;
import net.minecraft.network.protocol.game.ClientboundContainerClosePacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetDataPacket;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.network.protocol.game.ClientboundCooldownPacket;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ClientboundCustomSoundPacket;
import net.minecraft.network.protocol.game.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ClientboundForgetLevelChunkPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundHorseScreenOpenPacket;
import net.minecraft.network.protocol.game.ClientboundKeepAlivePacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacket;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import net.minecraft.network.protocol.game.ClientboundMapItemDataPacket;
import net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ClientboundOpenBookPacket;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.network.protocol.game.ClientboundOpenSignEditorPacket;
import net.minecraft.network.protocol.game.ClientboundPlaceGhostRecipePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerLookAtPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundRecipePacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundResourcePackPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSelectAdvancementsTabPacket;
import net.minecraft.network.protocol.game.ClientboundSetBorderPacket;
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;
import net.minecraft.network.protocol.game.ClientboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheRadiusPacket;
import net.minecraft.network.protocol.game.ClientboundSetDefaultSpawnPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetDisplayObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesPacket;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.network.protocol.game.ClientboundTabListPacket;
import net.minecraft.network.protocol.game.ClientboundTagQueryPacket;
import net.minecraft.network.protocol.game.ClientboundTakeItemEntityPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateTagsPacket;
import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.stats.StatsCounter;
import net.minecraft.tags.StaticTags;
import net.minecraft.util.profiling.InactiveProfiler;
import net.minecraft.world.Difficulty;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.HorseInventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.Scoreboard;

public class HeadlessPacketListener extends ClientPacketListener {
	
	private final HeadlessMinecraft headlessMinecraft;
	private final ConnectionManager connectionManager;
	
	public HeadlessPacketListener(HeadlessMinecraft headlessMinecraft, Connection connection, GameProfile gameProfile) {
		super(null, null, connection, gameProfile);
		this.headlessMinecraft = headlessMinecraft;
		connectionManager = headlessMinecraft.getConnectionManager();
		
		((ClientPacketListenerAccessorMixin) this).setAdvancements(null);
		((ClientPacketListenerAccessorMixin) this).setSuggestionsProvider(null);
	}
	
	@Override
	public void onDisconnect(Component component) {
		headlessMinecraft.getConnectionManager().disconnectedFromServer(component);
	}
	
	// Implemented
	@Override
	public void handleLogin(ClientboundLoginPacket packet) {
		PacketUtils.ensureRunningOnSameThread(packet, this, headlessMinecraft);
		
		StaticTags.resetAllToEmpty();
		
		final HeadlessMultiPlayerGameMode gameMode = new HeadlessMultiPlayerGameMode(this);
		
		connectionManager.setGameMode(gameMode);
		
		((ClientPacketListenerAccessorMixin) this).setLevels(packet.levels());
		((ClientPacketListenerAccessorMixin) this).setRegistryAccess(packet.registryAccess());
		((ClientPacketListenerAccessorMixin) this).setServerChunkRadius(packet.getChunkRadius());
		
		final ClientLevelData levelData = new ClientLevelData(Difficulty.NORMAL, packet.isHardcore(), packet.isFlat());
		final HeadlessLevel level = new HeadlessLevel(this, levelData, packet.getDimension(), packet.getDimensionType(), packet.getChunkRadius(), () -> InactiveProfiler.INSTANCE, packet.isDebug(), packet.getSeed());
		
		((ClientPacketListenerAccessorMixin) this).setLevelData(levelData);
		((ClientPacketListenerAccessorMixin) this).setLevel(level);
		
		connectionManager.setLevel(level);
		
		final HeadlessPlayer player;
		
		if (connectionManager.getPlayer() == null) {
			player = new HeadlessPlayer(this, level, new StatsCounter(), new ClientRecipeBook(), false, false);
			player.yRot = -180.0F;
			connectionManager.setPlayer(player);
		} else {
			player = connectionManager.getPlayer();
		}
		
		player.resetPos();
		level.addPlayer(packet.getPlayerId(), player);
		// TODO player.input = new KeyboardInput(xyz);
		gameMode.adjustPlayer(player);
		player.setId(packet.getPlayerId());
		player.setReducedDebugInfo(packet.isReducedDebugInfo());
		player.setShowDeathScreen(packet.shouldShowDeathScreen());
		gameMode.setLocalMode(packet.getGameType());
		gameMode.setPreviousLocalMode(packet.getPreviousGameType());
		// TODO or do not care? options.broadcastOptions();
		getConnection().send(new ServerboundCustomPayloadPacket(ServerboundCustomPayloadPacket.BRAND, new FriendlyByteBuf(Unpooled.buffer()).writeUtf(Constants.NAME)));
	}
	
	// Implemented
	@Override
	public void handleAddEntity(ClientboundAddEntityPacket packet) {
		super.handleAddEntity(packet);
	}
	
	// Implemented
	@Override
	public void handleAddExperienceOrb(ClientboundAddExperienceOrbPacket packet) {
		super.handleAddExperienceOrb(packet);
	}
	
	// Implemented
	@Override
	public void handleAddPainting(ClientboundAddPaintingPacket packet) {
		super.handleAddPainting(packet);
	}
	
	// Implemented
	@Override
	public void handleSetEntityMotion(ClientboundSetEntityMotionPacket packet) {
		super.handleSetEntityMotion(packet);
	}
	
	// Implemented
	@Override
	public void handleSetEntityData(ClientboundSetEntityDataPacket packet) {
		super.handleSetEntityData(packet);
	}
	
	// Implemented
	@Override
	public void handleAddPlayer(ClientboundAddPlayerPacket packet) {
		super.handleAddPlayer(packet);
	}
	
	// Implemented
	@Override
	public void handleKeepAlive(ClientboundKeepAlivePacket packet) {
		super.handleKeepAlive(packet);
	}
	
	// Implemented
	@Override
	public void handleTeleportEntity(ClientboundTeleportEntityPacket packet) {
		super.handleTeleportEntity(packet);
	}
	
	// Implemented
	@Override
	public void handleSetCarriedItem(ClientboundSetCarriedItemPacket packet) {
		super.handleSetCarriedItem(packet);
	}
	
	// Implemented
	@Override
	public void handleMoveEntity(ClientboundMoveEntityPacket packet) {
		super.handleMoveEntity(packet);
	}
	
	// Implemented
	@Override
	public void handleRotateMob(ClientboundRotateHeadPacket packet) {
		super.handleRotateMob(packet);
	}
	
	// Implemented
	@Override
	public void handleRemoveEntity(ClientboundRemoveEntitiesPacket packet) {
		super.handleRemoveEntity(packet);
	}
	
	// Implemented
	@Override
	public void handleMovePlayer(ClientboundPlayerPositionPacket packet) {
		super.handleMovePlayer(packet);
	}
	
	// Implemented
	@Override
	public void handleChunkBlocksUpdate(ClientboundSectionBlocksUpdatePacket packet) {
		super.handleChunkBlocksUpdate(packet);
	}
	
	// Implemented
	@Override
	public void handleLevelChunk(ClientboundLevelChunkPacket packet) {
		super.handleLevelChunk(packet);
	}
	
	// Implemented
	@Override
	public void handleForgetLevelChunk(ClientboundForgetLevelChunkPacket packet) {
		super.handleForgetLevelChunk(packet);
	}
	
	// Implemented
	@Override
	public void handleBlockUpdate(ClientboundBlockUpdatePacket packet) {
		super.handleBlockUpdate(packet);
	}
	
	// Implemented
	@Override
	public void handleDisconnect(ClientboundDisconnectPacket packet) {
		super.handleDisconnect(packet);
	}
	
	// Implemented
	@Override
	public void handleTakeItemEntity(ClientboundTakeItemEntityPacket packet) {
		super.handleTakeItemEntity(packet);
	}
	
	// Implemented
	@Override
	public void handleChat(ClientboundChatPacket packet) {
		Constants.CHAT_LOGGER.info("{}: {}", packet.getSender(), packet.getMessage().getString());
	}
	
	// Implemented
	@Override
	public void handleAnimate(ClientboundAnimatePacket packet) {
		super.handleAnimate(packet);
	}
	
	// Implemented
	@Override
	public void handleAddMob(ClientboundAddMobPacket packet) {
		super.handleAddMob(packet);
	}
	
	// Implemented
	@Override
	public void handleSetTime(ClientboundSetTimePacket packet) {
		super.handleSetTime(packet);
	}
	
	// Implemented
	@Override
	public void handleSetSpawn(ClientboundSetDefaultSpawnPositionPacket packet) {
		super.handleSetSpawn(packet);
	}
	
	// Implemented
	@Override
	public void handleSetEntityPassengersPacket(ClientboundSetPassengersPacket packet) {
		super.handleSetEntityPassengersPacket(packet);
	}
	
	// Implemented
	@Override
	public void handleEntityLinkPacket(ClientboundSetEntityLinkPacket packet) {
		super.handleEntityLinkPacket(packet);
	}
	
	// Implemented
	@Override
	public void handleEntityEvent(ClientboundEntityEventPacket packet) {
		// Do nothing, as everything here is only rendering relevant
	}
	
	// Implemented
	@Override
	public void handleSetHealth(ClientboundSetHealthPacket packet) {
		super.handleSetHealth(packet);
	}
	
	// Implemented
	@Override
	public void handleSetExperience(ClientboundSetExperiencePacket packet) {
		super.handleSetExperience(packet);
	}
	
	// Implemented
	@Override
	public void handleRespawn(ClientboundRespawnPacket packet) {
		PacketUtils.ensureRunningOnSameThread(packet, this, headlessMinecraft);
		
		final HeadlessPlayer previousPlayer = headlessMinecraft.getConnectionManager().getPlayer();
		final ResourceKey<Level> dimension = packet.getDimension();
		
		((ClientPacketListenerAccessorMixin) this).setStarted(false);
		
		if (dimension != previousPlayer.level.dimension()) {
			final Scoreboard scoreBoard = ((ClientPacketListenerAccessorMixin) this).getLevel().getScoreboard();
			final ClientLevelData previousLevelData = ((ClientPacketListenerAccessorMixin) this).getLevelData();
			
			final ClientLevelData levelData = new ClientLevelData(previousLevelData.getDifficulty(), previousLevelData.isHardcore(), packet.isFlat());
			final HeadlessLevel level = new HeadlessLevel(this, levelData, dimension, packet.getDimensionType(), ((ClientPacketListenerAccessorMixin) this).getServerChunkRadius(), () -> InactiveProfiler.INSTANCE, packet.isDebug(), packet.getSeed());
			
			level.setScoreboard(scoreBoard);
			
			((ClientPacketListenerAccessorMixin) this).setLevelData(levelData);
			((ClientPacketListenerAccessorMixin) this).setLevel(level);
			
			connectionManager.setLevel(level);
		}
		
		final HeadlessLevel level = connectionManager.getLevel();
		
		level.removeAllPendingEntityRemovals();
		
		final HeadlessPlayer player = new HeadlessPlayer(this, level, previousPlayer.getStats(), previousPlayer.getRecipeBook(), previousPlayer.isShiftKeyDown(), previousPlayer.isSprinting());
		
		player.setId(previousPlayer.getId());
		player.getEntityData().assignValues(previousPlayer.getEntityData().getAll());
		if (packet.shouldKeepAllPlayerData()) {
			player.getAttributes().assignValues(previousPlayer.getAttributes());
		}
		player.resetPos();
		player.setServerBrand(previousPlayer.getServerBrand());
		
		connectionManager.setPlayer(player);
		level.addPlayer(previousPlayer.getId(), player);
		
		player.yRot = -180;
		// TODO player.input = new KeyboardInput(this.minecraft.options);
		player.setReducedDebugInfo(previousPlayer.isReducedDebugInfo());
		player.setShowDeathScreen(previousPlayer.shouldShowDeathScreen());
		
		final HeadlessMultiPlayerGameMode gameMode = connectionManager.getGameMode();
		
		gameMode.adjustPlayer(player);
		
		gameMode.setLocalMode(packet.getPlayerGameType());
		gameMode.setPreviousLocalMode(packet.getPreviousPlayerGameType());
	}
	
	// Implemented
	@Override
	public void handleExplosion(ClientboundExplodePacket packet) {
		super.handleExplosion(packet);
	}
	
	// Implemented // TODO handle containers
	@Override
	public void handleHorseScreenOpen(ClientboundHorseScreenOpenPacket packet) {
		PacketUtils.ensureRunningOnSameThread(packet, this, headlessMinecraft);
		
		final Entity entity = ((ClientPacketListenerAccessorMixin) this).getLevel().getEntity(packet.getEntityId());
		if (entity instanceof AbstractHorse) {
			final AbstractHorse horse = (AbstractHorse) entity;
			final HeadlessPlayer player = connectionManager.getPlayer();
			
			final SimpleContainer container = new SimpleContainer(packet.getSize());
			final HorseInventoryMenu menu = new HorseInventoryMenu(packet.getContainerId(), player.inventory, container, horse);
			player.containerMenu = menu;
		}
	}
	
	// Implemented // TODO handle containers
	@Override
	public void handleOpenScreen(ClientboundOpenScreenPacket packet) {
		PacketUtils.ensureRunningOnSameThread(packet, this, headlessMinecraft);
		
		final HeadlessPlayer player = connectionManager.getPlayer();
		
		final AbstractContainerMenu menu = packet.getType().create(packet.getContainerId(), player.inventory);
		player.containerMenu = menu;
	}
	
	// Implemented // TODO handle containers
	@Override
	public void handleContainerSetSlot(ClientboundContainerSetSlotPacket packet) {
		PacketUtils.ensureRunningOnSameThread(packet, this, headlessMinecraft);
		
		final HeadlessPlayer player = connectionManager.getPlayer();
		
		final int containerId = packet.getContainerId();
		final int slot = packet.getSlot();
		final ItemStack stack = packet.getItem();
		
		if (containerId == -1) {
			player.inventory.setCarried(stack);
		} else if (containerId == -2) {
			player.inventory.setItem(slot, stack);
		} else if (containerId == 0 && slot >= 36 && slot < 45) {
			if (!stack.isEmpty()) {
				final ItemStack currentStack = player.inventoryMenu.getSlot(slot).getItem();
				if (currentStack.isEmpty() || currentStack.getCount() < stack.getCount()) {
					stack.setPopTime(5);
				}
			}
			player.inventoryMenu.setItem(slot, stack);
		} else if (containerId == player.containerMenu.containerId) {
			player.containerMenu.setItem(slot, stack);
		}
	}
	
	// Implemented
	@Override
	public void handleContainerAck(ClientboundContainerAckPacket packet) {
		super.handleContainerAck(packet);
	}
	
	// Implemented
	@Override
	public void handleContainerContent(ClientboundContainerSetContentPacket packet) {
		super.handleContainerContent(packet);
	}
	
	// Implemented
	@Override
	public void handleOpenSignEditor(ClientboundOpenSignEditorPacket packet) {
		super.handleOpenSignEditor(packet);
	}
	
	// Implemented
	@Override
	public void handleBlockEntityData(ClientboundBlockEntityDataPacket packet) {
		super.handleBlockEntityData(packet);
	}
	
	// Implemented
	@Override
	public void handleContainerSetData(ClientboundContainerSetDataPacket packet) {
		super.handleContainerSetData(packet);
	}
	
	// Implemented
	@Override
	public void handleSetEquipment(ClientboundSetEquipmentPacket packet) {
		super.handleSetEquipment(packet);
	}
	
	// Implemented
	@Override
	public void handleContainerClose(ClientboundContainerClosePacket packet) {
		super.handleContainerClose(packet);
	}
	
	// Implemented
	@Override
	public void handleBlockEvent(ClientboundBlockEventPacket packet) {
		super.handleBlockEvent(packet);
	}
	
	// Implemented
	@Override
	public void handleBlockDestruction(ClientboundBlockDestructionPacket packet) {
		super.handleBlockDestruction(packet);
	}
	
	// Implemented
	@Override
	public void handleGameEvent(ClientboundGameEventPacket packet) {
		PacketUtils.ensureRunningOnSameThread(packet, this, headlessMinecraft);
		
		final ClientboundGameEventPacket.Type type = packet.getEvent();
		
		if (type == ClientboundGameEventPacket.WIN_GAME) {
			getConnection().send(new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.PERFORM_RESPAWN));
			return; // Return to not perform the super stuff (gui stuff)
		} else if (type == ClientboundGameEventPacket.DEMO_EVENT) {
			return; // Return to not perform the super stuff (gui stuff)
		}
		
		super.handleGameEvent(packet);
	}
	
	// Skipped
	@Override
	public void handleMapItemData(ClientboundMapItemDataPacket packet) {
	}
	
	// Implemented
	@Override
	public void handleLevelEvent(ClientboundLevelEventPacket packet) {
		super.handleLevelEvent(packet);
	}
	
	// Skipped (advancements is no implemented currently)
	@Override
	public void handleUpdateAdvancementsPacket(ClientboundUpdateAdvancementsPacket packet) {
	}
	
	// Skipped (advancements is no implemented currently)
	@Override
	public void handleSelectAdvancementsTab(ClientboundSelectAdvancementsTabPacket packet) {
	}
	
	// Implemented
	@Override
	public void handleCommands(ClientboundCommandsPacket packet) {
		super.handleCommands(packet);
	}
	
	// Implemented
	@Override
	public void handleStopSoundEvent(ClientboundStopSoundPacket packet) {
		// Do nothing, as we do not have sounds
	}
	
	// Skipped (suggestion provider is currently not implemented)
	@Override
	public void handleCommandSuggestions(ClientboundCommandSuggestionsPacket packet) {
	}
	
	// Implemented
	@Override
	public void handleUpdateRecipes(ClientboundUpdateRecipesPacket packet) {
		PacketUtils.ensureRunningOnSameThread(packet, this, headlessMinecraft);
		
		((ClientPacketListenerAccessorMixin) this).getRecipeManager().replaceRecipes(packet.getRecipes());
		// Skipped client recipe book
	}
	
	// Implemented
	@Override
	public void handleLookAt(ClientboundPlayerLookAtPacket packet) {
		super.handleLookAt(packet);
	}
	
	// Implemented
	@Override
	public void handleTagQueryPacket(ClientboundTagQueryPacket packet) {
		super.handleTagQueryPacket(packet);
	}
	
	// Implemented
	@Override
	public void handleAwardStats(ClientboundAwardStatsPacket packet) {
		super.handleAwardStats(packet);
	}
	
	// Implemented
	@Override
	public void handleAddOrRemoveRecipes(ClientboundRecipePacket packet) {
		super.handleAddOrRemoveRecipes(packet);
	}
	
	// Implemented
	@Override
	public void handleUpdateMobEffect(ClientboundUpdateMobEffectPacket packet) {
		super.handleUpdateMobEffect(packet);
	}
	
	@Override
	public void handleAddObjective(ClientboundSetObjectivePacket packet) {
		
	}
	
	@Override
	public void handleBlockBreakAck(ClientboundBlockBreakAckPacket packet) {
		
	}
	
	@Override
	public void handleBossUpdate(ClientboundBossEventPacket packet) {
		
	}
	
	@Override
	public void handleChangeDifficulty(ClientboundChangeDifficultyPacket packet) {
		
	}
	
	@Override
	public void handleCustomPayload(ClientboundCustomPayloadPacket packet) {
		
	}
	
	@Override
	public void handleCustomSoundEvent(ClientboundCustomSoundPacket packet) {
		
	}
	
	@Override
	public void handleItemCooldown(ClientboundCooldownPacket packet) {
		
	}
	
	@Override
	public void handleLightUpdatePacked(ClientboundLightUpdatePacket packet) {
		
	}
	
	@Override
	public void handleMerchantOffers(ClientboundMerchantOffersPacket packet) {
		
	}
	
	@Override
	public void handleMoveVehicle(ClientboundMoveVehiclePacket packet) {
		
	}
	
	@Override
	public void handleOpenBook(ClientboundOpenBookPacket packet) {
		
	}
	
	@Override
	public void handleParticleEvent(ClientboundLevelParticlesPacket packet) {
		
	}
	
	@Override
	public void handlePlaceRecipe(ClientboundPlaceGhostRecipePacket packet) {
		
	}
	
	@Override
	public void handlePlayerAbilities(ClientboundPlayerAbilitiesPacket packet) {
		
	}
	
	@Override
	public void handlePlayerCombat(ClientboundPlayerCombatPacket packet) {
		
	}
	
	@Override
	public void handlePlayerInfo(ClientboundPlayerInfoPacket packet) {
		
	}
	
	@Override
	public void handleRemoveMobEffect(ClientboundRemoveMobEffectPacket packet) {
		
	}
	
	@Override
	public void handleResourcePack(ClientboundResourcePackPacket packet) {
		
	}
	
	@Override
	public void handleSetBorder(ClientboundSetBorderPacket packet) {
		
	}
	
	@Override
	public void handleSetCamera(ClientboundSetCameraPacket packet) {
		
	}
	
	@Override
	public void handleSetChunkCacheCenter(ClientboundSetChunkCacheCenterPacket packet) {
		
	}
	
	@Override
	public void handleSetChunkCacheRadius(ClientboundSetChunkCacheRadiusPacket packet) {
		
	}
	
	@Override
	public void handleSetDisplayObjective(ClientboundSetDisplayObjectivePacket packet) {
		
	}
	
	@Override
	public void handleSetPlayerTeamPacket(ClientboundSetPlayerTeamPacket packet) {
		
	}
	
	@Override
	public void handleSetScore(ClientboundSetScorePacket packet) {
		
	}
	
	@Override
	public void handleSetTitles(ClientboundSetTitlesPacket packet) {
		
	}
	
	@Override
	public void handleSoundEntityEvent(ClientboundSoundEntityPacket packet) {
		
	}
	
	@Override
	public void handleSoundEvent(ClientboundSoundPacket packet) {
		
	}
	
	@Override
	public void handleTabListCustomisation(ClientboundTabListPacket packet) {
		
	}
	
	@Override
	public void handleUpdateAttributes(ClientboundUpdateAttributesPacket packet) {
		
	}
	
	@Override
	public void handleUpdateTags(ClientboundUpdateTagsPacket packet) {
		
	}
	
}
