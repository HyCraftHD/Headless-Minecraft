package net.hycrafthd.event_system.util;

import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.hycrafthd.headless_minecraft.network.ConnectionManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class LeftClickManager {
	
	private static BlockHitResult current;
	
	private static boolean breaking = false;
	
	private static int missTime;
	
	private static Runnable blockCallback;
	
	static void breakBlock(Runnable callback) {
		
		if (breaking) {
			return;
		}
		
		blockCallback = callback;
		
		ConnectionManager manager = HeadlessMinecraft.getInstance().getConnectionManager();
		
		if (missTime <= 0) {
			if (PickHitResultManager.getHitResult() == null) {
				if (manager.getGameMode().hasMissTime()) {
					missTime = 10;
				}
			} else if (!manager.getPlayer().isHandsBusy()) {
				switch (PickHitResultManager.getHitResult().getType()) {
				case BLOCK:
					BlockHitResult blockHitResult = (BlockHitResult) PickHitResultManager.getHitResult();
					BlockPos blockPos = blockHitResult.getBlockPos();
					if (!manager.getLevel().getBlockState(blockPos).isAir()) {
						manager.getGameMode().startDestroyBlock(blockPos, blockHitResult.getDirection());
						manager.getPlayer().swing(InteractionHand.MAIN_HAND);
						current = blockHitResult;
						breaking = true;
						break;
					}
				case MISS:
					if (manager.getGameMode().hasMissTime()) {
						missTime = 10;
					}
					manager.getPlayer().resetAttackStrengthTicker();
				default:
					break;
				}
			}
		}
	}
	
	static void attackLookingAt() {
		ConnectionManager manager = HeadlessMinecraft.getInstance().getConnectionManager();
		
		if (missTime <= 0) {
			if (PickHitResultManager.getHitResult() == null) {
				if (manager.getGameMode().hasMissTime()) {
					missTime = 10;
				}
			} else if (!manager.getPlayer().isHandsBusy()) {
				if (PickHitResultManager.getHitResult() instanceof EntityHitResult) {
					manager.getGameMode().attack(manager.getPlayer(), ((EntityHitResult) PickHitResultManager.getHitResult()).getEntity());
					manager.getPlayer().swing(InteractionHand.MAIN_HAND);
				}
			}
		}
	}
	
	static boolean isBreaking() {
		return breaking;
	}
	
	public static void tick() {
		if (missTime > 0) {
			--missTime;
		}
		
		if (breaking) {
			if (!HeadlessMinecraft.getInstance().getConnectionManager().getPlayer().level.getBlockState(current.getBlockPos()).isAir()) {
				HeadlessMinecraft.getInstance().getConnectionManager().getGameMode().continueDestroyBlock(current.getBlockPos(), current.getDirection());
				HeadlessMinecraft.getInstance().getConnectionManager().getPlayer().swing(InteractionHand.MAIN_HAND);
			} else {
				HeadlessMinecraft.getInstance().getConnectionManager().getGameMode().stopDestroyBlock();
				breaking = false;
				blockCallback.run();
			}
		}
	}
}
