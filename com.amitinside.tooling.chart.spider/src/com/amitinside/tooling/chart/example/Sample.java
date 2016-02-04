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
package com.amitinside.tooling.chart.example;

import static com.amitinside.tooling.chart.LineStyle.NORMAL_LINE;
import static com.amitinside.tooling.chart.gc.SWTGraphicsSupplier.getColor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.amitinside.tooling.chart.LineStyle;
import com.amitinside.tooling.chart.api.ISpiderChartDrawable;
import com.amitinside.tooling.chart.builder.SpiderChartBuilder;

public final class Sample {

	private static void buildSpiderChart(final Shell shell) {

		final ISpiderChartDrawable iphoneData = new IPhone();
		final ISpiderChartDrawable nexusData = new Nexus();

		SpiderChartBuilder.config(shell, settings -> {

			settings.title(title -> title.setText("Smartphone Comparison")).legend(legend -> {
				legend.addItem("iPhone 6", LineStyle.of(1, getColor(iphoneData.areaColor()), NORMAL_LINE));
				legend.addItem("Nexus 6", LineStyle.of(1, getColor(nexusData.areaColor()), NORMAL_LINE));
			}).plotter(plotter -> {
				final double[] maxScales = { 5, 5, 5, 5, 5 };
				final double[] minScales = { 0, 0, 0, 0, 0 };
				final String[] axes = { "Battery", "Camera", "Display", "Memory", "Brand" };

				plotter.maxScaleFactors = maxScales;
				plotter.minScaleFactors = minScales;
				plotter.axesFactors = axes;
			});

		}).viewer(chart -> {
			chart.data(firstData -> firstData.inject(iphoneData)).data(secondData -> secondData.inject(nexusData));
		});
	}

	public static void main(final String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display, SWT.DIALOG_TRIM);
		shell.setSize(800, 750);

		buildSpiderChart(shell);

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

}
