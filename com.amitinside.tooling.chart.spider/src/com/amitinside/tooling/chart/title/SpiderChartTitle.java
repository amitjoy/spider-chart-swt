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
package com.amitinside.tooling.chart.title;

import com.amitinside.tooling.chart.SpiderChartComponent;
import com.amitinside.tooling.chart.gc.SWTGraphicsSupplier;
import com.amitinside.tooling.chart.gc.SpiderChartColor;
import com.amitinside.tooling.chart.gc.SpiderChartFont;
import com.amitinside.tooling.chart.gc.SpiderChartGraphics;

public final class SpiderChartTitle extends SpiderChartComponent {

	/** Title Color */
	public SpiderChartColor color = SWTGraphicsSupplier.getColor(SpiderChartColor.BLUE);
	/** Title Font */
	public SpiderChartFont font = SWTGraphicsSupplier.getFont("Verdana", SpiderChartFont.PLAIN, 14);
	/** Title Text */
	public String text;

	/** Constructor */
	public SpiderChartTitle() {
	}

	/** */
	public void draw(final SpiderChartGraphics g) {
		g.setColor(this.color);
		g.setFont(this.font);
		final String[] txt = new String[3];

		txt[0] = this.text;
		for (int i = 0; i < 3; i++) {
			if (txt[i] != null) {
				int toCenterX = (this.width - g.getFontWidth(null, txt[i])) / 2;
				if (toCenterX < 0) {
					toCenterX = 0;
				}
				int toCenterY = (this.height - g.getFontHeight()) / 2;
				if (toCenterY < 0) {
					toCenterY = 0;
				}
				g.drawText(txt[i], this.x + toCenterX, this.y + toCenterY + (g.getFontHeight() * (i + 1)));
			}
		}
	}
}