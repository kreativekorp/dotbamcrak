package com.kreative.dotbamcrak.menus;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import com.kreative.dotbamcrak.main.Controller;
import com.kreative.dotbamcrak.messages.MenuMessages;
import com.kreative.dotbamcrak.utility.OSUtilities;

public class GameMenu extends JMenu implements Updateable {
	private static final long serialVersionUID = 1L;
	
	public GameMenu(Controller controller) {
		super(MenuMessages.instance.getString(MenuMessages.MENU_GAME));
		add(new NewMenuItem(controller));
		add(new PauseMenuItem(controller));
		addSeparator();
		add(new UndoMenuItem(controller));
		add(new RedoMenuItem(controller));
		add(new StartOverMenuItem(controller));
		add(new ShuffleMenuItem(controller));
		addSeparator();
		add(new ShowRandomMatchMenuItem(controller));
		add(new ShowSelectedMatchMenuItem(controller));
		add(new ShowAllMatchesMenuItem(controller));
		add(new AutoPlayMenuItem(controller));
		addSeparator();
		add(new ScoresMenuItem(controller));
		if (!OSUtilities.isMacOS()) {
			addSeparator();
			add(new QuitMenuItem());
		}
	}
	
	public void update() {
		for (int i = 0; i < getItemCount(); i++) {
			JMenuItem mi = getItem(i);
			if (mi instanceof Updateable) {
				((Updateable)mi).update();
			}
		}
	}
}
