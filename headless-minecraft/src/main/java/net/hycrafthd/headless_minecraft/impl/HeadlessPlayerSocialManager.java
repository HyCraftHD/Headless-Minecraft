package net.hycrafthd.headless_minecraft.impl;

import com.mojang.authlib.minecraft.SocialInteractionsService;

import net.minecraft.client.gui.screens.social.PlayerSocialManager;

public class HeadlessPlayerSocialManager extends PlayerSocialManager {
	
	public HeadlessPlayerSocialManager(SocialInteractionsService socialInteractionService) {
		super(null, socialInteractionService);
	}
}
