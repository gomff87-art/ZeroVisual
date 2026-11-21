package com.zerovisual.mixin;

import com.zerovisual.ZeroVisualClient;
import com.zerovisual.gui.GlassTheme;
import com.zerovisual.gui.Theme;
import com.zerovisual.module.impl.hud.HudStyle;
import com.zerovisual.render.ColorUtil;
import com.zerovisual.render.Render2D;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Theme.class)
public abstract class GlassCardMixin {
	@Inject(method = "card(Lnet/minecraft/client/gui/GuiGraphics;FFFFF)V", at = @At("HEAD"), cancellable = true)
	private static void zerovisual$glassCard(GuiGraphics ctx, float x, float y, float w, float h, float opacity, CallbackInfo ci) {
		GlassTheme.tick();
		HudStyle style = ZeroVisualClient.get().modules().get(HudStyle.class);
		float a = Math.min(1.0F, opacity * style.opacity.getFloat() / 0.88F);
		float radius = style.radius.getFloat();
		if (a > 0.01F) {
			if (style.shadow.get()) {
				Render2D.shadow(ctx, x, y + 1.0F, w, h, radius, 6.0F, ColorUtil.fade(0x70000000, a));
			}
			Render2D.rect(ctx, x, y, w, h, radius, ColorUtil.fade(GlassTheme.map(0x992A6BD6), a));
		}
		Render2D.outline(ctx, x, y, w, h, radius, 1.0F, ColorUtil.fade(GlassTheme.map(0x66CFE6FF), Math.max(a, 0.0F)));
		ci.cancel();
	}
}
