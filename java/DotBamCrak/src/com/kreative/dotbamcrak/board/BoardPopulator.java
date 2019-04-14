package com.kreative.dotbamcrak.board;

import com.kreative.dotbamcrak.layout.Layout;
import com.kreative.dotbamcrak.tileset.Tile;
import com.kreative.dotbamcrak.tileset.TileSet;

public interface BoardPopulator {
	public void populateBoard(TileSet tileset, Layout layout, Tile[][][] tileArray);
	public void shuffleTiles(Layout layout, Tile[][][] unshuffledTiles, Tile[][][] shuffledTiles);
}
