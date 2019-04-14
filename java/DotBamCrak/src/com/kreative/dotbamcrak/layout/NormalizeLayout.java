package com.kreative.dotbamcrak.layout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class NormalizeLayout {
	public static void main(String[] args) {
		for (String arg : args) {
			File in = new File(arg);
			try {
				Layout layout = new Layout(in);
				int minx = Integer.MAX_VALUE;
				int miny = Integer.MAX_VALUE;
				int minz = Integer.MAX_VALUE;
				int maxx = Integer.MIN_VALUE;
				int maxy = Integer.MIN_VALUE;
				int maxz = Integer.MIN_VALUE;
				for (int z = 0; z < layout.getBoardDepth(); z++) {
					for (int y = 0; y < layout.getBoardHeight(); y++) {
						for (int x = 0; x < layout.getBoardWidth(); x++) {
							if (layout.getTile(x, y, z)) {
								if (x < minx) minx = x;
								if (y < miny) miny = y;
								if (z < minz) minz = z;
								if (x > maxx) maxx = x;
								if (y > maxy) maxy = y;
								if (z > maxz) maxz = z;
							}
						}
					}
				}
				PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File("NORM " + layout.toString() + ".mlo"))), true);
				out.println("boardsize\t"
						+ (maxx - minx + layout.getTileWidth()) + ","
						+ (maxy - miny + layout.getTileHeight()) + ","
						+ (maxz - minz + layout.getTileDepth()));
				out.println("tilesize\t"
						+ layout.getTileWidth() + ","
						+ layout.getTileHeight() + ","
						+ layout.getTileDepth());
				out.println("windowsize\t"
						+ (maxx - minx + layout.getTileWidth() + layout.getTileWidth() + layout.getTileWidth() / 2) + ","
						+ (maxy - miny + layout.getTileHeight() + layout.getTileHeight()));
				out.println();
				for (int z = 0; z < layout.getBoardDepth(); z++) {
					for (int y = 0; y < layout.getBoardHeight(); y++) {
						for (int x = 0; x < layout.getBoardWidth(); x++) {
							if (layout.getTile(x, y, z)) {
								out.println("tile\t" + (x - minx) + "," + (y - miny) + "," + (z - minz));
							}
						}
					}
				}
				out.flush();
				out.close();
			} catch (Exception e) {
				System.err.println(in.getAbsolutePath());
				System.err.println(e.getMessage());
			}
		}
	}
}
