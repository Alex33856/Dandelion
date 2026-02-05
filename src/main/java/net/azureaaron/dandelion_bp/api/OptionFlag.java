package net.azureaaron.dandelion_bp.api;

import java.util.function.Consumer;

import net.minecraft.client.Minecraft;

@FunctionalInterface
public interface OptionFlag extends Consumer<Minecraft> {
	/**
	 * When invoked, this flag will reload the game assets.
	 */
	OptionFlag ASSET_RELOAD = Minecraft::delayTextureReload;
}
