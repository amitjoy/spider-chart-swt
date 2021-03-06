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
package com.amitinside.tooling.chart.builder;

import static com.amitinside.tooling.chart.util.ChartUtil.toDoublePrimitiveArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Used to configure the axes for the spider chart diagram
 *
 * @author AMIT KUMAR MONDAL
 *
 */
public final class AxesConfigurer {

	public static class Builder {

		/** Maximum Scales */
		private final List<Double> maxScales;

		/** Minimum Scales */
		private final List<Double> minScales;

		/** Scaling Labels */
		private final List<String> scalesNames;

		/** Scaling Label Formats */
		private final List<Object> scalingLabelFormats;

		/** Constructor */
		public Builder() {
			this.maxScales = new ArrayList<>();
			this.minScales = new ArrayList<>();
			this.scalesNames = new ArrayList<>();
			this.scalingLabelFormats = new ArrayList<>();
		}

		/**
		 * Adds a new Axis to the Spider Chart
		 *
		 * @param name
		 *            Name of the axis
		 * @param value
		 *            Currently only Enum class object needs to be provided to
		 *            use its constants as labels
		 * @return the Builder object for method chaining
		 */
		public <E extends Enum<E>> Builder addAxis(final String name, final Class<E> value) {
			final double[] doubleValues = new double[value.getEnumConstants().length];
			int i = 0;
			for (final Enum<E> enumVal : value.getEnumConstants()) {
				doubleValues[i++] = enumVal.ordinal();
			}
			Arrays.sort(doubleValues);
			this.maxScales.add(doubleValues[doubleValues.length - 1] + 1);
			this.minScales.add(doubleValues[0]);
			this.scalesNames.add(name);
			this.scalingLabelFormats.add(value);
			return this;
		}

		/**
		 * Adds a new Axis to the Spider Chart
		 *
		 * @param name
		 *            name of the axis
		 * @param maxScale
		 *            maximum scale of axis
		 * @param minScale
		 *            minimum scale of axis
		 * @return the Builder object for method chaining
		 */
		public Builder addAxis(final String name, final double maxScale, final double minScale) {
			this.maxScales.add(maxScale);
			this.minScales.add(minScale);
			this.scalesNames.add(name);
			this.scalingLabelFormats.add("#.#");
			return this;
		}

		/**
		 * Adds a new Axis to the Spider Chart
		 *
		 * @param name
		 *            name of the axis
		 * @param maxScale
		 *            maximum scale of axis
		 * @param minScale
		 *            minimum scale of axis
		 * @param scalingLabelFormat
		 *            the scaling label format
		 * @return the Builder object for method chaining
		 */
		public Builder addAxis(final String name, final double maxScale, final double minScale,
				final Object scalingLabelFormat) {
			this.scalingLabelFormats.add(scalingLabelFormat);
			this.maxScales.add(maxScale);
			this.minScales.add(minScale);
			this.scalesNames.add(name);
			return this;
		}

		/** Builder */
		public AxesConfigurer build() {
			return new AxesConfigurer(this.maxScales, this.minScales, this.scalesNames, this.scalingLabelFormats);
		}

	}

	/** */
	private final List<Double> maxScales;
	/** */
	private final List<Double> minScales;
	/** */
	private final List<String> scalesNames;

	/** */
	private final List<Object> scalingLabelFormats;

	/** Constructor */
	private AxesConfigurer(final List<Double> maxScales, final List<Double> minScales, final List<String> scalesNames,
			final List<Object> scalingLabelFormats) {
		super();
		this.maxScales = maxScales;
		this.minScales = minScales;
		this.scalesNames = scalesNames;
		this.scalingLabelFormats = scalingLabelFormats;
	}

	/** getter for the axes names */
	public String[] axesNames() {
		final String[] axesNames = new String[this.scalesNames.size()];
		return this.scalesNames.toArray(axesNames);
	}

	/** getter for the scaling label formats */
	public Object[] axesScalingLabelFormats() {
		return this.scalingLabelFormats.toArray();
	}

	/** getter for the max scales */
	public double[] maxScales() {
		final Double[] maxScales = new Double[this.maxScales.size()];
		return toDoublePrimitiveArray(this.maxScales.toArray(maxScales));
	}

	/** getter for min scales */
	public double[] minScales() {
		final Double[] minScales = new Double[this.minScales.size()];
		return toDoublePrimitiveArray(this.minScales.toArray(minScales));
	}

}
