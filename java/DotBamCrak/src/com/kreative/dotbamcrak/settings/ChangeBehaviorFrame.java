package com.kreative.dotbamcrak.settings;

import javax.swing.JFrame;
import com.kreative.dotbamcrak.messages.SettingMessages;

public class ChangeBehaviorFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public ChangeBehaviorFrame(Library library, Settings settings) {
		super(SettingMessages.instance.getString(SettingMessages.BEHAVIOR_TITLE));
		setContentPane(new ChangeBehaviorPanel(library, settings));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
	}
}
