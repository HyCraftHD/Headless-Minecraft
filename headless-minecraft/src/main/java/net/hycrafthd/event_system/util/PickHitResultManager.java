package net.hycrafthd.event_system.util;

import net.hycrafthd.headless_minecraft.HeadlessMinecraft;
import net.hycrafthd.headless_minecraft.network.ConnectionManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class PickHitResultManager {
	
	private static Entity crosshairPickEntity;
	
	private static HitResult hitResult;
	
	public static void pick() {
		ConnectionManager minecraft = HeadlessMinecraft.getInstance().getConnectionManager();
		
		float f = 1.0F;
		
		Entity entity = HeadlessMinecraft.getInstance().getConnectionManager().getPlayer();
		
		if (entity != null) {
			if (minecraft.getLevel() != null) {
				crosshairPickEntity = null;
				double d = (double) minecraft.getGameMode().getPickRange();
				hitResult = entity.pick(d, f, false);
				Vec3 vec3 = entity.getEyePosition(f);
				boolean bl = false;
				double e = d;
				if (minecraft.getGameMode().hasFarPickRange()) {
					e = 6.0D;
					d = e;
				} else {
					if (d > 3.0D) {
						bl = true;
					}
				}
				
				e *= e;
				if (hitResult != null) {
					e = hitResult.getLocation().distanceToSqr(vec3);
				}
				
				Vec3 vec32 = entity.getViewVector(1.0F);
				Vec3 vec33 = vec3.add(vec32.x * d, vec32.y * d, vec32.z * d);
				float g = 1.0F;
				AABB aABB = entity.getBoundingBox().expandTowards(vec32.scale(d)).inflate(1.0D, 1.0D, 1.0D);
				EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(entity, vec3, vec33, aABB, (entityx) -> {
					return !entityx.isSpectator() && entityx.isPickable();
				}, e);
				if (entityHitResult != null) {
					Entity entity2 = entityHitResult.getEntity();
					Vec3 vec34 = entityHitResult.getLocation();
					double h = vec3.distanceToSqr(vec34);
					if (bl && h > 9.0D) {
						hitResult = BlockHitResult.miss(vec34, Direction.getNearest(vec32.x, vec32.y, vec32.z), new BlockPos(vec34));
					} else if (h < e || hitResult == null) {
						hitResult = entityHitResult;
						if (entity2 instanceof LivingEntity || entity2 instanceof ItemFrame) {
							crosshairPickEntity = entity2;
						}
					}
				}
			}
		}
	}
	
	public static HitResult getHitResult() {
		return hitResult;
	}
}
