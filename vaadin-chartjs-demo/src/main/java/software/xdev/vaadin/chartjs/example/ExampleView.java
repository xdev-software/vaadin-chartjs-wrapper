package software.xdev.vaadin.chartjs.example;

import java.util.concurrent.CompletableFuture;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;

import software.xdev.vaadin.chartjs.resources.js.src.ChartThemeManager;


@Route("")
public class ExampleView extends VerticalLayout
{
	private final ExampleChartContainer chart = new ExampleChartContainer();
	
	public ExampleView()
	{
		this.setSizeFull();
		this.chart.setHeight("50%");
		
		this.chart.setId("test");
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
}
