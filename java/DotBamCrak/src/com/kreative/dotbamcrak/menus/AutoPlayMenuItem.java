package com.kreative.dotbamcrak.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBoxMenuItem;
import com.kreative.dotbamcrak.main.Controller;
import com.kreative.dotbamcrak.messages.MenuMessages;

public class AutoPlayMenuItem extends JCheckBoxMenuItem implements ActionListener, Updateable {
	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	private Thread autoplayThread;
	
	public AutoPlayMenuItem(Controller controller) {
		super(MenuMessages.instance.getString(MenuMessages.MENU_GAME_AUTOPLAY));
		this.controller = controller;
		this.autoplayThread = null;
		addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (autoplayThread != null) {
			autoplayThread.interrupt();
			autoplayThread = null;
			setSelected(false);
		} else {
			setSelected(true);
			autoplayThread = new AutoPlayThread();
			autoplayThread.start();
		}
	}
	
	public void update() {
		setEnabled(controller.canShowMatches());
	}
	
	private class AutoPlayThread extends Thread {
		public void run() {
			while (!Thread.interrupted()) {
				try {
					if (!controller.showRandomMatchAndRemove()) break;
				} catch (Exception e) {
					break;
				}
			}
			autoplayThread = null;
			setSelected(false);
		}
	}
}
