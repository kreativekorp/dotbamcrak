package com.kreative.dotbamcrak.settings;

import java.awt.Color;
import java.awt.Cursor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import com.kreative.dotbamcrak.utility.ParseException;
import com.kreative.dotbamcrak.utility.ParseUtilities;

public class Settings {
	public Color backgroundColor = null;
	public File backgroundImage = null;
	public File tilesetResourceDirectory = null;
	public File tilesetFile = null;
	public File layoutFile = null;
	public File fortuneFile = null;
	
	public int cursor = Cursor.DEFAULT_CURSOR;
	public boolean cursorOnFreeTileOnly = false;
	public boolean deselectOnBackground = false;
	public boolean deselectOnNonFreeTile = false;
	public boolean warnOnNonFreeTile = false;
	public boolean reselectOnNonMatchingTile = true;
	public boolean warnOnNonMatchingTile = false;
	public boolean warnOnNoFreeTiles = true;
	public boolean allowShuffleOnNoFreeTiles = true;
	public boolean allowUndoOnNoFreeTiles = true;
	public boolean allowStartOverOnNoFreeTiles = true;
	public boolean allowNewGameOnNoFreeTiles = true;
	public boolean displayFortune = true;
	public boolean doubleClickToStartNewGame = true;
	public int undoPenalty = 10;
	public int redoPenalty = 5;
	public int showMatchPenalty = 60;
	public int showAllMatchesPenalty = 600;
	public int shufflePenalty = 120;
	public int autoPlayPenalty = 60;
	public int autoPlaySpeed = 1000;
	public int highScoreCount = 10;
	
	public void readSettings(File in) throws IOException {
		readSettings(new Scanner(in));
	}
	
	public void readSettings(Scanner in) {
		int lineNumber = 0;
		while (in.hasNextLine()) {
			lineNumber++;
			String line = in.nextLine().trim();
			if (line.length() > 0 && !line.startsWith("#")) {
				String[] args = line.split("\\s*\t\\s*");
				try {
					processDirective(args, lineNumber);
				} catch (ParseException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}
	
	private void processDirective(String[] args, int lineNumber) throws ParseException {
		if (args[0].equalsIgnoreCase("backgroundColor")) {
			String colorString = ParseUtilities.index(args, 1, "").trim();
			if (colorString.length() == 0 || colorString.equalsIgnoreCase("none") || colorString.equalsIgnoreCase("null")) {
				backgroundColor = null;
			} else {
				backgroundColor = ParseUtilities.parseColor(colorString, lineNumber);
			}
		} else if (args[0].equalsIgnoreCase("backgroundImage")) {
			String imageString = ParseUtilities.index(args, 1, "").trim();
			if (imageString.length() == 0 || imageString.equalsIgnoreCase("none") || imageString.equalsIgnoreCase("null")) {
				backgroundImage = null;
			} else {
				backgroundImage = new File(imageString);
			}
		} else if (args[0].equalsIgnoreCase("tilesetResourceDirectory")) {
			tilesetResourceDirectory = new File(ParseUtilities.index(args, 1, lineNumber));
		} else if (args[0].equalsIgnoreCase("tilesetFile")) {
			tilesetFile = new File(ParseUtilities.index(args, 1, lineNumber));
		} else if (args[0].equalsIgnoreCase("layoutFile")) {
			layoutFile = new File(ParseUtilities.index(args, 1, lineNumber));
		} else if (args[0].equalsIgnoreCase("fortuneFile")) {
			fortuneFile = new File(ParseUtilities.index(args, 1, lineNumber));
		} else if (args[0].equalsIgnoreCase("cursor")) {
			String cursorString = ParseUtilities.index(args, 1, "").trim();
			if (cursorString.equalsIgnoreCase("cross")) cursor = Cursor.CROSSHAIR_CURSOR;
			else if (cursorString.equalsIgnoreCase("hand")) cursor = Cursor.HAND_CURSOR;
			else if (cursorString.equalsIgnoreCase("arrow")) cursor = Cursor.DEFAULT_CURSOR;
			else throw new ParseException("Invalid value", lineNumber);
		} else if (args[0].equalsIgnoreCase("cursorOnFreeTileOnly")) {
			cursorOnFreeTileOnly = ParseUtilities.parseBoolean(ParseUtilities.index(args, 1, lineNumber), lineNumber);
		} else if (args[0].equalsIgnoreCase("deselectOnBackground")) {
			deselectOnBackground = ParseUtilities.parseBoolean(ParseUtilities.index(args, 1, lineNumber), lineNumber);
		} else if (args[0].equalsIgnoreCase("deselectOnNonFreeTile")) {
			deselectOnNonFreeTile = ParseUtilities.parseBoolean(ParseUtilities.index(args, 1, lineNumber), lineNumber);
		} else if (args[0].equalsIgnoreCase("warnOnNonFreeTile")) {
			warnOnNonFreeTile = ParseUtilities.parseBoolean(ParseUtilities.index(args, 1, lineNumber), lineNumber);
		} else if (args[0].equalsIgnoreCase("reselectOnNonMatchingTile")) {
			reselectOnNonMatchingTile = ParseUtilities.parseBoolean(ParseUtilities.index(args, 1, lineNumber), lineNumber);
		} else if (args[0].equalsIgnoreCase("warnOnNonMatchingTile")) {
			warnOnNonMatchingTile = ParseUtilities.parseBoolean(ParseUtilities.index(args, 1, lineNumber), lineNumber);
		} else if (args[0].equalsIgnoreCase("warnOnNoFreeTiles")) {
			warnOnNoFreeTiles = ParseUtilities.parseBoolean(ParseUtilities.index(args, 1, lineNumber), lineNumber);
		} else if (args[0].equalsIgnoreCase("allowShuffleOnNoFreeTiles")) {
			allowShuffleOnNoFreeTiles = ParseUtilities.parseBoolean(ParseUtilities.index(args, 1, lineNumber), lineNumber);
		} else if (args[0].equalsIgnoreCase("allowUndoOnNoFreeTiles")) {
			allowUndoOnNoFreeTiles = ParseUtilities.parseBoolean(ParseUtilities.index(args, 1, lineNumber), lineNumber);
		} else if (args[0].equalsIgnoreCase("allowStartOverOnNoFreeTiles")) {
			allowStartOverOnNoFreeTiles = ParseUtilities.parseBoolean(ParseUtilities.index(args, 1, lineNumber), lineNumber);
		} else if (args[0].equalsIgnoreCase("allowNewGameOnNoFreeTiles")) {
			allowNewGameOnNoFreeTiles = ParseUtilities.parseBoolean(ParseUtilities.index(args, 1, lineNumber), lineNumber);
		} else if (args[0].equalsIgnoreCase("displayFortune")) {
			displayFortune = ParseUtilities.parseBoolean(ParseUtilities.index(args, 1, lineNumber), lineNumber);
		} else if (args[0].equalsIgnoreCase("doubleClickToStartNewGame")) {
			doubleClickToStartNewGame = ParseUtilities.parseBoolean(ParseUtilities.index(args, 1, lineNumber), lineNumber);
		} else if (args[0].equalsIgnoreCase("undoPenalty")) {
			undoPenalty = ParseUtilities.parseInt(ParseUtilities.index(args, 1, lineNumber), lineNumber);
			if (undoPenalty < 0) undoPenalty = 0;
		} else if (args[0].equalsIgnoreCase("redoPenalty")) {
			redoPenalty = ParseUtilities.parseInt(ParseUtilities.index(args, 1, lineNumber), lineNumber);
			if (redoPenalty < 0) redoPenalty = 0;
		} else if (args[0].equalsIgnoreCase("showMatchPenalty")) {
			showMatchPenalty = ParseUtilities.parseInt(ParseUtilities.index(args, 1, lineNumber), lineNumber);
			if (showMatchPenalty < 0) showMatchPenalty = 0;
		} else if (args[0].equalsIgnoreCase("showAllMatchesPenalty")) {
			showAllMatchesPenalty = ParseUtilities.parseInt(ParseUtilities.index(args, 1, lineNumber), lineNumber);
			if (showAllMatchesPenalty < 0) showAllMatchesPenalty = 0;
		} else if (args[0].equalsIgnoreCase("shufflePenalty")) {
			shufflePenalty = ParseUtilities.parseInt(ParseUtilities.index(args, 1, lineNumber), lineNumber);
			if (shufflePenalty < 0) shufflePenalty = 0;
		} else if (args[0].equalsIgnoreCase("autoPlayPenalty")) {
			autoPlayPenalty = ParseUtilities.parseInt(ParseUtilities.index(args, 1, lineNumber), lineNumber);
			if (autoPlayPenalty < 0) autoPlayPenalty = 0;
		} else if (args[0].equalsIgnoreCase("autoPlaySpeed")) {
			autoPlaySpeed = ParseUtilities.parseInt(ParseUtilities.index(args, 1, lineNumber), lineNumber);
			if (autoPlaySpeed < 10) autoPlaySpeed = 10;
		} else if (args[0].equalsIgnoreCase("highScoreCount")) {
			highScoreCount = ParseUtilities.parseInt(ParseUtilities.index(args, 1, lineNumber), lineNumber);
			if (highScoreCount < 2) highScoreCount = 2;
		} else {
			throw new ParseException("Unknown directive", lineNumber);
		}
	}
	
	public void writeSettings(File out) throws IOException {
		writeSettings(new PrintWriter(new OutputStreamWriter(new FileOutputStream(out)), true));
	}
	
	public void writeSettings(PrintWriter out) {
		if (backgroundColor == null) {
			out.println("backgroundColor\tnone");
		} else {
			String s = "000000" + Integer.toHexString(backgroundColor.getRGB());
			s = s.substring(s.length() - 6).toUpperCase();
			out.println("backgroundColor\t#" + s);
		}
		if (backgroundImage == null) {
			out.println("backgroundImage\tnone");
		} else {
			out.println("backgroundImage\t" + backgroundImage.getAbsolutePath());
		}
		if (tilesetResourceDirectory != null) {
			out.println("tilesetResourceDirectory\t" + tilesetResourceDirectory.getAbsolutePath());
		}
		if (tilesetFile != null) {
			out.println("tilesetFile\t" + tilesetFile.getAbsolutePath());
		}
		if (layoutFile != null) {
			out.println("layoutFile\t" + layoutFile.getAbsolutePath());
		}
		if (fortuneFile != null) {
			out.println("fortuneFile\t" + fortuneFile.getAbsolutePath());
		}
		switch (cursor) {
		case Cursor.CROSSHAIR_CURSOR: out.println("cursor\tcross"); break;
		case Cursor.HAND_CURSOR: out.println("cursor\thand"); break;
		default: out.println("cursor\tarrow"); break;
		}
		out.println("cursorOnFreeTileOnly\t" + (cursorOnFreeTileOnly ? "true" : "false"));
		out.println("deselectOnBackground\t" + (deselectOnBackground ? "true" : "false"));
		out.println("deselectOnNonFreeTile\t" + (deselectOnNonFreeTile ? "true" : "false"));
		out.println("warnOnNonFreeTile\t" + (warnOnNonFreeTile ? "true" : "false"));
		out.println("reselectOnNonMatchingTile\t" + (reselectOnNonMatchingTile ? "true" : "false"));
		out.println("warnOnNonMatchingTile\t" + (warnOnNonMatchingTile ? "true" : "false"));
		out.println("warnOnNoFreeTiles\t" + (warnOnNoFreeTiles ? "true" : "false"));
		out.println("allowShuffleOnNoFreeTiles\t" + (allowShuffleOnNoFreeTiles ? "true" : "false"));
		out.println("allowUndoOnNoFreeTiles\t" + (allowUndoOnNoFreeTiles ? "true" : "false"));
		out.println("allowStartOverOnNoFreeTiles\t" + (allowStartOverOnNoFreeTiles ? "true" : "false"));
		out.println("allowNewGameOnNoFreeTiles\t" + (allowNewGameOnNoFreeTiles ? "true" : "false"));
		out.println("displayFortune\t" + (displayFortune ? "true" : "false"));
		out.println("doubleClickToStartNewGame\t" + (doubleClickToStartNewGame ? "true" : "false"));
		out.println("undoPenalty\t" + undoPenalty);
		out.println("redoPenalty\t" + redoPenalty);
		out.println("showMatchPenalty\t" + showMatchPenalty);
		out.println("showAllMatchesPenalty\t" + showAllMatchesPenalty);
		out.println("shufflePenalty\t" + shufflePenalty);
		out.println("autoPlayPenalty\t" + autoPlayPenalty);
		out.println("autoPlaySpeed\t" + autoPlaySpeed);
		out.println("highScoreCount\t" + highScoreCount);
	}
}
