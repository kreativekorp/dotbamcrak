package com.kreative.dotbamcrak.messages;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;

public class MenuMessages extends ListResourceBundle {
	public static final ResourceBundle instance = ResourceBundle.getBundle("com.kreative.dotbamcrak.messages.MenuMessages");
	
	public static final String MENU_GAME = "menu.game";
	public static final String MENU_GAME_NEW = "menu.game.new";
	public static final String MENU_GAME_PAUSE_RESUME = "menu.game.pauseresume";
	public static final String MENU_GAME_PAUSE = "menu.game.pause";
	public static final String MENU_GAME_RESUME = "menu.game.resume";
	public static final String MENU_GAME_UNDO = "menu.game.undo";
	public static final String MENU_GAME_REDO = "menu.game.redo";
	public static final String MENU_GAME_START_OVER = "menu.game.startover";
	public static final String MENU_GAME_SHUFFLE = "menu.game.shuffle";
	public static final String MENU_GAME_SHOW_RANDOM_MATCH = "menu.game.showrandommatch";
	public static final String MENU_GAME_SHOW_SELECTED_MATCH = "menu.game.showselectedmatch";
	public static final String MENU_GAME_SHOW_ALL_MATCHES = "menu.game.showallmatches";
	public static final String MENU_GAME_AUTOPLAY = "menu.game.autoplay";
	public static final String MENU_GAME_SCORES = "menu.game.scores";
	public static final String MENU_GAME_EXIT = "menu.game.exit";
	public static final String MENU_GAME_QUIT = "menu.game.quit";
	public static final String MENU_TILE_SET = "menu.tileset";
	public static final String MENU_LAYOUT = "menu.layout";
	public static final String MENU_FORTUNE_SET = "menu.fortuneset";
	public static final String MENU_OPTIONS = "menu.options";
	public static final String MENU_OPTIONS_FULL_SCREEN = "menu.options.fullscreen";
	public static final String MENU_OPTIONS_ACTUAL_SIZE = "menu.options.actualsize";
	public static final String MENU_OPTIONS_THREE_QUARTER_SIZE = "menu.options.threeqtrsize";
	public static final String MENU_OPTIONS_HALF_SIZE = "menu.options.halfsize";
	public static final String MENU_OPTIONS_CENTER_WINDOW = "menu.options.center";
	public static final String MENU_OPTIONS_RESET_BKGND = "menu.options.resetbg";
	public static final String MENU_OPTIONS_SET_BKGND_COLOR = "menu.options.bgcolor";
	public static final String MENU_OPTIONS_SET_BKGND_COLOR_TITLE = "menu.options.bgcolor.title";
	public static final String MENU_OPTIONS_SET_BKGND_IMAGE = "menu.options.bgimage";
	public static final String MENU_OPTIONS_SET_BKGND_IMAGE_TITLE = "menu.options.bgimage.title";
	public static final String MENU_OPTIONS_BEHAVIOR = "menu.options.behavior";
	
	private Object[][] contents = {
			{ MENU_GAME, "Game" },
			{ MENU_GAME_NEW, "New Game" },
			{ MENU_GAME_PAUSE_RESUME, "Pause/Resume" },
			{ MENU_GAME_PAUSE, "Pause" },
			{ MENU_GAME_RESUME, "Resume" },
			{ MENU_GAME_UNDO, "Undo" },
			{ MENU_GAME_REDO, "Redo" },
			{ MENU_GAME_START_OVER, "Start Over" },
			{ MENU_GAME_SHUFFLE, "Shuffle Tiles" },
			{ MENU_GAME_SHOW_RANDOM_MATCH, "Show Random Match" },
			{ MENU_GAME_SHOW_SELECTED_MATCH, "Show Selected Match" },
			{ MENU_GAME_SHOW_ALL_MATCHES, "Show All Matches" },
			{ MENU_GAME_AUTOPLAY, "Autoplay" },
			{ MENU_GAME_SCORES, "Scores" },
			{ MENU_GAME_EXIT, "Exit" },
			{ MENU_GAME_QUIT, "Quit" },
			{ MENU_TILE_SET, "Tiles" },
			{ MENU_LAYOUT, "Layout" },
			{ MENU_FORTUNE_SET, "Fortunes" },
			{ MENU_OPTIONS, "Options" },
			{ MENU_OPTIONS_FULL_SCREEN, "Full Screen" },
			{ MENU_OPTIONS_ACTUAL_SIZE, "Actual Size" },
			{ MENU_OPTIONS_THREE_QUARTER_SIZE, "Three-Quarter Size" },
			{ MENU_OPTIONS_HALF_SIZE, "Half Size" },
			{ MENU_OPTIONS_CENTER_WINDOW, "Center Window" },
			{ MENU_OPTIONS_RESET_BKGND, "Reset Background" },
			{ MENU_OPTIONS_SET_BKGND_COLOR, "Set Background Color..." },
			{ MENU_OPTIONS_SET_BKGND_COLOR_TITLE, "Set Background Color" },
			{ MENU_OPTIONS_SET_BKGND_IMAGE, "Set Background Image..." },
			{ MENU_OPTIONS_SET_BKGND_IMAGE_TITLE, "Set Background Image" },
			{ MENU_OPTIONS_BEHAVIOR, "Change Behavior..." },
	};
	
	protected Object[][] getContents() {
		return contents;
	}
}
