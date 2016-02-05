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
package com.amitinside.tooling.chart;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import com.amitinside.tooling.chart.api.IFloatingObject;
import com.amitinside.tooling.chart.axis.SpiderChartAxis;
import com.amitinside.tooling.chart.gc.SWTGraphicsSupplier;
import com.amitinside.tooling.chart.gc.SpiderChartColor;
import com.amitinside.tooling.chart.gc.SpiderChartFont;
import com.amitinside.tooling.chart.gc.SpiderChartGraphics;
import com.amitinside.tooling.chart.gc.SpiderChartImage;
import com.amitinside.tooling.chart.label.SpiderChartLabel;
import com.amitinside.tooling.chart.legend.SpiderChartLegend;
import com.amitinside.tooling.chart.listener.ISpiderChartListener;
import com.amitinside.tooling.chart.plotter.spider.SpiderChartPlotter;
import com.amitinside.tooling.chart.sequence.DataSeq;
import com.amitinside.tooling.chart.sequence.LineDataSeq;
import com.amitinside.tooling.chart.style.FillStyle;
import com.amitinside.tooling.chart.style.LineStyle;
import com.amitinside.tooling.chart.title.SpiderChartTitle;

/**
 * Actual Spider Chart
 *
 * @author AMIT KUMAR MONDAL
 *
 */
public class SpiderChart {

	private class SpiderChartWorker implements Runnable {

		/** */
		@SuppressWarnings("unused")
		public SpiderChart chart = null;

		/** */
		public boolean stop = false;

		/** Constructor */
		private SpiderChartWorker() {
		}

		/** Constructor */
		SpiderChartWorker(final Object object) {
			this();
		}

		/** {@inheritDoc} */
		@Override
		public void run() {
			while (!this.stop) {
				try {
					Thread.sleep(SpiderChart.this.msecs);
				} catch (final Exception e) {
					e.printStackTrace();
				}
				if (this.stop) {
					break;
				}
			}
		}
	}

	/** */
	public static final int dnum = 10;
	/** */
	public static final int LAYOUT_LEGEND_BOTTOM = 2;
	/** */
	public static final int LAYOUT_LEGEND_RIGHT = 0;
	/** */
	public static final int LAYOUT_LEGEND_TOP = 1;
	/** */
	protected static final int MAX_SERIES = 50;
	/** */
	public static String numberLocale;

	/** */
	public static int d() {
		return 0;
	}

	/** */
	public boolean activateSelection = false;

	/** Used to trigger thread automatically to build the Spider Chart */
	public boolean autoRebuild = true;

	/** Auto Sizeable Property */
	public boolean autoSize = true;

	/** */
	public double axisMargin = 0.0625D;

	/** */
	public FillStyle back = new FillStyle(SWTGraphicsSupplier.getColor(SpiderChartColor.AQUA));

	/** */
	public String backgroundCanvasColor = SpiderChartColor.AQUA;

	/** Spider Chart Back Image */
	public SpiderChartImage backImage;

	/** */
	private SpiderChartImage backTmpImage = null;

	/**  */
	public LineStyle border = null;

	/** */
	public double bottomMargin = 0.125D;

	/** */
	public Vector<Object> chartHotAreas = new Vector<>(0, 5);

	/** Spider Chart Image */
	private SpiderChartImage chartImage = null;

	/** */
	private final List<ISpiderChartListener> chartListeners = new CopyOnWriteArrayList<>();

	/** */
	private SpiderChartWorker deamon = null;

	/** */
	public boolean doubleBuffering = true;

	/** */
	private SpiderChartImage finalImage = null;

	/** */
	protected Vector<IFloatingObject> floatingObjects = new Vector<>(0, 5);

	/** */
	public boolean fullXAxis = false;

	/** */
	private int height = 0;

	/** */
	private int lastHeight = -1;

	/** */
	private int lastWidth = -1;

	/** */
	public int layout = 0;

	/** Chart Left Margin */
	public double leftMargin = 0.125D;

	/** Spider Chart Legend */
	public SpiderChartLegend legend;

	/** Spider Chart Legend Margin */
	public double legendMargin = 0.2D;

	/** */
	private int minimumHeight = 0;

	/** */
	private int minimumWidth = 0;

	/** */
	public long msecs = 2000L;

	/** */
	protected Vector<String> notes = new Vector<>();

	/** */
	public int offsetX = 0;

	/** */
	public int offsetY = 0;

	/** */
	public SpiderChartPlotter[] plotters = new SpiderChartPlotter[10];

	/** */
	private int plottersCount = 0;

	/** */
	public String reloadFrom = "";

	/** */
	public boolean repaintAll = true;

	/** */
	public boolean repaintAlways = true;

	/** */
	public double rightMargin = 0.125D;

	/** */
	public SpiderChartLabel selectedLabel = null;

	/** */
	public DataSeq selectedSerie = null;

	/** */
	public int selectedSeriePoint = -1;

	/** */
	@SuppressWarnings("unused")
	private boolean showingTip = false;

	/** */
	public boolean showPosition = false;

	/** Show Tips on the Spider Chart Points */
	public boolean showTips = false;

	/** */
	@SuppressWarnings("unused")
	private boolean stopped = false;

	/** */
	protected Vector<TargetZone> targetZones = new Vector<>();

	/** Spider Chart Tip Background Color */
	SpiderChartColor tipColor = SWTGraphicsSupplier.getColor(SpiderChartColor.YELLOW);

	/** Spider Chart Tip Font */
	SpiderChartFont tipFont = SWTGraphicsSupplier.getFont("Serif", SpiderChartFont.PLAIN, 10);

	/** Spider Chart Tip Font Color */
	SpiderChartColor tipFontColor = SWTGraphicsSupplier.getColor(SpiderChartColor.BLACK);

	/** Spider Chart Title */
	public SpiderChartTitle title;

	/** */
	public double topMargin = 0.125D;

	/** */
	public int virtualHeight = 0;

	/** */
	public int virtualWidth = 0;

	/** */
	private int width = 0;

	/** Scrollable Property */
	public boolean withScroll = false;

	/**
	 * Constructor
	 */
	protected SpiderChart() {
	}

	/** Constructor */
	public SpiderChart(final SpiderChartTitle t, final SpiderChartPlotter p) {
		this.resetChart(t, p, null, null);
	}

	/** */
	public void addChartListener(final ISpiderChartListener cl) {
		this.chartListeners.add(cl);
	}

	/** */
	public void addFloationgObject(final IFloatingObject obj) {
		this.floatingObjects.addElement(obj);
	}

	/** */
	public void addNote(final String note) {
		this.notes.addElement(note);
	}

	/** */
	public void addPlotter(final SpiderChartPlotter p) {
		this.plotters[this.plottersCount] = p;
		this.plotters[this.plottersCount].XScale = this.plotters[0].XScale;
		this.plotters[this.plottersCount].YScale = this.plotters[0].YScale;
		this.plotters[this.plottersCount].Y2Scale = this.plotters[0].Y2Scale;
		this.plottersCount += 1;
	}

	/** */
	public void addSeq(final DataSeq s) {
		this.plotters[0].addSeq(s);
	}

	/** */
	public void addTargetZone(final TargetZone zone) {
		this.targetZones.addElement(zone);
	}

	/** */
	private void autoSize() {
		if (this.layout == 0) {
			this.autoSize_LayoutRight();
		}
		if (this.layout == 1) {
			this.autoSize_LayoutTop();
		}
		if (this.layout == 2) {
			this.autoSize_LayoutBottom();
		}
	}

	/** */
	private void autoSize_LayoutBottom() {
		final int myHeight = this.getHeight();
		final int myWidth = this.getWidth();
		if (this.virtualWidth < myWidth) {
			this.virtualWidth = myWidth;
		}
		if (this.virtualHeight < myHeight) {
			this.virtualHeight = myHeight;
		}
		this.plotters[0].visibleWidth = (int) (myWidth * (1.0D - (this.leftMargin + this.rightMargin)));
		this.plotters[0].visibleHeight = (int) (myHeight
				* (1.0D - (this.topMargin + this.bottomMargin + this.legendMargin)));

		this.plotters[0].x = (int) (myWidth * this.leftMargin);
		this.plotters[0].y = (int) (myHeight * this.topMargin);
		this.plotters[0].width = this.virtualWidth - (myWidth - this.plotters[0].visibleWidth);
		this.plotters[0].height = this.virtualHeight - (myHeight - this.plotters[0].visibleHeight);

		this.title.x = 0;
		this.title.y = 0;
		this.title.height = (int) (myHeight * this.topMargin);
		this.title.width = myWidth;
		if (this.legend != null) {
			this.legend.x = (int) (myWidth * this.leftMargin);
			this.legend.width = (int) (myWidth * (1.0D - (this.leftMargin + this.leftMargin)));
			this.legend.y = (int) (myHeight * (1.0D - this.legendMargin));
			this.legend.height = (int) (myHeight * this.legendMargin);
		}
		this.setPlotterSize();
	}

	/** */
	private void autoSize_LayoutRight() {
		final int myHeight = this.getHeight();
		final int myWidth = this.getWidth();
		if (this.virtualWidth < myWidth) {
			this.virtualWidth = myWidth;
		}
		if (this.virtualHeight < myHeight) {
			this.virtualHeight = myHeight;
		}
		this.plotters[0].visibleWidth = (int) (myWidth * (1.0D - (this.legendMargin + this.leftMargin)));
		this.plotters[0].visibleHeight = (int) (myHeight * (1.0D - (this.topMargin + this.bottomMargin)));

		this.plotters[0].x = (int) (myWidth * this.leftMargin);
		this.plotters[0].y = (int) (myHeight * this.topMargin);
		this.plotters[0].width = this.virtualWidth - (myWidth - this.plotters[0].visibleWidth);
		this.plotters[0].height = this.virtualHeight - (myHeight - this.plotters[0].visibleHeight);

		this.title.x = 0;
		this.title.y = 0;
		this.title.height = (int) (myHeight * this.topMargin);
		this.title.width = myWidth;
		if (this.legend != null) {
			this.legend.x = (int) (myWidth * (1.0D - this.legendMargin));
			this.legend.width = (int) (myWidth * this.legendMargin);
			this.legend.y = (int) (myHeight * this.topMargin);
			this.legend.height = (int) (myHeight * 0.5D);
		}
		this.setPlotterSize();
	}

	/** */
	private void autoSize_LayoutTop() {
		final int myHeight = this.getHeight();
		final int myWidth = this.getWidth();
		if (this.virtualWidth < myWidth) {
			this.virtualWidth = myWidth;
		}
		if (this.virtualHeight < myHeight) {
			this.virtualHeight = myHeight;
		}
		this.plotters[0].visibleWidth = (int) (myWidth * (1.0D - (this.leftMargin + this.rightMargin)));
		this.plotters[0].visibleHeight = (int) (myHeight
				* (1.0D - (this.topMargin + this.legendMargin + this.bottomMargin)));

		this.plotters[0].x = (int) (myWidth * this.leftMargin);
		this.plotters[0].y = (int) (myHeight * (this.topMargin + this.legendMargin));
		this.plotters[0].width = this.virtualWidth - (myWidth - this.plotters[0].visibleWidth);
		this.plotters[0].height = this.virtualHeight - (myHeight - this.plotters[0].visibleHeight);

		this.title.x = 0;
		this.title.y = 0;
		this.title.height = (int) (myHeight * this.topMargin);
		this.title.width = myWidth;
		if (this.legend != null) {
			this.legend.x = (int) (myWidth * this.leftMargin);
			this.legend.width = (int) (myWidth * (1.0D - (this.leftMargin + this.rightMargin)));
			this.legend.y = (int) (myHeight * this.topMargin);
			this.legend.height = (int) (myHeight * this.legendMargin);
		}
		this.setPlotterSize();
	}

	/** */
	public void dispose() {
		for (int i = 0; i < this.plottersCount; i++) {
			if (this.plotters[i] != null) {
				for (int j = 0; j < this.plotters[i].getSeriesCount(); j++) {
					if (this.plotters[i].getSerie(j) instanceof LineDataSeq) {
						final LineDataSeq lSerie = (LineDataSeq) this.plotters[i].getSerie(j);
						if (lSerie.icon != null) {
							lSerie.icon.dispose();
						}
					}
				}
			}
		}
		if (this.chartImage != null) {
			this.chartImage.dispose();
		}
		if (this.finalImage != null) {
			this.finalImage.dispose();
		}
		if (this.backTmpImage != null) {
			this.backTmpImage.dispose();
		}
		if (this.backImage != null) {
			this.backImage.dispose();
		}
		this.stopWorker();
	}

	/** */
	private void drawBackImage(final SpiderChartGraphics g) {
		final int ImageW = this.backImage.getWidth();
		final int ImageH = this.backImage.getHeight();
		if ((ImageW == -1) || (ImageH == -1)) {
			return;
		}
		for (int j = 0; j <= this.virtualWidth; j += ImageW) {
			for (int i = 0; i <= this.virtualHeight; i += ImageH) {
				g.drawImage(this.backImage, j, i);
			}
		}
	}

	/** */
	public int getHeight() {
		return this.height;
	}

	/** */
	public TargetZone[] getTargetZones() {
		final TargetZone[] a = new TargetZone[this.targetZones.size()];
		for (int i = 0; i < a.length; i++) {
			a[i] = this.targetZones.elementAt(i);
		}
		return a;
	}

	/** */
	public int getWidth() {
		return this.width;
	}

	/** */
	public void mouseClick() {
		if (((this.selectedSerie != null) && (this.selectedSeriePoint >= 0)) || (this.selectedLabel != null)) {
			this.triggerEvent(5);
			return;
		}
		this.triggerEvent(6);
	}

	/** */
	public void mouseMoved(final int eX, final int eY) {
		if (this.plotters[0] == null) {
			return;
		}
		Object previousSelectedObject = this.selectedSerie;
		if ((this.selectedSerie == null) && (this.selectedLabel != null)) {
			previousSelectedObject = this.selectedLabel;
		}
		this.selectedSerie = null;
		this.selectedLabel = null;
		this.selectedSeriePoint = -1;
		if (this.activateSelection) {
			for (final SpiderChartPlotter plotter : this.plotters) {
				if (plotter == null) {
					break;
				}
			}
		}
		if ((previousSelectedObject != null) && (this.selectedSerie == null) && (this.selectedLabel == null)) {
			this.triggerEvent(3);
		}
	}

	/** */
	public void paint(final SpiderChartGraphics pg) {
		this.floatingObjects.removeAllElements();
		this.chartHotAreas.removeAllElements();

		System.currentTimeMillis();
		if ((this.plotters[0] == null) || (this.plottersCount <= 0)) {
			pg.setColor(SWTGraphicsSupplier.getColor(SpiderChartColor.RED));
			pg.drawString("Error: No plotters have been defined", 30, 30);
			return;
		}
		for (int j = 0; j < this.plottersCount; j++) {
			if ((this.plottersCount > 1) && !this.plotters[j].getCombinable()) {
				pg.setColor(SWTGraphicsSupplier.getColor(SpiderChartColor.RED));
				pg.drawString("Error: These plotters cannot be combined", 30, 30);
				return;
			}
		}
		SpiderChartGraphics gScroll = pg;
		SpiderChartGraphics gBack = pg;
		SpiderChartGraphics g = pg;
		if ((this.lastWidth != this.width) || (this.lastHeight != this.height)) {
			this.repaintAll = true;
			this.lastWidth = this.width;
			this.lastHeight = this.height;
		}
		if (!this.withScroll) {
			this.repaintAlways = true;
		}
		if (this.repaintAlways) {
			this.repaintAll = true;
		}
		if (this.autoSize) {
			this.autoSize();
		}
		try {
			if (this.doubleBuffering && (this.repaintAll || (this.finalImage == null))) {
				if (this.finalImage != null) {
					this.finalImage.dispose();
				}
				this.finalImage = SWTGraphicsSupplier.createImage(this.getWidth(), this.getHeight());
			}
		} catch (final Exception e) {
		}
		if (this.finalImage != null) {
			g = this.finalImage.getGraphics();
			gScroll = g;
			gBack = g;
		}
		if (this.withScroll) {
			if (this.repaintAll || (this.chartImage == null)) {
				if (this.chartImage != null) {
					this.chartImage.dispose();
				}
				this.chartImage = SWTGraphicsSupplier.createImage(this.virtualWidth, this.virtualHeight);
			}
			gScroll = this.chartImage.getGraphics();
			if (this.repaintAll || (this.backTmpImage == null)) {
				if (this.backTmpImage != null) {
					this.backTmpImage.dispose();
				}
				this.backTmpImage = SWTGraphicsSupplier.createImage(this.virtualWidth, this.virtualHeight);
			}
			gBack = this.backTmpImage.getGraphics();
		}
		if (this.repaintAll) {
			if ((this.back != null) && (this.backgroundCanvasColor != null)) {
				this.back.draw(gBack, this.backgroundCanvasColor, 0, 0, this.virtualWidth, this.virtualHeight);
			}
			if (this.backImage != null) {
				this.drawBackImage(gBack);
			}
		}
		if (this.withScroll && ((this.backImage != null) || (this.back != null))) {
			if (this.repaintAll) {
				gScroll.drawImage(this.backTmpImage, 0, 0, this.virtualWidth, this.virtualHeight, 0, 0,
						this.virtualWidth, this.virtualHeight);
			}
			g.drawImage(this.backTmpImage, 0, 0, this.getWidth(), this.getHeight(), this.offsetX, this.offsetY,
					this.getWidth() + this.offsetX, this.getHeight() + this.offsetY);
		}
		if (this.plotters[0].XScale != null) {
			this.plotters[0].XScale.screenMax = this.plotters[0].x + this.plotters[0].width;
			this.plotters[0].XScale.screenMaxMargin = (int) (this.plotters[0].XScale.screenMax
					* (1.0D - this.axisMargin));
			if (this.fullXAxis) {
				this.plotters[0].XScale.screenMaxMargin = this.plotters[0].XScale.screenMax;
			}
			this.plotters[0].XScale.screenMin = this.plotters[0].x;
		}
		if (this.plotters[0].YScale != null) {
			this.plotters[0].YScale.screenMax = this.plotters[0].y + this.plotters[0].height;
			this.plotters[0].YScale.screenMaxMargin = (int) (this.plotters[0].YScale.screenMax
					* (1.0D - this.axisMargin));
			this.plotters[0].YScale.screenMin = this.plotters[0].y;
		}
		if (this.plotters[0].Y2Scale != null) {
			this.plotters[0].Y2Scale.screenMax = (this.plotters[0].y + this.plotters[0].height)
					- this.plotters[0].depth;
			this.plotters[0].Y2Scale.screenMaxMargin = (int) (this.plotters[0].Y2Scale.screenMax
					* (1.0D - this.axisMargin));
			this.plotters[0].Y2Scale.screenMin = this.plotters[0].y - this.plotters[0].depth;
		}
		if (this.repaintAll) {
			final int plotterBackWidth = this.plotters[0].width;
			final int plotterBackHeight = this.plotters[0].height;
			this.plotters[0].plotBackground(gScroll, plotterBackWidth, plotterBackHeight, this.offsetX, this.offsetY);
		}
		this.title.chart = this;
		this.title.draw(g);
		this.paintTargetZones(g, true);
		if ((d() != 1) && (this.legend == null)) {
			this.legend = new SpiderChartLegend();
		}
		if (this.legend != null) {
			this.legend.chart = this;
			this.legend.draw(g);
		}
		if (this.repaintAll) {
			for (int i = 0; i < this.plottersCount; i++) {
				this.plotters[i].chart = this;
				this.plotters[i].plot(gScroll);
			}
		}
		if (this.border != null) {
			this.border.drawRect(g, 0, 0, this.getWidth() - 1, this.getHeight() - 1);
		}
		if (this.chartImage != null) {
			final int x1 = this.plotters[0].x;

			final int x2 = this.plotters[0].x + this.plotters[0].visibleWidth;

			final int y1 = this.plotters[0].y - this.plotters[0].depth;

			final int y2 = (this.plotters[0].y - this.plotters[0].depth) + this.plotters[0].visibleHeight;

			g.drawImage(this.chartImage, x1, y1, x2, y2, x1 + this.offsetX, y1 + this.offsetY, x2 + this.offsetX,
					y2 + this.offsetY);
		}
		if (this.chartListeners != null) {
			for (int i = 0; i < this.chartListeners.size(); i++) {
				this.chartListeners.get(i).paintUserExit(this, g);
			}
		}
		this.paintTargetZones(g, false);

		this.paintNotes(g);

		this.paintTips(g);
		if (this.finalImage != null) {
			pg.drawImage(this.finalImage, 0, 0, this.getWidth(), this.getHeight(), 0, 0, this.getWidth(),
					this.getHeight());
		}
		this.repaintAll = false;
		if (gScroll != pg) {
			gScroll.dispose();
		}
		if (gBack != pg) {
			gBack.dispose();
		}
		if (g != pg) {
			g.dispose();
		}
	}

	/** */
	private void paintNotes(final SpiderChartGraphics g) {
		if (g == null) {
			return;
		}
		for (int i = 0; i < this.notes.size(); i++) {
			final SpiderChartLabel label = new SpiderChartLabel(this.notes.elementAt(i), "", false, false);
			label.initialize(g, this);
			label.paint(g, 0, 0, 0, 0);
		}
	}

	/** */
	protected void paintTargetZones(final SpiderChartGraphics g, final boolean back) {
		g.setFont(SWTGraphicsSupplier.getFont("Verdana", SpiderChartFont.BOLD, 10));
		for (int i = 0; i < this.targetZones.size(); i++) {
			final TargetZone z = this.targetZones.elementAt(i);
			z.chart = this;
		}
	}

	/** */
	private void paintTips(final SpiderChartGraphics g) {
		// TODO (AKM) To be implemented properly: Tips Functionality
		this.showingTip = false;

		if (this.showTips && (this.selectedLabel != null)) {
			this.selectedLabel.getTip();
		}
	}

	/** */
	public void placeFloatingObject(final IFloatingObject obj) {
	}

	/** */
	public void removeAllChartListener() {
		this.chartListeners.clear();
	}

	/** */
	public void removeChartListener(final ISpiderChartListener cl) {
		this.chartListeners.remove(cl);
	}

	/** */
	public void removeNotes() {
		this.notes.removeAllElements();
	}

	/** */
	public void removePlotters() {
		for (int i = 0; i < this.plottersCount; i++) {
			this.plotters[i] = null;
		}
		this.plottersCount = 0;
	}

	/** */
	public void removeTargetZones() {
		this.targetZones.removeAllElements();
	}

	/** */
	protected void resetChart(final SpiderChartTitle t, final SpiderChartPlotter p, final SpiderChartAxis X,
			final SpiderChartAxis Y) {
		this.plottersCount = 0;
		this.plotters = new SpiderChartPlotter[10];
		this.legend = null;
		this.title = null;
		this.border = null;
		this.back = null;
		this.selectedSerie = null;
		this.selectedSeriePoint = -1;
		this.repaintAll = true;
		this.removeTargetZones();
		this.removeNotes();
		this.chartHotAreas.removeAllElements();
		this.floatingObjects.removeAllElements();

		this.plotters[0] = p;
		if ((X != null) && (this.plotters[0] != null)) {
			this.plotters[0].XScale = X.scale;
			X.plot = this.plotters[0];
		}
		if ((Y != null) && (this.plotters[0] != null)) {
			this.plotters[0].YScale = Y.scale;
			Y.plot = this.plotters[0];
		}
		this.title = t;
		if (this.title == null) {
			this.title = new SpiderChartTitle();
			this.title.text = "";
		}
		this.plottersCount = 1;
	}

	/** */
	public void setHeight(final int h) {
		if (h > this.minimumHeight) {
			this.height = h;
		}
	}

	/** */
	public void setMinimumSize(final int w, final int h) {
		this.minimumWidth = w;
		this.minimumHeight = h;
	}

	/** */
	private void setPlotterSize() {
		for (int i = 1; i < this.plottersCount; i++) {
			this.plotters[i].x = this.plotters[0].x;
			this.plotters[i].y = this.plotters[0].y;
			this.plotters[i].width = this.plotters[0].width;
			this.plotters[i].height = this.plotters[0].height;
		}
	}

	/** */
	public void setSize(final int w, final int h) {
		this.setWidth(w);
		this.setHeight(h);
	}

	/** */
	public void setWidth(final int w) {
		if (w > this.minimumWidth) {
			this.width = w;
		}
	}

	/** */
	public void startWorker() {
		this.stopped = false;

		this.deamon = new SpiderChartWorker(null);
		this.deamon.chart = this;
		new Thread(this.deamon).start();
	}

	/** */
	public void stopWorker() {
		this.stopped = true;
		if (this.deamon != null) {
			this.deamon.stop = true;
		}
		this.deamon = null;
	}

	/** */
	private void triggerEvent(final int event) {
		for (int i = 0; i < this.chartListeners.size(); i++) {
			this.chartListeners.get(i).chartEvent(this, event);
		}
	}
}
