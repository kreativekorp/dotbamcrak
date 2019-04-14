package com.kreative.dotbamcrak.tileset;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Rank implements Iterable<Tile> {
	private String id;
	private String name;
	private TraditionalRank rank;
	private Set<Tile> tiles;
	
	public Rank(String id, String name, Collection<Tile> tiles) {
		this.id = id;
		this.name = name;
		     if (id.equals("1")) this.rank = TraditionalRank.ONE;
		else if (id.equals("2")) this.rank = TraditionalRank.TWO;
		else if (id.equals("3")) this.rank = TraditionalRank.THREE;
		else if (id.equals("4")) this.rank = TraditionalRank.FOUR;
		else if (id.equals("5")) this.rank = TraditionalRank.FIVE;
		else if (id.equals("6")) this.rank = TraditionalRank.SIX;
		else if (id.equals("7")) this.rank = TraditionalRank.SEVEN;
		else if (id.equals("8")) this.rank = TraditionalRank.EIGHT;
		else if (id.equals("9")) this.rank = TraditionalRank.NINE;
		else                     this.rank = null;
		this.tiles = new HashSet<Tile>();
		this.tiles.addAll(tiles);
		this.tiles = Collections.unmodifiableSet(this.tiles);
		for (Tile tile : tiles) tile.setRank(this);
	}
	
	public String getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}
	
	public TraditionalRank toTraditional() {
		return rank;
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
	
	public int hashCode() {
		return id.hashCode();
	}
	
	public boolean equals(Object o) {
		if (o instanceof Rank) {
			return this.id.equals(((Rank)o).id);
		} else {
			return false;
		}
	}
}
