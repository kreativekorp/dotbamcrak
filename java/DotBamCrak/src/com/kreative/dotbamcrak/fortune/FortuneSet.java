package com.kreative.dotbamcrak.fortune;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class FortuneSet implements Iterable<String> {
	private File file;
	private String name;
	private List<String> fortunes;
	private Random random;
	
	public FortuneSet(File in) throws FileNotFoundException {
		file = in;
		name = in.getName().trim();
		while (name.startsWith(".")) {
			name = name.substring(1).trim();
		}
		if (name.contains(".")) {
			name = name.substring(0, name.lastIndexOf('.'));
		}
		fortunes = new ArrayList<String>();
		process(new Scanner(in));
		fortunes = Collections.unmodifiableList(fortunes);
		random = new Random();
	}
	
	private void process(Scanner in) {
		while (in.hasNextLine()) {
			String s = in.nextLine().trim();
			if (s.length() > 0) {
				fortunes.add(s);
			}
		}
	}
	
	public File getFile() {
		return file;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}
	
	public Iterator<String> iterator() {
		return fortunes.iterator();
	}
	
	public boolean isEmpty() {
		return fortunes.isEmpty();
	}
	
	public int size() {
		return fortunes.size();
	}
	
	public String[] toArray() {
		return fortunes.toArray(new String[0]);
	}
	
	public Collection<String> toCollection() {
		return fortunes;
	}
	
	public String getFortune() {
		return fortunes.get(random.nextInt(fortunes.size()));
	}
}
