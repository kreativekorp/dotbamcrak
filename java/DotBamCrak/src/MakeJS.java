import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

import com.kreative.dotbamcrak.fortune.FortuneSet;
import com.kreative.dotbamcrak.layout.Layout;
import com.kreative.dotbamcrak.settings.Library;
import com.kreative.dotbamcrak.tileset.Tile;
import com.kreative.dotbamcrak.tileset.TileSet;
import com.kreative.dotbamcrak.tileset.TileSetInfo;
import com.kreative.dotbamcrak.utility.ParseException;

public class MakeJS {
	public static void main(String[] args) throws IOException, ParseException {
		Library library = new Library();
		File root = new File("js");
		root.mkdirs();
		makeTilesets(library, root);
		makeLayouts(library, root);
		makeFortunes(library, root);
	}
	
	private static void makeTilesets(Library library, File root) throws IOException, ParseException {
		PrintWriter out = open(new File(root, "tilesets.js"));
		out.println("(function(window){");
		out.println("var dbc = window.DotBamCrak || (window.DotBamCrak = {});");
		out.println("var ts = dbc.tilesets || (dbc.tilesets = []);");
		for (TileSetInfo tilesetinfo : library.getTileSets()) {
			TileSet tileset = tilesetinfo.createTileSet();
			StringBuffer tsid = new StringBuffer();
			tsid.append(tileset.getSourceName().replaceAll("[^A-Za-z0-9]", "").toLowerCase());
			if (tsid.length() > 0) tsid.append("-");
			tsid.append(tileset.getName().replaceAll("[^A-Za-z0-9]", "").toLowerCase());
			
			out.println("ts.push({");
			out.println("  id: " + quote(tsid.toString()) + ",");
			out.println("  name: " + quote(tileset.getName()) + ",");
			if (tileset.getSourceName().length() > 0) {
				out.println("  source: " + quote(tileset.getSourceName()) + ",");
				out.println("  visible: false,");
			} else {
				out.println("  visible: true,");
			}
			
			Dimension imageSize = tileset.getImageSize();
			Dimension tileSize = tileset.getTileSize();
			Insets insets = tileset.getImageInsets();
			out.println("  dimensions: {");
			out.println("    cell: [" + imageSize.width + ", " + imageSize.height + "],");
			out.println("    tile: [" + tileSize.width + ", " + tileSize.height + "],");
			out.println("    border: [" + insets.top + ", " + insets.right + ", " + insets.bottom + ", " + insets.left + "],");
			out.println("  },");
			
			out.println("  background: {");
			if (tileset.getBackgroundImage() != null) {
				String fn = "tilesets-" + tsid + "-bgimg.png";
				RenderedImage bgimage = (RenderedImage)tileset.getBackgroundImage();
				ImageIO.write(bgimage, "png", new File(root, fn));
				out.println("    image: " + quote(fn) + ",");
			}
			if (tileset.getBackgroundPaint() instanceof Color) {
				Color bgcolor = (Color)tileset.getBackgroundPaint();
				out.println("    color: [" + bgcolor.getRed() + ", " + bgcolor.getGreen() + ", " + bgcolor.getBlue() + "],");
			} else {
				String fn = "tilesets-" + tsid + "-bgpat.png";
				BufferedImage bgimage = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g = bgimage.createGraphics();
				g.setPaint(tileset.getBackgroundPaint());
				g.fillRect(0, 0, 32, 32);
				g.dispose();
				ImageIO.write(bgimage, "png", new File(root, fn));
				out.println("    pattern: " + quote(fn) + ",");
			}
			out.println("  },");
			
			String fn = "tilesets-" + tsid + ".png";
			BufferedImage image = new BufferedImage(
				(imageSize.width + 2) * (tileset.getTileCount() + 1) + 2,
				(imageSize.height + 2) * 2 + 2,
				BufferedImage.TYPE_INT_ARGB
			);
			Graphics2D g = image.createGraphics();
			int y = imageSize.height + 4;
			Tile blank = tileset.getBlankTile();
			g.drawImage(blank.getImage(), 2, 2, null);
			g.drawImage(blank.getSelectedImage(), 2, y, null);
			int x = imageSize.width + 4;
			out.println("  spritesheet: " + quote(fn) + ",");
			out.println("  blank: {location: [2, 2, 2, " + y + "]},");
			out.println("  tiles: {");
			List<String> tileIds = sorted(tileset.getTileMap().keySet());
			for (String tileId : tileIds) {
				Tile tile = tileset.getTile(tileId);
				out.println("    " + quote(tile.getID()) + ": {");
				out.println("      id: " + quote(tile.getID()) + ",");
				if (tile.getName().length() > 0) {
					out.println("      name: " + quote(tile.getName()) + ",");
				}
				if (tile.getSuit() != null) {
					out.println("      suit: " + quote(tile.getSuit().getID()) + ",");
				}
				if (tile.getRank() != null) {
					out.println("      rank: " + quote(tile.getRank().getID()) + ",");
				}
				if (tile.getHonorSet() != null) {
					out.println("      honor: " + quote(tile.getHonorSet().getID()) + ",");
				}
				if (tile.getBonusSet() != null) {
					out.println("      bonus: " + quote(tile.getBonusSet().getID()) + ",");
				}
				if (tile.getMatchSet() != null) {
					List<String> matches = new ArrayList<String>();
					for (Tile t : tile.getMatchSet()) matches.add(t.getID());
					out.println("      matches: " + quote(matches) + ",");
				}
				out.println("      location: [" + x + ", 2, " + x + ", " + y + "],");
				out.println("    },");
				g.drawImage(tile.getImage(), x, 2, null);
				g.drawImage(tile.getSelectedImage(), x, y, null);
				x += imageSize.width + 2;
			}
			g.dispose();
			ImageIO.write(image, "png", new File(root, fn));
			out.println("  },");
			
			printSetMap("  ", "suits", tilesetinfo.getSuitMap(), out);
			printSetMap("  ", "ranks", tilesetinfo.getRankMap(), out);
			printSetMap("  ", "honorSets", tilesetinfo.getHonorSetMap(), out);
			printSetMap("  ", "bonusSets", tilesetinfo.getBonusSetMap(), out);
			
			List<List<String>> matches = sorted2(tilesetinfo.getMatchSets());
			if (!matches.isEmpty()) {
				out.println("  matches: [");
				for (List<String> match : matches) {
					out.println("    " + quote(match) + ",");
				}
				out.println("  ],");
			}
			
			out.println("});");
		}
		out.println("})(window);");
		out.flush();
		out.close();
	}
	
	private static void printSetMap(String prefix, String key, Map<String,Set<String>> map, PrintWriter out) {
		List<String> ids = sorted(map.keySet());
		if (ids.isEmpty()) return;
		out.println(prefix + key + ": {");
		for (String id : ids) {
			List<String> strs = sorted(map.get(id));
			out.println(prefix + "  " + quote(id) + ": " + quote(strs) + ",");
		}
		out.println(prefix + "},");
	}
	
	private static void makeLayouts(Library library, File root) throws IOException {
		PrintWriter out = open(new File(root, "layouts.js"));
		out.println("(function(window){");
		out.println("var dbc = window.DotBamCrak || (window.DotBamCrak = {});");
		out.println("var lo = dbc.layouts || (dbc.layouts = []);");
		for (Layout layout : library.getLayouts()) {
			out.println("lo.push({");
			out.println("  name: " + quote(layout.getName()) + ",");
			out.println("  visible: true,");
			out.println("  dimensions: {");
			out.println("    board: [" + layout.getBoardWidth() + ", " + layout.getBoardHeight() + ", " + layout.getBoardDepth() + "],");
			out.println("    tile: [" + layout.getTileWidth() + ", " + layout.getTileHeight() + ", " + layout.getTileDepth() + "],");
			out.println("    window: [" + layout.getWindowWidth() + ", " + layout.getWindowHeight() + "],");
			out.println("  },");
			out.println("  tiles: [");
			
			List<String> tiles = new ArrayList<String>();
			for (int z = 0, zn = layout.getBoardDepth(); z < zn; z++) {
				for (int y = 0, yn = layout.getBoardHeight(); y < yn; y++) {
					for (int x = 0, xn = layout.getBoardWidth(); x < xn; x++) {
						if (layout.getTile(x, y, z)) {
							tiles.add(" [" + x + ", " + y + ", " + z + "],");
						}
					}
				}
			}
			for (int i = 0, n = tiles.size(); i < n; i += 6) {
				StringBuffer sb = new StringBuffer("   ");
				for (String t: tiles.subList(i, Math.min(i + 6, n))) sb.append(t);
				out.println(sb);
			}
			
			out.println("  ],");
			out.println("});");
		}
		out.println("})(window);");
		out.flush();
		out.close();
	}
	
	private static void makeFortunes(Library library, File root) throws IOException {
		PrintWriter out = open(new File(root, "fortunes.js"));
		out.println("(function(window){");
		out.println("var dbc = window.DotBamCrak || (window.DotBamCrak = {});");
		out.println("var fs = dbc.fortunes || (dbc.fortunes = []);");
		for (FortuneSet fortuneset : library.getFortuneSets()) {
			String visible = (fortuneset.getName().equalsIgnoreCase("Fortunes")) ? "true" : "false";
			out.println("fs.push({");
			out.println("  name: " + quote(fortuneset.getName()) + ",");
			out.println("  visible: " + visible + ",");
			out.println("  fortunes: [");
			for (String fortune : fortuneset) {
				out.println("    " + quote(fortune) + ",");
			}
			out.println("  ],");
			out.println("});");
		}
		out.println("})(window);");
		out.flush();
		out.close();
	}
	
	private static PrintWriter open(File file) throws IOException {
		FileOutputStream stream = new FileOutputStream(file);
		OutputStreamWriter writer = new OutputStreamWriter(stream, "UTF-8");
		return new PrintWriter(writer, true);
	}
	
	private static String quote(String s) {
		s = s.replaceAll("\\\\", "\\\\\\\\");
		s = s.replaceAll("\"", "\\\\\"");
		return "\"" + s + "\"";
	}
	
	private static String quote(Collection<String> coll) {
		StringBuffer sb = new StringBuffer();
		boolean first = true;
		sb.append("[");
		for (String s : coll) {
			if (first) first = false;
			else sb.append(", ");
			sb.append(quote(s));
		}
		sb.append("]");
		return sb.toString();
	}
	
	private static List<String> sorted(Collection<String> coll) {
		List<String> list = new ArrayList<String>();
		list.addAll(coll);
		Collections.sort(list, new Comparator<String>() {
			@Override
			public int compare(String a, String b) {
				a = normalize(a);
				b = normalize(b);
				return a.compareToIgnoreCase(b);
			}
		});
		return list;
	}
	
	private static List<List<String>> sorted2(Collection<List<String>> coll) {
		List<List<String>> list = new ArrayList<List<String>>();
		list.addAll(coll);
		Collections.sort(list, new Comparator<List<String>>() {
			@Override
			public int compare(List<String> a, List<String> b) {
				int an = a.size(), bn = b.size();
				for (int i = 0; i < an && i < bn; i++) {
					String as = normalize(a.get(i));
					String bs = normalize(b.get(i));
					int cmp = as.compareToIgnoreCase(bs);
					if (cmp != 0) return cmp;
				}
				return an - bn;
			}
		});
		return list;
	}
	
	private static final Pattern NUMBER = Pattern.compile("[0-9]+");
	private static String normalize(String s) {
		StringBuffer sb = new StringBuffer();
		Matcher m = NUMBER.matcher(s);
		while (m.find()) {
			String r = "00000000" + m.group();
			r = r.substring(r.length() - 8);
			m.appendReplacement(sb, r);
		}
		m.appendTail(sb);
		return sb.toString();
	}
}
