package com.kreative.dotbamcrak.menus;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import com.kreative.dotbamcrak.main.Controller;
import com.kreative.dotbamcrak.messages.MenuMessages;

public class ShowSelectedMatchMenuItem extends JMenuItem implements Updateable {
	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	
	public ShowSelectedMatchMenuItem(Controller controller) {
		super(MenuMessages.instance.getString(MenuMessages.MENU_GAME_SHOW_SELECTED_MATCH));
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		this.controller = controller;
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowSelectedMatchMenuItem.this.controller.showSelectedMatch();
			}
		});
	}
	
	public void update() {
		setEnabled(controller.canShowMatches());
	}
}
