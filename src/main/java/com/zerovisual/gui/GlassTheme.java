package com.zerovisual.gui;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** Glass color schemes for the menu and HUD cards. Colors are remapped on the fly by the Glass mixins. */
public final class GlassTheme {
	public static final String[] NAMES = {"Blue", "Dark red", "White", "Black", "Crimson flow"};
	public static final int[] SWATCH = {0xFF4DA6FF, 0xFFB3202C, 0xFFF2F2F2, 0xFF15151A, 0xFFD01E2E};

	// the original (blue) colors used by the base theme; everything equal to these gets replaced
	private static final int M_GLASS = 0x992A6BD6;
	private static final int M_GLASS_H = 0xB33C7FE8;
	private static final int M_EDGE = 0x66CFE6FF;
	private static final int M_EXP = 0x661A3F80;
	private static final int M_TRACK = 0x99183A78;
	private static final int M_TINT = 0x000A3A7A;

	// glass, glass hover, edge, expanded, track, accent, tint
	private static final int[][] PAL = {
		{0x992A6BD6, 0xB33C7FE8, 0x66CFE6FF, 0x661A3F80, 0x99183A78, 0xFF4DA6FF, 0x000A3A7A},
		{0x99A01E28, 0xB3C03040, 0x66FFB4B8, 0x66551018, 0x99601820, 0xFFE5484D, 0x003A0A10},
		{0x6EAAB4CC, 0x8CC0CAE0, 0x99FFFFFF, 0x55788299, 0x99505A70, 0xFFFFFFFF, 0x00303846},
		{0xB30A0A0E, 0xCC1A1A22, 0x55FFFFFF, 0x66000000, 0x99262630, 0xFFE8E8EE, 0x00000000},
		{0xA0B3202C, 0xC0D03A48, 0x77FF5A66, 0x66651018, 0x99701822, 0xFFFF4757, 0x00480A12}
	};
	private static final int[] CRIMSON_B = {0xB30C0508, 0xCC1E0A10, 0x44FF2A3A, 0x66000000, 0x99200A10, 0xFFC4202F, 0x00060103};

	public static final int CRIMSON = 4;
	public static int selected = 4;

	private static final float[][] cur = new float[7][4];
	private static final int[] packed = new int[7];
	private static long last = System.nanoTime();
	private static final Path FILE = Paths.get("config", "zerovisual", "glass_theme.txt");

	static {
		try {
			if (Files.exists(FILE)) {
				int v = Integer.parseInt(Files.readString(FILE).trim());
				if (v >= 0 && v < PAL.length) selected = v;
			}
		} catch (Exception ignored) {
		}
		for (int i = 0; i < 7; i++) {
			set(cur[i], target(i, System.nanoTime()));
			packed[i] = pack(cur[i]);
		}
	}

	private GlassTheme() {
	}

	private static void set(float[] c, int argb) {
		c[0] = argb >>> 24;
		c[1] = (argb >> 16) & 0xFF;
		c[2] = (argb >> 8) & 0xFF;
		c[3] = argb & 0xFF;
	}

	private static int pack(float[] c) {
		return clamp(c[0]) << 24 | clamp(c[1]) << 16 | clamp(c[2]) << 8 | clamp(c[3]);
	}

	private static int clamp(float v) {
		return v < 0.0F ? 0 : (v > 255.0F ? 255 : Math.round(v));
	}

	public static void select(int index) {
		if (index >= 0 && index < PAL.length && index != selected) {
			selected = index;
			try {
				Files.createDirectories(FILE.getParent());
				Files.writeString(FILE, Integer.toString(index));
			} catch (Exception ignored) {
			}
		}
	}

	/** Smoothly moves the current colors toward the selected scheme. */
	public static void tick() {
		long now = System.nanoTime();
		if (now - last < 8_000_000L) return;
		float dt = Math.min(0.1F, (now - last) / 1.0E9F);
		last = now;
		float k = 1.0F - (float) Math.exp(-dt * 9.0F);
		for (int i = 0; i < 7; i++) {
			float[] t = new float[4];
			set(t, target(i, now));
			for (int j = 0; j < 4; j++) {
				cur[i][j] += (t[j] - cur[i][j]) * k;
			}
			packed[i] = pack(cur[i]);
		}
	}

	private static int target(int i, long now) {
		if (selected != CRIMSON) return PAL[selected][i];
		double wave = 0.5 + 0.5 * Math.sin(now / 1.0E9 * 0.9 + i * 0.85);
		float[] a = new float[4];
		float[] b = new float[4];
		set(a, PAL[CRIMSON][i]);
		set(b, CRIMSON_B[i]);
		float[] r = new float[4];
		for (int j = 0; j < 4; j++) {
			r[j] = (float) (a[j] + (b[j] - a[j]) * wave);
		}
		return pack(r);
	}

	public static int map(int color) {
		if (color == M_GLASS) return packed[0];
		if (color == M_GLASS_H) return packed[1];
		if (color == M_EDGE) return packed[2];
		if (color == M_EXP) return packed[3];
		if (color == M_TRACK) return packed[4];
		return color;
	}

	public static int mapTint(int color) {
		return (color & 0xFFFFFF) == (M_TINT & 0xFFFFFF) ? (color & 0xFF000000) | (packed[6] & 0xFFFFFF) : color;
	}

	public static int accent() {
		return packed[5] | 0xFF000000;
	}
}
