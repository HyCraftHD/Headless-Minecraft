package net.hycrafthd.headless_minecraft.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.Input;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.client.tutorial.TutorialSteps;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

public class FakeTutorial extends Tutorial {
	
	public static final FakeTutorial INSTANCE = new FakeTutorial();
	
	public FakeTutorial() {
		super(null);
	}
	
	@Override
	public GameType getGameMode() {
		throw new AssertionError("Method should not be called");
	}
	
	@Override
	public Minecraft getMinecraft() {
		throw new AssertionError("Method should not be called");
	}
	
	@Override
	public void addTimedToast(TutorialToast name, int name2) {
	}
	
	@Override
	public void onDestroyBlock(ClientLevel name, BlockPos name2, BlockState name3, float name4) {
	}
	
	@Override
	public void onGetItem(ItemStack name) {
	}
	
	@Override
	public void onInput(Input name) {
	}
	
	@Override
	public void onLookAt(ClientLevel name, HitResult name2) {
	}
	
	@Override
	public void onMouse(double name, double name2) {
	}
	
	@Override
	public void onOpenInventory() {
	}
	
	@Override
	public void removeTimedToast(TutorialToast name) {
	}
	
	@Override
	public void setStep(TutorialSteps name) {
	}
	
	@Override
	public void start() {
	}
	
	@Override
	public void stop() {
	}
	
	@Override
	public void tick() {
	}
	
}
