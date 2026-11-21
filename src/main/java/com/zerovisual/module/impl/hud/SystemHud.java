package com.zerovisual.module.impl.hud;

public class SystemHud extends InfoPanel {
	public SystemHud() {
		super("SystemHud", "Java version, CPU cores and operating system", 9);
	}

	@Override
	protected String title() {
		return "System";
	}

	@Override
	protected String[] lines() {
		return new String[]{
			"Java " + System.getProperty("java.version", "?"),
			Runtime.getRuntime().availableProcessors() + " cores",
			System.getProperty("os.name", "?")
		};
	}
}
