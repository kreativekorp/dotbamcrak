package com.kreative.dotbamcrak.board;

import java.util.ArrayList;
import java.util.List;
import com.kreative.dotbamcrak.layout.Layout;
import com.kreative.dotbamcrak.tileset.Tile;

public class TemporaryBoard {
	private int boardWidth;
	private int boardHeight;
	private int boardDepth;
	private int tileWidth;
	private int tileHeight;
	private int tileDepth;
	private int tileCount;
	private boolean[][][] tiles;
	
	public TemporaryBoard(Layout layout) {
		this.boardWidth = layout.getBoardWidth();
		this.boardHeight = layout.getBoardHeight();
		this.boardDepth = layout.getBoardDepth();
		this.tileWidth = layout.getTileWidth();
		this.tileHeight = layout.getTileHeight();
		this.tileDepth = layout.getTileDepth();
		this.tileCount = layout.getTileCount();
		this.tiles = new boolean[boardDepth][boardHeight][boardWidth];
		for (int z = 0; z < boardDepth; z++) {
			for (int y = 0; y < boardHeight; y++) {
				for (int x = 0; x < boardWidth; x++) {
					this.tiles[z][y][x] = layout.getTile(x, y, z);
				}
			}
		}
	}
	
	public TemporaryBoard(Layout layout, Tile[][][] tileArray) {
		this.boardWidth = layout.getBoardWidth();
		this.boardHeight = layout.getBoardHeight();
		this.boardDepth = layout.getBoardDepth();
		this.tileWidth = layout.getTileWidth();
		this.tileHeight = layout.getTileHeight();
		this.tileDepth = layout.getTileDepth();
		this.tileCount = 0;
		this.tiles = new boolean[boardDepth][boardHeight][boardWidth];
		for (int z = 0; z < boardDepth; z++) {
			for (int y = 0; y < boardHeight; y++) {
				for (int x = 0; x < boardWidth; x++) {
					if (tileArray[z][y][x] != null) {
						this.tileCount++;
						this.tiles[z][y][x] = true;
					}
				}
			}
		}
	}
	
	public synchronized boolean isEmpty() {
		return tileCount == 0;
	}
	
	public synchronized void removeTiles(int x1, int y1, int z1, int x2, int y2, int z2) {
		if (
				x1 >= 0 && y1 >= 0 && z1 >= 0 &&
				x1 < boardWidth && y1 < boardHeight && z1 < boardDepth &&
				x2 >= 0 && y2 >= 0 && z2 >= 0 &&
				x2 < boardWidth && y2 < boardHeight && z2 < boardDepth &&
				tiles[z1][y1][x1] &&
				tiles[z2][y2][x2]
		) {
			tileCount -= 2;
			tiles[z1][y1][x1] = false;
			tiles[z2][y2][x2] = false;
		}
	}
	
	private boolean isLeftFree(int x, int y, int z) {
		int tx1 = x - tileWidth;
		for (int ty = y - tileHeight + 1; ty < y + tileHeight; ty++) {
			if (ty >= 0 && ty < boardHeight) {
				if (tx1 >= 0 && tx1 < boardWidth && tiles[z][ty][tx1]) {
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean isRightFree(int x, int y, int z) {
		int tx2 = x + tileWidth;
		for (int ty = y - tileHeight + 1; ty < y + tileHeight; ty++) {
			if (ty >= 0 && ty < boardHeight) {
				if (tx2 >= 0 && tx2 < boardWidth && tiles[z][ty][tx2]) {
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean isTopFree(int x, int y, int z) {
		int tx1 = x - tileWidth;
		int tx2 = x + tileWidth;
		int tz = z + tileDepth;
		if (tz >= 0 && tz < boardDepth) {
			for (int ty = y - tileHeight + 1; ty < y + tileHeight; ty++) {
				if (ty >= 0 && ty < boardHeight) {
					for (int tx = tx1 + 1; tx < tx2; tx++) {
						if (tx >= 0 && tx < boardWidth && tiles[tz][ty][tx]) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	public synchronized boolean isTileFree(int x, int y, int z) {
		return (isLeftFree(x,y,z) || isRightFree(x,y,z)) && isTopFree(x,y,z);
	}
	
	public synchronized List<int[]> getFreeTiles() {
		List<int[]> freeTiles = new ArrayList<int[]>();
		for (int z = 0; z < boardDepth; z++) {
			for (int y = 0; y < boardHeight; y++) {
				for (int x = 0; x < boardWidth; x++) {
					if (tiles[z][y][x] && isTileFree(x, y, z)) {
						freeTiles.add(new int[]{x,y,z});
					}
				}
			}
		}
		return freeTiles;
	}
}
