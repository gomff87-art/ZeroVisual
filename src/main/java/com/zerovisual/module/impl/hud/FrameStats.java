package com.zerovisual.module.impl.hud;

import java.util.Arrays;

public class FrameStats extends InfoPanel {
	private final float[] win = new float[100];
	private int n;
	private int idx;
	private long last;

	public FrameStats() {
		super("FrameStats", "Average, minimum and 1% low FPS over the last 5 seconds", 3);
	}

	@Override
	protected String title() {
		return "Frame stats";
	}

	@Override
	protected String[] lines() {
		long now = System.currentTimeMillis();
		if (now - last >= 50L) {
			last = now;
			win[idx] = mc.getFps();
			idx = (idx + 1) % win.length;
			if (n < win.length) n++;
		}
		if (n == 0) return new String[]{"..."};
		float[] sorted = Arrays.copyOf(win, n);
		Arrays.sort(sorted);
		float sum = 0.0F;
		for (float v : sorted) sum += v;
		int low = Math.max(1, n / 100);
		float lowSum = 0.0F;
		for (int i = 0; i < low; i++) lowSum += sorted[i];
		return new String[]{"avg " + Math.round(sum / n), "min " + Math.round(sorted[0]), "1% low " + Math.round(lowSum / low)};
	}
}
