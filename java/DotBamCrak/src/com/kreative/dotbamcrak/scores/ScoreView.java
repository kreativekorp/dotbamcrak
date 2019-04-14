package com.kreative.dotbamcrak.scores;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ScoreView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTextField scoreNameField;
	
	public ScoreView(final Score score, boolean editable) {
		super(new BorderLayout());
		if (editable) {
			scoreNameField = new JTextField(score.getName(), 16);
			scoreNameField.selectAll();
			scoreNameField.getDocument().addDocumentListener(new DocumentListener() {
				public void changedUpdate(DocumentEvent e) {
					score.setName(scoreNameField.getText());
				}
				public void insertUpdate(DocumentEvent e) {
					score.setName(scoreNameField.getText());
				}
				public void removeUpdate(DocumentEvent e) {
					score.setName(scoreNameField.getText());
				}
			});
			add(scoreNameField, BorderLayout.CENTER);
		} else {
			scoreNameField = null;
			add(new JLabel(score.getName()), BorderLayout.CENTER);
		}
		JLabel scoreTimeView = new JLabel(score.getTimeString());
		scoreTimeView.setMinimumSize(new Dimension(80, scoreTimeView.getMinimumSize().height));
		scoreTimeView.setPreferredSize(new Dimension(80, scoreTimeView.getPreferredSize().height));
		scoreTimeView.setHorizontalAlignment(JLabel.RIGHT);
		add(scoreTimeView, BorderLayout.EAST);
	}
	
	public JTextField getScoreNameField() {
		return scoreNameField;
	}
}
