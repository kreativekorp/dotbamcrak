package com.kreative.dotbamcrak.scores;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import com.kreative.dotbamcrak.utility.ParseException;
import com.kreative.dotbamcrak.utility.ParseUtilities;

public class ScoreMap {
	private SortedMap<String, ScoreList> scores = new TreeMap<String, ScoreList>();
	
	public Set<String> getLayoutNames() {
		return scores.keySet();
	}
	
	public ScoreList getScoresForLayoutName(String layoutName) {
		if (scores.containsKey(layoutName)) {
			return scores.get(layoutName);
		} else {
			ScoreList scoreList = new ScoreList();
			scores.put(layoutName, scoreList);
			return scoreList;
		}
	}
	
	public void clear() {
		scores.clear();
	}
	
	public boolean isEmpty() {
		for (ScoreList scoreList : scores.values()) {
			if (!scoreList.isEmpty()) return false;
		}
		return true;
	}
	
	public void truncate(int max) {
		for (ScoreList scoreList : scores.values()) {
			scoreList.truncate(max);
		}
	}
	
	public void readScores(File in) throws IOException {
		readScores(new Scanner(in, "UTF-8"));
	}
	
	public void readScores(Scanner in) {
		ScoreList currentScoreList = null;
		while (in.hasNextLine()) {
			String line = in.nextLine().trim();
			if (line.length() > 0 && !line.startsWith("#")) {
				String[] args = line.split("\\s*\t\\s*");
				if (args[0].equalsIgnoreCase("layout")) {
					try {
						String layoutName = ParseUtilities.index(args, 1, 0);
						currentScoreList = getScoresForLayoutName(layoutName);
					} catch (ParseException ignored) {
						// ignored
					}
				} else if (args[0].equalsIgnoreCase("score")) {
					try {
						long scoreTime = ParseUtilities.parseLong(ParseUtilities.index(args, 1, 0), 0);
						String scoreName = ParseUtilities.index(args, 2, 0);
						if (currentScoreList != null) currentScoreList.add(new Score(scoreName, scoreTime));
					} catch (ParseException ignored) {
						// ignored
					}
				}
			}
		}
	}
	
	public void writeScores(File out) throws IOException {
		writeScores(new PrintWriter(new OutputStreamWriter(new FileOutputStream(out), "UTF-8"), true));
	}
	
	public void writeScores(PrintWriter out) {
		for (Map.Entry<String, ScoreList> e : scores.entrySet()) {
			out.println("layout\t" + e.getKey());
			for (Score score : e.getValue()) {
				out.println("score\t" + score.getTime() + "\t" + score.getName());
			}
		}
	}
}
