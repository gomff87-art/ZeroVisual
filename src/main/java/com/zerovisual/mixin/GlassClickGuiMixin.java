package com.zerovisual.mixin;

import com.zerovisual.gui.ClickGuiScreen;
import com.zerovisual.gui.GlassPicker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClickGuiScreen.class)
public abstract class GlassClickGuiMixin extends Screen {
	protected GlassClickGuiMixin(Component title) {
		super(title);
	}

	@Inject(method = "render", at = @At("RETURN"))
	private void zerovisual$glassPicker(GuiGraphics ctx, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		GlassPicker.render(ctx, mouseX, mouseY, this.width, this.height);
	}

	@Inject(method = "init", at = @At("RETURN"))
	private void zerovisual$glassReset(CallbackInfo ci) {
		GlassPicker.reset();
	}

	@Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
	private void zerovisual$glassClick(MouseButtonEvent event, boolean doubleClick, CallbackInfoReturnable<Boolean> cir) {
		if (GlassPicker.click(event.x(), event.y(), event.button())) {
			cir.setReturnValue(true);
		}
	}
}
