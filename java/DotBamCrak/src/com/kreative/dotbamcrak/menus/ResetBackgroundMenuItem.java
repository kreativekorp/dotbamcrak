package com.kreative.dotbamcrak.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import com.kreative.dotbamcrak.main.Controller;
import com.kreative.dotbamcrak.messages.MenuMessages;

public class ResetBackgroundMenuItem extends JMenuItem {
	private static final long serialVersionUID = 1L;
	
	public ResetBackgroundMenuItem(final Controller controller) {
		super(MenuMessages.instance.getString(MenuMessages.MENU_OPTIONS_RESET_BKGND));
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.resetBackground();
			}
		});
	}
}
