package com.kreative.dotbamcrak.menus;

import java.awt.Component;
import javax.swing.JMenu;
import com.kreative.dotbamcrak.fortune.FortuneSet;
import com.kreative.dotbamcrak.main.Controller;
import com.kreative.dotbamcrak.messages.MenuMessages;

public class FortuneSetMenu extends JMenu implements Updateable {
	private static final long serialVersionUID = 1L;
	
	public FortuneSetMenu(Controller controller) {
		super(MenuMessages.instance.getString(MenuMessages.MENU_FORTUNE_SET));
		for (FortuneSet fortunes : controller.getLibrary().getFortuneSets()) {
			add(new FortuneSetMenuItem(controller, fortunes));
		}
	}
	
	public void update() {
		for (Component c : getMenuComponents()) {
			if (c instanceof Updateable) {
				((Updateable)c).update();
			}
		}
	}
}
