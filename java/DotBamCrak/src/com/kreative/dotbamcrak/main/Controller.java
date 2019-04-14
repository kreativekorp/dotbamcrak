package com.kreative.dotbamcrak.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import com.kreative.dotbamcrak.board.Board;
import com.kreative.dotbamcrak.board.BoardPopulator;
import com.kreative.dotbamcrak.board.BoardView;
import com.kreative.dotbamcrak.board.RandomSolvableBoardPopulator;
import com.kreative.dotbamcrak.fortune.FortuneSet;
import com.kreative.dotbamcrak.layout.Layout;
import com.kreative.dotbamcrak.menus.MenuBar;
import com.kreative.dotbamcrak.messages.MainMessages;
import com.kreative.dotbamcrak.scores.Score;
import com.kreative.dotbamcrak.scores.ScoreList;
import com.kreative.dotbamcrak.scores.ScoreMap;
import com.kreative.dotbamcrak.scores.ScoreMapFrame;
import com.kreative.dotbamcrak.settings.ChangeBehaviorFrame;
import com.kreative.dotbamcrak.settings.Library;
import com.kreative.dotbamcrak.settings.Settings;
import com.kreative.dotbamcrak.tileset.Tile;
import com.kreative.dotbamcrak.tileset.TileSet;
import com.kreative.dotbamcrak.tileset.TileSetInfo;
import com.kreative.dotbamcrak.utility.OSUtilities;
import com.kreative.dotbamcrak.utility.Stopwatch;
import com.kreative.dotbamcrak.utility.StopwatchThread;

public class Controller implements MouseListener, MouseMotionListener {
	private static final String[] DEFAULT_TILE_SET = { "Default", "Standard", "Mahjong", "Kreative" };
	private static final String[] DEFAULT_LAYOUT = { "Default", "Standard", "Easy", "Turtle", "Dragon", "Kreative" };
	private static final String[] DEFAULT_FORTUNES = { "Default", "Standard", "Fortunes", "Kreative" };
	
	private static enum State {
		GAME_INITIALIZED,
		GAME_IN_PROGRESS,
		GAME_PAUSED,
		GAME_OVER;
	}
	
	private Random random;
	private ResourceBundle messages;
	private Library library;
	private Settings settings;
	private ScoreMap scores;
	private TileSet tileset;
	private Layout layout;
	private FortuneSet fortunes;
	private BoardPopulator boardPopulator;
	private Board board;
	private BoardView boardView;
	private JLabel tileDescriptionLabel;
	private JLabel tilesLeftLabel;
	private JLabel movesLeftLabel;
	private Stopwatch stopwatch;
	private JLabel stopwatchLabel;
	private StopwatchThread stopwatchThread;
	private MenuBar menuBar;
	private State state;
	
	public Controller() {
		random = new Random();
		messages = MainMessages.instance;
		library = new Library();
		settings = library.getSettings();
		scores = library.getScores();
		tileset = loadTileSet();
		layout = loadLayout();
		fortunes = loadFortunes();
		boardPopulator = new RandomSolvableBoardPopulator(random);
		board = new Board(tileset, layout, boardPopulator);
		boardView = new BoardView(board);
		tileDescriptionLabel = new JLabel("");
		tilesLeftLabel = new JLabel(Integer.toString(board.getTileCount()));
		movesLeftLabel = new JLabel(Integer.toString(board.getFreeMatchingTiles().size() / 2));
		stopwatch = new Stopwatch();
		stopwatchLabel = new JLabel("0:00");
		stopwatchThread = null;
		menuBar = new MenuBar(this);
		state = State.GAME_INITIALIZED;
		
		if (settings.backgroundColor != null) {
			boardView.setBackgroundPaint(settings.backgroundColor);
		}
		if (settings.backgroundImage != null) {
			try {
				boardView.setBackgroundImage(ImageIO.read(settings.backgroundImage));
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
		boardView.addMouseListener(this);
		boardView.addMouseMotionListener(this);
		menuBar.update();
	}
	
	private TileSet loadTileSet() {
		if (settings.tilesetFile != null && settings.tilesetResourceDirectory != null) try {
			return new TileSet(settings.tilesetFile, settings.tilesetResourceDirectory);
		} catch (Exception e) {}
		
		for (String name : DEFAULT_TILE_SET) {
			for (TileSetInfo tileset : library.getTileSets()) {
				if (tileset.getName().equalsIgnoreCase(name)) try {
					return tileset.createTileSet();
				} catch (Exception e) {}
			}
		}
		
		if (!library.getTileSets().isEmpty()) try {
			return library.getTileSets().get(0).createTileSet();
		} catch (Exception e) {}
		
		JOptionPane.showMessageDialog(null, messages.getString(MainMessages.ERROR_NO_TILE_SET), messages.getString(MainMessages.ERROR_TITLE), JOptionPane.ERROR_MESSAGE);
		System.exit(0);
		return null;
	}
	
	private Layout loadLayout() {
		if (settings.layoutFile != null) try {
			return new Layout(settings.layoutFile);
		} catch (Exception e) {}
		
		for (String name : DEFAULT_LAYOUT) {
			for (Layout layout : library.getLayouts()) {
				if (layout.getName().equalsIgnoreCase(name)) {
					return layout;
				}
			}
		}
		
		if (!library.getLayouts().isEmpty()) {
			return library.getLayouts().get(0);
		}
		
		JOptionPane.showMessageDialog(null, messages.getString(MainMessages.ERROR_NO_LAYOUT), messages.getString(MainMessages.ERROR_TITLE), JOptionPane.ERROR_MESSAGE);
		System.exit(0);
		return null;
	}
	
	private FortuneSet loadFortunes() {
		if (settings.fortuneFile != null) try {
			return new FortuneSet(settings.fortuneFile);
		} catch (Exception e) {}
		
		for (String name : DEFAULT_FORTUNES) {
			for (FortuneSet fortunes : library.getFortuneSets()) {
				if (fortunes.getName().equalsIgnoreCase(name)) {
					return fortunes;
				}
			}
		}
		
		if (!library.getFortuneSets().isEmpty()) {
			return library.getFortuneSets().get(0);
		}
		
		JOptionPane.showMessageDialog(null, messages.getString(MainMessages.ERROR_NO_FORTUNE_SET), messages.getString(MainMessages.ERROR_TITLE), JOptionPane.ERROR_MESSAGE);
		System.exit(0);
		return null;
	}
	
	public synchronized void startThreads() {
		if (stopwatchThread != null) stopwatchThread.interrupt();
		stopwatchThread = new StopwatchThread(stopwatch, stopwatchLabel);
		stopwatchThread.start();
	}
	
	public synchronized void stopThreads() {
		if (stopwatchThread != null) {
			stopwatchThread.interrupt();
			stopwatchThread = null;
		}
	}
	
	public Library getLibrary() {
		return library;
	}
	
	public BoardView getBoardView() {
		return boardView;
	}
	
	public JLabel getTileDescriptionView() {
		return tileDescriptionLabel;
	}
	
	public JLabel getTilesLeftView() {
		return tilesLeftLabel;
	}
	
	public JLabel getMovesLeftView() {
		return movesLeftLabel;
	}
	
	public JLabel getStopwatchView() {
		return stopwatchLabel;
	}
	
	public MenuBar getMenuBar() {
		return menuBar;
	}
	
	public synchronized void newGame() {
		board = new Board(tileset, layout, boardPopulator);
		boardView.setBoard(board);
		boardView.setPaused(false);
		tileDescriptionLabel.setVisible(true);
		tileDescriptionLabel.setText("");
		tilesLeftLabel.setText(Integer.toString(board.getTileCount()));
		movesLeftLabel.setText(Integer.toString(board.getFreeMatchingTiles().size() / 2));
		stopwatch.reset();
		state = State.GAME_INITIALIZED;
		menuBar.update();
	}
	
	private void startGame() {
		if (state == State.GAME_INITIALIZED || state == State.GAME_PAUSED) {
			boardView.setPaused(false);
			tileDescriptionLabel.setVisible(true);
			stopwatch.start();
			state = State.GAME_IN_PROGRESS;
			menuBar.update();
		}
	}
	
	public synchronized boolean canPauseGame() {
		return (state == State.GAME_INITIALIZED || state == State.GAME_IN_PROGRESS);
	}
	
	public synchronized void pauseGame() {
		if (state == State.GAME_INITIALIZED || state == State.GAME_IN_PROGRESS) {
			boardView.setPaused(true);
			tileDescriptionLabel.setVisible(false);
			stopwatch.stop();
			state = State.GAME_PAUSED;
			menuBar.update();
		}
	}
	
	public synchronized boolean canContinueGame() {
		return (state == State.GAME_INITIALIZED || state == State.GAME_PAUSED);
	}
	
	public synchronized void continueGame() {
		if (state == State.GAME_INITIALIZED || state == State.GAME_PAUSED) {
			boardView.setPaused(false);
			tileDescriptionLabel.setVisible(true);
			stopwatch.start();
			state = State.GAME_IN_PROGRESS;
			menuBar.update();
		}
	}
	
	private void endGame() {
		board.clearSelection();
		boardView.setPaused(false);
		tileDescriptionLabel.setVisible(true);
		tileDescriptionLabel.setText("");
		tilesLeftLabel.setText(Integer.toString(board.getTileCount()));
		movesLeftLabel.setText(Integer.toString(board.getFreeMatchingTiles().size() / 2));
		stopwatch.stop();
		state = State.GAME_OVER;
		menuBar.update();
	}
	
	public synchronized boolean canUndo() {
		return (state == State.GAME_IN_PROGRESS && board.canUndo());
	}
	
	public synchronized void undo() {
		if (state == State.GAME_IN_PROGRESS && board.canUndo()) {
			board.clearSelection();
			board.undo();
			tileDescriptionLabel.setText("");
			tilesLeftLabel.setText(Integer.toString(board.getTileCount()));
			movesLeftLabel.setText(Integer.toString(board.getFreeMatchingTiles().size() / 2));
			stopwatch.addTime(1000L * settings.undoPenalty);
			menuBar.update();
		}
	}
	
	public synchronized boolean canRedo() {
		return (state == State.GAME_IN_PROGRESS && board.canRedo());
	}
	
	public synchronized void redo() {
		if (state == State.GAME_IN_PROGRESS && board.canRedo()) {
			board.clearSelection();
			board.redo();
			tileDescriptionLabel.setText("");
			tilesLeftLabel.setText(Integer.toString(board.getTileCount()));
			movesLeftLabel.setText(Integer.toString(board.getFreeMatchingTiles().size() / 2));
			stopwatch.addTime(1000L * settings.redoPenalty);
			menuBar.update();
		}
	}
	
	public synchronized boolean canStartOver() {
		return (state == State.GAME_IN_PROGRESS && board.canUndo());
	}
	
	public synchronized void startOver() {
		if (state == State.GAME_IN_PROGRESS && board.canUndo()) {
			board.clearSelection();
			board.undoAll();
			tileDescriptionLabel.setText("");
			tilesLeftLabel.setText(Integer.toString(board.getTileCount()));
			movesLeftLabel.setText(Integer.toString(board.getFreeMatchingTiles().size() / 2));
			if (board.getTileCount() < layout.getTileCount()) {
				stopwatch.addTime(1000L * settings.undoPenalty);
			} else {
				stopwatch.reset();
				stopwatch.start();
			}
			menuBar.update();
		}
	}
	
	public synchronized boolean canShuffleTiles() {
		return (state == State.GAME_INITIALIZED || state == State.GAME_IN_PROGRESS);
	}
	
	public synchronized void shuffleTiles() {
		if (state == State.GAME_INITIALIZED) {
			startGame();
		}
		if (state == State.GAME_IN_PROGRESS) {
			board.clearSelection();
			board.shuffleTiles();
			tileDescriptionLabel.setText("");
			tilesLeftLabel.setText(Integer.toString(board.getTileCount()));
			movesLeftLabel.setText(Integer.toString(board.getFreeMatchingTiles().size() / 2));
			stopwatch.addTime(1000L * settings.shufflePenalty);
			menuBar.update();
		}
	}
	
	public synchronized boolean canShowMatches() {
		return (state == State.GAME_INITIALIZED || state == State.GAME_IN_PROGRESS);
	}
	
	public synchronized void showRandomMatch() {
		if (state == State.GAME_INITIALIZED) {
			startGame();
		}
		if (state == State.GAME_IN_PROGRESS) {
			List<int[]> freeTiles = board.getFreeMatchingTiles();
			board.clearSelection();
			if (freeTiles.size() >= 2) {
				int[] t1 = freeTiles.remove(random.nextInt(freeTiles.size()));
				Tile t1t = board.getTile(t1[0], t1[1], t1[2]);
				while (freeTiles.size() > 0) {
					int[] t2 = freeTiles.remove(random.nextInt(freeTiles.size()));
					Tile t2t = board.getTile(t2[0], t2[1], t2[2]);
					if (t1t.getMatchSet().contains(t2t)) {
						board.setTileSelected(t1[0], t1[1], t1[2], true);
						board.setTileSelected(t2[0], t2[1], t2[2], true);
						break;
					}
				}
			}
			tileDescriptionLabel.setText("");
			stopwatch.addTime(1000L * settings.showMatchPenalty);
		}
	}
	
	public synchronized void showSelectedMatch() {
		if (state == State.GAME_INITIALIZED) {
			startGame();
		}
		if (state == State.GAME_IN_PROGRESS) {
			List<int[]> selectedTiles = board.getSelectedTiles();
			List<int[]> freeTiles = board.getFreeMatchingTiles();
			board.clearSelection();
			if (selectedTiles.size() == 1 && freeTiles.size() > 0) {
				int[] t1 = removeIntArray(freeTiles, selectedTiles.get(0));
				Tile t1t = board.getTile(t1[0], t1[1], t1[2]);
				while (freeTiles.size() > 0) {
					int[] t2 = freeTiles.remove(random.nextInt(freeTiles.size()));
					Tile t2t = board.getTile(t2[0], t2[1], t2[2]);
					if (t1t.getMatchSet().contains(t2t)) {
						board.setTileSelected(t1[0], t1[1], t1[2], true);
						board.setTileSelected(t2[0], t2[1], t2[2], true);
						break;
					}
				}
			}
			tileDescriptionLabel.setText("");
			stopwatch.addTime(1000L * settings.showMatchPenalty);
		}
	}
	
	private int[] removeIntArray(List<int[]> list, int[] array) {
		for (int i = 0; i < list.size(); i++) {
			if (Arrays.equals(list.get(i), array)) {
				return list.remove(i);
			}
		}
		return null;
	}
	
	public synchronized void showAllMatches() {
		if (state == State.GAME_INITIALIZED) {
			startGame();
		}
		if (state == State.GAME_IN_PROGRESS) {
			List<int[]> freeTiles = board.getFreeMatchingTiles();
			board.clearSelection();
			for (int[] t : freeTiles) {
				board.setTileSelected(t[0], t[1], t[2], true);
			}
			tileDescriptionLabel.setText("");
			stopwatch.addTime(1000L * settings.showAllMatchesPenalty);
		}
	}
	
	public synchronized boolean showRandomMatchAndRemove() {
		if (state == State.GAME_INITIALIZED) {
			startGame();
		}
		if (state == State.GAME_IN_PROGRESS) {
			List<int[]> freeTiles = board.getFreeMatchingTiles();
			if (freeTiles.size() >= 2) {
				int[] t1 = freeTiles.remove(random.nextInt(freeTiles.size()));
				Tile t1t = board.getTile(t1[0], t1[1], t1[2]);
				while (freeTiles.size() > 0) {
					int[] t2 = freeTiles.remove(random.nextInt(freeTiles.size()));
					Tile t2t = board.getTile(t2[0], t2[1], t2[2]);
					if (t1t.getMatchSet().contains(t2t)) {
						try {
							Thread.sleep(settings.autoPlaySpeed);
						} catch (InterruptedException ignored) {
							return false;
						}
						board.clearSelection();
						board.setTileSelected(t1[0], t1[1], t1[2], true);
						board.setTileSelected(t2[0], t2[1], t2[2], true);
						if (t1t.getName().equals(t2t.getName())) {
							tileDescriptionLabel.setText(t1t.getName());
						} else {
							tileDescriptionLabel.setText("");
						}
						stopwatch.addTime(1000L * settings.autoPlayPenalty);
						try {
							Thread.sleep(settings.autoPlaySpeed);
						} catch (InterruptedException ignored) {
							return false;
						}
						board.removeTiles(t1[0], t1[1], t1[2], t2[0], t2[1], t2[2]);
						tileDescriptionLabel.setText("");
						tilesLeftLabel.setText(Integer.toString(board.getTileCount()));
						movesLeftLabel.setText(Integer.toString(board.getFreeMatchingTiles().size() / 2));
						menuBar.update();
						if (board.getTileCount() == 0) {
							endGame();
							// No fortune, no high score for cheating. ;)
							return false;
						} else {
							return true;
						}
					}
				}
			}
			noFreeTiles();
		}
		return false;
	}
	
	public boolean canShowHighScores() {
		return !scores.isEmpty();
	}
	
	public void showHighScores() {
		if (scores.isEmpty()) {
			JOptionPane.showMessageDialog(
					null,
					messages.getString(MainMessages.MESSAGE_NO_SCORES),
					messages.getString(MainMessages.MESSAGE_TITLE),
					JOptionPane.INFORMATION_MESSAGE
			);
		} else {
			ScoreMapFrame frame = new ScoreMapFrame(
					null, null,
					scores, settings.highScoreCount,
					null, layout.toString()
			);
			frame.setVisible(true);
		}
	}
	
	public synchronized TileSet getTileSet() {
		return tileset;
	}
	
	public synchronized void setTileSet(TileSet tileset) {
		this.tileset = tileset;
		settings.tilesetResourceDirectory = tileset.getResourceDirectory();
		settings.tilesetFile = tileset.getFile();
		library.setSettings(settings);
		newGame();
		pack();
	}
	
	public synchronized Layout getLayout() {
		return layout;
	}
	
	public synchronized void setLayout(Layout layout) {
		this.layout = layout;
		settings.layoutFile = layout.getFile();
		library.setSettings(settings);
		newGame();
		pack();
	}
	
	public synchronized FortuneSet getFortuneSet() {
		return fortunes;
	}
	
	public synchronized void setFortuneSet(FortuneSet fortunes) {
		this.fortunes = fortunes;
		settings.fortuneFile = fortunes.getFile();
		library.setSettings(settings);
	}
	
	public Color getBackgroundColor() {
		return settings.backgroundColor;
	}
	
	public File getBackgroundImage() {
		return settings.backgroundImage;
	}
	
	public void resetBackground() {
		boardView.setBackgroundPaint(null);
		boardView.setBackgroundImage(null);
		settings.backgroundColor = null;
		settings.backgroundImage = null;
		library.setSettings(settings);
	}
	
	public void setBackgroundColor(Color color) {
		boardView.setBackgroundPaint(color);
		settings.backgroundColor = color;
		library.setSettings(settings);
	}
	
	public void setBackgroundImage(File image) {
		try {
			boardView.setBackgroundImage(ImageIO.read(image));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		settings.backgroundImage = image;
		library.setSettings(settings);
	}
	
	public void changeBehavior() {
		new ChangeBehaviorFrame(library, settings).setVisible(true);
	}
	
	public synchronized void mouseEntered(MouseEvent e) {
		mouseMoved(e);
	}
	
	public synchronized void mouseMoved(MouseEvent e) {
		if (state == State.GAME_INITIALIZED || state == State.GAME_IN_PROGRESS) {
			if (settings.cursorOnFreeTileOnly) {
				int[] r = boardView.resolveTile(e);
				if (r != null && board.isTileFree(r[0], r[1], r[2])) {
					boardView.setCursor(Cursor.getPredefinedCursor(settings.cursor));
				} else {
					boardView.setCursor(Cursor.getDefaultCursor());
				}
			} else {
				boardView.setCursor(Cursor.getPredefinedCursor(settings.cursor));
			}
		} else {
			boardView.setCursor(Cursor.getDefaultCursor());
		}
	}
	
	public void mouseExited(MouseEvent e) {
		// Ignored.
	}
	
	public synchronized void mousePressed(MouseEvent e) {
		if (state == State.GAME_OVER && settings.doubleClickToStartNewGame && e.getClickCount() >= 2) {
			newGame();
			return;
		}
		if (state == State.GAME_PAUSED) {
			continueGame();
			return;
		}
		if (state == State.GAME_INITIALIZED) {
			startGame();
		}
		if (state == State.GAME_IN_PROGRESS) {
			int[] r = boardView.resolveTile(e);
			if (r == null) {
				if (settings.deselectOnBackground) {
					board.clearSelection();
					tileDescriptionLabel.setText("");
				}
			} else if (board.isTileFree(r[0], r[1], r[2])) {
				List<int[]> selectedTiles = board.getSelectedTiles();
				if (selectedTiles.isEmpty()) {
					board.setTileSelected(r[0], r[1], r[2], true);
					tileDescriptionLabel.setText(board.getTile(r[0], r[1], r[2]).toString());
				} else if (selectedTiles.size() > 1) {
					board.clearSelection();
					board.setTileSelected(r[0], r[1], r[2], true);
					tileDescriptionLabel.setText(board.getTile(r[0], r[1], r[2]).toString());
				} else {
					int[] s = selectedTiles.get(0);
					if (r[0] == s[0] && r[1] == s[1] && r[2] == s[2]) {
						board.setTileSelected(s[0], s[1], s[2], false);
						tileDescriptionLabel.setText("");
					} else {
						Tile t1 = board.getTile(r[0], r[1], r[2]);
						Tile t2 = board.getTile(s[0], s[1], s[2]);
						if (t1.getMatchSet().contains(t2)) {
							board.removeTiles(r[0], r[1], r[2], s[0], s[1], s[2]);
							tileDescriptionLabel.setText("");
							tilesLeftLabel.setText(Integer.toString(board.getTileCount()));
							movesLeftLabel.setText(Integer.toString(board.getFreeMatchingTiles().size() / 2));
							menuBar.update();
							if (board.getTileCount() == 0) {
								endGame();
								if (settings.displayFortune && !fortunes.isEmpty()) {
									JOptionPane.showMessageDialog(
											null,
											fortunes.getFortune(),
											messages.getString(MainMessages.FORTUNE_TITLE),
											JOptionPane.INFORMATION_MESSAGE
									);
								}
								String scoreName = OSUtilities.getLongUserName();
								if (scoreName.equals("")) scoreName = OSUtilities.getShortUserName();
								Score score = new Score(scoreName, stopwatch.getTime());
								ScoreList scoreList = scores.getScoresForLayoutName(layout.toString());
								scoreList.add(score);
								scoreList.truncate(settings.highScoreCount);
								if (scoreList.contains(score)) {
									ScoreMapFrame frame = new ScoreMapFrame(
											new Runnable() {
												public void run() {
													library.setScores(scores);
												}
											},
											new Runnable() {
												public void run() {
													newGame();
												}
											},
											scores, settings.highScoreCount,
											score, layout.toString()
									);
									frame.setVisible(true);
									if (frame.getScoreNameField() != null) {
										frame.getScoreNameField().requestFocusInWindow();
									}
								}
							} else if (board.getFreeMatchingTiles().isEmpty()) {
								if (settings.warnOnNoFreeTiles) {
									noFreeTiles();
								}
							}
						} else {
							if (settings.reselectOnNonMatchingTile) {
								board.setTileSelected(s[0], s[1], s[2], false);
								board.setTileSelected(r[0], r[1], r[2], true);
								tileDescriptionLabel.setText(board.getTile(r[0], r[1], r[2]).toString());
							}
							if (settings.warnOnNonMatchingTile) {
								JOptionPane.showMessageDialog(
										null,
										messages.getString(MainMessages.MESSAGE_TILES_DONT_MATCH),
										messages.getString(MainMessages.MESSAGE_TITLE),
										JOptionPane.INFORMATION_MESSAGE
								);
							}
						}
					}
				}
			} else {
				if (settings.deselectOnNonFreeTile) {
					board.clearSelection();
					tileDescriptionLabel.setText("");
				}
				if (settings.warnOnNonFreeTile) {
					JOptionPane.showMessageDialog(
							null,
							messages.getString(MainMessages.MESSAGE_TILE_NOT_FREE),
							messages.getString(MainMessages.MESSAGE_TITLE),
							JOptionPane.INFORMATION_MESSAGE
					);
				}
			}
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		// Ignored.
	}
	
	public void mouseReleased(MouseEvent e) {
		// Ignored.
	}
	
	public void mouseClicked(MouseEvent e) {
		// Ignored.
	}
	
	private void noFreeTiles() {
		String giveUpOption = messages.getString(MainMessages.MESSAGE_NO_FREE_TILES_GIVE_UP);
		String shuffleOption = messages.getString(MainMessages.MESSAGE_NO_FREE_TILES_SHUFFLE);
		String undoOption = messages.getString(MainMessages.MESSAGE_NO_FREE_TILES_UNDO);
		String startOverOption = messages.getString(MainMessages.MESSAGE_NO_FREE_TILES_START_OVER);
		String newGameOption = messages.getString(MainMessages.MESSAGE_NO_FREE_TILES_NEW_GAME);
		List<String> options = new ArrayList<String>();
		options.add(giveUpOption);
		if (settings.allowShuffleOnNoFreeTiles && canShuffleTiles() && board.getTileCount() > 2) options.add(shuffleOption);
		if (settings.allowUndoOnNoFreeTiles && canUndo()) options.add(undoOption);
		if (settings.allowStartOverOnNoFreeTiles && canStartOver()) options.add(startOverOption);
		if (settings.allowNewGameOnNoFreeTiles) options.add(newGameOption);
		int chosen = JOptionPane.showOptionDialog(
				null,
				messages.getString(MainMessages.MESSAGE_NO_FREE_TILES),
				messages.getString(MainMessages.MESSAGE_TITLE),
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE,
				null,
				options.toArray(new String[0]),
				options.get(0)
		);
		if (chosen >= 0) {
			if (options.get(chosen).equals(shuffleOption)) {
				shuffleTiles();
				if (board.getFreeMatchingTiles().isEmpty()) {
					noFreeTiles();
				}
			} else if (options.get(chosen).equals(undoOption)) {
				undo();
			} else if (options.get(chosen).equals(startOverOption)) {
				startOver();
			} else if (options.get(chosen).equals(newGameOption)) {
				newGame();
			} else {
				endGame();
			}
		} else {
			endGame();
		}
	}
	
	public void pack() {
		Component c = boardView;
		while (true) {
			if (c == null) {
				return;
			} else if (c instanceof Frame) {
				Frame f = (Frame)c;
				f.pack();
				return;
			} else if (c instanceof Dialog) {
				Dialog d = (Dialog)c;
				d.pack();
				return;
			} else if (c instanceof Window) {
				Window w = (Window)c;
				w.pack();
				return;
			} else {
				c = c.getParent();
			}
		}
	}
	
	public void pack(double scale) {
		Component c = boardView;
		while (true) {
			if (c == null) {
				return;
			} else if (c instanceof Frame) {
				Frame f = (Frame)c;
				Dimension size = f.getSize();
				size.width -= boardView.getWidth();
				size.height -= boardView.getHeight();
				size.width += (int)Math.round(scale * board.getWindowPixelWidth());
				size.height += (int)Math.round(scale * board.getWindowPixelHeight());
				f.setSize(size);
				return;
			} else if (c instanceof Dialog) {
				Dialog d = (Dialog)c;
				Dimension size = d.getSize();
				size.width -= boardView.getWidth();
				size.height -= boardView.getHeight();
				size.width += (int)Math.round(scale * board.getWindowPixelWidth());
				size.height += (int)Math.round(scale * board.getWindowPixelHeight());
				d.setSize(size);
				return;
			} else if (c instanceof Window) {
				Window w = (Window)c;
				Dimension size = w.getSize();
				size.width -= boardView.getWidth();
				size.height -= boardView.getHeight();
				size.width += (int)Math.round(scale * board.getWindowPixelWidth());
				size.height += (int)Math.round(scale * board.getWindowPixelHeight());
				w.setSize(size);
				return;
			} else {
				c = c.getParent();
			}
		}
	}
	
	public void fullScreen() {
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		Component c = boardView;
		while (true) {
			if (c == null) {
				return;
			} else if (c instanceof Frame) {
				Frame f = (Frame)c;
				f.setLocation(0, 0);
				f.setSize(size);
				return;
			} else if (c instanceof Dialog) {
				Dialog d = (Dialog)c;
				d.setLocation(0, 0);
				d.setSize(size);
				return;
			} else if (c instanceof Window) {
				Window w = (Window)c;
				w.setLocation(0, 0);
				w.setSize(size);
				return;
			} else {
				c = c.getParent();
			}
		}
	}
	
	public void centerWindow() {
		Component c = boardView;
		while (true) {
			if (c == null) {
				return;
			} else if (c instanceof Frame) {
				Frame f = (Frame)c;
				f.setLocationRelativeTo(null);
				return;
			} else if (c instanceof Dialog) {
				Dialog d = (Dialog)c;
				d.setLocationRelativeTo(null);
				return;
			} else if (c instanceof Window) {
				Window w = (Window)c;
				w.setLocationRelativeTo(null);
				return;
			} else {
				c = c.getParent();
			}
		}
	}
}
