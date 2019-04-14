package com.kreative.dotbamcrak.menus;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import com.kreative.dotbamcrak.main.Controller;
import com.kreative.dotbamcrak.messages.MenuMessages;

public class StartOverMenuItem extends JMenuItem implements Updateable {
	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	
	public StartOverMenuItem(Controller controller) {
		super(MenuMessages.instance.getString(MenuMessages.MENU_GAME_START_OVER));
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | KeyEvent.SHIFT_MASK));
		this.controller = controller;
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartOverMenuItem.this.controller.startOver();
			}
		});
	}
	
	public void update() {
		setEnabled(controller.canStartOver());
	}
}
