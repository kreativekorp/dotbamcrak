package com.kreative.dotbamcrak.board;

import com.kreative.dotbamcrak.tileset.Tile;

public class BoardEvent {
	public static final int MIN_ID = 1;
	public static final int TILE_REMOVED = 1;
	public static final int TILE_ADDED = 2;
	public static final int TILE_SELECTED = 3;
	public static final int TILES_DESELECTED = 4;
	public static final int TILES_SHUFFLED = 5;
	public static final int MAX_ID = 5;
	
	private Board board;
	private int id;
	private int x;
	private int y;
	private int z;
	private Tile tile;
	private boolean selected;
	
	public BoardEvent(Board board, int id, int x, int y, int z, Tile tile, boolean selected) {
		this.board = board;
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
		this.tile = tile;
		this.selected = selected;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public int getID() {
		return id;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getZ() {
		return z;
	}
	
	public Tile getTile() {
		return tile;
	}
	
	public boolean isSelected() {
		return selected;
	}
}
