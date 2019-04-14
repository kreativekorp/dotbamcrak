package com.kreative.dotbamcrak.menus;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JColorChooser;
import javax.swing.JMenuItem;
import com.kreative.dotbamcrak.main.Controller;
import com.kreative.dotbamcrak.messages.MenuMessages;

public class SetBackgroundColorMenuItem extends JMenuItem {
	private static final long serialVersionUID = 1L;
	
	public SetBackgroundColorMenuItem(final Controller controller) {
		super(MenuMessages.instance.getString(MenuMessages.MENU_OPTIONS_SET_BKGND_COLOR));
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(
						new Frame(),
						MenuMessages.instance.getString(MenuMessages.MENU_OPTIONS_SET_BKGND_COLOR_TITLE),
						controller.getBackgroundColor()
				);
				if (newColor != null) {
					controller.setBackgroundColor(newColor);
				}
			}
		});
	}
}
