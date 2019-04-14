package com.kreative.dotbamcrak.settings;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.kreative.dotbamcrak.messages.SettingMessages;

public class ChangeBehaviorPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final int MY_MAX_VALUE = 999999;
	
	public ChangeBehaviorPanel(final Library library, final Settings settings) {
		final ResourceBundle messages = SettingMessages.instance;
		
		JLabel cursorLabel = new JLabel(messages.getString(SettingMessages.BEHAVIOR_CURSOR));
		JRadioButton cursorArrowButton = new JRadioButton(messages.getString(SettingMessages.BEHAVIOR_CURSOR_ARROW));
		JRadioButton cursorHandButton = new JRadioButton(messages.getString(SettingMessages.BEHAVIOR_CURSOR_HAND));
		JRadioButton cursorCrossButton = new JRadioButton(messages.getString(SettingMessages.BEHAVIOR_CURSOR_CROSS));
		cursorArrowButton.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		cursorHandButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cursorCrossButton.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		cursorArrowButton.setSelected(settings.cursor == Cursor.DEFAULT_CURSOR);
		cursorHandButton.setSelected(settings.cursor == Cursor.HAND_CURSOR);
		cursorCrossButton.setSelected(settings.cursor == Cursor.CROSSHAIR_CURSOR);
		ButtonGroup cursorButtonGroup = new ButtonGroup();
		cursorButtonGroup.add(cursorArrowButton);
		cursorButtonGroup.add(cursorHandButton);
		cursorButtonGroup.add(cursorCrossButton);
		JPanel cursorPanel1 = new JPanel();
		cursorPanel1.setLayout(new BoxLayout(cursorPanel1, BoxLayout.LINE_AXIS));
		cursorPanel1.add(cursorArrowButton);
		cursorPanel1.add(Box.createHorizontalStrut(4));
		cursorPanel1.add(cursorHandButton);
		cursorPanel1.add(Box.createHorizontalStrut(4));
		cursorPanel1.add(cursorCrossButton);
		JPanel cursorPanel2 = new JPanel(new BorderLayout());
		cursorPanel2.add(cursorPanel1, BorderLayout.LINE_START);
		JPanel cursorPanel = new JPanel(new BorderLayout(4,4));
		cursorPanel.add(cursorLabel, BorderLayout.LINE_START);
		cursorPanel.add(cursorPanel2, BorderLayout.CENTER);
		
		final JCheckBox cursorOnFreeTileOnlyCheckBox = new JCheckBox(messages.getString(SettingMessages.BEHAVIOR_CURSOR_ON_FREE_TILE_ONLY));
		cursorOnFreeTileOnlyCheckBox.setSelected(settings.cursorOnFreeTileOnly);
		final JCheckBox deselectOnBackgroundCheckBox = new JCheckBox(messages.getString(SettingMessages.BEHAVIOR_DESELECT_ON_BACKGROUND));
		deselectOnBackgroundCheckBox.setSelected(settings.deselectOnBackground);
		final JCheckBox deselectOnNonFreeTileCheckBox = new JCheckBox(messages.getString(SettingMessages.BEHAVIOR_DESELECT_ON_NON_FREE_TILE));
		deselectOnNonFreeTileCheckBox.setSelected(settings.deselectOnNonFreeTile);
		final JCheckBox warnOnNonFreeTileCheckBox = new JCheckBox(messages.getString(SettingMessages.BEHAVIOR_WARN_ON_NON_FREE_TILE));
		warnOnNonFreeTileCheckBox.setSelected(settings.warnOnNonFreeTile);
		final JCheckBox reselectOnNonMatchingTileCheckBox = new JCheckBox(messages.getString(SettingMessages.BEHAVIOR_RESELECT_ON_NON_MATCHING_TILE));
		reselectOnNonMatchingTileCheckBox.setSelected(settings.reselectOnNonMatchingTile);
		final JCheckBox warnOnNonMatchingTileCheckBox = new JCheckBox(messages.getString(SettingMessages.BEHAVIOR_WARN_ON_NON_MATCHING_TILE));
		warnOnNonMatchingTileCheckBox.setSelected(settings.warnOnNonMatchingTile);
		final JCheckBox warnOnNoFreeTilesCheckBox = new JCheckBox(messages.getString(SettingMessages.BEHAVIOR_WARN_ON_NO_FREE_TILES));
		warnOnNoFreeTilesCheckBox.setSelected(settings.warnOnNoFreeTiles);
		final JCheckBox allowShuffleOnNoFreeTilesCheckBox = new JCheckBox(messages.getString(SettingMessages.BEHAVIOR_ALLOW_SHUFFLE_ON_NO_FREE_TILES));
		allowShuffleOnNoFreeTilesCheckBox.setSelected(settings.allowShuffleOnNoFreeTiles);
		final JCheckBox allowUndoOnNoFreeTilesCheckBox = new JCheckBox(messages.getString(SettingMessages.BEHAVIOR_ALLOW_UNDO_ON_NO_FREE_TILES));
		allowUndoOnNoFreeTilesCheckBox.setSelected(settings.allowUndoOnNoFreeTiles);
		final JCheckBox allowStartOverOnNoFreeTilesCheckBox = new JCheckBox(messages.getString(SettingMessages.BEHAVIOR_ALLOW_START_OVER_ON_NO_FREE_TILES));
		allowStartOverOnNoFreeTilesCheckBox.setSelected(settings.allowStartOverOnNoFreeTiles);
		final JCheckBox allowNewGameOnNoFreeTilesCheckBox = new JCheckBox(messages.getString(SettingMessages.BEHAVIOR_ALLOW_NEW_GAME_ON_NO_FREE_TILES));
		allowNewGameOnNoFreeTilesCheckBox.setSelected(settings.allowNewGameOnNoFreeTiles);
		final JCheckBox displayFortuneCheckBox = new JCheckBox(messages.getString(SettingMessages.BEHAVIOR_DISPLAY_FORTUNE));
		displayFortuneCheckBox.setSelected(settings.displayFortune);
		final JCheckBox doubleClickToStartNewGameCheckBox = new JCheckBox(messages.getString(SettingMessages.BEHAVIOR_DOUBLE_CLICK_TO_START_NEW_GAME));
		doubleClickToStartNewGameCheckBox.setSelected(settings.doubleClickToStartNewGame);
		JPanel checkBoxPanel = new JPanel(new GridLayout(0,1));
		checkBoxPanel.add(cursorOnFreeTileOnlyCheckBox);
		checkBoxPanel.add(deselectOnBackgroundCheckBox);
		checkBoxPanel.add(deselectOnNonFreeTileCheckBox);
		checkBoxPanel.add(warnOnNonFreeTileCheckBox);
		checkBoxPanel.add(reselectOnNonMatchingTileCheckBox);
		checkBoxPanel.add(warnOnNonMatchingTileCheckBox);
		checkBoxPanel.add(warnOnNoFreeTilesCheckBox);
		checkBoxPanel.add(allowShuffleOnNoFreeTilesCheckBox);
		checkBoxPanel.add(allowUndoOnNoFreeTilesCheckBox);
		checkBoxPanel.add(allowStartOverOnNoFreeTilesCheckBox);
		checkBoxPanel.add(allowNewGameOnNoFreeTilesCheckBox);
		checkBoxPanel.add(displayFortuneCheckBox);
		checkBoxPanel.add(doubleClickToStartNewGameCheckBox);
		
		JLabel undoPenaltyLabel = new JLabel(messages.getString(SettingMessages.BEHAVIOR_UNDO_PENALTY));
		final SpinnerNumberModel undoPenaltySpinnerModel = new SpinnerNumberModel(settings.undoPenalty, 0, MY_MAX_VALUE, 1);
		JSpinner undoPenaltySpinner = new JSpinner(undoPenaltySpinnerModel);
		JLabel undoPenaltyUnitLabel = new JLabel("s");
		JLabel redoPenaltyLabel = new JLabel(messages.getString(SettingMessages.BEHAVIOR_REDO_PENALTY));
		final SpinnerNumberModel redoPenaltySpinnerModel = new SpinnerNumberModel(settings.redoPenalty, 0, MY_MAX_VALUE, 1);
		JSpinner redoPenaltySpinner = new JSpinner(redoPenaltySpinnerModel);
		JLabel redoPenaltyUnitLabel = new JLabel("s");
		JLabel showMatchPenaltyLabel = new JLabel(messages.getString(SettingMessages.BEHAVIOR_SHOW_MATCH_PENALTY));
		final SpinnerNumberModel showMatchPenaltySpinnerModel = new SpinnerNumberModel(settings.showMatchPenalty, 0, MY_MAX_VALUE, 1);
		JSpinner showMatchPenaltySpinner = new JSpinner(showMatchPenaltySpinnerModel);
		JLabel showMatchPenaltyUnitLabel = new JLabel("s");
		JLabel showAllMatchesPenaltyLabel = new JLabel(messages.getString(SettingMessages.BEHAVIOR_SHOW_ALL_MATCHES_PENALTY));
		final SpinnerNumberModel showAllMatchesPenaltySpinnerModel = new SpinnerNumberModel(settings.showAllMatchesPenalty, 0, MY_MAX_VALUE, 1);
		JSpinner showAllMatchesPenaltySpinner = new JSpinner(showAllMatchesPenaltySpinnerModel);
		JLabel showAllMatchesPenaltyUnitLabel = new JLabel("s");
		JLabel shufflePenaltyLabel = new JLabel(messages.getString(SettingMessages.BEHAVIOR_SHUFFLE_PENALTY));
		final SpinnerNumberModel shufflePenaltySpinnerModel = new SpinnerNumberModel(settings.shufflePenalty, 0, MY_MAX_VALUE, 1);
		JSpinner shufflePenaltySpinner = new JSpinner(shufflePenaltySpinnerModel);
		JLabel shufflePenaltyUnitLabel = new JLabel("s");
		JLabel autoPlayPenaltyLabel = new JLabel(messages.getString(SettingMessages.BEHAVIOR_AUTOPLAY_PENALTY));
		final SpinnerNumberModel autoPlayPenaltySpinnerModel = new SpinnerNumberModel(settings.autoPlayPenalty, 0, MY_MAX_VALUE, 1);
		JSpinner autoPlayPenaltySpinner = new JSpinner(autoPlayPenaltySpinnerModel);
		JLabel autoPlayPenaltyUnitLabel = new JLabel("s");
		JLabel autoPlaySpeedLabel = new JLabel(messages.getString(SettingMessages.BEHAVIOR_AUTOPLAY_SPEED));
		final SpinnerNumberModel autoPlaySpeedSpinnerModel = new SpinnerNumberModel(settings.autoPlaySpeed, 0, MY_MAX_VALUE, 1);
		JSpinner autoPlaySpeedSpinner = new JSpinner(autoPlaySpeedSpinnerModel);
		JLabel autoPlaySpeedUnitLabel = new JLabel("ms");
		JLabel highScoreCountLabel = new JLabel(messages.getString(SettingMessages.BEHAVIOR_HIGH_SCORE_COUNT));
		final SpinnerNumberModel highScoreCountSpinnerModel = new SpinnerNumberModel(settings.highScoreCount, 0, MY_MAX_VALUE, 1);
		JSpinner highScoreCountSpinner = new JSpinner(highScoreCountSpinnerModel);
		JLabel highScoreCountUnitLabel = new JLabel("");
		JPanel labelPanel1 = new JPanel(new GridLayout(0,1,1,1));
		labelPanel1.add(undoPenaltyLabel);
		labelPanel1.add(redoPenaltyLabel);
		labelPanel1.add(showMatchPenaltyLabel);
		labelPanel1.add(showAllMatchesPenaltyLabel);
		labelPanel1.add(shufflePenaltyLabel);
		labelPanel1.add(autoPlayPenaltyLabel);
		labelPanel1.add(autoPlaySpeedLabel);
		labelPanel1.add(highScoreCountLabel);
		JPanel spinnerPanel = new JPanel(new GridLayout(0,1,1,1));
		spinnerPanel.add(undoPenaltySpinner);
		spinnerPanel.add(redoPenaltySpinner);
		spinnerPanel.add(showMatchPenaltySpinner);
		spinnerPanel.add(showAllMatchesPenaltySpinner);
		spinnerPanel.add(shufflePenaltySpinner);
		spinnerPanel.add(autoPlayPenaltySpinner);
		spinnerPanel.add(autoPlaySpeedSpinner);
		spinnerPanel.add(highScoreCountSpinner);
		JPanel unitLabelPanel = new JPanel(new GridLayout(0,1,1,1));
		unitLabelPanel.add(undoPenaltyUnitLabel);
		unitLabelPanel.add(redoPenaltyUnitLabel);
		unitLabelPanel.add(showMatchPenaltyUnitLabel);
		unitLabelPanel.add(showAllMatchesPenaltyUnitLabel);
		unitLabelPanel.add(shufflePenaltyUnitLabel);
		unitLabelPanel.add(autoPlayPenaltyUnitLabel);
		unitLabelPanel.add(autoPlaySpeedUnitLabel);
		unitLabelPanel.add(highScoreCountUnitLabel);
		JPanel numericOptionPanel = new JPanel(new BorderLayout(4,4));
		numericOptionPanel.add(labelPanel1, BorderLayout.LINE_START);
		numericOptionPanel.add(spinnerPanel, BorderLayout.CENTER);
		numericOptionPanel.add(unitLabelPanel, BorderLayout.LINE_END);
		
		JPanel mainPanel = new JPanel(new BorderLayout(8,8));
		mainPanel.add(cursorPanel, BorderLayout.PAGE_START);
		mainPanel.add(checkBoxPanel, BorderLayout.CENTER);
		mainPanel.add(numericOptionPanel, BorderLayout.PAGE_END);
		
		JButton okButton = new JButton(messages.getString(SettingMessages.BEHAVIOR_CLOSE));
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(okButton);
		buttonPanel.add(Box.createHorizontalGlue());
		
		setLayout(new BorderLayout(8,8));
		add(mainPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.PAGE_END);
		setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
		
		cursorArrowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.cursor = Cursor.DEFAULT_CURSOR;
				library.setSettings(settings);
			}
		});
		cursorHandButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.cursor = Cursor.HAND_CURSOR;
				library.setSettings(settings);
			}
		});
		cursorCrossButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.cursor = Cursor.CROSSHAIR_CURSOR;
				library.setSettings(settings);
			}
		});
		
		cursorOnFreeTileOnlyCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.cursorOnFreeTileOnly = cursorOnFreeTileOnlyCheckBox.isSelected();
				library.setSettings(settings);
			}
		});
		deselectOnBackgroundCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.deselectOnBackground = deselectOnBackgroundCheckBox.isSelected();
				library.setSettings(settings);
			}
		});
		deselectOnNonFreeTileCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.deselectOnNonFreeTile = deselectOnNonFreeTileCheckBox.isSelected();
				library.setSettings(settings);
			}
		});
		warnOnNonFreeTileCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.warnOnNonFreeTile = warnOnNonFreeTileCheckBox.isSelected();
				library.setSettings(settings);
			}
		});
		reselectOnNonMatchingTileCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.reselectOnNonMatchingTile = reselectOnNonMatchingTileCheckBox.isSelected();
				library.setSettings(settings);
			}
		});
		warnOnNonMatchingTileCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.warnOnNonMatchingTile = warnOnNonMatchingTileCheckBox.isSelected();
				library.setSettings(settings);
			}
		});
		warnOnNoFreeTilesCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.warnOnNoFreeTiles = warnOnNoFreeTilesCheckBox.isSelected();
				library.setSettings(settings);
			}
		});
		allowShuffleOnNoFreeTilesCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.allowShuffleOnNoFreeTiles = allowShuffleOnNoFreeTilesCheckBox.isSelected();
				library.setSettings(settings);
			}
		});
		allowUndoOnNoFreeTilesCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.allowUndoOnNoFreeTiles = allowUndoOnNoFreeTilesCheckBox.isSelected();
				library.setSettings(settings);
			}
		});
		allowStartOverOnNoFreeTilesCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.allowStartOverOnNoFreeTiles = allowStartOverOnNoFreeTilesCheckBox.isSelected();
				library.setSettings(settings);
			}
		});
		allowNewGameOnNoFreeTilesCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.allowNewGameOnNoFreeTiles = allowNewGameOnNoFreeTilesCheckBox.isSelected();
				library.setSettings(settings);
			}
		});
		displayFortuneCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.displayFortune = displayFortuneCheckBox.isSelected();
				library.setSettings(settings);
			}
		});
		doubleClickToStartNewGameCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settings.doubleClickToStartNewGame = doubleClickToStartNewGameCheckBox.isSelected();
				library.setSettings(settings);
			}
		});
		
		undoPenaltySpinnerModel.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				settings.undoPenalty = undoPenaltySpinnerModel.getNumber().intValue();
				library.setSettings(settings);
			}
		});
		redoPenaltySpinnerModel.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				settings.redoPenalty = redoPenaltySpinnerModel.getNumber().intValue();
				library.setSettings(settings);
			}
		});
		showMatchPenaltySpinnerModel.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				settings.showMatchPenalty = showMatchPenaltySpinnerModel.getNumber().intValue();
				library.setSettings(settings);
			}
		});
		showAllMatchesPenaltySpinnerModel.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				settings.showAllMatchesPenalty = showAllMatchesPenaltySpinnerModel.getNumber().intValue();
				library.setSettings(settings);
			}
		});
		shufflePenaltySpinnerModel.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				settings.shufflePenalty = shufflePenaltySpinnerModel.getNumber().intValue();
				library.setSettings(settings);
			}
		});
		autoPlayPenaltySpinnerModel.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				settings.autoPlayPenalty = autoPlayPenaltySpinnerModel.getNumber().intValue();
				library.setSettings(settings);
			}
		});
		autoPlaySpeedSpinnerModel.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				settings.autoPlaySpeed = autoPlaySpeedSpinnerModel.getNumber().intValue();
				library.setSettings(settings);
			}
		});
		highScoreCountSpinnerModel.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				settings.highScoreCount = highScoreCountSpinnerModel.getNumber().intValue();
				library.setSettings(settings);
			}
		});
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				library.setSettings(settings);
				close();
			}
		});
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
