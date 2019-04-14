package com.kreative.dotbamcrak.main;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.kreative.dotbamcrak.messages.MainMessages;

public class View extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public View(Controller controller) {
		super(new BorderLayout());
		add(controller.getBoardView(), BorderLayout.CENTER);
		
		JPanel tilesPanel = new JPanel(new BorderLayout(4,4));
		tilesPanel.add(new JLabel(MainMessages.instance.getString(MainMessages.STATUS_TILES_LEFT)), BorderLayout.CENTER);
		tilesPanel.add(controller.getTilesLeftView(), BorderLayout.LINE_END);
		
		JPanel movesPanel = new JPanel(new BorderLayout(4,4));
		movesPanel.add(new JLabel(MainMessages.instance.getString(MainMessages.STATUS_MOVES_LEFT)), BorderLayout.CENTER);
		movesPanel.add(controller.getMovesLeftView(), BorderLayout.LINE_END);
		
		JPanel timePanel = new JPanel(new BorderLayout(4,4));
		timePanel.add(new JLabel(MainMessages.instance.getString(MainMessages.STATUS_TIME)), BorderLayout.CENTER);
		timePanel.add(controller.getStopwatchView(), BorderLayout.LINE_END);
		
		JPanel panel1 = new JPanel(new BorderLayout(12,12));
		panel1.add(movesPanel, BorderLayout.CENTER);
		panel1.add(timePanel, BorderLayout.LINE_END);
		
		JPanel panel2 = new JPanel(new BorderLayout(12,12));
		panel2.add(tilesPanel, BorderLayout.CENTER);
		panel2.add(panel1, BorderLayout.LINE_END);
		
		JPanel statusBar = new JPanel(new BorderLayout());
		statusBar.add(controller.getTileDescriptionView(), BorderLayout.CENTER);
		statusBar.add(panel2, BorderLayout.LINE_END);
		statusBar.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
		add(statusBar, BorderLayout.PAGE_END);
	}
}
