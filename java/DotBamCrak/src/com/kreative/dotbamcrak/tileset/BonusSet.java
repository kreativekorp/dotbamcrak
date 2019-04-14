package com.kreative.dotbamcrak.tileset;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BonusSet implements Iterable<Tile> {
	private String id;
	private String name;
	private TraditionalBonusSet bonus;
	private Set<Tile> tiles;
	
	public BonusSet(String id, String name, Collection<Tile> tiles) {
		this.id = id;
		this.name = name;
		     if (id.equals("f")) this.bonus = TraditionalBonusSet.FLOWER;
		else if (id.equals("s")) this.bonus = TraditionalBonusSet.SEASON;
		else if (id.equals("a")) this.bonus = TraditionalBonusSet.ANIMAL;
		else                     this.bonus = null;
		this.tiles = new HashSet<Tile>();
		this.tiles.addAll(tiles);
		this.tiles = Collections.unmodifiableSet(this.tiles);
		for (Tile tile : tiles) tile.setBonusSet(this);
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
	
	public TraditionalBonusSet toTraditional() {
		return bonus;
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
		if (o instanceof BonusSet) {
			return this.id.equals(((BonusSet)o).id);
		} else {
			return false;
		}
	}
}
