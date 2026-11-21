package com.zerovisual.mixin;

import com.zerovisual.gui.GlassTheme;
import com.zerovisual.gui.Theme;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Theme.class)
public abstract class GlassThemeMixin {
	@Inject(method = "accent", at = @At("RETURN"), cancellable = true)
	private static void zerovisual$glassAccent(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(GlassTheme.accent());
	}
}
