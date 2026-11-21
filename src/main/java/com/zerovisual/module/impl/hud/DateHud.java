package com.zerovisual.module.impl.hud;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateHud extends InfoPanel {
	public DateHud() {
		super("DateHud", "Today's date", 10);
	}

	@Override
	protected String title() {
		return "Date";
	}

	@Override
	protected String[] lines() {
		return new String[]{LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))};
	}
}
