package com.kreative.dotbamcrak.settings;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.kreative.dotbamcrak.fortune.FortuneSet;
import com.kreative.dotbamcrak.layout.Layout;
import com.kreative.dotbamcrak.scores.ScoreMap;
import com.kreative.dotbamcrak.tileset.TileSetInfo;

public class Library {
	private List<File> roots;
	private File saveRoot;
	private Settings settings = null;
	private ScoreMap scores = null;
	private List<TileSetInfo> tilesets = null;
	private List<Layout> layouts = null;
	private List<FortuneSet> fortunes = null;
	
	public Library() {
		roots = new ArrayList<File>();
		saveRoot = null;
		try {
			String osName = System.getProperty("os.name").toLowerCase();
			File home = new File(System.getProperty("user.home"));
			if (osName.contains("mac os")) {
				for (File root : File.listRoots()) {
					File lib = new File(root, "Library");
					File appsup = new File(lib, "Application Support");
					File dbc = new File(appsup, "DotBamCrak").getAbsoluteFile();
					if (dbc.exists() && dbc.isDirectory() && !roots.contains(dbc)) roots.add(dbc);
				}
				File lib = new File(home, "Library");
				File appsup = new File(lib, "Application Support");
				File dbc = new File(appsup, "DotBamCrak").getAbsoluteFile();
				if (dbc.exists() && dbc.isDirectory() && !roots.contains(dbc)) roots.add(dbc);
				saveRoot = dbc;
				dbc = new File(home, ".dotbamcrak").getAbsoluteFile();
				if (dbc.exists() && dbc.isDirectory() && !roots.contains(dbc)) roots.add(dbc);
				dbc = new File(home, "DotBamCrak").getAbsoluteFile();
				if (dbc.exists() && dbc.isDirectory() && !roots.contains(dbc)) roots.add(dbc);
			} else if (osName.contains("windows")) {
				for (File root : File.listRoots()) {
					File docs = new File(root, "Documents and Settings");
					if (docs.exists() && docs.isDirectory()) {
						File appdata = new File(new File(docs, "All Users"), "Application Data");
						File krea = new File(appdata, "Kreative");
						File dbc = new File(krea, "DotBamCrak").getAbsoluteFile();
						if (dbc.exists() && dbc.isDirectory() && !roots.contains(dbc)) roots.add(dbc);
						dbc = new File(appdata, "DotBamCrak").getAbsoluteFile();
						if (dbc.exists() && dbc.isDirectory() && !roots.contains(dbc)) roots.add(dbc);
					}
					File users = new File(root, "Users");
					if (users.exists() && users.isDirectory()) {
						File appdata = new File(new File(users, "All Users"), "Application Data");
						File krea = new File(appdata, "Kreative");
						File dbc = new File(krea, "DotBamCrak").getAbsoluteFile();
						if (dbc.exists() && dbc.isDirectory() && !roots.contains(dbc)) roots.add(dbc);
						dbc = new File(appdata, "DotBamCrak").getAbsoluteFile();
						if (dbc.exists() && dbc.isDirectory() && !roots.contains(dbc)) roots.add(dbc);
					}
				}
				File appdata = new File(home, "Application Data");
				File krea = new File(appdata, "Kreative");
				File dbc = new File(krea, "DotBamCrak").getAbsoluteFile();
				if (dbc.exists() && dbc.isDirectory() && !roots.contains(dbc)) roots.add(dbc);
				saveRoot = dbc;
				dbc = new File(appdata, "DotBamCrak").getAbsoluteFile();
				if (dbc.exists() && dbc.isDirectory() && !roots.contains(dbc)) roots.add(dbc);
				dbc = new File(home, "DotBamCrak").getAbsoluteFile();
				if (dbc.exists() && dbc.isDirectory() && !roots.contains(dbc)) roots.add(dbc);
			} else {
				File dbc = new File("/usr/share/dotbamcrak").getAbsoluteFile();
				if (dbc.exists() && dbc.isDirectory() && !roots.contains(dbc)) roots.add(dbc);
				dbc = new File("/usr/local/share/dotbamcrak").getAbsoluteFile();
				if (dbc.exists() && dbc.isDirectory() && !roots.contains(dbc)) roots.add(dbc);
				dbc = new File(home, ".dotbamcrak").getAbsoluteFile();
				if (dbc.exists() && dbc.isDirectory() && !roots.contains(dbc)) roots.add(dbc);
				saveRoot = dbc;
				dbc = new File(home, "DotBamCrak").getAbsoluteFile();
				if (dbc.exists() && dbc.isDirectory() && !roots.contains(dbc)) roots.add(dbc);
			}
		} catch (Exception ignored) {}
		File dbc = new File("a").getAbsoluteFile().getParentFile().getAbsoluteFile();
		if (dbc.exists() && dbc.isDirectory() && !roots.contains(dbc)) roots.add(dbc);
		if (saveRoot == null) saveRoot = dbc;
	}
	
	public Settings getSettings() {
		if (settings != null) return settings;
		settings = new Settings();
		for (File root : roots) {
			File settingsFile = new File(root, "Settings.mjs");
			if (settingsFile.exists() && settingsFile.canRead()) {
				try {
					settings.readSettings(settingsFile);
				} catch (Exception e) {
					System.err.println(settingsFile.getAbsolutePath());
					System.err.println(e.getMessage());
				}
			}
		}
		return settings;
	}
	
	public void setSettings(Settings settings) {
		this.settings = settings;
		for (int i = roots.size() - 1; i >= 0; i--) {
			File settingsFile = new File(roots.get(i), "Settings.mjs");
			if (settingsFile.exists() && settingsFile.canWrite()) {
				try {
					settings.writeSettings(settingsFile);
					return;
				} catch (Exception e) {
					System.err.println(settingsFile.getAbsolutePath());
					System.err.println(e.getMessage());
				}
			}
		}
		
		saveRoot.mkdirs();
		File settingsFile = new File(saveRoot, "Settings.mjs");
		try {
			settings.writeSettings(settingsFile);
		} catch (Exception e) {
			System.err.println(settingsFile.getAbsolutePath());
			System.err.println(e.getMessage());
		}
	}
	
	public ScoreMap getScores() {
		if (scores != null) return scores;
		scores = new ScoreMap();
		for (File root : roots) {
			File scoresFile = new File(root, "Scores.mjs");
			if (scoresFile.exists() && scoresFile.canRead()) {
				try {
					scores.readScores(scoresFile);
				} catch (Exception e) {
					System.err.println(scoresFile.getAbsolutePath());
					System.err.println(e.getMessage());
				}
			}
		}
		return scores;
	}
	
	public void setScores(ScoreMap scores) {
		this.scores = scores;
		for (int i = roots.size() - 1; i >= 0; i--) {
			File scoresFile = new File(roots.get(i), "Scores.mjs");
			if (scoresFile.exists() && scoresFile.canWrite()) {
				try {
					scores.writeScores(scoresFile);
					return;
				} catch (Exception e) {
					System.err.println(scoresFile.getAbsolutePath());
					System.err.println(e.getMessage());
				}
			}
		}
		
		saveRoot.mkdirs();
		File scoresFile = new File(saveRoot, "Scores.mjs");
		try {
			scores.writeScores(scoresFile);
		} catch (Exception e) {
			System.err.println(scoresFile.getAbsolutePath());
			System.err.println(e.getMessage());
		}
	}
	
	public List<TileSetInfo> getTileSets() {
		if (tilesets != null) return tilesets;
		tilesets = new ArrayList<TileSetInfo>();
		for (File root : roots) {
			File tilesetsDirectory = new File(root, "Tile Sets");
			if (tilesetsDirectory.exists() && tilesetsDirectory.isDirectory()) {
				for (File tilesetFile : tilesetsDirectory.listFiles()) {
					if (acceptFileName(tilesetFile.getName())) {
						try {
							tilesets.add(new TileSetInfo(tilesetFile));
						} catch (Exception e) {
							System.err.println(tilesetFile.getAbsolutePath());
							System.err.println(e.getMessage());
						}
					}
				}
			}
		}
		Collections.sort(tilesets, new Comparator<TileSetInfo> () {
			public int compare(TileSetInfo a, TileSetInfo b) {
				return a.toString().compareToIgnoreCase(b.toString());
			}
		});
		return tilesets;
	}
	
	public List<Layout> getLayouts() {
		if (layouts != null) return layouts;
		layouts = new ArrayList<Layout>();
		for (File root : roots) {
			File layoutsDirectory = new File(root, "Layouts");
			if (layoutsDirectory.exists() && layoutsDirectory.isDirectory()) {
				for (File layoutFile : layoutsDirectory.listFiles()) {
					if (acceptFileName(layoutFile.getName())) {
						try {
							layouts.add(new Layout(layoutFile));
						} catch (Exception e) {
							System.err.println(layoutFile.getAbsolutePath());
							System.err.println(e.getMessage());
						}
					}
				}
			}
		}
		Collections.sort(layouts, new Comparator<Layout> () {
			public int compare(Layout a, Layout b) {
				return a.toString().compareToIgnoreCase(b.toString());
			}
		});
		return layouts;
	}
	
	public List<FortuneSet> getFortuneSets() {
		if (fortunes != null) return fortunes;
		fortunes = new ArrayList<FortuneSet>();
		for (File root : roots) {
			File fortunesDirectory = new File(root, "Fortunes");
			if (fortunesDirectory.exists() && fortunesDirectory.isDirectory()) {
				for (File fortuneFile : fortunesDirectory.listFiles()) {
					if (acceptFileName(fortuneFile.getName())) {
						try {
							fortunes.add(new FortuneSet(fortuneFile));
						} catch (Exception e) {
							System.err.println(fortuneFile.getAbsolutePath());
							System.err.println(e.getMessage());
						}
					}
				}
			}
		}
		Collections.sort(fortunes, new Comparator<FortuneSet> () {
			public int compare(FortuneSet a, FortuneSet b) {
				return a.toString().compareToIgnoreCase(b.toString());
			}
		});
		return fortunes;
	}
	
	private boolean acceptFileName(String name) {
		if (name.startsWith(".")) return false;
		if (name.endsWith("\n")) return false;
		if (name.endsWith("\r")) return false;
		if (name.endsWith("\uF00A")) return false;
		if (name.endsWith("\uF00D")) return false;
		return true;
	}
}
