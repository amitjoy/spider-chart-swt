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

/**
 * Represents a point with the given x and y-coordinate values
 * 
 * @author AMIT KUMAR MONDAL
 *
 */
public final class Point {

	/** X coordinate */
	private final int x;

	/** Y coordinate */
	private final int y;

	/** Constructor */
	public Point(final int x1, final int y1) {
		this.x = x1;
		this.y = y1;
	}

	/** */
	public int getX() {
		return this.x;
	}

	/** */
	public int getY() {
		return this.y;
	}
}
