package net.hycrafthd.headless_minecraft.mixin;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

@Mixin(ClientPacketListener.class)
public interface ClientPacketListenerAccessorMixin {
	
	@Accessor("advancements")
	public void setAdvancements(ClientAdvancements advancements);
	
	@Accessor("suggestionsProvider")
	public void setSuggestionsProvider(ClientSuggestionProvider suggestionsProvider);
	
	@Accessor("levels")
	public void setLevels(Set<ResourceKey<Level>> levels);
	
	@Accessor("registryAccess")
	public void setRegistryAccess(RegistryAccess registryAccess);
	
}
