package com.zerovisual.module.impl.hud;

public class SpeedHud extends InfoPanel {
	private double lx;
	private double lz;
	private long lt;
	private float speed;
	private boolean init;

	public SpeedHud() {
		super("SpeedHud", "Your movement speed in blocks per second", 6);
	}

	@Override
	protected String title() {
		return "Speed";
	}

	@Override
	protected String[] lines() {
		if (mc.player == null) return new String[]{"0.0 b/s"};
		double x = mc.player.getX();
		double z = mc.player.getZ();
		long now = System.currentTimeMillis();
		if (init && now > lt) {
			double dist = Math.sqrt((x - lx) * (x - lx) + (z - lz) * (z - lz));
			float v = (float) (dist / ((now - lt) / 1000.0));
			if (v > 200.0F) v = 0.0F;
			speed += (v - speed) * 0.15F;
		}
		init = true;
		lx = x;
		lz = z;
		lt = now;
		return new String[]{String.format("%.1f b/s", speed)};
	}
}
