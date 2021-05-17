package net.hycrafthd.event_system.util;

import java.util.Optional;

import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.hycrafthd.headless_minecraft.network.ConnectionManager;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class BlockPlaceUtil {
	
	public static Optional<InteractionResult> placeBlock(InteractionHand hand) {
		
		ConnectionManager manager = HeadlessMinecraft.getInstance().getConnectionManager();
		
		HitResult result = PickUtil.getHitResult();
		
		if (result instanceof BlockHitResult) {
			BlockHitResult blockHitResult = (BlockHitResult) result;
			
			InteractionResult interactionResult = manager.getGameMode().useItemOn(manager.getPlayer(), manager.getLevel(), hand, blockHitResult);
			if (interactionResult.consumesAction()) {
				if (interactionResult.shouldSwing()) {
					manager.getPlayer().swing(hand);
				}
			}
			return Optional.of(interactionResult);
		}
		return Optional.empty();
	}
}
