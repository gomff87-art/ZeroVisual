package com.zerovisual.module.impl.hud;

import com.zerovisual.gui.Theme;
import com.zerovisual.module.HudModule;
import com.zerovisual.render.Render2D;
import net.minecraft.client.gui.GuiGraphics;

public abstract class InfoPanel extends HudModule {
	private final int slot;

	protected InfoPanel(String name, String description, int slot) {
		super(name, description);
		this.slot = slot;
	}

	@Override
	public boolean isDraggable() {
		return true;
	}

	@Override
	protected float[] defaultPosition(float screenW, float screenH, float w, float h) {
		return new float[]{6.0F, 40.0F + slot * 34.0F};
	}

	protected abstract String title();

	protected abstract String[] lines();

	protected float extraHeight() {
		return 0.0F;
	}

	protected void drawExtra(GuiGraphics ctx, float x, float y, float w, float h) {
	}

	@Override
	public void render(GuiGraphics ctx, float tickDelta) {
		String[] lines;
		try {
			lines = lines();
		} catch (Throwable t) {
			lines = new String[]{"n/a"};
		}
		String title = title();
		float w = Render2D.textWidth(title) + 14.0F;
		for (String line : lines) {
			w = Math.max(w, Render2D.textWidth(line) + 14.0F);
		}
		w = Math.max(w, 70.0F);
		float extra = extraHeight();
		float h = 18.0F + lines.length * 11.0F + extra + (extra > 0.0F ? 4.0F : 1.0F);
		float[] pos = place(ctx, w, h);
		float left = pos[0];
		float top = pos[1];
		Theme.card(ctx, left, top, w, h);
		Render2D.text(ctx, title, left + 7.0F, top + 5.0F, Theme.accent());
		for (int i = 0; i < lines.length; i++) {
			Render2D.text(ctx, lines[i], left + 7.0F, top + 17.0F + i * 11.0F, Theme.TEXT);
		}
		if (extra > 0.0F) {
			try {
				drawExtra(ctx, left + 7.0F, top + 18.0F + lines.length * 11.0F, w - 14.0F, extra);
			} catch (Throwable ignored) {
			}
		}
	}

	protected static void graph(GuiGraphics ctx, float x, float y, float w, float h, float[] data, int count, float max) {
		Render2D.rect(ctx, x, y, w, h, 2.0F, 0xFF24242E);
		if (count <= 0 || max <= 0.0F) return;
		float step = w / data.length;
		int accent = Theme.accent();
		for (int i = 0; i < count; i++) {
			float v = Math.max(0.0F, Math.min(1.0F, data[i] / max));
			float bar = Math.max(1.0F, (h - 2.0F) * v);
			Render2D.rect(ctx, x + i * step, y + h - 1.0F - bar, Math.max(1.0F, step - 0.6F), bar, 0.5F, accent);
		}
	}
}
