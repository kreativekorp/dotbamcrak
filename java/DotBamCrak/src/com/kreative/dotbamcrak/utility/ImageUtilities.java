package com.kreative.dotbamcrak.utility;

import java.awt.image.BufferedImage;

public class ImageUtilities {
	private ImageUtilities() {}
	
	private static int darken(int color) {
		int a = ((color >> 24) & 0xFF);
		int r = ((color >> 16) & 0xFF) - 51; if (r < 0) r = 0;
		int g = ((color >>  8) & 0xFF) - 51; if (g < 0) g = 0;
		int b = ((color >>  0) & 0xFF) - 51; if (b < 0) b = 0;
		return (a << 24) | (r << 16) | (g << 8) | (b << 0);
	}
	
	private static int lighten(int color) {
		int a = ((color >> 24) & 0xFF);
		int r = ((color >> 16) & 0xFF) + 51; if (r > 255) r = 255;
		int g = ((color >>  8) & 0xFF) + 51; if (g > 255) g = 255;
		int b = ((color >>  0) & 0xFF) + 51; if (b > 255) b = 255;
		return (a << 24) | (r << 16) | (g << 8) | (b << 0);
	}
	
	public static void darken(BufferedImage image, int x, int y, int w, int h) {
		int[] rgb = new int[w*h];
		image.getRGB(x, y, w, h, rgb, 0, w);
		for (int i = 0; i < rgb.length; i++) {
			rgb[i] = darken(rgb[i]);
		}
		image.setRGB(x, y, w, h, rgb, 0, w);
	}
	
	public static void lighten(BufferedImage image, int x, int y, int w, int h) {
		int[] rgb = new int[w*h];
		image.getRGB(x, y, w, h, rgb, 0, w);
		for (int i = 0; i < rgb.length; i++) {
			rgb[i] = lighten(rgb[i]);
		}
		image.setRGB(x, y, w, h, rgb, 0, w);
	}
	
	public static void invert(BufferedImage image, int x, int y, int w, int h) {
		int[] rgb = new int[w*h];
		image.getRGB(x, y, w, h, rgb, 0, w);
		for (int i = 0; i < rgb.length; i++) {
			rgb[i] ^= 0x00FFFFFF;
		}
		image.setRGB(x, y, w, h, rgb, 0, w);
	}
	
	public static void blit(BufferedImage src, int srcx, int srcy, BufferedImage dst, int dstx, int dsty, int w, int h) {
		int[] rgb = new int[w*h];
		src.getRGB(srcx, srcy, w, h, rgb, 0, w);
		dst.setRGB(dstx, dsty, w, h, rgb, 0, w);
	}
}
