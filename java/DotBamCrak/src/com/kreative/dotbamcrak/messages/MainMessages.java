package com.kreative.dotbamcrak.messages;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;

public class MainMessages extends ListResourceBundle {
	public static final ResourceBundle instance = ResourceBundle.getBundle("com.kreative.dotbamcrak.messages.MainMessages");
	
	public static final String APP_TITLE = "app.title";
	public static final String ERROR_TITLE = "error.title";
	public static final String ERROR_NO_TILE_SET = "error.notileset";
	public static final String ERROR_NO_LAYOUT = "error.nolayout";
	public static final String ERROR_NO_FORTUNE_SET = "error.nofortuneset";
	public static final String MESSAGE_TITLE = "message.title";
	public static final String MESSAGE_TILE_NOT_FREE = "message.tilenotfree";
	public static final String MESSAGE_TILES_DONT_MATCH = "message.tilesdontmatch";
	public static final String MESSAGE_NO_FREE_TILES = "message.nofreetiles";
	public static final String MESSAGE_NO_FREE_TILES_GIVE_UP = "message.nofreetiles.giveup";
	public static final String MESSAGE_NO_FREE_TILES_SHUFFLE = "message.nofreetiles.shuffle";
	public static final String MESSAGE_NO_FREE_TILES_UNDO = "message.nofreetiles.undo";
	public static final String MESSAGE_NO_FREE_TILES_START_OVER = "message.nofreetiles.startover";
	public static final String MESSAGE_NO_FREE_TILES_NEW_GAME = "message.nofreetiles.newgame";
	public static final String MESSAGE_NO_SCORES = "message.noscores";
	public static final String FORTUNE_TITLE = "fortune.title";
	public static final String STATUS_TILES_LEFT = "status.tilesleft";
	public static final String STATUS_MOVES_LEFT = "status.movesleft";
	public static final String STATUS_TIME = "status.time";
	
	private Object[][] contents = {
			{ APP_TITLE, "DotBamCrak" },
			{ ERROR_TITLE, "DotBamCrak" },
			{ ERROR_NO_TILE_SET, "<html>Failed to find any tile sets. Please make sure the<br>Tile Sets folder contains at least one tile set.</html>" },
			{ ERROR_NO_LAYOUT, "<html>Failed to find any layouts. Please make sure the<br>Layouts folder contains at least one layout.</html>" },
			{ ERROR_NO_FORTUNE_SET, "<html>Failed to find any fortune sets. Please make sure the<br>Fortunes folder contains at least one fortune set.</html>" },
			{ MESSAGE_TITLE, "DotBamCrak" },
			{ MESSAGE_TILE_NOT_FREE, "That tile isn't free." },
			{ MESSAGE_TILES_DONT_MATCH, "Those tiles don't match." },
			{ MESSAGE_NO_FREE_TILES, "There are no more available moves." },
			{ MESSAGE_NO_FREE_TILES_GIVE_UP, "Give Up" },
			{ MESSAGE_NO_FREE_TILES_SHUFFLE, "Shuffle" },
			{ MESSAGE_NO_FREE_TILES_UNDO, "Undo" },
			{ MESSAGE_NO_FREE_TILES_START_OVER, "Start Over" },
			{ MESSAGE_NO_FREE_TILES_NEW_GAME, "New Game" },
			{ MESSAGE_NO_SCORES, "There aren't any high scores yet." },
			{ FORTUNE_TITLE, "Winner's Fortune" },
			{ STATUS_TILES_LEFT, "Tiles Left:" },
			{ STATUS_MOVES_LEFT, "Moves Left:" },
			{ STATUS_TIME, "Time:" },
	};
	
	protected Object[][] getContents() {
		return contents;
	}
}
