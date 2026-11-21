package com.zerovisual.module.impl.hud;

import com.zerovisual.render.ColorUtil;
import com.zerovisual.render.Render2D;
import net.minecraft.client.gui.GuiGraphics;

public class SelfHealth extends InfoPanel {
	public SelfHealth() {
		super("SelfHealth", "Your own health as a number and a bar", 8);
	}

	@Override
	protected String title() {
		return "Health";
	}

	@Override
	protected String[] lines() {
		if (mc.player == null) return new String[]{"-"};
		return new String[]{String.format("%.1f / %.0f", mc.player.getHealth(), mc.player.getMaxHealth())};
	}

	@Override
	protected float extraHeight() {
		return 5.0F;
	}

	@Override
	protected void drawExtra(GuiGraphics ctx, float x, float y, float w, float h) {
		float f = 0.0F;
		if (mc.player != null) {
			f = mc.player.getHealth() / Math.max(1.0F, mc.player.getMaxHealth());
		}
		f = Math.max(0.02F, Math.min(1.0F, f));
		int color = ColorUtil.lerp(0xFFFF5C6C, 0xFF4ADE80, f);
		Render2D.rect(ctx, x, y, w, h, 2.0F, 0xFF24242E);
		Render2D.gradientH(ctx, x, y, w * f, h, 2.0F, color, color);
	}
}
