package com.kreative.dotbamcrak.menus;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JMenu;
import com.kreative.dotbamcrak.main.Controller;
import com.kreative.dotbamcrak.messages.MenuMessages;
import com.kreative.dotbamcrak.tileset.TileSetInfo;

public class TileSetMenu extends JMenu implements Updateable {
	private static final long serialVersionUID = 1L;
	
	public TileSetMenu(String text) {
		super(text);
	}
	
	public TileSetMenu(Controller controller) {
		super(MenuMessages.instance.getString(MenuMessages.MENU_TILE_SET));
		Map<String,List<TileSetInfo>> tilesetsBySource = new TreeMap<String,List<TileSetInfo>>();
		for (TileSetInfo tileset : controller.getLibrary().getTileSets()) {
			if (tilesetsBySource.containsKey(tileset.getSourceName())) {
				tilesetsBySource.get(tileset.getSourceName()).add(tileset);
			} else {
				List<TileSetInfo> tilesets = new ArrayList<TileSetInfo>();
				tilesets.add(tileset);
				tilesetsBySource.put(tileset.getSourceName(), tilesets);
			}
		}
		if (tilesetsBySource.containsKey("")) {
			for (TileSetInfo tileset : tilesetsBySource.get("")) {
				add(new TileSetMenuItem(controller, tileset));
			}
			if (tilesetsBySource.size() > 1) {
				addSeparator();
			}
		}
		for (Map.Entry<String,List<TileSetInfo>> e : tilesetsBySource.entrySet()) {
			if (!e.getKey().equals("")) {
				TileSetMenu subMenu = new TileSetMenu(e.getKey());
				for (TileSetInfo tileset : e.getValue()) {
					subMenu.add(new TileSetMenuItem(controller, tileset));
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
