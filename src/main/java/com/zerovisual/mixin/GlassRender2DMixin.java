package com.zerovisual.mixin;

import com.zerovisual.gui.GlassTheme;
import com.zerovisual.render.Render2D;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Render2D.class)
public abstract class GlassRender2DMixin {
	@ModifyVariable(method = "rect", at = @At("HEAD"), argsOnly = true)
	private static int zerovisual$glassRect(int color) {
		return GlassTheme.map(color);
	}

	@ModifyVariable(method = "outline", at = @At("HEAD"), argsOnly = true)
	private static int zerovisual$glassOutline(int color) {
		return GlassTheme.map(color);
	}

	@ModifyVariable(method = "gradientH", at = @At("HEAD"), argsOnly = true, ordinal = 0)
	private static int zerovisual$glassGradHL(int color) {
		return GlassTheme.map(color);
	}

	@ModifyVariable(method = "gradientH", at = @At("HEAD"), argsOnly = true, ordinal = 1)
	private static int zerovisual$glassGradHR(int color) {
		return GlassTheme.map(color);
	}

	@ModifyVariable(method = "gradientV", at = @At("HEAD"), argsOnly = true, ordinal = 0)
	private static int zerovisual$glassGradVT(int color) {
		return GlassTheme.map(color);
	}

	@ModifyVariable(method = "gradientV", at = @At("HEAD"), argsOnly = true, ordinal = 1)
	private static int zerovisual$glassGradVB(int color) {
		return GlassTheme.map(color);
	}
}
