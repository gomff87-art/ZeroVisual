package com.zerovisual.module.impl.hud;

import com.zerovisual.module.Module;
import com.zerovisual.setting.NumberSetting;

public class VisualFps extends Module {
	public final NumberSetting min = add(new NumberSetting("Min FPS", 2000.0, 500.0, 9000.0, 100.0));
	public final NumberSetting max = add(new NumberSetting("Max FPS", 5000.0, 500.0, 9000.0, 100.0));
	private float current;
	private float target;
	private long lastPick;

	public VisualFps() {
		super("VisualFps", "Shows a made-up 2000-5000 FPS number in the Watermark (display only)", Category.HUD);
	}

	public int fake() {
		double lo = Math.min(min.get(), max.get());
		double hi = Math.max(min.get(), max.get());
		long now = System.currentTimeMillis();
		if (now - lastPick > 350L) {
			lastPick = now;
			target = (float) (lo + Math.random() * (hi - lo));
			if (current < lo || current > hi) current = target;
		}
		current += (target - current) * 0.06F;
		return Math.round(current);
	}
}
