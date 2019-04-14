package com.kreative.dotbamcrak.menus;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import com.kreative.dotbamcrak.messages.MenuMessages;
import com.kreative.dotbamcrak.utility.OSUtilities;

public class QuitMenuItem extends JMenuItem {
	private static final long serialVersionUID = 1L;
	
	public QuitMenuItem() {
		super(MenuMessages.instance.getString(OSUtilities.isMacOS() ? MenuMessages.MENU_GAME_QUIT : MenuMessages.MENU_GAME_EXIT));
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
}
