package com.kreative.dotbamcrak.menus;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import com.kreative.dotbamcrak.main.Controller;
import com.kreative.dotbamcrak.messages.MenuMessages;

public class PauseMenuItem extends JMenuItem implements Updateable {
	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	
	public PauseMenuItem(Controller controller) {
		super(MenuMessages.instance.getString(MenuMessages.MENU_GAME_PAUSE_RESUME));
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		this.controller = controller;
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (PauseMenuItem.this.controller.canPauseGame()) {
					PauseMenuItem.this.controller.pauseGame();
				} else if (PauseMenuItem.this.controller.canContinueGame()) {
					PauseMenuItem.this.controller.continueGame();
				}
			}
		});
	}
	
	public void update() {
		if (controller.canPauseGame()) {
			setText(MenuMessages.instance.getString(MenuMessages.MENU_GAME_PAUSE));
			setEnabled(true);
		} else if (controller.canContinueGame()) {
			setText(MenuMessages.instance.getString(MenuMessages.MENU_GAME_RESUME));
			setEnabled(true);
		} else {
			setText(MenuMessages.instance.getString(MenuMessages.MENU_GAME_PAUSE_RESUME));
			setEnabled(false);
		}
	}
}
