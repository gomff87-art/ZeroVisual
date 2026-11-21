package com.zerovisual.module.impl.hud;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;

public class CpuHud extends InfoPanel {
	private String cached = "n/a";
	private long last;

	public CpuHud() {
		super("CpuHud", "CPU load of the game process and thread count", 5);
	}

	@Override
	protected String title() {
		return "CPU";
	}

	@Override
	protected String[] lines() {
		long now = System.currentTimeMillis();
		if (now - last >= 1000L) {
			last = now;
			String load = "n/a";
			String threads = "n/a";
			try {
				if (ManagementFactory.getOperatingSystemMXBean() instanceof OperatingSystemMXBean os) {
					double v = os.getProcessCpuLoad();
					if (v >= 0.0) load = Math.round(v * 100.0) + "%";
				}
			} catch (Throwable ignored) {
			}
			try {
				threads = String.valueOf(ManagementFactory.getThreadMXBean().getThreadCount());
			} catch (Throwable ignored) {
			}
			cached = "game " + load + "  threads " + threads;
		}
		return new String[]{cached};
	}
}
