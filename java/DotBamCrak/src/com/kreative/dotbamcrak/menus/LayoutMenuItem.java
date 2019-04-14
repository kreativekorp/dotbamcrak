package com.kreative.dotbamcrak.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButtonMenuItem;
import com.kreative.dotbamcrak.layout.Layout;
import com.kreative.dotbamcrak.main.Controller;

public class LayoutMenuItem extends JRadioButtonMenuItem implements Updateable {
	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	private Layout layout;
	
	public LayoutMenuItem(Controller controller, Layout layout) {
		super(layout.getName());
		this.controller = controller;
		this.layout = layout;
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LayoutMenuItem.this.controller.setLayout(LayoutMenuItem.this.layout);
			}
		});
	}
	
	public void update() {
		setSelected(controller.getLayout().getFile().getAbsolutePath().equals(layout.getFile().getAbsolutePath()));
	}
}
