package com.kreative.dotbamcrak.board;

import java.util.List;
import java.util.Random;
import com.kreative.dotbamcrak.layout.Layout;
import com.kreative.dotbamcrak.tileset.Tile;
import com.kreative.dotbamcrak.tileset.TileSet;

public class RandomBoardPopulator implements BoardPopulator {
	private Random random;
	
	public RandomBoardPopulator(Random random) {
		this.random = random;
	}
	
	public void populateBoard(TileSet tileset, Layout layout, Tile[][][] tileArray) {
		Pile pile = new Pile(random);
		int tilesNeeded = layout.getTileCount();
		while (pile.size() < tilesNeeded) pile.addTiles(tileset);
		List<Tile> tilesDrawn = pile.drawMatchingTiles(tilesNeeded);
		for (int z = 0; z < layout.getBoardDepth(); z++) {
			for (int y = 0; y < layout.getBoardHeight(); y++) {
				for (int x = 0; x < layout.getBoardWidth(); x++) {
					if (layout.getTile(x, y, z)) {
						tileArray[z][y][x] = tilesDrawn.remove(0);
					}
				}
			}
		}
	}
	
	public void shuffleTiles(Layout layout, Tile[][][] unshuffledTiles, Tile[][][] shuffledTiles) {
		Pile pile = new Pile(random);
		for (int z = 0; z < layout.getBoardDepth(); z++) {
			for (int y = 0; y < layout.getBoardHeight(); y++) {
				for (int x = 0; x < layout.getBoardWidth(); x++) {
					if (unshuffledTiles[z][y][x] != null) {
						pile.addTile(unshuffledTiles[z][y][x]);
					}
				}
			}
		}
		for (int z = 0; z < layout.getBoardDepth(); z++) {
			for (int y = 0; y < layout.getBoardHeight(); y++) {
				for (int x = 0; x < layout.getBoardWidth(); x++) {
					if (unshuffledTiles[z][y][x] != null) {
						shuffledTiles[z][y][x] = pile.drawTile();
					}
				}
			}
		}
	}
}
