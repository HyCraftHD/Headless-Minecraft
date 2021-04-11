package net.hycrafthd.headless_minecraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;

@Mixin(ClientPacketListener.class)
public interface ClientPacketListenerAccessorMixin {
	
	@Accessor("advancements")
	public void setAdvancements(ClientAdvancements advancements);
	
	@Accessor("suggestionsProvider")
	public void setSuggestionsProvider(ClientSuggestionProvider suggestionsProvider);
	
}
