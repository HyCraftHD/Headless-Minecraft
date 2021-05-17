package net.hycrafthd.event_system.util;

import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.hycrafthd.headless_minecraft.network.ConnectionManager;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class BlockBreakUtil {
	
	private static BlockHitResult current;
	
	private static boolean breaking = false;
	
	private static Runnable blockCallback;
	
	public static void breakBlock(Runnable callback) {
		
		if (breaking) {
			return;
		}
		
		blockCallback = callback;
		
		ConnectionManager manager = HeadlessMinecraft.getInstance().getConnectionManager();
		
		HitResult result = PickUtil.getHitResult();
		
		if (result instanceof BlockHitResult) {
			BlockHitResult blockHitResult = (BlockHitResult) result;
			
			manager.getGameMode().startDestroyBlock(blockHitResult.getBlockPos(), blockHitResult.getDirection());
			current = blockHitResult;
			breaking = true;
		}
	}
	
	public boolean isBreaking() {
		return breaking;
	}
	
	public static void tick() {
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
