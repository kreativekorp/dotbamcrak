package com.kreative.dotbamcrak.utility;

import javax.swing.JLabel;

public class StopwatchThread extends Thread {
	private Stopwatch stopwatch;
	private JLabel label;
	
	public StopwatchThread(Stopwatch stopwatch, JLabel label) {
		this.stopwatch = stopwatch;
		this.label = label;
	}
	
	public void run() {
		while (!Thread.interrupted()) {
			label.setText(stopwatch.getTimeString());
			try {
				Thread.sleep(50);
			} catch (InterruptedException ie) {
				break;
			}
		}
	}
}
