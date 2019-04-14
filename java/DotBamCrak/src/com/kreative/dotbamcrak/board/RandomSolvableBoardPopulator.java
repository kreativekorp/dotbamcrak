package com.kreative.dotbamcrak.board;

import java.util.List;
import java.util.Random;
import com.kreative.dotbamcrak.layout.Layout;
import com.kreative.dotbamcrak.tileset.Tile;
import com.kreative.dotbamcrak.tileset.TileSet;

public class RandomSolvableBoardPopulator implements BoardPopulator {
	private Random random;
	private BoardPopulator fallback;
	
	public RandomSolvableBoardPopulator(Random random) {
		this.random = random;
		this.fallback = new RandomBoardPopulator(random);
	}
	
	public void populateBoard(TileSet tileset, Layout layout, Tile[][][] tileArray) {
		for (int i = 0; i < 16; i++) {
			Pile pile = new Pile(random);
			int tilesNeeded = layout.getTileCount();
			while (pile.size() < tilesNeeded) pile.addTiles(tileset);
			
			TemporaryBoard tb = new TemporaryBoard(layout);
			List<int[]> freeTiles;
			while ((freeTiles = tb.getFreeTiles()).size() >= 2) {
				int[] t1 = freeTiles.remove(random.nextInt(freeTiles.size()));
				int[] t2 = freeTiles.remove(random.nextInt(freeTiles.size()));
				Tile t1t = pile.drawTile();
				Tile t2t = pile.drawMatchingTile(t1t);
				if (t2t == null) t2t = t1t;
				tileArray[t1[2]][t1[1]][t1[0]] = t1t;
				tileArray[t2[2]][t2[1]][t2[0]] = t2t;
				tb.removeTiles(t1[0], t1[1], t1[2], t2[0], t2[1], t2[2]);
			}
			
			if (tb.isEmpty()) {
				return;
			}
		}
		fallback.populateBoard(tileset, layout, tileArray);
	}
	
	public void shuffleTiles(Layout layout, Tile[][][] unshuffledTiles, Tile[][][] shuffledTiles) {
		for (int i = 0; i < 16; i++) {
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
			
			TemporaryBoard tb = new TemporaryBoard(layout, unshuffledTiles);
			List<int[]> freeTiles;
			while ((freeTiles = tb.getFreeTiles()).size() >= 2) {
				int[] t1 = freeTiles.remove(random.nextInt(freeTiles.size()));
				int[] t2 = freeTiles.remove(random.nextInt(freeTiles.size()));
				Tile t1t = pile.drawTile();
				Tile t2t = pile.drawMatchingTile(t1t);
				if (t2t == null) t2t = t1t;
				shuffledTiles[t1[2]][t1[1]][t1[0]] = t1t;
				shuffledTiles[t2[2]][t2[1]][t2[0]] = t2t;
				tb.removeTiles(t1[0], t1[1], t1[2], t2[0], t2[1], t2[2]);
			}

			if (tb.isEmpty()) {
				return;
			}
		}
		fallback.shuffleTiles(layout, unshuffledTiles, shuffledTiles);
	}
}
