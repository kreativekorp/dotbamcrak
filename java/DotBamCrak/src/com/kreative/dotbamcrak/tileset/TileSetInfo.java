package com.kreative.dotbamcrak.tileset;

import java.awt.Dimension;
import java.awt.Insets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import com.kreative.dotbamcrak.utility.ParseException;
import com.kreative.dotbamcrak.utility.ParseUtilities;

public class TileSetInfo {
	private File resourceDirectory;
	private File tilesetFile;
	
	private String source = "";
	private String name = "";
	private int imageWidth = 0;
	private int imageHeight = 0;
	private int tileWidth = 0;
	private int tileHeight = 0;
	private int borderLeft = 0;
	private int borderTop = 0;
	private int borderRight = 0;
	private int borderBottom = 0;
	
	private Map<String,String> tiles = new HashMap<String,String>();
	private Map<String,Set<String>> suits = new HashMap<String,Set<String>>();
	private Map<String,Set<String>> ranks = new HashMap<String,Set<String>>();
	private Map<String,Set<String>> honors = new HashMap<String,Set<String>>();
	private Map<String,Set<String>> bonuses = new HashMap<String,Set<String>>();
	private Set<List<String>> matches = new HashSet<List<String>>();
	
	public TileSetInfo(File in) throws FileNotFoundException, ParseException {
		if (in.isDirectory()) {
			resourceDirectory = in;
			if (in.getName().endsWith(".mts")) {
				tilesetFile = new File(in, in.getName());
			} else {
				tilesetFile = new File(in, in.getName() + ".mts");
			}
			for (File f : in.listFiles()) {
				if (f.getName().endsWith(".mts")) {
					tilesetFile = f;
					break;
				}
			}
		} else {
			resourceDirectory = in.getParentFile();
			tilesetFile = in;
		}
		processDirectives(new Scanner(tilesetFile), resourceDirectory);
	}
	
	public TileSetInfo(File tilesetFile, File resourceDirectory) throws FileNotFoundException, ParseException {
		this.tilesetFile = tilesetFile;
		this.resourceDirectory = resourceDirectory;
		processDirectives(new Scanner(tilesetFile), resourceDirectory);
	}
	
	private class ProcessingVariables {
		int cellWidth = 0;
		int cellHeight = 0;
		boolean addBorder = false;
	}
	
	private void processDirectives(Scanner in, File resourceDirectory) throws ParseException {
		ProcessingVariables vars = new ProcessingVariables();
		int lineNumber = 0;
		while (in.hasNextLine()) {
			lineNumber++;
			String line = in.nextLine().trim();
			if (line.length() > 0 && !line.startsWith("#")) {
				String[] args = line.split("\\s*\t\\s*");
				processDirective(args, resourceDirectory, vars, lineNumber);
			}
		}
		if (imageWidth == 0 || imageHeight == 0 || tileWidth == 0 || tileHeight == 0) throw new ParseException("Missing cellsize directive.");
		if (tiles.isEmpty()) throw new ParseException("Missing tile directives.");
		if (matches.isEmpty()) throw new ParseException("Missing match directives.");
	}
	
	private void processDirective(String[] args, File resourceDirectory, ProcessingVariables vars, int lineNumber) throws ParseException {
		if (args[0].equalsIgnoreCase("source")) {
			source = ParseUtilities.index(args, 1, lineNumber).trim();
		} else if (args[0].equalsIgnoreCase("name")) {
			name = ParseUtilities.index(args, 1, lineNumber).trim();
		} else if (args[0].equalsIgnoreCase("image")) {
			// ignored
		} else if (args[0].equalsIgnoreCase("vector")) {
			// ignored
		} else if (args[0].equalsIgnoreCase("cellsize")) {
			String[] subop = ParseUtilities.index(args, 1, lineNumber).split("\\s*,\\s*");
			vars.cellWidth = ParseUtilities.parseInt(ParseUtilities.index(subop, 0, lineNumber), lineNumber);
			vars.cellHeight = ParseUtilities.parseInt(ParseUtilities.index(subop, 1, lineNumber), lineNumber);
			ParseUtilities.checkRange(vars.cellWidth >= 1 && vars.cellHeight >= 1, lineNumber);
			imageWidth = vars.addBorder ? (vars.cellWidth + borderLeft + borderRight) : vars.cellWidth;
			imageHeight = vars.addBorder ? (vars.cellHeight + borderTop + borderBottom) : vars.cellHeight;
			tileWidth = vars.addBorder ? vars.cellWidth : (vars.cellWidth - borderLeft - borderRight);
			tileHeight = vars.addBorder ? vars.cellHeight : (vars.cellHeight - borderTop - borderBottom);
		} else if (args[0].equalsIgnoreCase("cellborder")) {
			String[] subop = ParseUtilities.index(args, 1, lineNumber).split("\\s*,\\s*");
			vars.addBorder = false;
			borderLeft = ParseUtilities.parseInt(ParseUtilities.index(subop, 0, lineNumber), lineNumber);
			borderTop = ParseUtilities.parseInt(ParseUtilities.index(subop, 1, lineNumber), lineNumber);
			borderRight = ParseUtilities.parseInt(ParseUtilities.index(subop, 2, lineNumber), lineNumber);
			borderBottom = ParseUtilities.parseInt(ParseUtilities.index(subop, 3, lineNumber), lineNumber);
			ParseUtilities.checkRange(borderLeft >= 0 && borderTop >= 0 && borderRight >= 0 && borderBottom >= 0, lineNumber);
			imageWidth = vars.cellWidth;
			imageHeight = vars.cellHeight;
			tileWidth = vars.cellWidth - borderLeft - borderRight;
			tileHeight = vars.cellHeight - borderTop - borderBottom;
		} else if (args[0].equalsIgnoreCase("addborder")) {
			String[] subop = ParseUtilities.index(args, 1, lineNumber).split("\\s*,\\s*");
			vars.addBorder = true;
			borderLeft = ParseUtilities.parseInt(ParseUtilities.index(subop, 0, lineNumber), lineNumber);
			borderTop = ParseUtilities.parseInt(ParseUtilities.index(subop, 1, lineNumber), lineNumber);
			borderRight = ParseUtilities.parseInt(ParseUtilities.index(subop, 2, lineNumber), lineNumber);
			borderBottom = ParseUtilities.parseInt(ParseUtilities.index(subop, 3, lineNumber), lineNumber);
			ParseUtilities.checkRange(borderLeft >= 0 && borderTop >= 0 && borderRight >= 0 && borderBottom >= 0, lineNumber);
			imageWidth = vars.cellWidth + borderLeft + borderRight;
			imageHeight = vars.cellHeight + borderTop + borderBottom;
			tileWidth = vars.cellWidth;
			tileHeight = vars.cellHeight;
		} else if (args[0].equalsIgnoreCase("hilighttype")) {
			// ignored
		} else if (args[0].equalsIgnoreCase("blanktile")) {
			// ignored
		} else if (args[0].equalsIgnoreCase("blankrect")) {
			// ignored
		} else if (args[0].equalsIgnoreCase("bgcolor")) {
			// ignored
		} else if (args[0].equalsIgnoreCase("bgpattern")) {
			// ignored
		} else if (args[0].equalsIgnoreCase("bgimage")) {
			// ignored
		} else if (args[0].equalsIgnoreCase("tile")) {
			String id = ParseUtilities.index(args, 1, lineNumber);
			String name = ParseUtilities.index(args, 3, "");
			tiles.put(id, name);
		} else if (args[0].equalsIgnoreCase("suit")) {
			String id = ParseUtilities.index(args, 1, lineNumber);
			String[] tileIDs = ParseUtilities.index(args, 2, lineNumber).split("\\s*,\\s*");
			Set<String> tiles = new HashSet<String>();
			tiles.addAll(Arrays.asList(tileIDs));
			suits.put(id, tiles);
		} else if (args[0].equalsIgnoreCase("rank")) {
			String id = ParseUtilities.index(args, 1, lineNumber);
			String[] tileIDs = ParseUtilities.index(args, 2, lineNumber).split("\\s*,\\s*");
			Set<String> tiles = new HashSet<String>();
			tiles.addAll(Arrays.asList(tileIDs));
			ranks.put(id, tiles);
		} else if (args[0].equalsIgnoreCase("honor")) {
			String id = ParseUtilities.index(args, 1, lineNumber);
			String[] tileIDs = ParseUtilities.index(args, 2, lineNumber).split("\\s*,\\s*");
			Set<String> tiles = new HashSet<String>();
			tiles.addAll(Arrays.asList(tileIDs));
			honors.put(id, tiles);
		} else if (args[0].equalsIgnoreCase("bonus")) {
			String id = ParseUtilities.index(args, 1, lineNumber);
			String[] tileIDs = ParseUtilities.index(args, 2, lineNumber).split("\\s*,\\s*");
			Set<String> tiles = new HashSet<String>();
			tiles.addAll(Arrays.asList(tileIDs));
			bonuses.put(id, tiles);
		} else if (args[0].equalsIgnoreCase("match")) {
			String[] tileIDs = ParseUtilities.index(args, 1, lineNumber).split("\\s*,\\s*");
			matches.add(Arrays.asList(tileIDs));
		} else {
			throw new ParseException("Unknown directive", lineNumber);
		}
	}
	
	public File getResourceDirectory() {
		return resourceDirectory;
	}
	
	public File getFile() {
		return tilesetFile;
	}
	
	public String getSourceName() {
		return source;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		if (name.length() == 0) {
			return source;
		} else if (source.length() == 0 || name.startsWith(source) || name.endsWith(source)) {
			return name;
		} else {
			return source + " " + name;
		}
	}
	
	public int getImageWidth() {
		return imageWidth;
	}
	
	public int getImageHeight() {
		return imageHeight;
	}
	
	public Dimension getImageSize() {
		return new Dimension(imageWidth, imageHeight);
	}
	
	public int getTileWidth() {
		return tileWidth;
	}
	
	public int getTileHeight() {
		return tileHeight;
	}
	
	public Dimension getTileSize() {
		return new Dimension(tileWidth, tileHeight);
	}
	
	public Insets getImageInsets() {
		return new Insets(borderTop, borderLeft, borderBottom, borderRight);
	}
	
	public int getTileCount() {
		return tiles.size();
	}
	
	public String getTileName(String id) {
		return tiles.get(id);
	}
	
	public Collection<String> getTileNames() {
		return tiles.values();
	}
	
	public Map<String,String> getTileNameMap() {
		return Collections.unmodifiableMap(tiles);
	}
	
	public int getSuitCount() {
		return suits.size();
	}
	
	public Set<String> getSuit(String id) {
		return suits.get(id);
	}
	
	public Collection<Set<String>> getSuits() {
		return suits.values();
	}
	
	public Map<String,Set<String>> getSuitMap() {
		return Collections.unmodifiableMap(suits);
	}
	
	public int getRankCount() {
		return ranks.size();
	}
	
	public Set<String> getRank(String id) {
		return ranks.get(id);
	}
	
	public Collection<Set<String>> getRanks() {
		return ranks.values();
	}
	
	public Map<String,Set<String>> getRankMap() {
		return Collections.unmodifiableMap(ranks);
	}
	
	public int getHonorSetCount() {
		return honors.size();
	}
	
	public Set<String> getHonorSet(String id) {
		return honors.get(id);
	}
	
	public Collection<Set<String>> getHonorSets() {
		return honors.values();
	}
	
	public Map<String,Set<String>> getHonorSetMap() {
		return Collections.unmodifiableMap(honors);
	}
	
	public int getBonusSetCount() {
		return bonuses.size();
	}
	
	public Set<String> getBonusSet(String id) {
		return bonuses.get(id);
	}
	
	public Collection<Set<String>> getBonusSets() {
		return bonuses.values();
	}
	
	public Map<String,Set<String>> getBonusSetMap() {
		return Collections.unmodifiableMap(bonuses);
	}
	
	public int getMatchSetCount() {
		return matches.size();
	}
	
	public Collection<List<String>> getMatchSets() {
		return Collections.unmodifiableSet(matches);
	}
	
	public TileSet createTileSet() throws IOException, ParseException {
		return new TileSet(tilesetFile, resourceDirectory);
	}
}
