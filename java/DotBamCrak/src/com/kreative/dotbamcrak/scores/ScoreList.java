package com.kreative.dotbamcrak.scores;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ScoreList implements Iterable<Score> {
	private List<Score> scores = new ArrayList<Score>();
	
	public void add(Score score) {
		scores.add(score);
		Collections.sort(scores);
	}
	
	public void clear() {
		scores.clear();
	}
	
	public boolean contains(Score score) {
		return scores.contains(score);
	}
	
	public Score get(int index) {
		return scores.get(index);
	}
	
	public int indexOf(Score score) {
		return scores.indexOf(score);
	}
	
	public boolean isEmpty() {
		return scores.isEmpty();
	}
	
	public Iterator<Score> iterator() {
		return scores.iterator();
	}
	
	public int lastIndexOf(Score score) {
		return scores.lastIndexOf(score);
	}
	
	public int size() {
		return scores.size();
	}
	
	public Score[] toArray() {
		return scores.toArray(new Score[0]);
	}
	
	public Collection<Score> toCollection() {
		return Collections.unmodifiableList(scores);
	}
	
	public void truncate(int max) {
		if (scores.size() > max) {
			scores.subList(max, scores.size()).clear();
		}
	}
}
