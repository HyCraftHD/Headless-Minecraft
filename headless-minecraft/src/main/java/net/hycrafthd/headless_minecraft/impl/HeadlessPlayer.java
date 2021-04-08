package net.hycrafthd.headless_minecraft.impl;

import java.util.UUID;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import net.hycrafthd.headless_minecraft.Constants;
import net.hycrafthd.headless_minecraft.network.HeadlessPacketListener;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.StatsCounter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.phys.Vec3;

public class HeadlessPlayer extends LocalPlayer {
	
	private final HeadlessPacketListener packetListener;
	
	public HeadlessPlayer(HeadlessPacketListener packetListener, HeadlessLevel level, StatsCounter stats, ClientRecipeBook recipeBook, boolean wasShiftKeyDown, boolean wasSprinting) {
		super(null, level, packetListener, stats, recipeBook, wasShiftKeyDown, wasSprinting);
		this.packetListener = packetListener;
	}
	
	@Override
	public void clientSideCloseContainer() {
	}
	
	@Override
	public void displayClientMessage(Component component, boolean hotbar) {
		final Marker marker;
		if (hotbar) {
			marker = MarkerManager.getMarker("HOTBAR");
		} else {
			marker = null;
		}
		Constants.CHAT_LOGGER.info(marker, component.getString());
	}
	
	@Override
	public void sendMessage(Component component, UUID uuid) {
	}
	
	@Override
	public void openTextEdit(SignBlockEntity signBlockEntity) {
	}
	
	@Override
	public void openMinecartCommandBlock(BaseCommandBlock baseCommandBlock) {
	}
	
	@Override
	public void openCommandBlock(CommandBlockEntity commandBlockEntity) {
	}
	
	@Override
	public void openStructureBlock(StructureBlockEntity structureBlockEntity) {
	}
	
	@Override
	public void openJigsawBlock(JigsawBlockEntity jigsawBlockEntity) {
	}
	
	@Override
	public void openItemGui(ItemStack itemStack, InteractionHand interactionHand) {
	}
	
	@Override
	public void crit(Entity entity) {
	}
	
	@Override
	public void magicCrit(Entity entity) {
	}
	
	@Override
	protected boolean isControlledCamera() {
		return false;
	}
	
	@Override
	protected boolean updateIsUnderwater() {
		return this.isUnderWater();
	}
	
	@Override
	public Vec3 getRopeHoldPosition(float f) {
		return new Vec3(0, 0, 0);
	}
}
