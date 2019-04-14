package com.kreative.dotbamcrak.layout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ConvertMAPtoMLO {
	public static void main(String[] args) throws Exception {
		for (String arg : args) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setExpandEntityReferences(false);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new FileReader(new File(arg))));
			parseDocument(document);
		}
	}
	
	private static void parseDocument(Document node) throws Exception {
		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			String type = child.getNodeName();
			if (type.equalsIgnoreCase("mahjongg")) {
				parseMahjongg(child);
			}
		}
	}
	
	private static void parseMahjongg(Node node) throws Exception {
		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			String type = child.getNodeName();
			if (type.equalsIgnoreCase("map")) {
				parseMap(child);
			}
		}
	}
	
	private static void parseMap(Node node) throws Exception {
		NamedNodeMap attributes = node.getAttributes();
		String name = attributes.getNamedItem("name").getTextContent();
		List<Point3D> tiles = new ArrayList<Point3D>();
		Point3D min = new Point3D(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
		Point3D max = new Point3D(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			String type = child.getNodeName();
			if (type.equalsIgnoreCase("layer")) {
				parseLayer(child, tiles, min, max);
			} else if (type.equalsIgnoreCase("block")) {
				parseBlock(child, null, tiles, min, max);
			} else if (type.equalsIgnoreCase("row")) {
				parseRow(child, null, tiles, min, max);
			} else if (type.equalsIgnoreCase("column")) {
				parseColumn(child, null, tiles, min, max);
			} else if (type.equalsIgnoreCase("tile")) {
				parseTile(child, null, tiles, min, max);
			}
		}
		int boardWidth = max.x + 2 + min.x;
		int boardHeight = max.y + 2 + min.y;
		int boardDepth = max.z + 1;
		int windowWidth = boardWidth + 3;
		int windowHeight = boardHeight + 2;
		PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File("GNOME " + name + ".mlo"))), true);
		out.println("source\tGNOME Mahjongg");
		out.println("name\t" + name);
		out.println("boardsize\t" + boardWidth + "," + boardHeight + "," + boardDepth);
		out.println("tilesize\t2,2,1");
		out.println("windowsize\t" + windowWidth + "," + windowHeight);
		out.println();
		for (Point3D tile : tiles) {
			out.println("tile\t" + tile.x + "," + tile.y + "," + tile.z);
		}
		out.flush();
		out.close();
	}
	
	private static void parseLayer(Node node, List<Point3D> tiles, Point3D min, Point3D max) {
		NamedNodeMap attributes = node.getAttributes();
		int z = Integer.parseInt(attributes.getNamedItem("z").getTextContent());
		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			String type = child.getNodeName();
			if (type.equalsIgnoreCase("block")) {
				parseBlock(child, z, tiles, min, max);
			} else if (type.equalsIgnoreCase("row")) {
				parseRow(child, z, tiles, min, max);
			} else if (type.equalsIgnoreCase("column")) {
				parseColumn(child, z, tiles, min, max);
			} else if (type.equalsIgnoreCase("tile")) {
				parseTile(child, z, tiles, min, max);
			}
		}
	}
	
	private static void parseBlock(Node node, Integer z, List<Point3D> tiles, Point3D min, Point3D max) {
		NamedNodeMap attributes = node.getAttributes();
		if (z == null) z = Integer.parseInt(attributes.getNamedItem("z").getTextContent());
		if (z < min.z) min.z = z; if (z > max.z) max.z = z;
		int left = parseInt2(attributes.getNamedItem("left").getTextContent());
		int top = parseInt2(attributes.getNamedItem("top").getTextContent());
		int right = parseInt2(attributes.getNamedItem("right").getTextContent());
		int bottom = parseInt2(attributes.getNamedItem("bottom").getTextContent());
		for (int y = top; y <= bottom; y += 2) {
			if (y < min.y) min.y = y; if (y > max.y) max.y = y;
			for (int x = left; x <= right; x += 2) {
				if (x < min.x) min.x = x; if (x > max.x) max.x = x;
				tiles.add(new Point3D(x, y, z));
			}
		}
	}
	
	private static void parseRow(Node node, Integer z, List<Point3D> tiles, Point3D min, Point3D max) {
		NamedNodeMap attributes = node.getAttributes();
		if (z == null) z = Integer.parseInt(attributes.getNamedItem("z").getTextContent());
		if (z < min.z) min.z = z; if (z > max.z) max.z = z;
		int y = parseInt2(attributes.getNamedItem("y").getTextContent());
		if (y < min.y) min.y = y; if (y > max.y) max.y = y;
		int left = parseInt2(attributes.getNamedItem("left").getTextContent());
		int right = parseInt2(attributes.getNamedItem("right").getTextContent());
		for (int x = left; x <= right; x += 2) {
			if (x < min.x) min.x = x; if (x > max.x) max.x = x;
			tiles.add(new Point3D(x, y, z));
		}
	}
	
	private static void parseColumn(Node node, Integer z, List<Point3D> tiles, Point3D min, Point3D max) {
		NamedNodeMap attributes = node.getAttributes();
		if (z == null) z = Integer.parseInt(attributes.getNamedItem("z").getTextContent());
		if (z < min.z) min.z = z; if (z > max.z) max.z = z;
		int x = parseInt2(attributes.getNamedItem("x").getTextContent());
		if (x < min.x) min.x = x; if (x > max.x) max.x = x;
		int top = parseInt2(attributes.getNamedItem("top").getTextContent());
		int bottom = parseInt2(attributes.getNamedItem("bottom").getTextContent());
		for (int y = top; y <= bottom; y += 2) {
			if (y < min.y) min.y = y; if (y > max.y) max.y = y;
			tiles.add(new Point3D(x, y, z));
		}
	}
	
	private static void parseTile(Node node, Integer z, List<Point3D> tiles, Point3D min, Point3D max) {
		NamedNodeMap attributes = node.getAttributes();
		if (z == null) z = Integer.parseInt(attributes.getNamedItem("z").getTextContent());
		if (z < min.z) min.z = z; if (z > max.z) max.z = z;
		int y = parseInt2(attributes.getNamedItem("y").getTextContent());
		if (y < min.y) min.y = y; if (y > max.y) max.y = y;
		int x = parseInt2(attributes.getNamedItem("x").getTextContent());
		if (x < min.x) min.x = x; if (x > max.x) max.x = x;
		tiles.add(new Point3D(x, y, z));
	}
	
	private static class Point3D {
		int x, y, z;
		public Point3D(int x, int y, int z) {
			this.x = x; this.y = y; this.z = z;
		}
	}
	
	private static int parseInt2(String s) {
		s = s.trim();
		if (s.endsWith(".5")) {
			s = s.substring(0, s.length()-2).trim();
			return Integer.parseInt(s) * 2 + 1;
		} else {
			return Integer.parseInt(s) * 2;
		}
	}
}
