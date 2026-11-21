package com.zerovisual.module.impl.hud;

public class SessionTimer extends InfoPanel {
	private static final long START = System.currentTimeMillis();

	public SessionTimer() {
		super("SessionTimer", "Time since the game was started", 1);
	}

	@Override
	protected String title() {
		return "Session";
	}

	@Override
	protected String[] lines() {
		long s = (System.currentTimeMillis() - START) / 1000L;
		return new String[]{String.format("%d:%02d:%02d", s / 3600L, s / 60L % 60L, s % 60L)};
	}
}
