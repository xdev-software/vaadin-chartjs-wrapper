package software.xdev.vaadin.chartjs.demo;

import java.util.concurrent.CompletableFuture;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;

import software.xdev.chartjs.model.charts.BarChart;
import software.xdev.chartjs.model.data.BarData;
import software.xdev.chartjs.model.dataset.BarDataset;
import software.xdev.chartjs.model.options.BarOptions;
import software.xdev.chartjs.model.options.Plugins;
import software.xdev.chartjs.model.options.Title;
import software.xdev.vaadin.chartjs.ChartContainer;
import software.xdev.vaadin.chartjs.resources.js.src.ChartThemeManager;


@Route(FunctionalityShowcaseDemo.NAV)
public class FunctionalityShowcaseDemo extends VerticalLayout
{
	public static final String NAV = "/functionality";
	
	private final DummyChartContainer chart = new DummyChartContainer();
	
	public FunctionalityShowcaseDemo()
	{
		this.setSizeFull();
		this.chart.setHeight("50%");
		
		this.add(
			this.chart,
			new Button("Run dummy load", ev -> this.runDummyLoad()),
			new HorizontalLayout(
				new Button("Load", ev -> this.chart.showLoading()),
				new Button("Show data", ev -> this.chart.show()),
				new Button(
					"Show data with problem indicator", ev -> {
					this.chart.show();
					this.chart.showProblemsIndicator("Failed to load some data because ...");
				}),
				new Button("Error", ev -> this.chart.showFailed("Something went wrong"))
			),
			new Checkbox(
				"DarkMode (requires chart rebuild)", ev -> {
				this.getElement().executeJs(
					"document.documentElement.setAttribute('theme', $0)",
					Boolean.TRUE.equals(ev.getValue()) ? Lumo.DARK : Lumo.LIGHT);
				this.getElement().executeJs(ChartThemeManager.TRY_UPDATE_CHART_JS_THEMING.formatted(true));
			}));
	}
	
	private void runDummyLoad()
	{
		this.chart.showLoading();
		
		final UI ui = UI.getCurrent();
		CompletableFuture.runAsync(() -> {
			try
			{
				Thread.sleep(1000);
			}
			catch(final InterruptedException iex)
			{
				Thread.currentThread().interrupt();
			}
			ui.access(this.chart::show);
		});
	}
	
	@Override
	protected void onAttach(final AttachEvent attachEvent)
	{
		this.runDummyLoad();
	}
	
	@SuppressWarnings("java:S110")
	public static class DummyChartContainer extends ChartContainer
	{
		public void show()
		{
			this.showChart(new BarChart(new BarData()
				.addLabels("2020", "2021", "2022", "2023")
				.addDataset(new BarDataset()
					.setBackgroundColor("#c02222")
					.setLabel("Hans")
					.addData(1)
					.addData(2)
					.addData(3)
					.addData(4))
				.addDataset(new BarDataset()
					.setBackgroundColor("orange")
					.setLabel("Franz")
					.addData(2)
					.addData(3)
					.addData(4)
					.addData(5)))
				.setOptions(new BarOptions()
					.setResponsive(true)
					.setMaintainAspectRatio(false)
					.setPlugins(new Plugins()
						.setTitle(new Title()
							.setText("Age")
							.setDisplay(true))))
				.toJson());
		}
	}
}
