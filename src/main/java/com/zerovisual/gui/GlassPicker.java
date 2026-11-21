package com.zerovisual.gui;

import com.zerovisual.render.ColorUtil;
import com.zerovisual.render.Render2D;
import net.minecraft.client.gui.GuiGraphics;

/** The "Glass" color panel on the right side of the menu. */
public final class GlassPicker {
	private static final float W = 30.0F;
	private static final float ROW = 25.0F;
	private static final float TOP_PAD = 22.0F;
	private static final float BOTTOM_PAD = 8.0F;
	private static final int COUNT = GlassTheme.NAMES.length;

	private static float open = 0.0F;
	private static float px;
	private static float py;
	private static float ph;
	private static final float[] hover = new float[COUNT];
	private static final float[] sel = new float[COUNT];
	private static long last = System.nanoTime();

	private GlassPicker() {
	}

	public static void reset() {
		open = 0.0F;
		for (int i = 0; i < COUNT; i++) {
			hover[i] = 0.0F;
			sel[i] = i == GlassTheme.selected ? 1.0F : 0.0F;
		}
		last = System.nanoTime();
	}

	private static float cy(int i) {
		return py + TOP_PAD + i * ROW + ROW / 2.0F - 3.0F;
	}

	public static void render(GuiGraphics ctx, int mouseX, int mouseY, int width, int height) {
		GlassTheme.tick();
		long now = System.nanoTime();
		float dt = Math.min(0.1F, (now - last) / 1.0E9F);
		last = now;
		float k = 1.0F - (float) Math.exp(-dt * 12.0F);
		open += (1.0F - open) * (1.0F - (float) Math.exp(-dt * 7.0F));
		float e = 1.0F - (1.0F - open) * (1.0F - open) * (1.0F - open);

		ph = TOP_PAD + COUNT * ROW + BOTTOM_PAD;
		px = width - W - 8.0F + (1.0F - e) * 44.0F;
		py = height / 2.0F - ph / 2.0F;

		Render2D.setAlpha(e);
		Render2D.shadow(ctx, px, py + 2.0F, W, ph, 9.0F, 10.0F, 0x70000000);
		Render2D.rect(ctx, px, py, W, ph, 9.0F, 0x992A6BD6);
		Render2D.outline(ctx, px, py, W, ph, 9.0F, 1.0F, 0x66CFE6FF);
		Render2D.centeredText(ctx, "Glass", px + W / 2.0F, py + 7.0F, 0xFFD8DCEA);

		float cx = px + W / 2.0F;
		for (int i = 0; i < COUNT; i++) {
			float cyy = cy(i);
			boolean over = Math.abs(mouseX - cx) <= 12.0F && Math.abs(mouseY - cyy) <= 12.0F && e > 0.6F;
			hover[i] += ((over ? 1.0F : 0.0F) - hover[i]) * k;
			sel[i] += ((i == GlassTheme.selected ? 1.0F : 0.0F) - sel[i]) * k;
			float r = 8.0F * (1.0F + 0.16F * hover[i] + 0.08F * sel[i]);
			Render2D.circle(ctx, cx, cyy, r + 1.0F, 0x66CFE6FF);
			Render2D.circle(ctx, cx, cyy, r, GlassTheme.SWATCH[i]);
			if (sel[i] > 0.02F) {
				float ring = r + 3.5F;
				Render2D.outline(ctx, cx - ring, cyy - ring, ring * 2.0F, ring * 2.0F, ring, 1.5F, ColorUtil.withAlpha(0xFFFFFF, sel[i]));
			}
			if (hover[i] > 0.05F) {
				String name = GlassTheme.NAMES[i];
				float tx = px - 8.0F - Render2D.textWidth(name);
				Render2D.rect(ctx, tx - 5.0F, cyy - 7.0F, Render2D.textWidth(name) + 10.0F, 14.0F, 5.0F, ColorUtil.withAlpha(0x101018, 0.75F * hover[i]));
				Render2D.text(ctx, name, tx, cyy - 4.0F, ColorUtil.withAlpha(0xFFFFFF, hover[i]));
			}
		}
		Render2D.setAlpha(1.0F);
	}

	public static boolean click(double mx, double my, int button) {
		if (open < 0.6F) return false;
		if (mx < px || mx > px + W || my < py || my > py + ph) return false;
		if (button == 0) {
			float cx = px + W / 2.0F;
			for (int i = 0; i < COUNT; i++) {
				if (Math.abs(mx - cx) <= 12.0 && Math.abs(my - cy(i)) <= 12.0) {
					GlassTheme.select(i);
					break;
				}
			}
		}
		return true;
	}
}
