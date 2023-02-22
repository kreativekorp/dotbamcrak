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
	
	private String lastOpenDirectory = null;
	
	public SetBackgroundImageMenuItem(final Controller controller) {
		super(MenuMessages.instance.getString(MenuMessages.MENU_OPTIONS_SET_BKGND_IMAGE));
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Frame frame = new Frame();
				FileDialog fd = new FileDialog(
						frame,
						MenuMessages.instance.getString(MenuMessages.MENU_OPTIONS_SET_BKGND_IMAGE_TITLE),
						FileDialog.LOAD
				);
				if (lastOpenDirectory != null) fd.setDirectory(lastOpenDirectory);
				fd.setVisible(true);
				String ds = fd.getDirectory(), fs = fd.getFile();
				fd.dispose();
				frame.dispose();
				if (ds == null || fs == null) return;
				File file = new File((lastOpenDirectory = ds), fs);
				controller.setBackgroundImage(file);
			}
		});
	}
}
