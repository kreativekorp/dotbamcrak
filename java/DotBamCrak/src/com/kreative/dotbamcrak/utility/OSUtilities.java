package com.kreative.dotbamcrak.utility;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OSUtilities {
	private OSUtilities() {}
	
	private static String osString = null;
	private static String unString = null;
	private static String ufnString = null;
	
	public static boolean isMacOS() {
		if (osString == null) {
			try {
				osString = System.getProperty("os.name").toUpperCase();
			} catch (Exception e) {
				osString = "";
			}
		}
		return osString.contains("MAC OS");
	}
	
	public static boolean isWindows() {
		if (osString == null) {
			try {
				osString = System.getProperty("os.name").toUpperCase();
			} catch (Exception e) {
				osString = "";
			}
		}
		return osString.contains("WINDOWS");
	}
	
	public static String getShortUserName() {
		if (unString != null) {
			return unString;
		} else {
			try {
				unString = System.getProperty("user.name");
				return unString;
			} catch (Exception e) {
				unString = null;
				return "";
			}
		}
	}
	
	public static String getLongUserName() {
		if (ufnString != null) {
			return ufnString;
		} else if (isWindows()) {
			try {
				Pattern p = Pattern.compile("[Ff]ull [Nn]ame\\s+(.*)");
				String s1 = captureProcessOutput("net user "+System.getProperty("user.name"));
				Matcher m1 = p.matcher(s1);
				if (m1.find()) {
					ufnString = m1.group(1).trim();
					return ufnString;
				} else {
					String s2 = captureProcessOutput("net user "+System.getProperty("user.name")+" /domain");
					Matcher m2 = p.matcher(s2);
					if (m2.find()) {
						ufnString = m2.group(1).trim();
						return ufnString;
					} else {
						ufnString = null;
						return "";
					}
				}
			} catch (Exception e) {
				ufnString = null;
				return "";
			}
		} else {
			try {
				String[] s1 = captureProcessOutput(new String[]{"id", "-P"}).split(":");
				if (s1.length > 7) {
					ufnString = s1[7];
					return ufnString;
				} else {
					Pattern p = Pattern.compile("\t[Nn]ame:\\s+(.*)");
					String s2 = captureProcessOutput(new String[]{"finger", "-mlp"});
					Matcher m2 = p.matcher(s2);
					if (m2.find()) {
						ufnString = m2.group(1).trim();
						return ufnString;
					} else {
						ufnString = null;
						return "";
					}
				}
			} catch (Exception e) {
				ufnString = null;
				return "";
			}
		}
	}
	
	public static String captureProcessOutput(String s) throws IOException {
		return captureProcessOutput(Runtime.getRuntime().exec(s));
	}
	
	public static String captureProcessOutput(String[] s) throws IOException {
		return captureProcessOutput(Runtime.getRuntime().exec(s));
	}
	
	public static String captureProcessOutput(Process p) throws IOException {
		InputStream in = new BufferedInputStream(p.getInputStream());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buff = new byte[1048576];
		int len;
		while ((len = in.read(buff)) >= 0) {
			out.write(buff, 0, len);
		}
		in.close();
		out.close();
		return new String(out.toByteArray());
	}
}
