package net.hycrafthd.headless_minecraft.network;

import com.mojang.authlib.GameProfile;

import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
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
import net.minecraft.network.protocol.game.ServerboundKeepAlivePacket;

public class ClientListener implements ClientGamePacketListener {
	
	private final Connection connection;
	private final HeadlessMinecraft headlessMinecraft;
	private final GameProfile gameProfile;
	
	public ClientListener(Connection connection, HeadlessMinecraft headlessMinecraft, GameProfile gameProfile) {
		this.connection = connection;
		this.headlessMinecraft = headlessMinecraft;
		this.gameProfile = gameProfile;
	}
	
	@Override
	public void handleKeepAlive(ClientboundKeepAlivePacket packet) {
		connection.send(new ServerboundKeepAlivePacket(packet.getId()));
	}
	
	@Override
	public void onDisconnect(Component packet) {
	}
	
	@Override
	public Connection getConnection() {
		return connection;
	}
	
	@Override
	public void handleAddEntity(ClientboundAddEntityPacket packet) {
		
	}
	
	@Override
	public void handleAddExperienceOrb(ClientboundAddExperienceOrbPacket packet) {
		
	}
	
	@Override
	public void handleAddMob(ClientboundAddMobPacket packet) {
		
	}
	
	@Override
	public void handleAddObjective(ClientboundSetObjectivePacket packet) {
		
	}
	
	@Override
	public void handleAddOrRemoveRecipes(ClientboundRecipePacket packet) {
		
	}
	
	@Override
	public void handleAddPainting(ClientboundAddPaintingPacket packet) {
		
	}
	
	@Override
	public void handleAddPlayer(ClientboundAddPlayerPacket packet) {
		
	}
	
	@Override
	public void handleAnimate(ClientboundAnimatePacket packet) {
		
	}
	
	@Override
	public void handleAwardStats(ClientboundAwardStatsPacket packet) {
		
	}
	
	@Override
	public void handleBlockBreakAck(ClientboundBlockBreakAckPacket packet) {
		
	}
	
	@Override
	public void handleBlockDestruction(ClientboundBlockDestructionPacket packet) {
		
	}
	
	@Override
	public void handleBlockEntityData(ClientboundBlockEntityDataPacket packet) {
		
	}
	
	@Override
	public void handleBlockEvent(ClientboundBlockEventPacket packet) {
		
	}
	
	@Override
	public void handleBlockUpdate(ClientboundBlockUpdatePacket packet) {
		
	}
	
	@Override
	public void handleBossUpdate(ClientboundBossEventPacket packet) {
		
	}
	
	@Override
	public void handleChangeDifficulty(ClientboundChangeDifficultyPacket packet) {
		
	}
	
	@Override
	public void handleChat(ClientboundChatPacket packet) {
		
	}
	
	@Override
	public void handleChunkBlocksUpdate(ClientboundSectionBlocksUpdatePacket packet) {
		
	}
	
	@Override
	public void handleCommandSuggestions(ClientboundCommandSuggestionsPacket packet) {
		
	}
	
	@Override
	public void handleCommands(ClientboundCommandsPacket packet) {
		
	}
	
	@Override
	public void handleContainerAck(ClientboundContainerAckPacket packet) {
		
	}
	
	@Override
	public void handleContainerClose(ClientboundContainerClosePacket packet) {
		
	}
	
	@Override
	public void handleContainerContent(ClientboundContainerSetContentPacket packet) {
		
	}
	
	@Override
	public void handleContainerSetData(ClientboundContainerSetDataPacket packet) {
		
	}
	
	@Override
	public void handleContainerSetSlot(ClientboundContainerSetSlotPacket packet) {
		
	}
	
	@Override
	public void handleCustomPayload(ClientboundCustomPayloadPacket packet) {
		
	}
	
	@Override
	public void handleCustomSoundEvent(ClientboundCustomSoundPacket packet) {
		
	}
	
	@Override
	public void handleDisconnect(ClientboundDisconnectPacket packet) {
		
	}
	
	@Override
	public void handleEntityEvent(ClientboundEntityEventPacket packet) {
		
	}
	
	@Override
	public void handleEntityLinkPacket(ClientboundSetEntityLinkPacket packet) {
		
	}
	
	@Override
	public void handleExplosion(ClientboundExplodePacket packet) {
		
	}
	
	@Override
	public void handleForgetLevelChunk(ClientboundForgetLevelChunkPacket packet) {
		
	}
	
	@Override
	public void handleGameEvent(ClientboundGameEventPacket packet) {
		
	}
	
	@Override
	public void handleHorseScreenOpen(ClientboundHorseScreenOpenPacket packet) {
		
	}
	
	@Override
	public void handleItemCooldown(ClientboundCooldownPacket packet) {
		
	}
	
	@Override
	public void handleLevelChunk(ClientboundLevelChunkPacket packet) {
		
	}
	
	@Override
	public void handleLevelEvent(ClientboundLevelEventPacket packet) {
		
	}
	
	@Override
	public void handleLightUpdatePacked(ClientboundLightUpdatePacket packet) {
		
	}
	
	@Override
	public void handleLogin(ClientboundLoginPacket packet) {
		
	}
	
	@Override
	public void handleLookAt(ClientboundPlayerLookAtPacket packet) {
		
	}
	
	@Override
	public void handleMapItemData(ClientboundMapItemDataPacket packet) {
		
	}
	
	@Override
	public void handleMerchantOffers(ClientboundMerchantOffersPacket packet) {
		
	}
	
	@Override
	public void handleMoveEntity(ClientboundMoveEntityPacket packet) {
		
	}
	
	@Override
	public void handleMovePlayer(ClientboundPlayerPositionPacket packet) {
		
	}
	
	@Override
	public void handleMoveVehicle(ClientboundMoveVehiclePacket packet) {
		
	}
	
	@Override
	public void handleOpenBook(ClientboundOpenBookPacket packet) {
		
	}
	
	@Override
	public void handleOpenScreen(ClientboundOpenScreenPacket packet) {
		
	}
	
	@Override
	public void handleOpenSignEditor(ClientboundOpenSignEditorPacket packet) {
		
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
	public void handleRemoveEntity(ClientboundRemoveEntitiesPacket packet) {
		
	}
	
	@Override
	public void handleRemoveMobEffect(ClientboundRemoveMobEffectPacket packet) {
		
	}
	
	@Override
	public void handleResourcePack(ClientboundResourcePackPacket packet) {
		
	}
	
	@Override
	public void handleRespawn(ClientboundRespawnPacket packet) {
		
	}
	
	@Override
	public void handleRotateMob(ClientboundRotateHeadPacket packet) {
		
	}
	
	@Override
	public void handleSelectAdvancementsTab(ClientboundSelectAdvancementsTabPacket packet) {
		
	}
	
	@Override
	public void handleSetBorder(ClientboundSetBorderPacket packet) {
		
	}
	
	@Override
	public void handleSetCamera(ClientboundSetCameraPacket packet) {
		
	}
	
	@Override
	public void handleSetCarriedItem(ClientboundSetCarriedItemPacket packet) {
		
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
	public void handleSetEntityData(ClientboundSetEntityDataPacket packet) {
		
	}
	
	@Override
	public void handleSetEntityMotion(ClientboundSetEntityMotionPacket packet) {
		
	}
	
	@Override
	public void handleSetEntityPassengersPacket(ClientboundSetPassengersPacket packet) {
		
	}
	
	@Override
	public void handleSetEquipment(ClientboundSetEquipmentPacket packet) {
		
	}
	
	@Override
	public void handleSetExperience(ClientboundSetExperiencePacket packet) {
		
	}
	
	@Override
	public void handleSetHealth(ClientboundSetHealthPacket packet) {
		
	}
	
	@Override
	public void handleSetPlayerTeamPacket(ClientboundSetPlayerTeamPacket packet) {
		
	}
	
	@Override
	public void handleSetScore(ClientboundSetScorePacket packet) {
		
	}
	
	@Override
	public void handleSetSpawn(ClientboundSetDefaultSpawnPositionPacket packet) {
		
	}
	
	@Override
	public void handleSetTime(ClientboundSetTimePacket packet) {
		
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
	public void handleStopSoundEvent(ClientboundStopSoundPacket packet) {
		
	}
	
	@Override
	public void handleTabListCustomisation(ClientboundTabListPacket packet) {
		
	}
	
	@Override
	public void handleTagQueryPacket(ClientboundTagQueryPacket packet) {
		
	}
	
	@Override
	public void handleTakeItemEntity(ClientboundTakeItemEntityPacket packet) {
		
	}
	
	@Override
	public void handleTeleportEntity(ClientboundTeleportEntityPacket packet) {
		
	}
	
	@Override
	public void handleUpdateAdvancementsPacket(ClientboundUpdateAdvancementsPacket packet) {
		
	}
	
	@Override
	public void handleUpdateAttributes(ClientboundUpdateAttributesPacket packet) {
		
	}
	
	@Override
	public void handleUpdateMobEffect(ClientboundUpdateMobEffectPacket packet) {
		
	}
	
	@Override
	public void handleUpdateRecipes(ClientboundUpdateRecipesPacket packet) {
		
	}
	
	@Override
	public void handleUpdateTags(ClientboundUpdateTagsPacket packet) {
		
	}
	
}
