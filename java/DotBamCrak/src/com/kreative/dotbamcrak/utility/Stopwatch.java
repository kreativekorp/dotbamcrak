package com.kreative.dotbamcrak.utility;

public class Stopwatch {
	private long accumulatedTime;
	private boolean running;
	private long startTime;
	
	public Stopwatch() {
		reset();
	}
	
	public synchronized void reset() {
		accumulatedTime = 0;
		running = false;
	}
	
	public synchronized void start() {
		if (!running) {
			running = true;
			startTime = System.currentTimeMillis();
		}
	}
	
	public synchronized void stop() {
		if (running) {
			accumulatedTime += System.currentTimeMillis() - startTime;
			running = false;
		}
	}
	
	public synchronized void addTime(long millis) {
		accumulatedTime += millis;
	}
	
	public synchronized boolean isRunning() {
		return running;
	}
	
	public synchronized long getTime() {
		if (running) {
			return accumulatedTime + System.currentTimeMillis() - startTime;
		} else {
			return accumulatedTime;
		}
	}
	
	public synchronized String getTimeString() {
		long t = getTime() / 1000L;
		StringBuffer s = new StringBuffer();
		do {
			String tmp = "00" + Long.toString(t % 60);
			tmp = ":" + tmp.substring(tmp.length() - 2);
			s.insert(0, tmp);
			t /= 60;
		} while (t >= 60);
		s.insert(0, Long.toString(t));
		return s.toString();
	}
}
