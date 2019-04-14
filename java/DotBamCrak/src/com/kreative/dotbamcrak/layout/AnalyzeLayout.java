package com.kreative.dotbamcrak.layout;

import java.io.File;
import java.util.List;
import java.util.Random;

import com.kreative.dotbamcrak.board.Board;
import com.kreative.dotbamcrak.board.BoardPopulator;
import com.kreative.dotbamcrak.board.RandomSolvableBoardPopulator;
import com.kreative.dotbamcrak.settings.Library;
import com.kreative.dotbamcrak.tileset.Tile;
import com.kreative.dotbamcrak.tileset.TileSet;

public class AnalyzeLayout {
	public static void main(String[] args) {
		int iterations = 1000;
		Random random = new Random();
		BoardPopulator populator = new RandomSolvableBoardPopulator(random);
		TileSet tileset;
		try {
			tileset = new Library().getTileSets().get(0).createTileSet();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
			return;
		}
		System.out.println("Layout\tAvg. Opening Moves\tAvg. Leftover Tiles\tSuccess Rate");
		for (String arg : args) {
			if (arg.startsWith("-n=")) {
				iterations = Integer.parseInt(arg.substring(3));
			} else {
				File in = new File(arg);
				try {
					Layout layout = new Layout(in);
					analyzeLayout(random, populator, tileset, layout, iterations);
				} catch (Exception e) {
					System.err.println(in.getAbsolutePath());
					System.err.println(e.getMessage());
				}
			}
		}
	}
	
	public static void analyzeLayout(Random random, BoardPopulator populator, TileSet tileset, Layout layout, int iterations) {
		System.out.print(layout.toString());
		int openingMoves = 0;
		int leftOverTiles = 0;
		int successfulBoards = 0;
		int attempts = 0;
		for (int i = 0; i < iterations; i++) {
			Board board = new Board(tileset, layout, populator);
			List<int[]> freeTiles = board.getFreeMatchingTiles();
			openingMoves += freeTiles.size() / 2;
			while (freeTiles.size() >= 2) {
				int[] t1 = freeTiles.remove(random.nextInt(freeTiles.size()));
				Tile t1t = board.getTile(t1[0], t1[1], t1[2]);
				while (freeTiles.size() > 0) {
					int[] t2 = freeTiles.remove(random.nextInt(freeTiles.size()));
					Tile t2t = board.getTile(t2[0], t2[1], t2[2]);
					if (t1t.getMatchSet().contains(t2t)) {
						board.removeTiles(t1[0], t1[1], t1[2], t2[0], t2[1], t2[2]);
						break;
					}
				}
				freeTiles = board.getFreeMatchingTiles();
			}
			leftOverTiles += board.getTileCount();
			if (board.getTileCount() == 0) successfulBoards++;
			attempts++;
		}
		System.out.print("\t" + (double)openingMoves/(double)attempts);
		System.out.print("\t" + (double)leftOverTiles/(double)attempts);
		System.out.print("\t" + (double)successfulBoards/(double)attempts);
		System.out.println();
	}
}
