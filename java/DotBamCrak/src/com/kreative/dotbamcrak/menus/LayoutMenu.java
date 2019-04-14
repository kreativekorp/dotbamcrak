package com.kreative.dotbamcrak.menus;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JMenu;
import com.kreative.dotbamcrak.layout.Layout;
import com.kreative.dotbamcrak.main.Controller;
import com.kreative.dotbamcrak.messages.MenuMessages;

public class LayoutMenu extends JMenu implements Updateable {
	private static final long serialVersionUID = 1L;
	
	private LayoutMenu(String text) {
		super(text);
	}
	
	public LayoutMenu(Controller controller) {
		super(MenuMessages.instance.getString(MenuMessages.MENU_LAYOUT));
		Map<String,List<Layout>> layoutsBySource = new TreeMap<String,List<Layout>>();
		for (Layout layout : controller.getLibrary().getLayouts()) {
			if (layoutsBySource.containsKey(layout.getSourceName())) {
				layoutsBySource.get(layout.getSourceName()).add(layout);
			} else {
				List<Layout> layouts = new ArrayList<Layout>();
				layouts.add(layout);
				layoutsBySource.put(layout.getSourceName(), layouts);
			}
		}
		if (layoutsBySource.containsKey("")) {
			for (Layout layout : layoutsBySource.get("")) {
				add(new LayoutMenuItem(controller, layout));
			}
			if (layoutsBySource.size() > 1) {
				addSeparator();
			}
		}
		for (Map.Entry<String,List<Layout>> e : layoutsBySource.entrySet()) {
			if (!e.getKey().equals("")) {
				LayoutMenu subMenu = new LayoutMenu(e.getKey());
				for (Layout layout : e.getValue()) {
					subMenu.add(new LayoutMenuItem(controller, layout));
				}
				add(subMenu);
			}
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
