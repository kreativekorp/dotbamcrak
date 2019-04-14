package com.kreative.dotbamcrak.scores;

public class Score implements Comparable<Score> {
	private String name;
	private long time;
	
	public Score(String name, long time) {
		this.name = name;
		this.time = time;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getTime() {
		return time;
	}
	
	public String getTimeString() {
		long t = time / 1000L;
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
	
	public String toString() {
		return name + " - " + getTimeString();
	}
	
	public int compareTo(Score other) {
		return Long.valueOf(this.time).compareTo(Long.valueOf(other.time));
	}
}
