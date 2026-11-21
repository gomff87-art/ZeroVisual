package com.zerovisual.module.impl.hud;

public class ChunkHud extends InfoPanel {
	public ChunkHud() {
		super("ChunkHud", "Chunk coordinates and position inside the chunk", 11);
	}

	@Override
	protected String title() {
		return "Chunk";
	}

	@Override
	protected String[] lines() {
		if (mc.player == null) return new String[]{"-"};
		int x = (int) Math.floor(mc.player.getX());
		int z = (int) Math.floor(mc.player.getZ());
		return new String[]{"chunk " + (x >> 4) + " " + (z >> 4), "in chunk " + (x & 15) + " " + (z & 15)};
	}
}
