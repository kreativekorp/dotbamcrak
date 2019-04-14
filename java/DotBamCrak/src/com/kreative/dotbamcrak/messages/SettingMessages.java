package com.kreative.dotbamcrak.messages;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;

public class SettingMessages extends ListResourceBundle {
	public static final ResourceBundle instance = ResourceBundle.getBundle("com.kreative.dotbamcrak.messages.SettingMessages");
	
	public static final String BEHAVIOR_TITLE = "options.title";
	public static final String BEHAVIOR_CURSOR = "options.cursor";
	public static final String BEHAVIOR_CURSOR_ARROW = "options.cursor.arrow";
	public static final String BEHAVIOR_CURSOR_HAND = "options.cursor.hand";
	public static final String BEHAVIOR_CURSOR_CROSS = "options.cursor.cross";
	public static final String BEHAVIOR_CURSOR_ON_FREE_TILE_ONLY = "options.cursorOnFreeTileOnly";
	public static final String BEHAVIOR_DESELECT_ON_BACKGROUND = "options.deselectOnBackground";
	public static final String BEHAVIOR_DESELECT_ON_NON_FREE_TILE = "options.deselectOnNonFreeTile";
	public static final String BEHAVIOR_WARN_ON_NON_FREE_TILE = "options.warnOnNonFreeTile";
	public static final String BEHAVIOR_RESELECT_ON_NON_MATCHING_TILE = "options.reselectOnNonMatchingTile";
	public static final String BEHAVIOR_WARN_ON_NON_MATCHING_TILE = "options.warnOnNonMatchingTile";
	public static final String BEHAVIOR_WARN_ON_NO_FREE_TILES = "options.warnOnNoFreeTiles";
	public static final String BEHAVIOR_ALLOW_SHUFFLE_ON_NO_FREE_TILES = "options.allowShuffleOnNoFreeTiles";
	public static final String BEHAVIOR_ALLOW_UNDO_ON_NO_FREE_TILES = "options.allowUndoOnNoFreeTiles";
	public static final String BEHAVIOR_ALLOW_START_OVER_ON_NO_FREE_TILES = "options.allowStartOverOnNoFreeTiles";
	public static final String BEHAVIOR_ALLOW_NEW_GAME_ON_NO_FREE_TILES = "options.allowNewGameOnNoFreeTiles";
	public static final String BEHAVIOR_DISPLAY_FORTUNE = "options.displayFortune";
	public static final String BEHAVIOR_DOUBLE_CLICK_TO_START_NEW_GAME = "options.doubleClickToStartNewGame";
	public static final String BEHAVIOR_UNDO_PENALTY = "options.undoPenalty";
	public static final String BEHAVIOR_REDO_PENALTY = "options.redoPenalty";
	public static final String BEHAVIOR_SHOW_MATCH_PENALTY = "options.showMatchPenalty";
	public static final String BEHAVIOR_SHOW_ALL_MATCHES_PENALTY = "options.showAllMatchesPenalty";
	public static final String BEHAVIOR_SHUFFLE_PENALTY = "options.shufflePenalty";
	public static final String BEHAVIOR_AUTOPLAY_PENALTY = "options.autoPlayPenalty";
	public static final String BEHAVIOR_AUTOPLAY_SPEED = "options.autoPlaySpeed";
	public static final String BEHAVIOR_HIGH_SCORE_COUNT = "options.highScoreCount";
	public static final String BEHAVIOR_CLOSE = "options.close";
	
	private Object[][] contents = {
			{ BEHAVIOR_TITLE, "Change Behavior" },
			{ BEHAVIOR_CURSOR, "Cursor:" },
			{ BEHAVIOR_CURSOR_ARROW, "Arrow Pointer" },
			{ BEHAVIOR_CURSOR_HAND, "Hand Pointer" },
			{ BEHAVIOR_CURSOR_CROSS, "Crosshair" },
			{ BEHAVIOR_CURSOR_ON_FREE_TILE_ONLY, "Show cursor over free tiles only." },
			{ BEHAVIOR_DESELECT_ON_BACKGROUND, "Deselect tiles when the background is clicked." },
			{ BEHAVIOR_DESELECT_ON_NON_FREE_TILE, "Deselect tiles when a non-free tile is clicked." },
			{ BEHAVIOR_WARN_ON_NON_FREE_TILE, "Show warning when a non-free tile is clicked." },
			{ BEHAVIOR_RESELECT_ON_NON_MATCHING_TILE, "Change selected tile when a non-matching tile is clicked." },
			{ BEHAVIOR_WARN_ON_NON_MATCHING_TILE, "Show warning when a non-matching tile is clicked." },
			{ BEHAVIOR_WARN_ON_NO_FREE_TILES, "Show message when there are no more available moves." },
			{ BEHAVIOR_ALLOW_SHUFFLE_ON_NO_FREE_TILES, "Show Shuffle option in the \"no more moves\" message." },
			{ BEHAVIOR_ALLOW_UNDO_ON_NO_FREE_TILES, "Show Undo option in the \"no more moves\" message." },
			{ BEHAVIOR_ALLOW_START_OVER_ON_NO_FREE_TILES, "Show Start Over option in the \"no more moves\" message." },
			{ BEHAVIOR_ALLOW_NEW_GAME_ON_NO_FREE_TILES, "Show New Game option in the \"no more moves\" message." },
			{ BEHAVIOR_DISPLAY_FORTUNE, "Display fortunes upon completing the board." },
			{ BEHAVIOR_DOUBLE_CLICK_TO_START_NEW_GAME, "Double click finished game to start new game." },
			{ BEHAVIOR_UNDO_PENALTY, "Time penalty for Undo:" },
			{ BEHAVIOR_REDO_PENALTY, "Time penalty for Redo:" },
			{ BEHAVIOR_SHOW_MATCH_PENALTY, "Time penalty for Show Match:" },
			{ BEHAVIOR_SHOW_ALL_MATCHES_PENALTY, "Time penalty for Show All Matches:" },
			{ BEHAVIOR_SHUFFLE_PENALTY, "Time penalty for Shuffle Tiles:" },
			{ BEHAVIOR_AUTOPLAY_PENALTY, "Time penalty for Autoplay:" },
			{ BEHAVIOR_AUTOPLAY_SPEED, "Delay between Autoplay moves:" },
			{ BEHAVIOR_HIGH_SCORE_COUNT, "Number of high scores to keep:" },
			{ BEHAVIOR_CLOSE, "OK" },
	};
	
	protected Object[][] getContents() {
		return contents;
	}
}
