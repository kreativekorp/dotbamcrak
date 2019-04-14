package com.kreative.dotbamcrak.main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.kreative.dotbamcrak.messages.MainMessages;

public class Main {
	public static void main(String[] args) {
		try { System.setProperty("com.apple.mrj.application.apple.menu.about.name", MainMessages.instance.getString(MainMessages.APP_TITLE)); } catch (Exception e) {}
		try { System.setProperty("apple.laf.useScreenMenuBar", "true"); } catch (Exception e) {}
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				final Controller controller = new Controller();
				View view = new View(controller);
				JFrame frame = new JFrame(MainMessages.instance.getString(MainMessages.APP_TITLE));
				frame.setContentPane(view);
				frame.setJMenuBar(controller.getMenuBar());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				controller.startThreads();
				frame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						controller.stopThreads();
					}
				});
			}
		});
	}
}
