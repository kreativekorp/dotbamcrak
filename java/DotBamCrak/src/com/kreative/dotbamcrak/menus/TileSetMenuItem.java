package com.kreative.dotbamcrak.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButtonMenuItem;
import com.kreative.dotbamcrak.main.Controller;
import com.kreative.dotbamcrak.tileset.TileSetInfo;

public class TileSetMenuItem extends JRadioButtonMenuItem implements Updateable {
	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	private TileSetInfo tileset;
	
	public TileSetMenuItem(Controller controller, TileSetInfo tileset) {
		super(tileset.getName());
		this.controller = controller;
		this.tileset = tileset;
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TileSetMenuItem.this.controller.setTileSet(TileSetMenuItem.this.tileset.createTileSet());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}
	
	public void update() {
		setSelected(controller.getTileSet().getFile().getAbsolutePath().equals(tileset.getFile().getAbsolutePath()));
	}
}
