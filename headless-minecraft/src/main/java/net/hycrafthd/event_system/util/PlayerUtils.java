package net.hycrafthd.event_system.util;

import java.util.Optional;

import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.hycrafthd.headless_minecraft.impl.HeadlessPlayer;
import net.hycrafthd.headless_minecraft.mixin.accessor.LocalPlayerAccessorMixin;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;

public class PlayerUtils {
	
	public void setAutoJump(boolean value) {
		((LocalPlayerAccessorMixin) getPlayer()).setAutoJumpEnabled(value);
	}
	
	public static void breakBlockFacing(Runnable callable) {
		LeftClickManager.breakBlock(callable);
	}
	
	public static void attackEntity() {
		LeftClickManager.attackLookingAt();
	}
	
	public static Optional<InteractionResult> rightClick(InteractionHand hand) {
		return RightClickManager.singleRightClick(hand);
	}
	
	public static void selectHotbarSlot(int slot) {
		if (slot > -1 && slot < 9)
			getPlayer().inventory.selected = slot;
	}
	
	public static void setPitchYaw(float pitch, float yaw) {
		HeadlessPlayer player = getPlayer();
		player.xRot = pitch;
		player.yRot = yaw;
	}
	
	public static boolean dropSelected(boolean stack) {
		return getPlayer().drop(stack);
	}
	
	public static void swapHand() {
		if (!getPlayer().isSpectator()) {
			HeadlessMinecraft.getInstance().getConnectionManager().getConnection().send((Packet) (new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ZERO, Direction.DOWN)));
		}
	}
	
	public static ItemStack getSelectedItem(InteractionHand hand) {
		return getPlayer().getItemInHand(hand);
	}
	
	// public static void getInventoryContents() {
	// return getPlayer().inventory.get
	// }
	
	public static void chat(String message) {
		getPlayer().chat(message);
	}
	
	public static HeadlessPlayer getPlayer() {
		HeadlessPlayer player = HeadlessMinecraft.getInstance().getConnectionManager().getPlayer();
		if (player == null) {
			throw new IllegalStateException("Player not connected!");
		}
		return player;
	}
}
