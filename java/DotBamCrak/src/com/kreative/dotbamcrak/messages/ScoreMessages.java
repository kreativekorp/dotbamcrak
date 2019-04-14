package com.kreative.dotbamcrak.messages;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;

public class ScoreMessages extends ListResourceBundle {
	public static final ResourceBundle instance = ResourceBundle.getBundle("com.kreative.dotbamcrak.messages.ScoreMessages");
	
	public static final String HIGH_SCORES_TITLE = "scores.title";
	public static final String HIGH_SCORES_CONGRATULATIONS = "scores.congratulations";
	public static final String HIGH_SCORES_NEW_GAME = "scores.newgame";
	public static final String HIGH_SCORES_CLOSE = "scores.close";
	
	private Object[][] contents = {
			{ HIGH_SCORES_TITLE, "High Scores" },
			{ HIGH_SCORES_CONGRATULATIONS, "Congratulations! You got a high score." },
			{ HIGH_SCORES_NEW_GAME, "New Game" },
			{ HIGH_SCORES_CLOSE, "Close" },
	};
	
	protected Object[][] getContents() {
		return contents;
	}
}
