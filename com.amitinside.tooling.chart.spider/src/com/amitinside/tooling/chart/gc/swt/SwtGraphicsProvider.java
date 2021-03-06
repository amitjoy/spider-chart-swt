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

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Display;

/**
 * Represents a factory for useful SWT operations
 *
 * @author AMIT KUMAR MONDAL
 *
 */
public final class SwtGraphicsProvider {

	/** SWT Device object */
	private static Device display = null;

	/** returns the display instance */
	public synchronized static Device getDisplay() {
		if (display == null) {
			display = Display.getCurrent();
		}
		return display;
	}

	/** Starts the provided execution in UI thread */
	public static void startUIThread(final Runnable r) {
		((Display) getDisplay()).syncExec(r);
	}

	/** Constructor */
	private SwtGraphicsProvider() {
	}
}
