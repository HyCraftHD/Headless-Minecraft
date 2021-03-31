package net.hycrafthd.headless_minecraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.world.level.chunk.LevelChunk;

@Mixin(ClientChunkCache.class)
public interface ClientChunkCacheMixin {
	
	@Invoker("isValidChunk")
	static boolean isValidChunk(LevelChunk levelChunk, int i, int j) {
		return false;
	}
}
