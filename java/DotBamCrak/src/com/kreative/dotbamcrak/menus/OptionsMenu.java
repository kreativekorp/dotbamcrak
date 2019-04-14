package com.kreative.dotbamcrak.menus;

import javax.swing.JMenu;
import com.kreative.dotbamcrak.main.Controller;
import com.kreative.dotbamcrak.messages.MenuMessages;

public class OptionsMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	
	public OptionsMenu(Controller controller) {
		super(MenuMessages.instance.getString(MenuMessages.MENU_OPTIONS));
		add(new FullScreenMenuItem(controller));
		add(new ActualSizeMenuItem(controller));
		add(new ThreeQuarterSizeMenuItem(controller));
		add(new HalfSizeMenuItem(controller));
		add(new CenterWindowMenuItem(controller));
		addSeparator();
		add(new ResetBackgroundMenuItem(controller));
		add(new SetBackgroundColorMenuItem(controller));
		add(new SetBackgroundImageMenuItem(controller));
		addSeparator();
		add(new ChangeBehaviorMenuItem(controller));
	}
}
