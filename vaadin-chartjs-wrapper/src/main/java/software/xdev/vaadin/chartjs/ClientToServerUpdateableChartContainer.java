/*
 * Copyright © 2025 XDEV Software (https://xdev.software)
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
 */
package software.xdev.vaadin.chartjs;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.dom.Element;

import software.xdev.vaadin.chartjs.resources.js.src.ChartClientToServerUpdater;


/**
 * Diagram that is updated by the client/browser side when it becomes visible/added to the DOM.
 * <p>
 * This can be used in e.g. Grids with renderers where not every Chart might be rendered instantly due to lazy loading.
 * </p>
 *
 * @implNote internal/original: TIMELINE-709/2020-09-01
 */
@JsModule(ChartClientToServerUpdater.LOCATION)
@SuppressWarnings("java:S110") // Caused by Vaadin design
public abstract class ClientToServerUpdateableChartContainer extends ChartContainer
{
	@ClientCallable
	public void updateFromClient()
	{
		this.update();
	}
	
	/**
	 * Tries to run a client-side update via a script-tag.
	 * <p>
	 * When the script was executed once it's automatically removed.
	 * </p>
	 */
	public ClientToServerUpdateableChartContainer scheduleUpdateViaScript()
	{
		final String js = this.wrapJsFunc.apply(
			this,
			String.format(
				ChartClientToServerUpdater.CHECK_IF_EXISTS_ON_CLIENT_AND_UPDATE_SERVER,
				this.getChartJSDivId()));
		
		final Element el = new Element("script");
		el.setAttribute("type", "text/javascript");
		el.setText(js);
		
		this.getElement().appendChild(el);
		return this;
	}
	
	/**
	 * Builds and adds a chart.
	 */
	protected abstract void update();
}
