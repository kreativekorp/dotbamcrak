package com.kreative.dotbamcrak.board;

import java.util.ArrayList;

public class BoardListenerList extends ArrayList<BoardListener> {
	private static final long serialVersionUID = 1L;
	
	public void dispatchEvent(BoardEvent e) {
		switch (e.getID()) {
		case BoardEvent.TILE_REMOVED: for (BoardListener l : this) l.tileRemoved(e); break;
		case BoardEvent.TILE_ADDED: for (BoardListener l : this) l.tileAdded(e); break;
		case BoardEvent.TILE_SELECTED: for (BoardListener l : this) l.tileSelected(e); break;
		case BoardEvent.TILES_DESELECTED: for (BoardListener l : this) l.tilesDeselected(e); break;
		case BoardEvent.TILES_SHUFFLED: for (BoardListener l : this) l.tilesShuffled(e); break;
		}
	}
}
