package com.kreative.dotbamcrak.utility;

import java.awt.Color;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseUtilities {
	private ParseUtilities() {}
	
	public static <T> T index(T[] array, int index, int lineNumber) throws ParseException {
		if (index >= 0 && index < array.length) {
			return array[index];
		} else {
			throw new ParseException("Missing parameter", lineNumber);
		}
	}
	
	public static <T> T index(T[] array, int index, T def) throws ParseException {
		if (index >= 0 && index < array.length) {
			return array[index];
		} else {
			return def;
		}
	}
	
	public static int parseInt(String s, int lineNumber) throws ParseException {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException nfe) {
			throw new ParseException("Invalid value", lineNumber, nfe);
		}
	}
	
	public static long parseLong(String s, int lineNumber) throws ParseException {
		try {
			return Long.parseLong(s);
		} catch (NumberFormatException nfe) {
			throw new ParseException("Invalid value", lineNumber, nfe);
		}
	}
	
	private static long parseBitLongInternal(char ch) {
		if (ch >= '0' && ch <= '9') return (ch - '0');
		else if (ch >= 'A' && ch <= 'Z') return (ch - 'A' + 10);
		else if (ch >= 'a' && ch <= 'z') return (ch - 'a' + 10);
		else return -1;
	}
	
	private static long parseBitLongInternal(String s, int radix) {
		// Because Long.parseLong() is COMPLETELY BRAIN-DEAD about unsigned integers.
		s = s.trim();
		if (s.startsWith("+")) return parseBitLongInternal(s.substring(1), radix);
		else if (s.startsWith("-")) return -parseBitLongInternal(s.substring(1), radix);
		else {
			long l = 0;
			CharacterIterator i = new StringCharacterIterator(s);
			for (char ch = i.first(); ch != CharacterIterator.DONE; ch = i.next()) {
				long v = parseBitLongInternal(ch);
				if (v >= 0 && v < radix) {
					l *= radix;
					l += v;
				} else {
					throw new NumberFormatException("For input string: \"" + s + "\"");
				}
			}
			return l;
		}
	}
	
	public static long parseBitLong(String s, int lineNumber) throws ParseException {
		s = s.trim().toLowerCase();
		try {
			     if (s.startsWith("0x")) return parseBitLongInternal(s.substring(2), 16);
			else if (s.startsWith("0d")) return parseBitLongInternal(s.substring(2), 10);
			else if (s.startsWith("0o")) return parseBitLongInternal(s.substring(2),  8);
			else if (s.startsWith("0b")) return parseBitLongInternal(s.substring(2),  2);
			else if (s.startsWith("$" )) return parseBitLongInternal(s.substring(1), 16);
			else if (s.startsWith("#" )) return parseBitLongInternal(s.substring(1), 16);
			else if (s.startsWith("0" )) return parseBitLongInternal(s.substring(1),  8);
			else if (s.startsWith("%" )) return parseBitLongInternal(s.substring(1),  2);
			else return parseBitLongInternal(s, 10);
		} catch (NumberFormatException nfe) {
			throw new ParseException("Invalid value", lineNumber, nfe);
		}
	}
	
	public static void checkRange(boolean b, int lineNumber) throws ParseException {
		if (!b) {
			throw new ParseException("Value out of range", lineNumber);
		}
	}
	
	public static <T> T checkTile(T t, int lineNumber) throws ParseException {
		if (t == null) {
			throw new ParseException("Unknown tile id", lineNumber);
		} else {
			return t;
		}
	}
	
	public static boolean parseBoolean(String s, int lineNumber) throws ParseException {
		s = s.trim();
		if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("on") || s.equalsIgnoreCase("1")) return true;
		if (s.equalsIgnoreCase("false") || s.equalsIgnoreCase("no") || s.equalsIgnoreCase("off") || s.equalsIgnoreCase("0")) return false;
		throw new ParseException("Invalid value", lineNumber);
	}
	
	private static final Pattern STRIP_NONALPHA = Pattern.compile("[^A-Za-z0-9]");
	
	public static <E extends Enum<E>> E parseEnum(E[] values, String s, int lineNumber) throws ParseException {
		s = STRIP_NONALPHA.matcher(s).replaceAll("");
		for (E value : values) {
			if (STRIP_NONALPHA.matcher(value.name()).replaceAll("").equalsIgnoreCase(s)) {
				return value;
			}
		}
		throw new ParseException("Invalid value", lineNumber);
	}
	
	private static final Pattern DECIMAL_PATTERN = Pattern.compile("([0-9]+)\\s*,\\s*([0-9]+)\\s*,\\s*([0-9]+)");
	private static final Pattern LONG_HEX_PATTERN = Pattern.compile("#?([0-9A-Fa-f]{2})([0-9A-Fa-f]{2})([0-9A-Fa-f]{2})");
	private static final Pattern SHORT_HEX_PATTERN = Pattern.compile("#?([0-9A-Fa-f])([0-9A-Fa-f])([0-9A-Fa-f])");
	private static final Map<String, Color> COLORS = new HashMap<String, Color>();
	
	static {
		COLORS.put("a", new Color(0xFF00FF80));
		COLORS.put("aqua", new Color(0xFF00FFFF));
		COLORS.put("aquamarine", new Color(0xFF00FF80));
		COLORS.put("azure", new Color(0xFF0080FF));
		COLORS.put("b", new Color(0xFF0000FF));
		COLORS.put("black", new Color(0xFF000000));
		COLORS.put("blonde", new Color(0xFFFFC000));
		COLORS.put("blue", new Color(0xFF0000FF));
		COLORS.put("brown", new Color(0xFF996633));
		COLORS.put("c", new Color(0xFF00FFFF));
		COLORS.put("chartreuse", new Color(0xFF80FF00));
		COLORS.put("coral", new Color(0xFFFF8080));
		COLORS.put("corange", new Color(0xFFFFC080));
		COLORS.put("cream", new Color(0xFFFFEECC));
		COLORS.put("creme", new Color(0xFFFFEECC));
		COLORS.put("cyan", new Color(0xFF00FFFF));
		COLORS.put("d", new Color(0xFF0080FF));
		COLORS.put("denim", new Color(0xFF0080FF));
		COLORS.put("e", new Color(0xFFFFC000));
		COLORS.put("eggplant", new Color(0xFF400080));
		COLORS.put("f", new Color(0xFFFF4000));
		COLORS.put("fire", new Color(0xFFFF4000));
		COLORS.put("forest", new Color(0xFF008000));
		COLORS.put("forrest", new Color(0xFF008000));
		COLORS.put("frost", new Color(0xFF8080FF));
		COLORS.put("fuchsia", new Color(0xFFFF00FF));
		COLORS.put("fuschia", new Color(0xFFFF00FF));
		COLORS.put("g", new Color(0xFF00FF00));
		COLORS.put("gold", new Color(0xFFFFC000));
		COLORS.put("gray", new Color(0xFF808080));
		COLORS.put("green", new Color(0xFF00FF00));
		COLORS.put("grey", new Color(0xFF808080));
		COLORS.put("h", new Color(0xFF80FF00));
		COLORS.put("i", new Color(0xFF4000FF));
		COLORS.put("indigo", new Color(0xFF4000FF));
		COLORS.put("j", new Color(0xFFFFEECC));
		COLORS.put("k", new Color(0xFF000000));
		COLORS.put("l", new Color(0xFF80FF00));
		COLORS.put("lavendar", new Color(0xFFC080FF));
		COLORS.put("lavender", new Color(0xFFC080FF));
		COLORS.put("lemon", new Color(0xFFFFFF80));
		COLORS.put("lime", new Color(0xFF80FF80));
		COLORS.put("m", new Color(0xFFFF00FF));
		COLORS.put("magenta", new Color(0xFFFF00FF));
		COLORS.put("maroon", new Color(0xFF800000));
		COLORS.put("n", new Color(0xFF996633));
		COLORS.put("navy", new Color(0xFF000080));
		COLORS.put("o", new Color(0xFFFF8000));
		COLORS.put("olive", new Color(0xFF808000));
		COLORS.put("orange", new Color(0xFFFF8000));
		COLORS.put("p", new Color(0xFFC000FF));
		COLORS.put("pine", new Color(0xFF008000));
		COLORS.put("pink", new Color(0xFFFF80FF));
		COLORS.put("plum", new Color(0xFF800080));
		COLORS.put("purple", new Color(0xFFC000FF));
		COLORS.put("q", new Color(0xFFFFEECC));
		COLORS.put("r", new Color(0xFFFF0000));
		COLORS.put("red", new Color(0xFFFF0000));
		COLORS.put("rose", new Color(0xFFFF0080));
		COLORS.put("s", new Color(0xFFFF0080));
		COLORS.put("scarlet", new Color(0xFFFF4000));
		COLORS.put("scarlett", new Color(0xFFFF4000));
		COLORS.put("silver", new Color(0xFFC0C0C0));
		COLORS.put("sky", new Color(0xFF80FFFF));
		COLORS.put("t", new Color(0xFF000000));
		COLORS.put("teal", new Color(0xFF008080));
		COLORS.put("u", new Color(0xFF8000FF));
		COLORS.put("umber", new Color(0xFF804000));
		COLORS.put("v", new Color(0xFF8000FF));
		COLORS.put("violet", new Color(0xFF8000FF));
		COLORS.put("w", new Color(0xFFFFFFFF));
		COLORS.put("white", new Color(0xFFFFFFFF));
		COLORS.put("x", new Color(0xFF808080));
		COLORS.put("y", new Color(0xFFFFFF00));
		COLORS.put("yellow", new Color(0xFFFFFF00));
		COLORS.put("z", new Color(0xFF0080FF));
	}
	
	public static Color parseColor(String name, int lineNumber) throws ParseException {
		Matcher m;
		name = name.trim().toLowerCase();
		if ((m = DECIMAL_PATTERN.matcher(name)).matches()) {
			return new Color(0xFF000000 | (Integer.parseInt(m.group(1)) << 16) | (Integer.parseInt(m.group(2)) << 8) | Integer.parseInt(m.group(3)));
		} else if ((m = LONG_HEX_PATTERN.matcher(name)).matches()) {
			return new Color(0xFF000000 | (Integer.parseInt(m.group(1), 16) << 16) | (Integer.parseInt(m.group(2), 16) << 8) | Integer.parseInt(m.group(3), 16));
		} else if ((m = SHORT_HEX_PATTERN.matcher(name)).matches()) {
			return new Color(0xFF000000 | ((Integer.parseInt(m.group(1), 16) * 17) << 16) | ((Integer.parseInt(m.group(2), 16) * 17) << 8) | (Integer.parseInt(m.group(3), 16) * 17));
		} else if (COLORS.containsKey(name)) {
			return COLORS.get(name);
		} else {
			throw new ParseException("Invalid value", lineNumber);
		}
	}
}
