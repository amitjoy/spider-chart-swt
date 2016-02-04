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
package com.amitinside.tooling.chart.gc;

import java.util.Vector;

public class Polygon extends Vector<Point> {

	/** */
	private static final long serialVersionUID = 1L;

	/** */
	public void addPoint(final int x, final int y) {
		super.addElement(new Point(x, y));
	}

	/** */
	public boolean contains(final int x, final int y) {
		return this.contains1(x, y);
	}

	/** */
	private boolean contains1(final int x, final int y) {
		int i = 0;
		int j = 0;
		boolean c = false;

		i = 0;
		for (j = this.size() - 1; i < this.size(); j = i++) {
			if (((this.getY(i) <= y) && (y < this.getY(j))) || ((this.getY(j) <= y) && (y < this.getY(i))
					&& (x < ((((this.getX(j) - this.getX(i)) * (y - this.getY(i))) / (this.getY(j) - this.getY(i)))
							+ this.getX(i))))) {
				c = !c;
			}
		}
		return c;
	}

	/** */
	public int getX(final int i) {
		return super.elementAt(i).x;
	}

	/** */
	public int getY(final int i) {
		return super.elementAt(i).y;
	}
}
