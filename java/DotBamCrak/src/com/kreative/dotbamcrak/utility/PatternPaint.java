package com.kreative.dotbamcrak.utility;

import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;

public class PatternPaint implements Paint {
	private Paint forePaint;
	private Paint backPaint;
	private long pattern;
	
	public PatternPaint(Paint fore, Paint back, long pattern) {
		this.forePaint = fore;
		this.backPaint = back;
		this.pattern = pattern;
	}
	
	public Paint getForeground() {
		return forePaint;
	}
	
	public Paint getBackground() {
		return backPaint;
	}
	
	public long getPattern() {
		return pattern;
	}

	public PaintContext createContext(ColorModel cm, Rectangle deviceBounds, Rectangle2D userBounds, AffineTransform xform, RenderingHints hints) {
		PaintContext fore = forePaint.createContext(cm, deviceBounds, userBounds, xform, hints);
		PaintContext back = backPaint.createContext(cm, deviceBounds, userBounds, xform, hints);
		return new PatternPaintContext(fore, back, pattern);
	}
	
	public int getTransparency() {
		int fore = forePaint.getTransparency();
		int back = backPaint.getTransparency();
		if (fore == back) return fore;
		else if (fore == TRANSLUCENT || back == TRANSLUCENT) return TRANSLUCENT;
		else if (fore == BITMASK || back == BITMASK) return BITMASK;
		else if (fore == OPAQUE || back == OPAQUE) return OPAQUE;
		else return OPAQUE;
	}
	
	private static class PatternPaintContext implements PaintContext {
		private PaintContext foreContext;
		private PaintContext backContext;
		private long pattern;
		
		public PatternPaintContext(PaintContext fore, PaintContext back, long pattern) {
			this.foreContext = fore;
			this.backContext = back;
			this.pattern = pattern;
		}

		public Raster getRaster(int x, int y, int w, int h) {
			if (pattern == -1) {
				return foreContext.getRaster(x, y, w, h);
			}
			else if (pattern == 0) {
				return backContext.getRaster(x, y, w, h);
			}
			else {
				Raster foreRaster = foreContext.getRaster(x, y, w, h);
				Raster backRaster = backContext.getRaster(x, y, w, h);
				ColorModel foreModel = foreContext.getColorModel();
				ColorModel backModel = backContext.getColorModel();
				BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
				for (int ry = y, ny = 0; ny < h; ry++, ny++) {
					for (int rx = x, nx = 0; nx < w; rx++, nx++) {
						int rgb;
						if (((pattern >> (((ry & 7) << 3) | (rx & 7))) & 1) != 0) {
							rgb = foreModel.getRGB(foreRaster.getDataElements(nx, ny, null));
						} else {
							rgb = backModel.getRGB(backRaster.getDataElements(nx, ny, null));
						}
						img.setRGB(nx, ny, rgb);
					}
				}
				return img.getData();
			}
		}

		public ColorModel getColorModel() {
			if (pattern == -1) {
				return foreContext.getColorModel();
			}
			else if (pattern == 0) {
				return backContext.getColorModel();
			}
			else {
				BufferedImage img = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);
				return img.getColorModel();
			}
		}

		public void dispose() {
			foreContext.dispose();
			backContext.dispose();
		}
	}
}
