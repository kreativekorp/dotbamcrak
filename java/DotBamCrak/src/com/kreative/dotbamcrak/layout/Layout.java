package com.kreative.dotbamcrak.layout;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import com.kreative.dotbamcrak.utility.ParseException;
import com.kreative.dotbamcrak.utility.ParseUtilities;

public class Layout {
	private File file = null;
	
	private String source = "";
	private String name = "";
	private int boardWidth = 0;
	private int boardHeight = 0;
	private int boardDepth = 0;
	private int tileWidth = 0;
	private int tileHeight = 0;
	private int tileDepth = 0;
	private int windowWidth = 0;
	private int windowHeight = 0;
	
	private int tileCount = 0;
	private boolean[][][] tiles = null;
	
	public Layout(File in) throws FileNotFoundException, ParseException {
		this.file = in;
		processDirectives(new Scanner(in));
		if (source.equals("") && name.equals("")) {
			name = in.getName().trim();
			while (name.startsWith(".")) {
				name = name.substring(1).trim();
			}
			if (name.contains(".")) {
				name = name.substring(0, name.lastIndexOf('.'));
			}
		}
	}
	
	private void processDirectives(Scanner in) throws ParseException {
		int lineNumber = 0;
		while (in.hasNextLine()) {
			lineNumber++;
			String line = in.nextLine().trim();
			if (line.length() > 0 && !line.startsWith("#")) {
				String[] args = line.split("\\s*\t\\s*");
				processDirective(args, lineNumber);
			}
		}
		if (boardWidth == 0 || boardHeight == 0 || boardDepth == 0 || tiles == null) throw new ParseException("Missing boardsize directive.");
		if (tileWidth == 0 || tileHeight == 0 || tileDepth == 0) throw new ParseException("Missing tilesize directive.");
		if (windowWidth == 0 || windowHeight == 0) throw new ParseException("Missing windowsize directive.");
		if (tileCount == 0) throw new ParseException("Missing tile directives.");
		if ((tileCount & 1) != 0) throw new ParseException("Uneven number of tile directives.");
	}
	
	private void processDirective(String[] args, int lineNumber) throws ParseException {
		if (args[0].equalsIgnoreCase("source")) {
			source = ParseUtilities.index(args, 1, lineNumber).trim();
		} else if (args[0].equalsIgnoreCase("name")) {
			name = ParseUtilities.index(args, 1, lineNumber).trim();
		} else if (args[0].equalsIgnoreCase("boardsize")) {
			String[] subop = ParseUtilities.index(args, 1, lineNumber).split("\\s*,\\s*");
			boardWidth = ParseUtilities.parseInt(ParseUtilities.index(subop, 0, lineNumber), lineNumber);
			boardHeight = ParseUtilities.parseInt(ParseUtilities.index(subop, 1, lineNumber), lineNumber);
			boardDepth = ParseUtilities.parseInt(ParseUtilities.index(subop, 2, lineNumber), lineNumber);
			ParseUtilities.checkRange(boardWidth >= 1 && boardHeight >= 1 && boardDepth >= 1, lineNumber);
			tileCount = 0;
			tiles = new boolean[boardDepth][boardHeight][boardWidth];
		} else if (args[0].equalsIgnoreCase("tilesize")) {
			if (boardWidth == 0 || boardHeight == 0 || boardDepth == 0) {
				throw new ParseException("Tilesize directive before boardsize directive", lineNumber);
			} else {
				String[] subop = ParseUtilities.index(args, 1, lineNumber).split("\\s*,\\s*");
				tileWidth = ParseUtilities.parseInt(ParseUtilities.index(subop, 0, lineNumber), lineNumber);
				tileHeight = ParseUtilities.parseInt(ParseUtilities.index(subop, 1, lineNumber), lineNumber);
				tileDepth = ParseUtilities.parseInt(ParseUtilities.index(subop, 2, lineNumber), lineNumber);
				ParseUtilities.checkRange(tileWidth >= 1 && tileHeight >= 1 && tileDepth >= 1 && tileWidth <= boardWidth && tileHeight <= boardHeight && tileDepth <= boardDepth, lineNumber);
			}
		} else if (args[0].equalsIgnoreCase("windowsize")) {
			if (boardWidth == 0 || boardHeight == 0 || boardDepth == 0) {
				throw new ParseException("Windowsize directive before boardsize directive", lineNumber);
			} else {
				String[] subop = ParseUtilities.index(args, 1, lineNumber).split("\\s*,\\s*");
				windowWidth = ParseUtilities.parseInt(ParseUtilities.index(subop, 0, lineNumber), lineNumber);
				windowHeight = ParseUtilities.parseInt(ParseUtilities.index(subop, 1, lineNumber), lineNumber);
				ParseUtilities.checkRange(windowWidth >= boardWidth && windowHeight >= boardHeight, lineNumber);
			}
		} else if (args[0].equalsIgnoreCase("tile")) {
			if (boardWidth == 0 || boardHeight == 0 || boardDepth == 0 || tiles == null || tileWidth == 0 || tileHeight == 0 || tileDepth == 0) {
				throw new ParseException("Tile directive before boardsize or tilesize directive", lineNumber);
			} else {
				String[] subop = ParseUtilities.index(args, 1, lineNumber).split("\\s*,\\s*");
				int x = ParseUtilities.parseInt(ParseUtilities.index(subop, 0, lineNumber), lineNumber);
				int y = ParseUtilities.parseInt(ParseUtilities.index(subop, 1, lineNumber), lineNumber);
				int z = ParseUtilities.parseInt(ParseUtilities.index(subop, 2, lineNumber), lineNumber);
				ParseUtilities.checkRange(x >= 0 && y >= 0 && z >= 0 && x+tileWidth <= boardWidth && y+tileHeight <= boardHeight && z+tileDepth <= boardDepth, lineNumber);
				checkOverlap(x, y, z, lineNumber);
				tileCount++;
				tiles[z][y][x] = true;
			}
		} else {
			throw new ParseException("Unknown directive", lineNumber);
		}
	}
	
	private void checkOverlap(int x, int y, int z, int lineNumber) throws ParseException {
		for (int zi = z-tileDepth+1; zi < z+tileDepth; zi++) {
			if (zi >= 0 && zi < boardDepth) {
				for (int yi = y-tileHeight+1; yi < y+tileHeight; yi++) {
					if (yi >= 0 && yi < boardHeight) {
						for (int xi = x-tileWidth+1; xi < x+tileWidth; xi++) {
							if (xi >= 0 && xi < boardWidth) {
								if (tiles[zi][yi][xi]) {
									throw new ParseException("Overlapping tile", lineNumber);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public File getFile() {
		return file;
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
	
	public int getBoardWidth() {
		return boardWidth;
	}
	
	public int getBoardHeight() {
		return boardHeight;
	}
	
	public int getBoardDepth() {
		return boardDepth;
	}
	
	public int getTileWidth() {
		return tileWidth;
	}
	
	public int getTileHeight() {
		return tileHeight;
	}
	
	public int getTileDepth() {
		return tileDepth;
	}
	
	public int getWindowWidth() {
		return windowWidth;
	}
	
	public int getWindowHeight() {
		return windowHeight;
	}
	
	public int getTileCount() {
		return tileCount;
	}
	
	public boolean getTile(int x, int y, int z) {
		return tiles[z][y][x];
	}
}
