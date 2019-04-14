package com.kreative.dotbamcrak.scores;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.kreative.dotbamcrak.messages.ScoreMessages;

public class ScoreMapView extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JComboBox scorePopup;
	private CardLayout scoreLayout;
	private JPanel scorePanel;
	private JTextField scoreNameField;
	private JButton newGameButton;
	private JButton closeButton;
	
	public ScoreMapView(
			final Runnable saveCallback,
			final Runnable newGameCallback,
			final ScoreMap scores,
			int max,
			final Score editableScore,
			String layoutName
	) {
		super(new BorderLayout(8, 8));
		
		Set<String> scoreNames = scores.getLayoutNames();
		scorePopup = new JComboBox(scoreNames.toArray(new String[0]));
		scoreLayout = new CardLayout();
		scorePanel = new JPanel(scoreLayout);
		scoreNameField = null;
		for (String scoreName : scoreNames) {
			ScoreListView view = new ScoreListView(scores.getScoresForLayoutName(scoreName), max, editableScore);
			if (view.getScoreNameField() != null) {
				scoreNameField = view.getScoreNameField();
			}
			scorePanel.add(scoreName, view);
		}
		newGameButton = new JButton(ScoreMessages.instance.getString(ScoreMessages.HIGH_SCORES_NEW_GAME));
		closeButton = new JButton(ScoreMessages.instance.getString(ScoreMessages.HIGH_SCORES_CLOSE));
		
		add(scorePanel, BorderLayout.CENTER);
		if (editableScore != null) {
			JLabel congratsLabel = new JLabel(ScoreMessages.instance.getString(ScoreMessages.HIGH_SCORES_CONGRATULATIONS));
			congratsLabel.setHorizontalAlignment(JLabel.CENTER);
			JPanel topPanel = new JPanel(new BorderLayout(8, 8));
			topPanel.add(scorePopup, BorderLayout.CENTER);
			topPanel.add(congratsLabel, BorderLayout.PAGE_END);
			add(topPanel, BorderLayout.PAGE_START);
			JPanel bottomPanel = new JPanel(new GridLayout(1, 0, 8, 8));
			bottomPanel.add(newGameButton);
			bottomPanel.add(closeButton);
			add(bottomPanel, BorderLayout.PAGE_END);
		} else {
			add(scorePopup, BorderLayout.PAGE_START);
			add(closeButton, BorderLayout.PAGE_END);
		}
		
		setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
		
		if (scoreNames.contains(layoutName)) {
			scorePopup.setSelectedItem(layoutName);
			scoreLayout.show(scorePanel, layoutName);
		}
		
		scorePopup.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String layoutName = scorePopup.getSelectedItem().toString();
				scoreLayout.show(scorePanel, layoutName);
			}
		});
		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (saveCallback != null) saveCallback.run();
				if (newGameCallback != null) newGameCallback.run();
				close();
			}
		});
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (saveCallback != null) saveCallback.run();
				close();
			}
		});
	}
	
	public JTextField getScoreNameField() {
		return scoreNameField;
	}
	
	private void close() {
		Component c = this;
		while (true) {
			if (c == null) {
				return;
			} else if (c instanceof Frame) {
				((Frame)c).dispose();
				return;
			} else if (c instanceof Dialog) {
				((Dialog)c).dispose();
				return;
			} else if (c instanceof Window) {
				((Window)c).dispose();
				return;
			} else {
				c = c.getParent();
			}
		}
	}
}
