package com.kreative.dotbamcrak.menus;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import com.kreative.dotbamcrak.main.Controller;

public class MenuBar extends JMenuBar implements Updateable {
	private static final long serialVersionUID = 1L;
	
	public MenuBar(Controller controller) {
		add(new GameMenu(controller));
		add(new TileSetMenu(controller));
		add(new LayoutMenu(controller));
		add(new FortuneSetMenu(controller));
		add(new OptionsMenu(controller));
	}
	
	public void update() {
		for (int i = 0; i < getMenuCount(); i++) {
			JMenu menu = getMenu(i);
			if (menu instanceof Updateable) {
				((Updateable)menu).update();
			}
		}
	}
}
