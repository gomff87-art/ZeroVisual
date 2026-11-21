package com.zerovisual.module.impl.hud;

import net.minecraft.client.gui.GuiGraphics;

public class FpsGraph extends InfoPanel {
	private final float[] samples = new float[60];
	private int count;
	private long last;

	public FpsGraph() {
		super("FpsGraph", "Graph of your real FPS over the last seconds", 2);
	}

	@Override
	protected String title() {
		return "FPS graph";
	}

	private void sample() {
		long now = System.currentTimeMillis();
		if (now - last >= 100L) {
			last = now;
			System.arraycopy(samples, 1, samples, 0, samples.length - 1);
			samples[samples.length - 1] = mc.getFps();
			if (count < samples.length) count++;
		}
	}

	@Override
	protected String[] lines() {
		sample();
		float sum = 0.0F;
		for (int i = samples.length - count; i < samples.length; i++) sum += samples[i];
		int avg = count == 0 ? 0 : Math.round(sum / count);
		return new String[]{"now " + mc.getFps() + "  avg " + avg};
	}

	@Override
	protected float extraHeight() {
		return 26.0F;
	}

	@Override
	protected void drawExtra(GuiGraphics ctx, float x, float y, float w, float h) {
		float max = 1.0F;
		for (float v : samples) max = Math.max(max, v);
		graph(ctx, x, y, w, h, samples, samples.length, max);
	}
}
