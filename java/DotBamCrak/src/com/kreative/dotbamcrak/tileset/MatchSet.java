package com.kreative.dotbamcrak.tileset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MatchSet implements Iterable<Tile> {
	private List<Tile> tiles;
	
	public MatchSet(Collection<Tile> tiles) {
		this.tiles = new ArrayList<Tile>();
		this.tiles.addAll(tiles);
		this.tiles = Collections.unmodifiableList(this.tiles);
		for (Tile tile : tiles) tile.setMatchSet(this);
	}
	
	public boolean contains(Tile tile) {
		return tiles.contains(tile);
	}
	
	public Iterator<Tile> iterator() {
		return tiles.iterator();
	}
	
	public boolean isEmpty() {
		return tiles.isEmpty();
	}
	
	public int size() {
		return tiles.size();
	}
	
	public Tile[] toArray() {
		return tiles.toArray(new Tile[0]);
	}
	
	public Collection<Tile> toCollection() {
		return tiles;
	}
}
