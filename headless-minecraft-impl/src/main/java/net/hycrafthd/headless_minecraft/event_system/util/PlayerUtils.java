package net.hycrafthd.headless_minecraft.event_system.util;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;

public class PlayerUtils {
	
	public static final int MAX_TELEPORT_RANGE = 20;
	
	private static final Queue<String> messages = new LinkedList<String>();
	
	public static Player getPlayer(UUID uuid) {
		return Minecraft.getInstance().level.getPlayerByUUID(uuid);
	}
	
	
//	public void setAutoJump(boolean value) {
//		((LocalPlayerAccessorMixin) getPlayer()).setAutoJumpEnabled(value);
//	}
//	
//	public static void breakBlockFacing(Runnable callable) {
//		LeftClickManager.breakBlock(callable);
//	}
//	
//	public static void attackEntity() {
//		LeftClickManager.attackLookingAt();
//	}
//	
//	public static Optional<InteractionResult> rightClick(InteractionHand hand) {
//		return RightClickManager.singleRightClick(hand);
//	}
//	
//	public static void selectHotbarSlot(int slot) {
//		if (slot > -1 && slot < 9)
//			getPlayer().inventory.selected = slot;
//	}
//	
//	public static void setPitchYaw(float pitch, float yaw) {
//		HeadlessPlayer player = getPlayer();
//		player.xRot = pitch;
//		player.yRot = yaw;
//	}
//	
//	public static boolean dropSelected(boolean stack) {
//		return getPlayer().drop(stack);
//	}
//	
//	public static void swapHand() {
//		if (!getPlayer().isSpectator()) {
//			HeadlessMinecraft.getInstance().getConnectionManager().getConnection().send((Packet) (new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ZERO, Direction.DOWN)));
//		}
//	}
//	
//	public static ItemStack getSelectedItem(InteractionHand hand) {
//		return getPlayer().getItemInHand(hand);
//	}
//	
//	public static boolean teleportTo(double x, double z) {
//		if (Math.sqrt(x * x + z * z) < MAX_TELEPORT_RANGE) {
//			teleport(getPlayer().getX() + x, getPlayer().getZ() + z, getPlayer().isOnGround());
//			return true;
//		} else {
//			return false;
//		}
//	}
//	
//	public static boolean alignOnBlock(double x, double z) {
//		if (x >= 0 && z >= 0 && x <= 1 && z <= 1) {
//			teleport(Math.floor(getPlayer().getX()) + x, Math.floor(getPlayer().getZ()) + z, getPlayer().isOnGround());
//			return true;
//		} else {
//			return false;
//		}
//	}
//	
//	public static Optional<BlockState> getBlockLookingAt() {
//		if (PickHitResultManager.getHitResult().getType() == Type.BLOCK) {
//			BlockHitResult bhr = (BlockHitResult) PickHitResultManager.getHitResult();
//			return Optional.of(getPlayer().level.getBlockState(bhr.getBlockPos()));
//		} else {
//			return Optional.empty();
//		}
//	}
//	
//	public static void slowChat(String message) {
//		messages.add(message);
//	}
//	
//	// public static void getInventoryContents() {
//	// return getPlayer().inventory.get
//	// }
//	
//	public static void chat(String message) {
//		getPlayer().chat(message);
//	}
//	
//	public static HeadlessPlayer getPlayer() {
//		HeadlessPlayer player = HeadlessMinecraft.getInstance().getConnectionManager().getPlayer();
//		if (player == null) {
//			throw new IllegalStateException("Player not connected!");
//		}
//		return player;
//	}
//	
//	public static Queue<String> getMessagesToSend() {
//		return messages;
//	}
//	
//	private static void teleport(double x, double z, boolean onGround) {
//		HeadlessMinecraft.getInstance().getConnectionManager().getConnection().send(new ServerboundMovePlayerPacket.Pos(x, getPlayer().getY(), z, onGround));
//		getPlayer().setPos(x, getPlayer().getY(), z);
//	}
}
