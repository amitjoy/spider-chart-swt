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
package com.amitinside.tooling.chart.plotter;

import java.util.Vector;

import com.amitinside.tooling.chart.SpiderChartComponent;
import com.amitinside.tooling.chart.gc.AbstractChartGraphics;
import com.amitinside.tooling.chart.gc.AbstractChartImage;
import com.amitinside.tooling.chart.scale.SpiderChartScale;
import com.amitinside.tooling.chart.sequence.DataSeq;
import com.amitinside.tooling.chart.style.FillStyle;

/**
 * Base class used to create spider chart plotters
 *
 * @author AMIT KUMAR MONDAL
 *
 */
public abstract class AbstractPlotter extends SpiderChartComponent {

	/** Spider Chart Background Image */
	public AbstractChartImage backgroundImage;

	/** */
	public FillStyle backgroundStyle = null;

	/** */
	public int depth = 0;

	/** */
	public Vector<DataSeq> seq = new Vector<>(0, 1);

	/** */
	public int visibleHeight = 0;

	/** */
	public int visibleWidth = 0;

	/** */
	public SpiderChartScale xScale;

	/** */
	public SpiderChartScale yScale;

	/** */
	public void addSeq(final DataSeq s) {
		this.replaceSeq(-1, s);
	}

	/** */
	private void calculateMax(final SpiderChartScale s, final double m) {
		if (!s.exactMaxValue) {
			s.max = m;
			return;
		}
		if ((s.preferred_MaxMin_values != null) && (s.preferred_MaxMin_values.length > 0)) {
			for (final double preferred_MaxMin_value : s.preferred_MaxMin_values) {
				if (preferred_MaxMin_value >= m) {
					s.max = preferred_MaxMin_value;

					break;
				}
			}
		}
	}

	/** */
	private void calculateMin(final SpiderChartScale s, final double m) {
		if (!s.exactMinValue) {
			s.min = m;
			return;
		}
		if ((s.preferred_MaxMin_values != null) && (s.preferred_MaxMin_values.length > 0)) {
			for (int j = s.preferred_MaxMin_values.length - 1; j > 0; j--) {
				if (s.preferred_MaxMin_values[j] <= m) {
					s.min = s.preferred_MaxMin_values[j];

					break;
				}
			}
		}
	}

	/** */
	protected SpiderChartScale getActiveXScale(final DataSeq s) {
		final SpiderChartScale scale = this.xScale;
		return scale;
	}

	/** */
	protected SpiderChartScale getActiveYScale(final DataSeq s) {
		final SpiderChartScale scale = this.yScale;
		return scale;
	}

	/** */
	public DataSeq getSeq(final int p) {
		return this.seq.elementAt(p);
	}

	/** */
	public int getSeqCount() {
		return this.seq.size();
	}

	/** */
	public void plot(final AbstractChartGraphics g) {
		for (int i = 0; i < this.seq.size(); i++) {
			final DataSeq s = this.seq.elementAt(i);

			this.plot(g, s, i);
		}
	}

	/** */
	protected void plot(final AbstractChartGraphics g, final DataSeq s, final int serieSec) {
	}

	/** */
	public void plotBackground(final AbstractChartGraphics g, final int bw, final int bh, final int offsetX,
			final int offsetY) {
		if (this.backgroundStyle != null) {
			final boolean D3 = false;
			if (D3) {
				final int[] xs = new int[6];
				final int[] ys = new int[6];
				xs[0] = this.x + offsetX;
				ys[0] = this.y + offsetY;

				xs[1] = this.x + offsetX + this.depth;
				ys[1] = (this.y + offsetY) - this.depth;

				xs[2] = this.x + offsetX + this.visibleWidth;
				ys[2] = (this.y + offsetY) - this.depth;

				xs[3] = this.x + offsetX + this.visibleWidth;
				ys[3] = (this.y + offsetY + this.visibleHeight) - this.depth - this.depth;

				xs[4] = (this.x + offsetX + this.visibleWidth) - this.depth;
				ys[4] = (this.y + offsetY + this.visibleHeight) - this.depth;

				xs[5] = this.x + offsetX;
				ys[5] = (this.y + offsetY + this.visibleHeight) - this.depth;

				this.backgroundStyle.drawPolygon(g, xs, ys, 6);
			} else {
				this.backgroundStyle.draw(g, this.x, this.y, this.x + bw, this.y + bh);
			}
		}
		if (this.backgroundImage != null) {
			final int w = this.backgroundImage.getWidth();
			final int h = this.backgroundImage.getHeight();
			if ((w > -1) && (h > -1)) {
				int toCenterX = (bw - w) / 2;
				if (toCenterX < 0) {
					toCenterX = 0;
				}
				int toCenterY = (bh - h) / 2;
				if (toCenterY < 0) {
					toCenterY = 0;
				}
				g.drawImage(this.backgroundImage, toCenterX + this.x, this.y + toCenterY);
			}
		}
	}

	/** */
	public void replaceSeq(final int p, final DataSeq s) {
		final SpiderChartScale tmpScaleX = this.getActiveXScale(s);
		this.getActiveYScale(s);
		if (p >= this.seq.size()) {
			return;
		}
		if ((this.seq.size() > 3)) {
			return;
		}
		if (p == -1) {
			this.seq.addElement(s);
		} else {
			this.seq.setElementAt(s, p);
		}
		final boolean fixedLimits = false;
		final boolean cumulative = false;
		if (!(this instanceof AbstractPlotter)) {
			for (int i = 0; i < s.getSize(); i++) {
				if (s.getElementY(i) != null) {
					final double XValue = ((Double) s.getElementX(i)).doubleValue();
					double YValue = ((Double) s.getElementY(i)).doubleValue();
					if (cumulative) {
						YValue = 0.0D;
						for (int si = 0; si < this.seq.size(); si++) {
							final DataSeq ser = this.seq.elementAt(si);
						}
					}
					if (XValue >= tmpScaleX.max) {
						this.calculateMax(tmpScaleX, XValue);
					}
					if (XValue < tmpScaleX.min) {
						this.calculateMin(tmpScaleX, XValue);
					}
					if (!fixedLimits) {
						if (YValue > this.yScale.max) {
							this.calculateMax(this.yScale, YValue);
						}
						if (YValue < this.yScale.min) {
							this.calculateMin(this.yScale, YValue);
						}
					}
				}
			}
		}
	}

	/** */
	public void setSeq(final int p, final DataSeq s) {
		if (p < this.seq.size()) {
			this.seq.setElementAt(s, p);
		}
	}
}