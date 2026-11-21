package com.zerovisual.module.impl.hud;

public class NetherCoords extends InfoPanel {
	public NetherCoords() {
		super("NetherCoords", "Overworld and Nether coordinates (x8 / x1/8 conversion)", 12);
	}

	@Override
	protected String title() {
		return "Overworld / Nether";
	}

	@Override
	protected String[] lines() {
		if (mc.player == null) return new String[]{"-"};
		double x = mc.player.getX();
		double z = mc.player.getZ();
		return new String[]{"ow " + Math.round(x * 8.0) + " " + Math.round(z * 8.0), "nether " + Math.round(x / 8.0) + " " + Math.round(z / 8.0)};
	}
}
