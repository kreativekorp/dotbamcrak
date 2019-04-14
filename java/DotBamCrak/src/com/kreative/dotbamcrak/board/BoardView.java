package com.kreative.dotbamcrak.board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;

public class BoardView extends JComponent implements BoardListener {
	private static final long serialVersionUID = 1L;
	
	private Paint bgpaint;
	private Image bgimage;
	private Board board;
	private boolean hidden;
	
	public BoardView(Board board) {
		this.bgpaint = null;
		this.bgimage = null;
		this.board = board;
		this.hidden = false;
		if (board != null) {
			board.addBoardListener(this);
		}
	}
	
	public Paint getBackgroundPaint() {
		return bgpaint;
	}
	
	public void setBackgroundPaint(Paint bgpaint) {
		this.bgpaint = bgpaint;
		repaint();
	}
	
	public Image getBackgroundImage() {
		return bgimage;
	}
	
	public void setBackgroundImage(Image bgimage) {
		this.bgimage = bgimage;
		repaint();
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void setBoard(Board board) {
		if (this.board != null) {
			this.board.removeBoardListener(this);
		}
		this.board = board;
		if (board != null) {
			board.addBoardListener(this);
		}
		repaint();
	}
	
	public boolean isPaused() {
		return hidden;
	}
	
	public void setPaused(boolean paused) {
		this.hidden = paused;
		repaint();
	}
	
	public void tileRemoved(BoardEvent e) {
		repaint();
	}
	
	public void tileAdded(BoardEvent e) {
		repaint();
	}
	
	public void tileSelected(BoardEvent e) {
		repaint();
	}
	
	public void tilesDeselected(BoardEvent e) {
		repaint();
	}
	
	public void tilesShuffled(BoardEvent e) {
		repaint();
	}
	
	protected void paintComponent(Graphics g) {
		Insets i = getInsets();
		int x = i.left;
		int y = i.top;
		int w = getWidth() - i.left - i.right;
		int h = getHeight() - i.top - i.bottom;
		
		if (g instanceof Graphics2D) {
			((Graphics2D)g).setRenderingHint(
					RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC
			);
		}
		
		Paint bgpaint = ((this.bgpaint != null) ? this.bgpaint : board.getTileSet().getBackgroundPaint());
		if (bgpaint != null) {
			if (g instanceof Graphics2D) {
				((Graphics2D)g).setPaint(bgpaint);
				g.fillRect(x, y, w, h);
			} else if (bgpaint instanceof Color) {
				g.setColor((Color)bgpaint);
				g.fillRect(x, y, w, h);
			}
		}
		
		Image bgimage = ((this.bgimage != null) ? this.bgimage : board.getTileSet().getBackgroundImage());
		if (bgimage != null) {
			int iw = bgimage.getWidth(null);
			int ih = bgimage.getHeight(null);
			if (w >= iw && h >= ih) {
				g.drawImage(bgimage, x + (w-iw)/2, y + (h-ih)/2, null);
			} else if (w >= board.getWindowWidth() && h >= board.getWindowHeight()) {
				int sw = Math.min(w, iw * h / ih);
				int sh = Math.min(h, ih * w / iw);
				g.drawImage(
						bgimage,
						x + (w-sw)/2, y + (h-sh)/2,
						x + (w-sw)/2 + sw, y + (h-sh)/2 + sh,
						0, 0, iw, ih,
						null
				);
			}
		}
		
		board.paintBoard(g, x, y, w, h, hidden);
	}
	
	public int[] resolveTile(MouseEvent e) {
		Insets i = getInsets();
		int x = i.left;
		int y = i.top;
		int w = getWidth() - i.left - i.right;
		int h = getHeight() - i.top - i.bottom;
		return board.resolveTile(x, y, w, h, e.getX(), e.getY());
	}
	
	private Dimension userMinSize = null;
	
	public Dimension getMinimumSize() {
		if (userMinSize != null) {
			return userMinSize;
		} else {
			Insets i = getInsets();
			int w = board.getWindowWidth()*4 + i.left + i.right;
			int h = board.getWindowHeight()*4 + i.top + i.bottom;
			return new Dimension(w, h);
		}
	}
	
	public void setMinimumSize(Dimension size) {
		this.userMinSize = size;
	}
	
	private Dimension userPrefSize = null;
	
	public Dimension getPreferredSize() {
		if (userPrefSize != null) {
			return userPrefSize;
		} else {
			Insets i = getInsets();
			int w = board.getWindowPixelWidth() + i.left + i.right;
			int h = board.getWindowPixelHeight() + i.top + i.bottom;
			return new Dimension(w, h);
		}
	}
	
	public void setPreferredSize(Dimension size) {
		this.userPrefSize = size;
	}
}
