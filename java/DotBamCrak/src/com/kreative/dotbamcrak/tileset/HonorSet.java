package com.kreative.dotbamcrak.tileset;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class HonorSet implements Iterable<Tile> {
	private String id;
	private String name;
	private TraditionalHonorSet honor;
	private Set<Tile> tiles;
	
	public HonorSet(String id, String name, Collection<Tile> tiles) {
		this.id = id;
		this.name = name;
		     if (id.equals("d")) this.honor = TraditionalHonorSet.DRAGON;
		else if (id.equals("w")) this.honor = TraditionalHonorSet.WIND;
		else                     this.honor = null;
		this.tiles = new HashSet<Tile>();
		this.tiles.addAll(tiles);
		this.tiles = Collections.unmodifiableSet(this.tiles);
		for (Tile tile : tiles) tile.setHonorSet(this);
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
	
	public TraditionalHonorSet toTraditional() {
		return honor;
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
		if (o instanceof HonorSet) {
			return this.id.equals(((HonorSet)o).id);
		} else {
			return false;
		}
	}
}
