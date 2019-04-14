package com.kreative.dotbamcrak.menus;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JMenuItem;
import com.kreative.dotbamcrak.main.Controller;
import com.kreative.dotbamcrak.messages.MenuMessages;

public class SetBackgroundImageMenuItem extends JMenuItem {
	private static final long serialVersionUID = 1L;
	
	public SetBackgroundImageMenuItem(final Controller controller) {
		super(MenuMessages.instance.getString(MenuMessages.MENU_OPTIONS_SET_BKGND_IMAGE));
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileDialog fd = new FileDialog(
						new Frame(),
						MenuMessages.instance.getString(MenuMessages.MENU_OPTIONS_SET_BKGND_IMAGE_TITLE),
						FileDialog.LOAD
				);
				fd.setVisible(true);
				if (fd.getDirectory() == null || fd.getFile() == null) return;
				File f = new File(fd.getDirectory(), fd.getFile());
				controller.setBackgroundImage(f);
			}
		});
	}
}
