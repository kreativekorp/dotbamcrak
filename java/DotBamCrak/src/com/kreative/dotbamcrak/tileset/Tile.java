package com.kreative.dotbamcrak.tileset;

import java.awt.Image;

public class Tile {
	private String id;
	private String name;
	private Image image;
	private Image selectedImage;
	private int imageWidth;
	private int imageHeight;
	private int tileWidth;
	private int tileHeight;
	private int borderLeft;
	private int borderTop;
	private int borderRight;
	private int borderBottom;
	private Suit suit;
	private Rank rank;
	private HonorSet honor;
	private BonusSet bonus;
	private MatchSet match;
	
	public Tile(
			String id, String name, Image image, Image selectedImage,
			int imageWidth, int imageHeight, int tileWidth, int tileHeight,
			int borderLeft, int borderTop, int borderRight, int borderBottom
	) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.selectedImage = selectedImage;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.borderLeft = borderLeft;
		this.borderTop = borderTop;
		this.borderRight = borderRight;
		this.borderBottom = borderBottom;
		this.suit = null;
		this.rank = null;
		this.honor = null;
		this.bonus = null;
		this.match = null;
	}
	
	public String getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}
	
	public Image getImage() {
		return image;
	}
	
	public Image getSelectedImage() {
		return selectedImage;
	}
	
	public int getImageWidth() {
		return imageWidth;
	}
	
	public int getImageHeight() {
		return imageHeight;
	}
	
	public int getTileWidth() {
		return tileWidth;
	}
	
	public int getTileHeight() {
		return tileHeight;
	}
	
	public int getBorderLeft() {
		return borderLeft;
	}
	
	public int getBorderTop() {
		return borderTop;
	}
	
	public int getBorderRight() {
		return borderRight;
	}
	
	public int getBorderBottom() {
		return borderBottom;
	}
	
	public Suit getSuit() {
		return suit;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public HonorSet getHonorSet() {
		return honor;
	}
	
	public BonusSet getBonusSet() {
		return bonus;
	}
	
	public MatchSet getMatchSet() {
		return match;
	}
	
	void setSuit(Suit suit) {
		this.suit = suit;
	}
	
	void setRank(Rank rank) {
		this.rank = rank;
	}
	
	void setHonorSet(HonorSet honor) {
		this.honor = honor;
	}
	
	void setBonusSet(BonusSet bonus) {
		this.bonus = bonus;
	}
	
	void setMatchSet(MatchSet match) {
		this.match = match;
	}
	
	public int hashCode() {
		return id.hashCode();
	}
	
	public boolean equals(Object o) {
		if (o instanceof Tile) {
			return this.id.equals(((Tile)o).id);
		} else {
			return false;
		}
	}
}
