package com.kreative.dotbamcrak.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButtonMenuItem;
import com.kreative.dotbamcrak.fortune.FortuneSet;
import com.kreative.dotbamcrak.main.Controller;

public class FortuneSetMenuItem extends JRadioButtonMenuItem implements Updateable {
	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	private FortuneSet fortunes;
	
	public FortuneSetMenuItem(Controller controller, FortuneSet fortunes) {
		super(fortunes.getName());
		this.controller = controller;
		this.fortunes = fortunes;
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FortuneSetMenuItem.this.controller.setFortuneSet(FortuneSetMenuItem.this.fortunes);
			}
		});
	}
	
	public void update() {
		setSelected(controller.getFortuneSet().getFile().getAbsolutePath().equals(fortunes.getFile().getAbsolutePath()));
	}
}
