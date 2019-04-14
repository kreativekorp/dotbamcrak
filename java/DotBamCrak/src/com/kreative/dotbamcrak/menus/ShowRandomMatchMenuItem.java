package com.kreative.dotbamcrak.menus;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import com.kreative.dotbamcrak.main.Controller;
import com.kreative.dotbamcrak.messages.MenuMessages;
import com.kreative.dotbamcrak.utility.OSUtilities;

public class ShowRandomMatchMenuItem extends JMenuItem implements Updateable {
	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	
	public ShowRandomMatchMenuItem(Controller controller) {
		super(MenuMessages.instance.getString(MenuMessages.MENU_GAME_SHOW_RANDOM_MATCH));
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, OSUtilities.isMacOS() ? (Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | KeyEvent.SHIFT_MASK) : Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		this.controller = controller;
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowRandomMatchMenuItem.this.controller.showRandomMatch();
			}
		});
	}
	
	public void update() {
		setEnabled(controller.canShowMatches());
	}
}
