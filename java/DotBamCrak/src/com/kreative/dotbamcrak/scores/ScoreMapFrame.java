package com.kreative.dotbamcrak.scores;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import com.kreative.dotbamcrak.messages.ScoreMessages;

public class ScoreMapFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private ScoreMapView view;
	
	public ScoreMapFrame(
			final Runnable saveCallback,
			final Runnable newGameCallback,
			final ScoreMap scores,
			int max,
			final Score editableScore,
			String layoutName
	) {
		super(ScoreMessages.instance.getString(ScoreMessages.HIGH_SCORES_TITLE));
		view = new ScoreMapView(saveCallback, newGameCallback, scores, max, editableScore, layoutName);
		setContentPane(view);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (saveCallback != null) saveCallback.run();
				dispose();
			}
		});
	}
	
	public JTextField getScoreNameField() {
		return view.getScoreNameField();
	}
}
