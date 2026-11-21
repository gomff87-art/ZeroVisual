package com.zerovisual.module.impl.hud;

import com.zerovisual.gui.Theme;
import com.zerovisual.render.Render2D;
import net.minecraft.client.gui.GuiGraphics;

public class MemoryHud extends InfoPanel {
	public MemoryHud() {
		super("MemoryHud", "Java memory used by the game", 4);
	}

	@Override
	protected String title() {
		return "Memory";
	}

	private static float fraction() {
		Runtime rt = Runtime.getRuntime();
		return (float) (rt.totalMemory() - rt.freeMemory()) / (float) rt.maxMemory();
	}

	@Override
	protected String[] lines() {
		Runtime rt = Runtime.getRuntime();
		long used = (rt.totalMemory() - rt.freeMemory()) / 1048576L;
		return new String[]{used + " / " + rt.maxMemory() / 1048576L + " MB"};
	}

	@Override
	protected float extraHeight() {
		return 5.0F;
	}

	@Override
	protected void drawExtra(GuiGraphics ctx, float x, float y, float w, float h) {
		Render2D.rect(ctx, x, y, w, h, 2.0F, 0xFF24242E);
		float f = Math.max(0.02F, Math.min(1.0F, fraction()));
		Render2D.gradientH(ctx, x, y, w * f, h, 2.0F, Theme.accent(), Theme.accentLight());
	}
}
