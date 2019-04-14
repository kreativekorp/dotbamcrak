package com.kreative.dotbamcrak.menus;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import com.kreative.dotbamcrak.main.Controller;
import com.kreative.dotbamcrak.messages.MenuMessages;

public class ThreeQuarterSizeMenuItem extends JMenuItem {
	private static final long serialVersionUID = 1L;
	
	public ThreeQuarterSizeMenuItem(final Controller controller) {
		super(MenuMessages.instance.getString(MenuMessages.MENU_OPTIONS_THREE_QUARTER_SIZE));
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.pack(0.75);
			}
		});
	}
}
