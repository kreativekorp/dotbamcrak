package com.kreative.dotbamcrak.scores;

import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ScoreListView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTextField scoreNameField = null;
	
	public ScoreListView(ScoreList scores, int max, Score editableScore) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		Iterator<Score> i = scores.iterator();
		while (max > 0 && i.hasNext()) {
			max--;
			Score score = i.next();
			ScoreView view = new ScoreView(score, score == editableScore);
			if (view.getScoreNameField() != null) {
				scoreNameField = view.getScoreNameField();
			}
			add(view);
			add(Box.createVerticalStrut(4));
		}
		while (max-->0) {
			add(new JLabel(" "));
			add(Box.createVerticalStrut(4));
		}
		setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
	}
	
	public JTextField getScoreNameField() {
		return scoreNameField;
	}
}
