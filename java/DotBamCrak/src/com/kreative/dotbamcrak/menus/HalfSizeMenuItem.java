package com.kreative.dotbamcrak.menus;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import com.kreative.dotbamcrak.main.Controller;
import com.kreative.dotbamcrak.messages.MenuMessages;

public class HalfSizeMenuItem extends JMenuItem {
	private static final long serialVersionUID = 1L;
	
	public HalfSizeMenuItem(final Controller controller) {
		super(MenuMessages.instance.getString(MenuMessages.MENU_OPTIONS_HALF_SIZE));
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.pack(0.5);
			}
		});
	}
}
