package com.zerovisual.module.impl.hud;

public class VerticalSpeed extends InfoPanel {
	private double ly;
	private long lt;
	private float speed;
	private boolean init;

	public VerticalSpeed() {
		super("VerticalSpeed", "Vertical speed in blocks per second (falling is negative)", 13);
	}

	@Override
	protected String title() {
		return "Vertical";
	}

	@Override
	protected String[] lines() {
		if (mc.player == null) return new String[]{"0.0 b/s"};
		double y = mc.player.getY();
		long now = System.currentTimeMillis();
		if (init && now > lt) {
			float v = (float) ((y - ly) / ((now - lt) / 1000.0));
			if (Math.abs(v) > 200.0F) v = 0.0F;
			speed += (v - speed) * 0.2F;
		}
		init = true;
		ly = y;
		lt = now;
		return new String[]{String.format("%.1f b/s", speed)};
	}
}
