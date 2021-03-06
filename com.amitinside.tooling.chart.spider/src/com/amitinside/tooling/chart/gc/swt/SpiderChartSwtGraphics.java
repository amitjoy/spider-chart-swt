/*******************************************************************************
 * Copyright 2016 Amit Kumar Mondal
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.amitinside.tooling.chart.gc.swt;

import static com.amitinside.tooling.chart.gc.AbstractGraphicsSupplier.getImage;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;

import com.amitinside.tooling.chart.gc.AbstractChartColor;
import com.amitinside.tooling.chart.gc.AbstractChartFont;
import com.amitinside.tooling.chart.gc.AbstractChartGraphics;
import com.amitinside.tooling.chart.gc.AbstractChartImage;

/**
 * Represents a chart graphics to be used in SWT
 *
 * @author AMIT KUMAR MONDAL
 *
 */
public final class SpiderChartSwtGraphics extends AbstractChartGraphics {
	/** */
	private float alphaValue = 1.0F;
	/** */
	private Color currentColor;
	/** */
	private Font currentFont;
	/** */
	private GC graphics;
	/** */
	private Image imageForTransparentFilling = null;
	/** */
	private Image srcImage;
	/** */
	private Color transparent = null;

	/** Constructor */
	public SpiderChartSwtGraphics(final Object g) {
		this.graphics = (GC) g;
	}

	/** {@inheritDoc}} */
	@Override
	public void createFadeArea(final AbstractChartColor colorFrom, final AbstractChartColor colorUntil, final int x,
			final int y, final int w, final int h, final boolean vertical, final boolean cyclic) {
		final Color fore = ((SpiderChartSwtColor) colorFrom).getColor();
		final Color back = ((SpiderChartSwtColor) colorUntil).getColor();

		this.graphics.setForeground(fore);
		this.graphics.setBackground(back);

		this.graphics.fillGradientRectangle(x, y, w, h, vertical);
		if (this.currentColor != null) {
			this.graphics.setForeground(this.currentColor);
			this.graphics.setBackground(this.currentColor);
		}
		fore.dispose();
		back.dispose();
	}

	/** {@inheritDoc} */
	@Override
	public void dispose() {
		this.disposeCurrentColor();
		this.disposeCurrentFont();
		if ((this.graphics != null) && !this.graphics.isDisposed()) {
			this.graphics.dispose();
		}
		this.graphics = null;
	}

	/** */
	private void disposeCurrentColor() {
		if ((this.currentColor != null) && !this.currentColor.isDisposed()) {
			this.currentColor.dispose();
		}
		this.currentColor = null;
	}

	/** */
	private void disposeCurrentFont() {
		if ((this.currentFont != null) && !this.currentFont.isDisposed()) {
			this.currentFont.dispose();
		}
		this.currentFont = null;
	}

	/** {@inheritDoc}} */
	@Override
	public void drawArc(final int x, final int y, final int w, final int h, final int a1, final int a2) {
		this.graphics.drawArc(x, y, w, h, a1, a2);
	}

	/** {@inheritDoc} */
	@Override
	public void drawImage(final AbstractChartImage image, final int x, final int y) {
		if (image == null) {
			return;
		}
		if (((SpiderChartSwtImage) image).getImage() == null) {
			return;
		}
		this.graphics.drawImage(((SpiderChartSwtImage) image).getImage(), x, y);
	}

	/** {@inheritDoc} */
	@Override
	public void drawImage(final AbstractChartImage image, final int x1Dest, final int y1Dest, final int x2Dest,
			final int y2Dest, final int x1Source, final int y1Source, final int x2Source, final int y2Source) {
		if (image == null) {
			return;
		}
		if (((SpiderChartSwtImage) image).getImage() == null) {
			return;
		}
		this.graphics.drawImage(((SpiderChartSwtImage) image).getImage(), x1Source, y1Source, x2Source - x1Source,
				y2Source - y1Source, x1Dest, y1Dest, x2Dest - x1Dest, y2Dest - y1Dest);
	}

	/** {@inheritDoc} */
	@Override
	public void drawLine(final int x1, final int y1, final int x2, final int y2) {
		this.graphics.drawLine(x1, y1, x2, y2);
	}

	/** {@inheritDoc} */
	@Override
	public void drawPolygon(final int[] x1, final int[] y1, final int count) {
		final int[] points = new int[count * 2];
		int j = 0;
		for (int i = 0; i < count; i++) {
			points[j++] = x1[i];
			points[j++] = y1[i];
		}
		this.graphics.drawPolygon(points);
	}

	/** {@inheritDoc} */
	@Override
	public void drawRect(final int x1, final int y1, final int w, final int h) {
		super.drawRect(x1, y1, w, h);
	}

	/** {@inheritDoc} */
	@Override
	public boolean drawRotatedText(final AbstractChartFont descFont, final AbstractChartColor descColor,
			final String txt, final int angle, final int x, final int y, final boolean b) {
		this.setFont(descFont);
		this.setColor(descColor);

		final int h = this.getFontHeight();
		final int w = this.getFontWidth(txt);
		int size = w;
		int toCenterX = 0;
		int toCenterY = 0;
		if (h > w) {
			size = h;
			toCenterX = (size - w) / 2;
		} else {
			toCenterY = (size - h) / 2;
		}
		Image tmpImage = new Image(SwtGraphicsProvider.getDisplay(), size, size);
		final ImageData imageData = tmpImage.getImageData();
		tmpImage.dispose();

		Color transparent = null;
		if ((descColor.getRed() == 255) && (descColor.getBlue() == 255) && (descColor.getGreen() == 255)) {
			transparent = new Color(SwtGraphicsProvider.getDisplay(), 0, 0, 0);
			imageData.transparentPixel = imageData.palette.getPixel(new RGB(0, 0, 0));
		} else {
			transparent = new Color(SwtGraphicsProvider.getDisplay(), 255, 255, 255);
			imageData.transparentPixel = imageData.palette.getPixel(new RGB(255, 255, 255));
		}
		tmpImage = new Image(SwtGraphicsProvider.getDisplay(), imageData);

		final GC g = new GC(tmpImage);
		final Color c = ((SpiderChartSwtColor) descColor).getColor();
		final Font f = ((SpiderChartSwtFont) descFont).getFont();

		g.setForeground(transparent);
		g.setBackground(transparent);
		g.fillRectangle(0, 0, size, size);

		g.setFont(f);
		g.setForeground(c);
		g.drawText(txt, toCenterX, toCenterY, true);

		g.dispose();
		c.dispose();
		f.dispose();

		final AbstractChartImage tmpChartImage = getImage(tmpImage);
		this.paintRotatedImage(tmpChartImage, angle, x - ((w - h) / 2), y + 4, ROTATE_CENTER);

		tmpChartImage.dispose();
		tmpImage.dispose();

		return true;
	}

	/** {@inheritDoc} */
	@Override
	public void drawText(final String s, final int x, final int y) {
		this.graphics.drawString(s, x, y - this.graphics.getFontMetrics().getHeight(), true);
	}

	/** {@inheritDoc} */
	@Override
	public void fillArc(final int x, final int y, final int w, final int h, final int a1, final int a2) {
		final GC g = this.getGraphicForFilling();
		g.fillArc(x, y, w, h, a1, a2);
		this.processFilling(g);
	}

	/** {@inheritDoc} */
	@Override
	public void fillPolygon(final int[] x1, final int[] y1, final int count) {
		final int[] points = new int[count * 2];
		int j = 0;
		for (int i = 0; i < count; i++) {
			points[j++] = x1[i];
			points[j++] = y1[i];
		}
		final GC g = this.getGraphicForFilling();
		g.fillPolygon(points);
		this.processFilling(g);
	}

	/** {@inheritDoc} */
	@Override
	public void fillRect(final int x1, final int y1, final int w, final int h) {
		final GC g = this.getGraphicForFilling();
		g.fillRectangle(new Rectangle(x1, y1, w, h));
		this.processFilling(g);
	}

	/** {@inheritDoc} */
	@Override
	public Object getAlphaComposite() {
		return new Float(this.alphaValue);
	}

	public float getAlphaValue() {
		return this.alphaValue;
	}

	/** {@inheritDoc} */
	@Override
	public AbstractChartColor getColor() {
		return new SpiderChartSwtColor(this.currentColor);
	}

	public Color getCurrentColor() {
		return this.currentColor;
	}

	public Font getCurrentFont() {
		return this.currentFont;
	}

	/** {@inheritDoc} */
	@Override
	public AbstractChartFont getFont() {
		return new SpiderChartSwtFont(this.currentFont);
	}

	/** {@inheritDoc} */
	@Override
	public int getFontHeight(final AbstractChartFont font) {
		final Font tmpFont = this.currentFont;
		this.currentFont = null;
		if (font != null) {
			this.setFont(font);
		}
		final int result = this.graphics.getFontMetrics().getHeight();

		this.disposeCurrentFont();
		this.currentFont = tmpFont;

		return result;
	}

	/** {@inheritDoc} */
	@Override
	public int getFontWidth(final AbstractChartFont font, final String s) {
		final Font tmpFont = this.currentFont;
		this.currentFont = null;
		if (font != null) {
			this.setFont(font);
		}
		int result = 0;

		result = this.graphics.textExtent(s).x;

		this.disposeCurrentFont();
		this.currentFont = tmpFont;

		return result;
	}

	/** {@inheritDoc} */
	protected GC getGraphicForFilling() {
		if ((this.alphaValue >= 1.0F) || (this.srcImage == null)) {
			this.imageForTransparentFilling = null;
			return this.graphics;
		}
		this.transparent = null;
		if ((this.currentColor.getRed() == 255) && (this.currentColor.getBlue() == 255)
				&& (this.currentColor.getGreen() == 255)) {
			this.transparent = new Color(SwtGraphicsProvider.getDisplay(), 0, 0, 0);
		} else {
			this.transparent = new Color(SwtGraphicsProvider.getDisplay(), 255, 255, 255);
		}
		final RGB[] rgbs = new RGB[256];
		rgbs[0] = this.transparent.getRGB();
		for (int i = 1; i <= 255; i++) {
			rgbs[i] = this.currentColor.getRGB();
		}
		final ImageData imageData = new ImageData(this.srcImage.getBounds().width, this.srcImage.getBounds().height,
				this.srcImage.getImageData().depth, this.srcImage.getImageData().palette);

		this.imageForTransparentFilling = new Image(SwtGraphicsProvider.getDisplay(), imageData);

		final GC g = new GC(this.imageForTransparentFilling);

		g.setForeground(this.transparent);
		g.setBackground(this.transparent);
		g.fillRectangle(0, 0, this.srcImage.getBounds().width, this.srcImage.getBounds().height);

		g.setForeground(this.currentColor);
		g.setBackground(this.currentColor);

		return g;
	}

	public GC getGraphics() {
		return this.graphics;
	}

	public Image getImageForTransparentFilling() {
		return this.imageForTransparentFilling;
	}

	public Image getSrcImage() {
		return this.srcImage;
	}

	public Color getTransparent() {
		return this.transparent;
	}

	/** {@inheritDoc} */
	@Override
	public void paintRotatedImage(AbstractChartImage srcImage, final int angle, final int x, final int y,
			final int alignment) {
		Image srcSwtImage = ((SpiderChartSwtImage) srcImage).getImage();
		int size = srcSwtImage.getImageData().width;
		int h = srcSwtImage.getImageData().height;
		int w = srcSwtImage.getImageData().width;
		final int originalH = h;
		if (h != w) {
			srcImage = ((SpiderChartSwtImage) srcImage).forRotation();
			srcSwtImage = ((SpiderChartSwtImage) srcImage).getImage();

			size = srcSwtImage.getImageData().width;
			h = srcSwtImage.getImageData().height;
			w = srcSwtImage.getImageData().width;
		}
		final ImageData srcData = srcSwtImage.getImageData();
		final ImageData destData = new ImageData(size, size, srcData.depth, srcData.palette);
		destData.transparentPixel = srcData.transparentPixel;
		if (destData.transparentPixel != -1) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					destData.setPixel(i, j, destData.transparentPixel);
				}
			}
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				final int x2 = this.rotateX(i - (size / 2), j - (size / 2), -angle) + (size / 2);
				final int y2 = this.rotateY(i - (size / 2), j - (size / 2), -angle) + (size / 2);

				if ((x2 < size) && (y2 < size) && (x2 >= 0) && (y2 >= 0)) {
					destData.setPixel(i, j, srcData.getPixel(x2, y2));
				}
			}
		}
		final Image destImage = new Image(SwtGraphicsProvider.getDisplay(), destData);
		if (alignment == ROTATE_LEFTTOP) {
			if (angle == 90) {
				this.graphics.drawImage(destImage, x - (w - originalH), y);
			} else if (angle == -90) {
				this.graphics.drawImage(destImage, x, y);
			}
		} else if (angle == 90) {
			this.graphics.drawImage(destImage, x, y);
		} else if (angle == -90) {
			this.graphics.drawImage(destImage, x, y);
		}
		destImage.dispose();
	}

	/** */
	protected void processFilling(final GC g) {
		if (g != this.graphics) {
			g.dispose();

			final ImageData imageData = this.imageForTransparentFilling.getImageData();
			for (int i = 0; i < this.srcImage.getBounds().width; i++) {
				for (int j = 0; j < this.srcImage.getBounds().height; j++) {
					final RGB rgb = imageData.palette.getRGB(imageData.getPixel(i, j));
					if ((rgb.red == this.transparent.getRed()) && (rgb.green == this.transparent.getGreen())
							&& (rgb.blue == this.transparent.getBlue())) {
						imageData.setAlpha(i, j, 0);
					} else {
						imageData.setAlpha(i, j, (int) (this.alphaValue * 255.0F));
					}
				}
			}
			this.transparent.dispose();
			this.imageForTransparentFilling.dispose();

			this.imageForTransparentFilling = new Image(SwtGraphicsProvider.getDisplay(), imageData);

			this.graphics.drawImage(this.imageForTransparentFilling, 0, 0);
			this.imageForTransparentFilling.dispose();
			this.imageForTransparentFilling = null;
		}
	}

	/** */
	private int rotateX(final int x, final int y, final int angle) {
		if (angle == 90) {
			return y * -1;
		}
		if (angle == -90) {
			return y * 1;
		}
		return (int) ((x * Math.cos(Math.toRadians(angle))) - (y * Math.sin(Math.toRadians(angle))));
	}

	/** */
	private int rotateY(final int x, final int y, final int angle) {
		if (angle == 90) {
			return x * 1;
		}
		if (angle == -90) {
			return x * -1;
		}
		return (int) ((x * Math.sin(Math.toRadians(angle))) + (y * Math.cos(Math.toRadians(angle))));
	}

	/** {@inheritDoc} */
	@Override
	public void setAlpha(final float a) {
		this.alphaValue = a;
	}

	/** {@inheritDoc} */
	@Override
	public void setAlphaComposite(final Object a) {
		if (a instanceof Float) {
			this.alphaValue = ((Float) a).floatValue();
		}
	}

	public void setAlphaValue(final float alphaValue) {
		this.alphaValue = alphaValue;
	}

	/** {@inheritDoc} */
	@Override
	public void setColor(final AbstractChartColor color) {
		this.disposeCurrentColor();
		this.currentColor = ((SpiderChartSwtColor) color).getColor();

		this.graphics.setForeground(this.currentColor);
		this.graphics.setBackground(this.currentColor);
	}

	public void setCurrentColor(final Color currentColor) {
		this.currentColor = currentColor;
	}

	public void setCurrentFont(final Font currentFont) {
		this.currentFont = currentFont;
	}

	/** {@inheritDoc} */
	@Override
	public void setFont(final AbstractChartFont font) {
		this.disposeCurrentFont();
		this.currentFont = ((SpiderChartSwtFont) font).getFont();

		this.graphics.setFont(this.currentFont);
	}

	public void setGraphics(final GC graphics) {
		this.graphics = graphics;
	}

	public void setImageForTransparentFilling(final Image imageForTransparentFilling) {
		this.imageForTransparentFilling = imageForTransparentFilling;
	}

	/** {@inheritDoc} */
	@Override
	public void setLineStyle(final int style) {
		super.setLineStyle(style);
	}

	/** {@inheritDoc} */
	@Override
	public void setLineWidth(final int w) {
		super.setLineWidth(w);
	}

	public void setSrcImage(final Image srcImage) {
		this.srcImage = srcImage;
	}

	public void setTransparent(final Color transparent) {
		this.transparent = transparent;
	}
}
