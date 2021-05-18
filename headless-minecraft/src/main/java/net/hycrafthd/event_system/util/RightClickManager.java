package net.hycrafthd.event_system.util;

import java.util.Optional;

import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.hycrafthd.headless_minecraft.network.ConnectionManager;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class RightClickManager {
	
	private static int rightClickDelay;
	
	static Optional<InteractionResult> singleRightClick(InteractionHand hand) {
		ConnectionManager manager = HeadlessMinecraft.getInstance().getConnectionManager();
		
		if (!manager.getGameMode().isDestroying()) {
			rightClickDelay = 4;
			if (!manager.getPlayer().isHandsBusy()) {
				if (PickHitResultManager.getHitResult() == null) {
					// TODO err
				}
				
				InteractionHand[] var1 = InteractionHand.values();
				int var2 = var1.length;
				
				for (int var3 = 0; var3 < var2; ++var3) {
					InteractionHand interactionHand = var1[var3];
					ItemStack itemStack = manager.getPlayer().getItemInHand(interactionHand);
					if (PickHitResultManager.getHitResult() != null) {
						switch (PickHitResultManager.getHitResult().getType()) {
						case ENTITY:
							EntityHitResult entityHitResult = (EntityHitResult) PickHitResultManager.getHitResult();
							Entity entity = entityHitResult.getEntity();
							InteractionResult interactionResult = manager.getGameMode().interactAt(manager.getPlayer(), entity, entityHitResult, interactionHand);
							if (!interactionResult.consumesAction()) {
								interactionResult = manager.getGameMode().interact(manager.getPlayer(), entity, interactionHand);
							}
							
							if (interactionResult.consumesAction()) {
								if (interactionResult.shouldSwing()) {
									manager.getPlayer().swing(interactionHand);
								}
								return Optional.of(interactionResult);
							}
							break;
						case BLOCK:
							BlockHitResult blockHitResult = (BlockHitResult) PickHitResultManager.getHitResult();
							InteractionResult interactionResult2 = manager.getGameMode().useItemOn(manager.getPlayer(), manager.getLevel(), interactionHand, blockHitResult);
							if (interactionResult2.consumesAction()) {
								if (interactionResult2.shouldSwing()) {
									manager.getPlayer().swing(interactionHand);
								}
								return Optional.of(interactionResult2);
							}
							
							if (interactionResult2 == InteractionResult.FAIL) {
								return Optional.of(interactionResult2);
							}
						default:
							break;
						}
					}
					
					if (!itemStack.isEmpty()) {
						InteractionResult interactionResult3 = manager.getGameMode().useItem(manager.getPlayer(), manager.getLevel(), interactionHand);
						if (interactionResult3.consumesAction()) {
							if (interactionResult3.shouldSwing()) {
								manager.getPlayer().swing(interactionHand);
							}
							return Optional.of(interactionResult3);
						}
					}
				}
			}
		}
		
		return Optional.empty();
		
		// HitResult result = PickHitResultManager.getHitResult();
		//
		// if (result == null) {
		//
		// } else if (result instanceof BlockHitResult) {
		// BlockHitResult blockHitResult = (BlockHitResult) result;
		//
		// InteractionResult interactionResult = manager.getGameMode().useItemOn(manager.getPlayer(), manager.getLevel(), hand,
		// blockHitResult);
		// if (interactionResult.consumesAction()) {
		// if (interactionResult.shouldSwing()) {
		// manager.getPlayer().swing(hand);
		// }
		// }
		// return Optional.of(interactionResult);
		// } else if (result instanceof EntityHitResult) {
		//
		// }
		//
		
	}
	
	public static void tick() {
		if (rightClickDelay > 0) {
			--rightClickDelay;
		}
	}
}
