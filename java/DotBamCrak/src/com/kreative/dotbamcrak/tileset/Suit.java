package com.kreative.dotbamcrak.tileset;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Suit implements Iterable<Tile> {
	private String id;
	private String name;
	private TraditionalSuit suit;
	private Set<Tile> tiles;
	
	public Suit(String id, String name, Collection<Tile> tiles) {
		this.id = id;
		this.name = name;
		     if (id.equals("c")) this.suit = TraditionalSuit.CHARACTER;
		else if (id.equals("b")) this.suit = TraditionalSuit.BAMBOO;
		else if (id.equals("d")) this.suit = TraditionalSuit.DOT;
		else                     this.suit = null;
		this.tiles = new HashSet<Tile>();
		this.tiles.addAll(tiles);
		this.tiles = Collections.unmodifiableSet(this.tiles);
		for (Tile tile : tiles) tile.setSuit(this);
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
	
	public TraditionalSuit toTraditional() {
		return suit;
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
		if (o instanceof Suit) {
			return this.id.equals(((Suit)o).id);
		} else {
			return false;
		}
	}
}
