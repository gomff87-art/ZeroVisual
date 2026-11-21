package com.zerovisual.module.impl.hud;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.PlayerInfo;

public class PingGraph extends InfoPanel {
	private final float[] samples = new float[40];
	private int count;
	private long last;
	private int current;

	public PingGraph() {
		super("PingGraph", "Graph of your ping to the server", 7);
	}

	@Override
	protected String title() {
		return "Ping graph";
	}

	private int ping() {
		if (mc.player == null || mc.getConnection() == null) return 0;
		PlayerInfo info = mc.getConnection().getPlayerInfo(mc.player.getUUID());
		return info == null ? 0 : info.getLatency();
	}

	@Override
	protected String[] lines() {
		long now = System.currentTimeMillis();
		if (now - last >= 500L) {
			last = now;
			current = ping();
			System.arraycopy(samples, 1, samples, 0, samples.length - 1);
			samples[samples.length - 1] = current;
			if (count < samples.length) count++;
		}
		float sum = 0.0F;
		for (int i = samples.length - count; i < samples.length; i++) sum += samples[i];
		return new String[]{current + " ms  avg " + (count == 0 ? 0 : Math.round(sum / count))};
	}

	@Override
	protected float extraHeight() {
		return 22.0F;
	}

	@Override
	protected void drawExtra(GuiGraphics ctx, float x, float y, float w, float h) {
		float max = 50.0F;
		for (float v : samples) max = Math.max(max, v);
		graph(ctx, x, y, w, h, samples, samples.length, max);
	}
}
