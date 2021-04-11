package net.hycrafthd.headless_minecraft.mixin.accessor;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientLevel.ClientLevelData;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

@Mixin(ClientPacketListener.class)
public interface ClientPacketListenerAccessorMixin {
	
	// Getter
	@Accessor("serverChunkRadius")
	int getServerChunkRadius();
	
	@Accessor("levelData")
	ClientLevelData getLevelData();
	
	@Accessor("level")
	ClientLevel getLevel();
	
	// Setter
	
	@Accessor("advancements")
	void setAdvancements(ClientAdvancements advancements);
	
	@Accessor("suggestionsProvider")
	void setSuggestionsProvider(ClientSuggestionProvider suggestionsProvider);
	
	@Accessor("levels")
	void setLevels(Set<ResourceKey<Level>> levels);
	
	@Accessor("registryAccess")
	void setRegistryAccess(RegistryAccess registryAccess);
	
	@Accessor("serverChunkRadius")
	void setServerChunkRadius(int serverChunkRadius);
	
	@Accessor("levelData")
	void setLevelData(ClientLevelData levelData);
	
	@Accessor("level")
	void setLevel(ClientLevel level);
	
}
