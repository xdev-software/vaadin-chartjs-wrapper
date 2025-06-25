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

import java.util.Objects;
import java.util.UUID;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.theme.lumo.LumoUtility;

import software.xdev.vaadin.chartjs.loading.DefaultLoadingErrorComponent;
import software.xdev.vaadin.chartjs.loading.DefaultLoadingLoadComponent;
import software.xdev.vaadin.chartjs.loading.LoadingErrorComponent;
import software.xdev.vaadin.chartjs.resources.js.JSLibs;
import software.xdev.vaadin.chartjs.resources.js.src.ChartControlFunc;
import software.xdev.vaadin.chartjs.resources.js.src.ChartThemeManager;


@JsModule(JSLibs.CHARTJS)
@JsModule(ChartThemeManager.LOCATION)
@JsModule(ChartControlFunc.LOCATION)
@CssImport(ChartContainerStyles.LOCATION)
public abstract class ChartContainer extends Div implements ChartCom
{
	protected Component loading = new DefaultLoadingLoadComponent();
	protected Component error = new DefaultLoadingErrorComponent();
	protected Div chartJSDiv = new Div();
	
	protected Icon icoProblemsIndicator = VaadinIcon.WARNING.create();
	
	protected ChartContainer()
	{
		this.loading.addClassName(ChartContainerStyles.LOADING_COMPONENT_CLASS);
		this.error.addClassName(ChartContainerStyles.ERROR_COMPONENT_CLASS);
		this.icoProblemsIndicator.addClassName(ChartContainerStyles.PROBLEM_INDICATOR_CLASS);
		
		this.chartJSDiv.addClassNames(
			ChartContainerStyles.DIV_CLASS,
			// Otherwise temporary scrollbars might be visible
			LumoUtility.Overflow.HIDDEN);
		this.chartJSDiv.setId(this.createChartJSDivId());
		this.chartJSDiv.setHeightFull();
		
		this.addClassName(ChartContainerStyles.ROOT_CLASS);
		this.add(this.chartJSDiv);
		this.setSizeFull();
	}
	
	protected String createChartJSDivId()
	{
		return "chartjsdiv" + UUID.randomUUID();
	}
	
	public String getChartJSDivId()
	{
		return this.chartJSDiv.getId().orElseThrow();
	}
	
	@Override
	public void showLoading()
	{
		this.clear();
		this.add(this.loading);
	}
	
	@Override
	public void showFailed(final String errorMsg)
	{
		this.clear();
		this.add(this.error);
		if(this.error instanceof final LoadingErrorComponent loadingErrorComp)
		{
			loadingErrorComp.setVisible(true, errorMsg);
		}
	}
	
	@Override
	public void clear()
	{
		this.clear(false);
	}
	
	protected void clear(final boolean forDisplayingChart)
	{
		// Ignored on forDisplayingChart so that no "flickering" of the chart occurs
		if(!forDisplayingChart)
		{
			this.tryDestroyChart();
			this.chartJSDiv.setVisible(false);
			this.chartJSDiv.removeAll();
		}
		this.remove(this.getChildren()
			.filter(c -> !Objects.equals(c, this.chartJSDiv))
			.toList());
	}
	
	public void showProblemsIndicator(final String problemsText)
	{
		this.add(this.icoProblemsIndicator);
		Tooltip.forComponent(this.icoProblemsIndicator)
			.withText(problemsText);
	}
	
	public void showChart(final String payloadJson)
	{
		Objects.requireNonNull(payloadJson);
		
		this.clear(true);
		this.chartJSDiv.setVisible(true);
		
		final String thisid = this.getChartJSDivId();
		this.executeJS(String.format(ChartControlFunc.BUILD_CHART, thisid, thisid + "Canvas", payloadJson));
	}
	
	protected void tryDestroyChart()
	{
		final String thisid = this.getChartJSDivId();
		this.executeJS(String.format(ChartControlFunc.DESTROY_CHART, thisid + "Canvas"));
	}
	
	protected void executeJS(final String js)
	{
		this.getElement().executeJs(js);
	}
	
	// region Getter + Setter
	public Component getLoading()
	{
		return this.loading;
	}
	
	public void setLoading(final Component loading)
	{
		this.loading = loading;
	}
	
	public Component getError()
	{
		return this.error;
	}
	
	public void setError(final Component error)
	{
		this.error = error;
	}
	
	public Div getChartJSDiv()
	{
		return this.chartJSDiv;
	}
	
	public void setChartJSDiv(final Div chartJSDiv)
	{
		this.chartJSDiv = chartJSDiv;
	}
	
	public Icon getIcoProblemsIndicator()
	{
		return this.icoProblemsIndicator;
	}
	
	public void setIcoProblemsIndicator(final Icon icoProblemsIndicator)
	{
		this.icoProblemsIndicator = icoProblemsIndicator;
	}
	// endregion
}
