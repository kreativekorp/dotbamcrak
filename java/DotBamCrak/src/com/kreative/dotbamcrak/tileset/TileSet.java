package com.kreative.dotbamcrak.tileset;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import javax.imageio.ImageIO;
import com.kreative.dotbamcrak.utility.ImageUtilities;
import com.kreative.dotbamcrak.utility.ParseException;
import com.kreative.dotbamcrak.utility.ParseUtilities;
import com.kreative.dotbamcrak.utility.PatternPaint;

public class TileSet {
	private File resourceDirectory;
	private File tilesetFile;
	
	private String source = "";
	private String name = "";
	private int imageWidth = 0;
	private int imageHeight = 0;
	private int tileWidth = 0;
	private int tileHeight = 0;
	private int borderLeft = 0;
	private int borderTop = 0;
	private int borderRight = 0;
	private int borderBottom = 0;
	private Tile blankTile = null;
	private Image backgroundImage = null;
	private Paint backgroundPaint = Color.white;
	
	private Map<String,Tile> tiles = new HashMap<String,Tile>();
	private Map<String,Suit> suits = new HashMap<String,Suit>();
	private Map<String,Rank> ranks = new HashMap<String,Rank>();
	private Map<String,HonorSet> honors = new HashMap<String,HonorSet>();
	private Map<String,BonusSet> bonuses = new HashMap<String,BonusSet>();
	private Set<MatchSet> matches = new HashSet<MatchSet>();
	
	public TileSet(File in) throws IOException, ParseException {
		if (in.isDirectory()) {
			resourceDirectory = in;
			if (in.getName().endsWith(".mts")) {
				tilesetFile = new File(in, in.getName());
			} else {
				tilesetFile = new File(in, in.getName() + ".mts");
			}
			for (File f : in.listFiles()) {
				if (f.getName().endsWith(".mts")) {
					tilesetFile = f;
					break;
				}
			}
		} else {
			resourceDirectory = in.getParentFile();
			tilesetFile = in;
		}
		processDirectives(new Scanner(tilesetFile), resourceDirectory);
	}
	
	public TileSet(File tilesetFile, File resourceDirectory) throws IOException, ParseException {
		this.tilesetFile = tilesetFile;
		this.resourceDirectory = resourceDirectory;
		processDirectives(new Scanner(tilesetFile), resourceDirectory);
	}
	
	private enum HilightType {
		ALT_IMAGE,
		INVERT_FACE,
		INVERT_TILE,
		DARKEN_FACE,
		DARKEN_TILE,
		LIGHTEN_FACE,
		LIGHTEN_TILE;
	}
	
	private class ProcessingVariables {
		BufferedImage image = null;
		int cellWidth = 0;
		int cellHeight = 0;
		boolean addBorder = false;
		HilightType hilightType = HilightType.DARKEN_TILE;
	}
	
	private void processDirectives(Scanner in, File resourceDirectory) throws IOException, ParseException {
		ProcessingVariables vars = new ProcessingVariables();
		int lineNumber = 0;
		while (in.hasNextLine()) {
			lineNumber++;
			String line = in.nextLine().trim();
			if (line.length() > 0 && !line.startsWith("#")) {
				String[] args = line.split("\\s*\t\\s*");
				processDirective(args, resourceDirectory, vars, lineNumber);
			}
		}
		if (imageWidth == 0 || imageHeight == 0 || tileWidth == 0 || tileHeight == 0) throw new ParseException("Missing cellsize directive.");
		if (blankTile == null) throw new ParseException("Missing blanktile or blankrect directive.");
		if (tiles.isEmpty()) throw new ParseException("Missing tile directives.");
		if (matches.isEmpty()) throw new ParseException("Missing match directives.");
	}
	
	private void processDirective(String[] args, File resourceDirectory, ProcessingVariables vars, int lineNumber) throws IOException, ParseException {
		if (args[0].equalsIgnoreCase("source")) {
			source = ParseUtilities.index(args, 1, lineNumber).trim();
		} else if (args[0].equalsIgnoreCase("name")) {
			name = ParseUtilities.index(args, 1, lineNumber).trim();
		} else if (args[0].equalsIgnoreCase("image")) {
			File imageFile = new File(resourceDirectory, ParseUtilities.index(args, 1, lineNumber));
			vars.image = ImageIO.read(imageFile);
		} else if (args[0].equalsIgnoreCase("vector")) {
			// ignored
		} else if (args[0].equalsIgnoreCase("cellsize")) {
			String[] subop = ParseUtilities.index(args, 1, lineNumber).split("\\s*,\\s*");
			vars.cellWidth = ParseUtilities.parseInt(ParseUtilities.index(subop, 0, lineNumber), lineNumber);
			vars.cellHeight = ParseUtilities.parseInt(ParseUtilities.index(subop, 1, lineNumber), lineNumber);
			ParseUtilities.checkRange(vars.cellWidth >= 1 && vars.cellHeight >= 1, lineNumber);
			imageWidth = vars.addBorder ? (vars.cellWidth + borderLeft + borderRight) : vars.cellWidth;
			imageHeight = vars.addBorder ? (vars.cellHeight + borderTop + borderBottom) : vars.cellHeight;
			tileWidth = vars.addBorder ? vars.cellWidth : (vars.cellWidth - borderLeft - borderRight);
			tileHeight = vars.addBorder ? vars.cellHeight : (vars.cellHeight - borderTop - borderBottom);
		} else if (args[0].equalsIgnoreCase("cellborder")) {
			String[] subop = ParseUtilities.index(args, 1, lineNumber).split("\\s*,\\s*");
			vars.addBorder = false;
			borderLeft = ParseUtilities.parseInt(ParseUtilities.index(subop, 0, lineNumber), lineNumber);
			borderTop = ParseUtilities.parseInt(ParseUtilities.index(subop, 1, lineNumber), lineNumber);
			borderRight = ParseUtilities.parseInt(ParseUtilities.index(subop, 2, lineNumber), lineNumber);
			borderBottom = ParseUtilities.parseInt(ParseUtilities.index(subop, 3, lineNumber), lineNumber);
			ParseUtilities.checkRange(borderLeft >= 0 && borderTop >= 0 && borderRight >= 0 && borderBottom >= 0, lineNumber);
			imageWidth = vars.cellWidth;
			imageHeight = vars.cellHeight;
			tileWidth = vars.cellWidth - borderLeft - borderRight;
			tileHeight = vars.cellHeight - borderTop - borderBottom;
		} else if (args[0].equalsIgnoreCase("addborder")) {
			String[] subop = ParseUtilities.index(args, 1, lineNumber).split("\\s*,\\s*");
			vars.addBorder = true;
			borderLeft = ParseUtilities.parseInt(ParseUtilities.index(subop, 0, lineNumber), lineNumber);
			borderTop = ParseUtilities.parseInt(ParseUtilities.index(subop, 1, lineNumber), lineNumber);
			borderRight = ParseUtilities.parseInt(ParseUtilities.index(subop, 2, lineNumber), lineNumber);
			borderBottom = ParseUtilities.parseInt(ParseUtilities.index(subop, 3, lineNumber), lineNumber);
			ParseUtilities.checkRange(borderLeft >= 0 && borderTop >= 0 && borderRight >= 0 && borderBottom >= 0, lineNumber);
			imageWidth = vars.cellWidth + borderLeft + borderRight;
			imageHeight = vars.cellHeight + borderTop + borderBottom;
			tileWidth = vars.cellWidth;
			tileHeight = vars.cellHeight;
		} else if (args[0].equalsIgnoreCase("hilighttype")) {
			vars.hilightType = ParseUtilities.parseEnum(HilightType.values(), ParseUtilities.index(args, 1, lineNumber), lineNumber);
		} else if (args[0].equalsIgnoreCase("blanktile")) {
			if (imageWidth == 0 || imageHeight == 0 || vars.image == null) {
				throw new ParseException("Blanktile directive before image or cellsize directive", lineNumber);
			} else {
				Image[] images = createTileImage(ParseUtilities.index(args, 1, lineNumber), vars, lineNumber);
				blankTile = new Tile(
						"", "", images[0], images[1],
						imageWidth, imageHeight, tileWidth, tileHeight,
						borderLeft, borderTop, borderRight, borderBottom
				);
			}
		} else if (args[0].equalsIgnoreCase("blankrect")) {
			if (imageWidth == 0 || imageHeight == 0) {
				throw new ParseException("Blanktile directive before cellsize directive", lineNumber);
			} else {
				Image[] images = createRectImage(ParseUtilities.index(args, 1, lineNumber), vars, lineNumber);
				blankTile = new Tile(
						"", "", images[0], images[1],
						imageWidth, imageHeight, tileWidth, tileHeight,
						borderLeft, borderTop, borderRight, borderBottom
				);
			}
		} else if (args[0].equalsIgnoreCase("bgcolor")) {
			backgroundPaint = ParseUtilities.parseColor(ParseUtilities.index(args, 1, lineNumber), lineNumber);
		} else if (args[0].equalsIgnoreCase("bgpattern")) {
			String[] subop = ParseUtilities.index(args, 1, lineNumber).split("\\s*,\\s*");
			Color backPaint = ParseUtilities.parseColor(ParseUtilities.index(subop, 0, lineNumber), lineNumber);
			Color forePaint = ParseUtilities.parseColor(ParseUtilities.index(subop, 1, lineNumber), lineNumber);
			long pattern = ParseUtilities.parseBitLong(ParseUtilities.index(subop, 2, lineNumber), lineNumber);
			backgroundPaint = new PatternPaint(forePaint, backPaint, pattern);
		} else if (args[0].equalsIgnoreCase("bgimage")) {
			File imageFile = new File(resourceDirectory, ParseUtilities.index(args, 1, lineNumber));
			backgroundImage = ImageIO.read(imageFile);
		} else if (args[0].equalsIgnoreCase("tile")) {
			String id = ParseUtilities.index(args, 1, lineNumber);
			String coord = ParseUtilities.index(args, 2, lineNumber);
			String name = ParseUtilities.index(args, 3, "");
			Image[] images = createTileImage(coord, vars, lineNumber);
			tiles.put(id, new Tile(
					id, name, images[0], images[1],
					imageWidth, imageHeight, tileWidth, tileHeight,
					borderLeft, borderTop, borderRight, borderBottom
			));
		} else if (args[0].equalsIgnoreCase("suit")) {
			String id = ParseUtilities.index(args, 1, lineNumber);
			String[] tileIDs = ParseUtilities.index(args, 2, lineNumber).split("\\s*,\\s*");
			String name = ParseUtilities.index(args, 3, "");
			List<Tile> tiles = new ArrayList<Tile>();
			for (String tileID : tileIDs) {
				tiles.add(ParseUtilities.checkTile(this.tiles.get(tileID), lineNumber));
			}
			suits.put(id, new Suit(id, name, tiles));
		} else if (args[0].equalsIgnoreCase("rank")) {
			String id = ParseUtilities.index(args, 1, lineNumber);
			String[] tileIDs = ParseUtilities.index(args, 2, lineNumber).split("\\s*,\\s*");
			String name = ParseUtilities.index(args, 3, "");
			List<Tile> tiles = new ArrayList<Tile>();
			for (String tileID : tileIDs) {
				tiles.add(ParseUtilities.checkTile(this.tiles.get(tileID), lineNumber));
			}
			ranks.put(id, new Rank(id, name, tiles));
		} else if (args[0].equalsIgnoreCase("honor")) {
			String id = ParseUtilities.index(args, 1, lineNumber);
			String[] tileIDs = ParseUtilities.index(args, 2, lineNumber).split("\\s*,\\s*");
			String name = ParseUtilities.index(args, 3, "");
			List<Tile> tiles = new ArrayList<Tile>();
			for (String tileID : tileIDs) {
				tiles.add(ParseUtilities.checkTile(this.tiles.get(tileID), lineNumber));
			}
			honors.put(id, new HonorSet(id, name, tiles));
		} else if (args[0].equalsIgnoreCase("bonus")) {
			String id = ParseUtilities.index(args, 1, lineNumber);
			String[] tileIDs = ParseUtilities.index(args, 2, lineNumber).split("\\s*,\\s*");
			String name = ParseUtilities.index(args, 3, "");
			List<Tile> tiles = new ArrayList<Tile>();
			for (String tileID : tileIDs) {
				tiles.add(ParseUtilities.checkTile(this.tiles.get(tileID), lineNumber));
			}
			bonuses.put(id, new BonusSet(id, name, tiles));
		} else if (args[0].equalsIgnoreCase("match")) {
			String[] tileIDs = ParseUtilities.index(args, 1, lineNumber).split("\\s*,\\s*");
			List<Tile> tiles = new ArrayList<Tile>();
			for (String tileID : tileIDs) {
				tiles.add(ParseUtilities.checkTile(this.tiles.get(tileID), lineNumber));
			}
			matches.add(new MatchSet(tiles));
		} else {
			throw new ParseException("Unknown directive", lineNumber);
		}
	}
	
	private BufferedImage createTileImage(boolean addBorder) {
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
		if (addBorder) {
			Graphics2D g = image.createGraphics();
			int tlox = borderRight;
			int tloy = borderBottom;
			int trox = imageWidth - borderLeft - 1;
			int troy = borderBottom;
			int blox = borderRight;
			int bloy = imageHeight - borderTop - 1;
			int brox = imageWidth - borderLeft - 1;
			int broy = imageHeight - borderTop - 1;
			int tlix = borderLeft;
			int tliy = borderTop;
			int trix = imageWidth - borderRight - 1;
			int triy = borderTop;
			int blix = borderLeft;
			int bliy = imageHeight - borderBottom - 1;
			int brix = imageWidth - borderRight - 1;
			int briy = imageHeight - borderBottom - 1;
			int[] topx = new int[] { tlox, trox, trix, tlix, tlox };
			int[] topy = new int[] { tloy, troy, triy, tliy, tloy };
			int[] rightx = new int[] { trix, trox, brox, brix, trix };
			int[] righty = new int[] { triy, troy, broy, briy, triy };
			int[] bottomx = new int[] { blix, brix, brox, blox, blix };
			int[] bottomy = new int[] { bliy, briy, broy, bloy, bliy };
			int[] leftx = new int[] { tlox, tlix, blix, blox, tlox };
			int[] lefty = new int[] { tloy, tliy, bliy, bloy, tloy };
			Polygon top = new Polygon(topx, topy, 5);
			Polygon right = new Polygon(rightx, righty, 5);
			Polygon bottom = new Polygon(bottomx, bottomy, 5);
			Polygon left = new Polygon(leftx, lefty, 5);
			Rectangle inside = new Rectangle(tlix, tliy, brix-tlix, briy-tliy);
			Area outside = new Area();
			outside.add(new Area(top));
			outside.add(new Area(right));
			outside.add(new Area(bottom));
			outside.add(new Area(left));
			outside.add(new Area(inside));
			g.setColor(Color.gray);
			g.fill(outside);
			g.setColor(Color.black);
			g.draw(outside);
			g.drawLine(tlox, tloy, tlix, tliy);
			g.drawLine(trox, troy, trix, triy);
			g.drawLine(blox, bloy, blix, bliy);
			g.drawLine(brox, broy, brix, briy);
			g.setColor(Color.gray);
			g.fill(inside);
			g.setColor(Color.black);
			g.draw(inside);
			g.dispose();
		}
		return image;
	}
	
	private Image[] createTileImage(String subops, ProcessingVariables vars, int lineNumber) throws ParseException {
		String[] subop = subops.split("\\s*,\\s*");
		int cellX = ParseUtilities.parseInt(ParseUtilities.index(subop, 0, lineNumber), lineNumber);
		int cellY = ParseUtilities.parseInt(ParseUtilities.index(subop, 1, lineNumber), lineNumber);
		int cellX2 = ParseUtilities.parseInt(ParseUtilities.index(subop, 2, Integer.toString(cellX)), lineNumber);
		int cellY2 = ParseUtilities.parseInt(ParseUtilities.index(subop, 3, Integer.toString(cellY)), lineNumber);
		int ncx = vars.image.getWidth() / vars.cellWidth;
		int ncy = vars.image.getHeight() / vars.cellHeight;
		ParseUtilities.checkRange(cellX >= 0 && cellY >= 0 && cellX2 >= 0 && cellY2 >= 0, lineNumber);
		ParseUtilities.checkRange(cellX < ncx && cellY < ncy && cellX2 < ncx && cellY2 < ncy, lineNumber);
		BufferedImage image = createTileImage(vars.addBorder);
		BufferedImage image2 = createTileImage(vars.addBorder);
		ImageUtilities.blit(vars.image, vars.cellWidth * cellX, vars.cellHeight * cellY, image, vars.addBorder ? borderLeft : 0, vars.addBorder ? borderTop : 0, vars.cellWidth, vars.cellHeight);
		ImageUtilities.blit(vars.image, vars.cellWidth * cellX2, vars.cellHeight * cellY2, image2, vars.addBorder ? borderLeft : 0, vars.addBorder ? borderTop : 0, vars.cellWidth, vars.cellHeight);
		switch (vars.hilightType) {
		case INVERT_FACE: ImageUtilities.invert(image2, borderLeft, borderTop, tileWidth, tileHeight); break;
		case INVERT_TILE: ImageUtilities.invert(image2, 0, 0, imageWidth, imageHeight); break;
		case DARKEN_FACE: ImageUtilities.darken(image2, borderLeft, borderTop, tileWidth, tileHeight); break;
		case DARKEN_TILE: ImageUtilities.darken(image2, 0, 0, imageWidth, imageHeight); break;
		case LIGHTEN_FACE: ImageUtilities.lighten(image2, borderLeft, borderTop, tileWidth, tileHeight); break;
		case LIGHTEN_TILE: ImageUtilities.lighten(image2, 0, 0, imageWidth, imageHeight); break;
		}
		return new Image[] { image, image2 };
	}
	
	private Image[] createRectImage(String subops, ProcessingVariables vars, int lineNumber) throws ParseException {
		String[] subop = subops.split("\\s*,\\s*");
		Color fill = ParseUtilities.parseColor(ParseUtilities.index(subop, 0, lineNumber), lineNumber);
		Color stroke = ParseUtilities.parseColor(ParseUtilities.index(subop, 1, lineNumber), lineNumber);
		int l = ParseUtilities.parseInt(ParseUtilities.index(subop, 2, lineNumber), lineNumber);
		int t = ParseUtilities.parseInt(ParseUtilities.index(subop, 3, lineNumber), lineNumber);
		int r = ParseUtilities.parseInt(ParseUtilities.index(subop, 4, lineNumber), lineNumber);
		int b = ParseUtilities.parseInt(ParseUtilities.index(subop, 5, lineNumber), lineNumber);
		ParseUtilities.checkRange(l >= 0 && t >= 0 && r >= 0 && b >= 0, lineNumber);
		BufferedImage image = createTileImage(vars.addBorder);
		BufferedImage image2 = createTileImage(vars.addBorder);
		Graphics2D g;
		g = image.createGraphics();
		g.setColor(stroke);
		g.fillRect(borderLeft, borderTop, tileWidth, tileHeight);
		g.setColor(fill);
		g.fillRect(borderLeft + l, borderTop + t, tileWidth - l - r, tileHeight - t - b);
		g.dispose();
		g = image2.createGraphics();
		g.setColor(stroke);
		g.fillRect(borderLeft, borderTop, tileWidth, tileHeight);
		g.setColor(fill);
		g.fillRect(borderLeft + l, borderTop + t, tileWidth - l - r, tileHeight - t - b);
		g.dispose();
		switch (vars.hilightType) {
		case INVERT_FACE: ImageUtilities.invert(image2, borderLeft, borderTop, tileWidth, tileHeight); break;
		case INVERT_TILE: ImageUtilities.invert(image2, 0, 0, imageWidth, imageHeight); break;
		case DARKEN_FACE: ImageUtilities.darken(image2, borderLeft, borderTop, tileWidth, tileHeight); break;
		case DARKEN_TILE: ImageUtilities.darken(image2, 0, 0, imageWidth, imageHeight); break;
		case LIGHTEN_FACE: ImageUtilities.lighten(image2, borderLeft, borderTop, tileWidth, tileHeight); break;
		case LIGHTEN_TILE: ImageUtilities.lighten(image2, 0, 0, imageWidth, imageHeight); break;
		}
		return new Image[] { image, image2 };
	}
	
	public File getResourceDirectory() {
		return resourceDirectory;
	}
	
	public File getFile() {
		return tilesetFile;
	}
	
	public String getSourceName() {
		return source;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		if (name.length() == 0) {
			return source;
		} else if (source.length() == 0 || name.startsWith(source) || name.endsWith(source)) {
			return name;
		} else {
			return source + " " + name;
		}
	}
	
	public int getImageWidth() {
		return imageWidth;
	}
	
	public int getImageHeight() {
		return imageHeight;
	}
	
	public Dimension getImageSize() {
		return new Dimension(imageWidth, imageHeight);
	}
	
	public int getTileWidth() {
		return tileWidth;
	}
	
	public int getTileHeight() {
		return tileHeight;
	}
	
	public Dimension getTileSize() {
		return new Dimension(tileWidth, tileHeight);
	}
	
	public Insets getImageInsets() {
		return new Insets(borderTop, borderLeft, borderBottom, borderRight);
	}
	
	public Tile getBlankTile() {
		return blankTile;
	}
	
	public Image getBackgroundImage() {
		return backgroundImage;
	}
	
	public Paint getBackgroundPaint() {
		return backgroundPaint;
	}
	
	public int getTileCount() {
		return tiles.size();
	}
	
	public Tile getTile(String id) {
		return tiles.get(id);
	}
	
	public Collection<Tile> getTiles() {
		return tiles.values();
	}
	
	public Map<String,Tile> getTileMap() {
		return Collections.unmodifiableMap(tiles);
	}
	
	public int getSuitCount() {
		return suits.size();
	}
	
	public Suit getSuit(String id) {
		return suits.get(id);
	}
	
	public Collection<Suit> getSuits() {
		return suits.values();
	}
	
	public Map<String,Suit> getSuitMap() {
		return Collections.unmodifiableMap(suits);
	}
	
	public int getRankCount() {
		return ranks.size();
	}
	
	public Rank getRank(String id) {
		return ranks.get(id);
	}
	
	public Collection<Rank> getRanks() {
		return ranks.values();
	}
	
	public Map<String,Rank> getRankMap() {
		return Collections.unmodifiableMap(ranks);
	}
	
	public int getHonorSetCount() {
		return honors.size();
	}
	
	public HonorSet getHonorSet(String id) {
		return honors.get(id);
	}
	
	public Collection<HonorSet> getHonorSets() {
		return honors.values();
	}
	
	public Map<String,HonorSet> getHonorSetMap() {
		return Collections.unmodifiableMap(honors);
	}
	
	public int getBonusSetCount() {
		return bonuses.size();
	}
	
	public BonusSet getBonusSet(String id) {
		return bonuses.get(id);
	}
	
	public Collection<BonusSet> getBonusSets() {
		return bonuses.values();
	}
	
	public Map<String,BonusSet> getBonusSetMap() {
		return Collections.unmodifiableMap(bonuses);
	}
	
	public int getMatchSetCount() {
		return matches.size();
	}
	
	public Collection<MatchSet> getMatchSets() {
		return Collections.unmodifiableSet(matches);
	}
}
