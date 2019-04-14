package com.kreative.dotbamcrak.board;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import com.kreative.dotbamcrak.layout.Layout;
import com.kreative.dotbamcrak.tileset.Tile;
import com.kreative.dotbamcrak.tileset.TileSet;

public class Board {
	private TileSet tileset;
	private Layout layout;
	private BoardPopulator populator;
	
	private int boardWidth;
	private int boardHeight;
	private int boardDepth;
	private int tileWidth;
	private int tileHeight;
	private int tileDepth;
	private int windowWidth;
	private int windowHeight;

	private int tilePixelWidth;
	private int tilePixelHeight;
	private Insets tilePixelInsets;
	private int tilePixelDepthX;
	private int tilePixelDepthY;
	private int boardPixelWidth;
	private int boardPixelHeight;
	private int windowPixelWidth;
	private int windowPixelHeight;
	
	private int tileCount;
	private Tile[][][] tiles;
	private List<Action> tileHistory;
	private int tileHistoryPointer;
	private boolean[][][] tileSelection;
	private BoardListenerList listeners;
	
	public Board(TileSet tileset, Layout layout, BoardPopulator populator) {
		this.tileset = tileset;
		this.layout = layout;
		this.populator = populator;
		
		this.boardWidth = layout.getBoardWidth();
		this.boardHeight = layout.getBoardHeight();
		this.boardDepth = layout.getBoardDepth();
		this.tileWidth = layout.getTileWidth();
		this.tileHeight = layout.getTileHeight();
		this.tileDepth = layout.getTileDepth();
		this.windowWidth = layout.getWindowWidth();
		this.windowHeight = layout.getWindowHeight();
		
		this.tilePixelWidth = tileset.getTileWidth();
		this.tilePixelHeight = tileset.getTileHeight();
		this.tilePixelInsets = tileset.getImageInsets();
		this.tilePixelDepthX = tilePixelInsets.left - tilePixelInsets.right;
		this.tilePixelDepthY = tilePixelInsets.top - tilePixelInsets.bottom;
		this.boardPixelWidth = boardWidth * tilePixelWidth / tileWidth;
		this.boardPixelHeight = boardHeight * tilePixelHeight / tileHeight;
		this.windowPixelWidth = windowWidth * tilePixelWidth / tileWidth;
		this.windowPixelHeight = windowHeight * tilePixelHeight / tileHeight;
		
		this.tileCount = layout.getTileCount();
		this.tiles = new Tile[boardDepth][boardHeight][boardWidth];
		this.tileHistory = new ArrayList<Action>();
		this.tileHistoryPointer = 0;
		this.tileSelection = new boolean[boardDepth][boardHeight][boardWidth];
		this.listeners = new BoardListenerList();
		
		populator.populateBoard(tileset, layout, tiles);
	}
	
	public TileSet getTileSet() {
		return tileset;
	}
	
	public Layout getLayout() {
		return layout;
	}
	
	public int getBoardWidth() {
		return boardWidth;
	}
	
	public int getBoardHeight() {
		return boardHeight;
	}
	
	public int getBoardDepth() {
		return boardDepth;
	}
	
	public int getTileWidth() {
		return tileWidth;
	}
	
	public int getTileHeight() {
		return tileHeight;
	}
	
	public int getTileDepth() {
		return tileDepth;
	}
	
	public int getWindowWidth() {
		return windowWidth;
	}
	
	public int getWindowHeight() {
		return windowHeight;
	}
	
	public int getTilePixelWidth() {
		return tilePixelWidth;
	}
	
	public int getTilePixelHeight() {
		return tilePixelHeight;
	}
	
	public int getTilePixelDepthX() {
		return tilePixelDepthX;
	}
	
	public int getTilePixelDepthY() {
		return tilePixelDepthY;
	}
	
	public int getBoardPixelWidth() {
		return boardPixelWidth;
	}
	
	public int getBoardPixelHeight() {
		return boardPixelHeight;
	}
	
	public int getWindowPixelWidth() {
		return windowPixelWidth;
	}
	
	public int getWindowPixelHeight() {
		return windowPixelHeight;
	}
	
	public synchronized int getTileCount() {
		return tileCount;
	}
	
	public synchronized boolean isEmpty() {
		return tileCount == 0;
	}
	
	public synchronized int[] resolveTile(int x, int y, int z) {
		for (int rz = z, zi = 0; rz >= 0 && zi < tileDepth; rz--, zi++) {
			if (rz >= 0 && rz < boardDepth) {
				for (int ry = y, yi = 0; ry >= 0 && yi < tileHeight; ry--, yi++) {
					if (ry >= 0 && ry < boardHeight) {
						for (int rx = x, xi = 0; rx >= 0 && xi < tileWidth; rx--, xi++) {
							if (rx >= 0 && rx < boardWidth) {
								if (tiles[rz][ry][rx] != null) {
									return new int[]{rx,ry,rz};
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	public synchronized Tile getTile(int x, int y, int z) {
		if (x >= 0 && y >= 0 && z >= 0 && x < boardWidth && y < boardHeight && z < boardDepth) {
			return tiles[z][y][x];
		} else {
			return null;
		}
	}
	
	public synchronized void removeTiles(int x1, int y1, int z1, int x2, int y2, int z2) {
		if (
				x1 >= 0 && y1 >= 0 && z1 >= 0 &&
				x1 < boardWidth && y1 < boardHeight && z1 < boardDepth &&
				x2 >= 0 && y2 >= 0 && z2 >= 0 &&
				x2 < boardWidth && y2 < boardHeight && z2 < boardDepth &&
				tiles[z1][y1][x1] != null &&
				tiles[z2][y2][x2] != null
		) {
			RemoveTilesAction action = new RemoveTilesAction(
					x1, y1, z1, tiles[z1][y1][x1],
					x2, y2, z2, tiles[z2][y2][x2]
			);
			tileHistory.subList(tileHistoryPointer, tileHistory.size()).clear();
			tileHistory.add(action);
			tileHistoryPointer++;
			action.redo();
		}
	}
	
	public synchronized void shuffleTiles() {
		Tile[][][] pre = copyArray(tiles);
		Tile[][][] post = new Tile[boardDepth][boardHeight][boardWidth];
		populator.shuffleTiles(layout, pre, post);
		ShuffleAction action = new ShuffleAction(pre, post);
		tileHistory.subList(tileHistoryPointer, tileHistory.size()).clear();
		tileHistory.add(action);
		tileHistoryPointer++;
		action.redo();
	}
	
	public synchronized boolean canUndo() {
		return tileHistoryPointer > 0;
	}
	
	public synchronized void undo() {
		if (tileHistoryPointer > 0) {
			tileHistoryPointer--;
			tileHistory.get(tileHistoryPointer).undo();
		}
	}
	
	public synchronized void undoAll() {
		while (tileHistoryPointer > 0) {
			tileHistoryPointer--;
			tileHistory.get(tileHistoryPointer).undo();
		}
	}
	
	public synchronized boolean canRedo() {
		return tileHistoryPointer < tileHistory.size();
	}
	
	public synchronized void redo() {
		if (tileHistoryPointer < tileHistory.size()) {
			tileHistory.get(tileHistoryPointer).redo();
			tileHistoryPointer++;
		}
	}
	
	public synchronized void redoAll() {
		while (tileHistoryPointer < tileHistory.size()) {
			tileHistory.get(tileHistoryPointer).redo();
			tileHistoryPointer++;
		}
	}
	
	public synchronized boolean isTileSelected(int x, int y, int z) {
		if (x >= 0 && y >= 0 && z >= 0 && x < boardWidth && y < boardHeight && z < boardDepth) {
			return tileSelection[z][y][x];
		} else {
			return false;
		}
	}
	
	public synchronized void setTileSelected(int x, int y, int z, boolean selected) {
		if (x >= 0 && y >= 0 && z >= 0 && x < boardWidth && y < boardHeight && z < boardDepth) {
			tileSelection[z][y][x] = selected;
			listeners.dispatchEvent(new BoardEvent(
					this, BoardEvent.TILE_SELECTED,
					x, y, z, tiles[z][y][x], selected
			));
		}
	}
	
	public synchronized void clearSelection() {
		for (int z = 0; z < boardDepth; z++) {
			for (int y = 0; y < boardHeight; y++) {
				for (int x = 0; x < boardWidth; x++) {
					tileSelection[z][y][x] = false;
				}
			}
		}
		listeners.dispatchEvent(new BoardEvent(
				this, BoardEvent.TILES_DESELECTED,
				-1, -1, -1, null, false
		));
	}
	
	public synchronized void addBoardListener(BoardListener l) {
		listeners.remove(l);
		listeners.add(l);
	}
	
	public synchronized void removeBoardListener(BoardListener l) {
		listeners.remove(l);
	}
	
	public synchronized BoardListener[] getBoardListeners() {
		return listeners.toArray(new BoardListener[0]);
	}
	
	private boolean isLeftFree(int x, int y, int z) {
		int tx1 = x - tileWidth;
		for (int ty = y - tileHeight + 1; ty < y + tileHeight; ty++) {
			if (ty >= 0 && ty < boardHeight) {
				if (tx1 >= 0 && tx1 < boardWidth && tiles[z][ty][tx1] != null) {
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
				if (tx2 >= 0 && tx2 < boardWidth && tiles[z][ty][tx2] != null) {
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
						if (tx >= 0 && tx < boardWidth && tiles[tz][ty][tx] != null) {
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
					if (tiles[z][y][x] != null && isTileFree(x, y, z)) {
						freeTiles.add(new int[]{x,y,z});
					}
				}
			}
		}
		return freeTiles;
	}
	
	public synchronized List<int[]> getFreeMatchingTiles() {
		List<int[]> freeTiles = getFreeTiles();
		List<int[]> freeMatchingTiles = new ArrayList<int[]>();
		for (int i = 0; i < freeTiles.size(); i++) {
			int[] ii = freeTiles.get(i);
			for (int j = 0; j < freeTiles.size(); j++) {
				if (j != i) {
					int[] jj = freeTiles.get(j);
					Tile t1 = tiles[ii[2]][ii[1]][ii[0]];
					Tile t2 = tiles[jj[2]][jj[1]][jj[0]];
					if (t1.getMatchSet().contains(t2)) {
						freeMatchingTiles.add(ii);
						break;
					}
				}
			}
		}
		return freeMatchingTiles;
	}
	
	public synchronized List<int[]> getSelectedTiles() {
		List<int[]> selectedTiles = new ArrayList<int[]>();
		for (int z = 0; z < boardDepth; z++) {
			for (int y = 0; y < boardHeight; y++) {
				for (int x = 0; x < boardWidth; x++) {
					if (tiles[z][y][x] != null && tileSelection[z][y][x]) {
						selectedTiles.add(new int[]{x,y,z});
					}
				}
			}
		}
		return selectedTiles;
	}
	
	public void paintBackground(Graphics g, int x, int y, int w, int h) {
		if (g instanceof Graphics2D) {
			((Graphics2D)g).setRenderingHint(
					RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC
			);
		}
		
		Paint bgpaint = tileset.getBackgroundPaint();
		if (bgpaint != null) {
			if (g instanceof Graphics2D) {
				((Graphics2D)g).setPaint(bgpaint);
				g.fillRect(x, y, w, h);
			} else if (bgpaint instanceof Color) {
				g.setColor((Color)bgpaint);
				g.fillRect(x, y, w, h);
			}
		}
		
		Image bgimage = tileset.getBackgroundImage();
		if (bgimage != null) {
			int iw = bgimage.getWidth(null);
			int ih = bgimage.getHeight(null);
			if (w >= iw && h >= ih) {
				g.drawImage(bgimage, x + (w-iw)/2, y + (h-ih)/2, null);
			} else if (w >= windowWidth && h >= windowHeight) {
				int sw = Math.min(w, iw * h / ih);
				int sh = Math.min(h, ih * w / iw);
				g.drawImage(
						bgimage,
						x + (w-sw)/2, y + (h-sh)/2,
						x + (w-sw)/2 + sw, y + (h-sh)/2 + sh,
						0, 0, iw, ih,
						null
				);
			}
		}
	}
	
	private void paintCell(Graphics g, int bx, int by, int z, int y, int x, boolean hidden) {
		if (tiles[z][y][x] != null) {
			Image image =
				hidden ? tileset.getBlankTile().getImage() :
				tileSelection[z][y][x] ? tiles[z][y][x].getSelectedImage() :
				tiles[z][y][x].getImage();
			g.drawImage(image, bx - tilePixelInsets.left, by - tilePixelInsets.top, null);
		}
	}
	
	private void paintLayer(Graphics g, int bx, int by, int z, boolean hidden) {
		boolean yDecreasing = tilePixelInsets.top > tilePixelInsets.bottom;
		boolean xDecreasing = tilePixelInsets.left > tilePixelInsets.right;
		int yInitial = yDecreasing ? (boardHeight - 1) : 0;
		int xInitial = xDecreasing ? (boardWidth - 1) : 0;
		int n = boardWidth + boardHeight;
		for (int i = 1; i < n; i++) {
			for (int j = 0, k = i-1; j < i; j++, k--) {
				int x = xDecreasing ? (xInitial - j) : (xInitial + j);
				int y = yDecreasing ? (yInitial - k) : (yInitial + k);
				if (x >= 0 && y >= 0 && x < boardWidth && y < boardHeight) {
					paintCell(
							g,
							bx + x * tilePixelWidth / tileWidth,
							by + y * tilePixelHeight / tileHeight,
							z, y, x,
							hidden
					);
				}
			}
		}
	}
	
	private void paintBoard(Graphics g, int bx, int by, boolean hidden) {
		for (int z = 0; z < boardDepth; z++) {
			paintLayer(
					g,
					bx + z * tilePixelDepthX / tileDepth,
					by + z * tilePixelDepthY / tileDepth,
					z,
					hidden
			);
		}
	}
	
	public void paintBoard(Graphics g, int x, int y, int w, int h, boolean hidden) {
		if (g instanceof Graphics2D) {
			((Graphics2D)g).setRenderingHint(
					RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC
			);
		}
		
		if (w >= windowPixelWidth && h >= windowPixelHeight) {
			BufferedImage buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			Graphics bg = buffer.createGraphics();
			paintBoard(bg, (w-boardPixelWidth)/2, (h-boardPixelHeight)/2, hidden);
			bg.dispose();
			g.drawImage(buffer, x, y, null);
		} else if (w >= windowWidth*4 && h >= windowHeight*4) {
			int bw = Math.max(windowPixelWidth, w * windowPixelHeight / h);
			int bh = Math.max(windowPixelHeight, h * windowPixelWidth / w);
			BufferedImage buffer = new BufferedImage(bw, bh, BufferedImage.TYPE_INT_ARGB);
			Graphics bg = buffer.createGraphics();
			paintBoard(bg, (bw-boardPixelWidth)/2, (bh-boardPixelHeight)/2, hidden);
			bg.dispose();
			g.drawImage(buffer, x, y, x + w, y + h, 0, 0, bw, bh, null);
		}
	}
	
	private int[] resolveTile(int mx, int my) {
		for (int z = boardDepth-1; z >= 0; z--) {
			int[] r = resolveTile(
					(mx - z * tilePixelDepthX / tileDepth) * tileWidth / tilePixelWidth,
					(my - z * tilePixelDepthY / tileDepth) * tileHeight / tilePixelHeight,
					z
			);
			if (r != null) return r;
		}
		return null;
	}
	
	public synchronized int[] resolveTile(int x, int y, int w, int h, int mx, int my) {
		if (w >= windowPixelWidth && h >= windowPixelHeight) {
			int bx = x + (w-boardPixelWidth)/2;
			int by = y + (h-boardPixelHeight)/2;
			return resolveTile(mx - bx, my - by);
		} else if (w >= windowWidth*4 && h >= windowHeight*4) {
			int bw = Math.min(w, windowPixelWidth * h / windowPixelHeight) * boardPixelWidth / windowPixelWidth;
			int bh = Math.min(h, windowPixelHeight * w / windowPixelWidth) * boardPixelHeight / windowPixelHeight;
			int bx = x + (w-bw)/2;
			int by = y + (h-bh)/2;
			int nx = (mx - bx) * boardPixelWidth / bw;
			int ny = (my - by) * boardPixelHeight / bh;
			return resolveTile(nx, ny);
		} else {
			return null;
		}
	}
	
	private abstract class Action {
		public abstract void undo();
		public abstract void redo();
	}
	
	private class RemoveTilesAction extends Action {
		int x1, y1, z1; Tile t1;
		int x2, y2, z2; Tile t2;
		
		public RemoveTilesAction(int x1, int y1, int z1, Tile t1, int x2, int y2, int z2, Tile t2) {
			this.x1 = x1; this.y1 = y1; this.z1 = z1; this.t1 = t1;
			this.x2 = x2; this.y2 = y2; this.z2 = z2; this.t2 = t2;
		}
		
		public void undo() {
			if (tiles[z1][y1][x1] == null) {
				tileCount++;
				tiles[z1][y1][x1] = t1;
				listeners.dispatchEvent(new BoardEvent(
						Board.this, BoardEvent.TILE_ADDED,
						x1, y1, z1, t1, tileSelection[z1][y1][x1]
				));
			}
			if (tiles[z2][y2][x2] == null) {
				tileCount++;
				tiles[z2][y2][x2] = t2;
				listeners.dispatchEvent(new BoardEvent(
						Board.this, BoardEvent.TILE_ADDED,
						x2, y2, z2, t2, tileSelection[z2][y2][x2]
				));
			}
		}
		
		public void redo() {
			if (tiles[z1][y1][x1] != null) {
				tileCount--;
				tiles[z1][y1][x1] = null;
				listeners.dispatchEvent(new BoardEvent(
						Board.this, BoardEvent.TILE_REMOVED,
						x1, y1, z1, t1, tileSelection[z1][y1][x1]
				));
			}
			if (tiles[z2][y2][x2] != null) {
				tileCount--;
				tiles[z2][y2][x2] = null;
				listeners.dispatchEvent(new BoardEvent(
						Board.this, BoardEvent.TILE_REMOVED,
						x2, y2, z2, t2, tileSelection[z2][y2][x2]
				));
			}
		}
	}
	
	private class ShuffleAction extends Action {
		Tile[][][] pre;
		Tile[][][] post;
		
		public ShuffleAction(Tile[][][] pre, Tile[][][] post) {
			this.pre = pre;
			this.post = post;
		}
		
		public void undo() {
			tiles = copyArray(pre);
			listeners.dispatchEvent(new BoardEvent(
					Board.this, BoardEvent.TILES_SHUFFLED,
					-1, -1, -1, null, false
			));
		}
		
		public void redo() {
			tiles = copyArray(post);
			listeners.dispatchEvent(new BoardEvent(
					Board.this, BoardEvent.TILES_SHUFFLED,
					-1, -1, -1, null, false
			));
		}
	}
	
	private static Tile[][][] copyArray(Tile[][][] a) {
		Tile[][][] b = new Tile[a.length][][];
		for (int i = 0; i < a.length; i++) {
			b[i] = new Tile[a[i].length][];
			for (int j = 0; j < a[i].length; j++) {
				b[i][j] = new Tile[a[i][j].length];
				for (int k = 0; k < a[i][j].length; k++) {
					b[i][j][k] = a[i][j][k];
				}
			}
		}
		return b;
	}
}
