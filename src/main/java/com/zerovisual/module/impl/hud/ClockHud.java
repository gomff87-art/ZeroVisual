package com.zerovisual.module.impl.hud;

import com.zerovisual.setting.BooleanSetting;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClockHud extends InfoPanel {
	public final BooleanSetting seconds = add(new BooleanSetting("Seconds", true));
	public final BooleanSetting hour24 = add(new BooleanSetting("24-hour", true));

	public ClockHud() {
		super("ClockHud", "Current real time", 0);
	}

	@Override
	protected String title() {
		return "Clock";
	}

	@Override
	protected String[] lines() {
		String pattern = (hour24.get() ? "HH" : "hh") + ":mm" + (seconds.get() ? ":ss" : "") + (hour24.get() ? "" : " a");
		return new String[]{LocalTime.now().format(DateTimeFormatter.ofPattern(pattern))};
	}
}
