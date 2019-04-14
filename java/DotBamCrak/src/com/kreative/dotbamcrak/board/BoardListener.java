package com.kreative.dotbamcrak.board;

public interface BoardListener {
	public void tileRemoved(BoardEvent e);
	public void tileAdded(BoardEvent e);
	public void tileSelected(BoardEvent e);
	public void tilesDeselected(BoardEvent e);
	public void tilesShuffled(BoardEvent e);
}
