package com.kreative.dotbamcrak.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import com.kreative.dotbamcrak.tileset.MatchSet;
import com.kreative.dotbamcrak.tileset.Tile;
import com.kreative.dotbamcrak.tileset.TileSet;

public class Pile {
	private Random random;
	private List<Tile> tiles;
	
	public Pile(Random random) {
		this.random = random;
		this.tiles = new ArrayList<Tile>();
	}
	
	public void addTile(Tile tile) {
		this.tiles.add(tile);
	}
	
	public void addTiles(Collection<Tile> tiles) {
		this.tiles.addAll(tiles);
	}
	
	public void addTiles(TileSet tileset) {
		for (MatchSet match : tileset.getMatchSets()) {
			this.tiles.addAll(match.toCollection());
		}
	}
	
	public Tile drawTile() {
		return tiles.remove(random.nextInt(tiles.size()));
	}
	
	public Tile drawMatchingTile(Tile tile) {
		List<Tile> matches = new ArrayList<Tile>();
		matches.addAll(tile.getMatchSet().toCollection());
		while (!matches.isEmpty()) {
			Tile matchingTile = matches.remove(random.nextInt(matches.size()));
			if (tiles.remove(matchingTile)) return matchingTile;
		}
		return null;
	}
	
	public List<Tile> drawTiles(int count) {
		if (count < 0 || count > tiles.size()) throw new IllegalArgumentException(Integer.toString(count));
		List<Tile> drawnTiles = new ArrayList<Tile>(count);
		while (count-->0) drawnTiles.add(drawTile());
		return drawnTiles;
	}
	
	public List<Tile> drawMatchingTiles(int count) {
		if (count < 0 || count > tiles.size() || (count & 1) != 0) throw new IllegalArgumentException(Integer.toString(count));
		List<Tile> drawnTiles = new ArrayList<Tile>(count);
		while (count > 0) {
			Tile tile = drawTile();
			Tile matchingTile = drawMatchingTile(tile);
			if (matchingTile == null) matchingTile = tile;
			drawnTiles.add(tile);
			drawnTiles.add(random.nextInt(drawnTiles.size()), matchingTile);
			count -= 2;
		}
		return drawnTiles;
	}
	
	public boolean isEmpty() {
		return tiles.isEmpty();
	}
	
	public int size() {
		return tiles.size();
	}
}
