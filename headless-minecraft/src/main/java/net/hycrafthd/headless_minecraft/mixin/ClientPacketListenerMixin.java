package net.hycrafthd.headless_minecraft.mixin;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.mojang.authlib.GameProfile;

import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.hycrafthd.headless_minecraft.impl.HeadlessRemotePlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.social.PlayerSocialManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.particle.ItemPickupParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.crafting.Recipe;

@Mixin(ClientPacketListener.class)
abstract class ClientPacketListenerMixin {
	
	@Redirect(method = "*", at = @At(value = "INVOKE", opcode = Opcodes.INVOKESTATIC, target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/util/thread/BlockableEventLoop;)V"))
	private <T extends PacketListener> void replaceEnsureRunningOnSameThread(Packet<T> packet, T listener, BlockableEventLoop<?> unused) {
		PacketUtils.ensureRunningOnSameThread(packet, listener, HeadlessMinecraft.getInstance());
	}
	
	@ModifyConstant(method = "handleAddEntity", constant = @Constant(classValue = AbstractMinecart.class, ordinal = 0))
	public boolean removeSoundManagerCallForMinecartEntity(Object entity, Class<?> constant) {
		return false;
	}
	
	@Redirect(method = { "handleAddPlayer", "handleAddMob", "handleSetTime", "handleSetSpawn", "handleExplosion", "handleBlockEntityData", "handleBlockEvent", "handleBlockDestruction", "handleLevelEvent" }, at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/Minecraft;level:Lnet/minecraft/client/multiplayer/ClientLevel;"))
	private ClientLevel replaceGetLevel(Minecraft minecraft) {
		return HeadlessMinecraft.getInstance().getConnectionManager().getLevel();
	}
	
	@Redirect(method = "handleAddPlayer", at = @At(value = "NEW", target = "Lnet/minecraft/client/player/RemotePlayer;"))
	private RemotePlayer replaceConstructRemovePlayer(ClientLevel unused, GameProfile profile) {
		return new HeadlessRemotePlayer(HeadlessMinecraft.getInstance().getConnectionManager().getLevel(), profile);
	}
	
	@Redirect(method = { "handleSetCarriedItem", "handleMovePlayer", "handleTakeItemEntity", "handleSetEntityPassengersPacket", "handleSetHealth", "handleSetExperience", "handleExplosion", "handleContainerAck", "handleContainerContent", "handleOpenSignEditor", "handleContainerSetData", "handleContainerClose", "handleGameEvent", "handleLookAt", "handleAwardStats", "handleAddOrRemoveRecipes" }, at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/Minecraft;player:Lnet/minecraft/client/player/LocalPlayer;"))
	private LocalPlayer replaceGetPlayer(Minecraft minecraft) {
		return HeadlessMinecraft.getInstance().getConnectionManager().getPlayer();
	}
	
	@Redirect(method = "handleMovePlayer", at = @At(value = "INVOKE", opcode = Opcodes.INVOKEVIRTUAL, target = "Lnet/minecraft/client/Minecraft;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V"))
	private void replaceSetScreen(Minecraft minecraft, Screen screen) {
	}
	
	@Redirect(method = "handleTakeItemEntity", at = @At(value = "INVOKE", opcode = Opcodes.INVOKEVIRTUAL, target = "Lnet/minecraft/client/Minecraft;getEntityRenderDispatcher()Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;"))
	private EntityRenderDispatcher replaceGetEntityRenderDispatcher(Minecraft minecraft) {
		return null;
	}
	
	@Redirect(method = "handleTakeItemEntity", at = @At(value = "INVOKE", opcode = Opcodes.INVOKEVIRTUAL, target = "Lnet/minecraft/client/Minecraft;renderBuffers()Lnet/minecraft/client/renderer/RenderBuffers;"))
	private RenderBuffers replaceRenderBuffers(Minecraft minecraft) {
		return null;
	}
	
	@Redirect(method = "handleTakeItemEntity", at = @At(value = "NEW", target = "Lnet/minecraft/client/particle/ItemPickupParticle;"))
	private ItemPickupParticle replaceConstructItemPickupParticle(EntityRenderDispatcher dispatcher, RenderBuffers buffers, ClientLevel level, Entity entity, Entity entity2) {
		return null;
	}
	
	@Redirect(method = "handleTakeItemEntity", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/Minecraft;particleEngine:Lnet/minecraft/client/particle/ParticleEngine;"))
	private ParticleEngine replaceGetParticleEngine(Minecraft minecraft) {
		return null;
	}
	
	@Redirect(method = "handleTakeItemEntity", at = @At(value = "INVOKE", opcode = Opcodes.INVOKEVIRTUAL, target = "Lnet/minecraft/client/particle/ParticleEngine;add(Lnet/minecraft/client/particle/Particle;)V"))
	private void replaceAddToRenderEngine(ParticleEngine engine, Particle particle) {
	}
	
	@ModifyConstant(method = "handleAnimate", constant = @Constant(intValue = 4, ordinal = 0))
	public int removeAnimation4(int value) {
		return 555555; // Must not be below 5
	}
	
	@ModifyConstant(method = "handleAnimate", constant = @Constant(intValue = 5, ordinal = 0))
	public int removeAnimation5(int value) {
		return 555555; // Must not be below 5
	}
	
	@ModifyConstant(method = "handleAddMob", constant = @Constant(classValue = Bee.class, ordinal = 0))
	public boolean removeSoundManagerCallForBee(Object entity, Class<?> constant) {
		return false;
	}
	
	@ModifyVariable(method = "handleSetEntityPassengersPacket", ordinal = 1, at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;minecraft:Lnet/minecraft/client/Minecraft;", shift = Shift.BY, by = -3, ordinal = 2))
	public Entity removeOverlayMountMessage(Entity entity) {
		return null;
	}
	
	@Redirect(method = { "handleBlockEntityData", "handleAwardStats", "handleAddOrRemoveRecipes" }, at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/Minecraft;screen:Lnet/minecraft/client/gui/screens/Screen;"))
	private Screen replaceGetScreen(Minecraft minecraft) {
		return null;
	}
	
	@Redirect(method = "handleGameEvent", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/client/Minecraft;gameMode:Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;"))
	private MultiPlayerGameMode replaceGetGameMode(Minecraft minecraft) {
		return HeadlessMinecraft.getInstance().getConnectionManager().getGameMode();
	}
	
	@Redirect(method = "lambda$handleAddOrRemoveRecipes$3", at = @At(value = "INVOKE", opcode = Opcodes.INVOKEVIRTUAL, target = "Lnet/minecraft/client/Minecraft;getToasts()Lnet/minecraft/client/gui/components/toasts/ToastComponent;"))
	private ToastComponent replaceGetToasts(Minecraft minecraft) {
		return null;
	}
	
	@Redirect(method = "lambda$handleAddOrRemoveRecipes$3", at = @At(value = "INVOKE", opcode = Opcodes.INVOKESTATIC, target = "Lnet/minecraft/client/gui/components/toasts/RecipeToast;addOrUpdate(Lnet/minecraft/client/gui/components/toasts/ToastComponent;Lnet/minecraft/world/item/crafting/Recipe;)V"))
	private void replaceAddOrUpdate(ToastComponent component, Recipe<?> recipe) {
	}
	
	@Inject(method = "handleUpdateTags", cancellable = true, at = @At(value = "INVOKE", opcode = Opcodes.INVOKEVIRTUAL, target = "Lnet/minecraft/client/Minecraft;getSearchTree(Lnet/minecraft/client/searchtree/SearchRegistry$Key;)Lnet/minecraft/client/searchtree/MutableSearchTree;"))
	private void removeGetSearchTree(CallbackInfo callback) {
		callback.cancel();
	}
	
	@Redirect(method = "handlePlayerInfo", at = @At(value = "INVOKE", opcode = Opcodes.INVOKEVIRTUAL, target = "Lnet/minecraft/client/Minecraft;getPlayerSocialManager()Lnet/minecraft/client/gui/screens/social/PlayerSocialManager;"))
	private PlayerSocialManager replaceGetSocialManager(Minecraft minecraft) {
		return HeadlessMinecraft.getInstance().getSocialManager();
	}
	
}
