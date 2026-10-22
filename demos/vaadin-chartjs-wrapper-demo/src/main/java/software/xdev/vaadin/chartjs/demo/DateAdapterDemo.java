package software.xdev.vaadin.chartjs.demo;

import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import software.xdev.chartjs.model.charts.LineChart;
import software.xdev.chartjs.model.data.LineData;
import software.xdev.chartjs.model.dataset.LineDataset;
import software.xdev.chartjs.model.javascript.JavaScriptFunction;
import software.xdev.chartjs.model.options.LineOptions;
import software.xdev.chartjs.model.options.scale.Scales;
import software.xdev.chartjs.model.options.scale.cartesian.AbstractCartesianScaleOptions;
import software.xdev.chartjs.model.options.scale.cartesian.time.TimeScaleOptions;
import software.xdev.vaadin.chartjs.ChartContainer;


@NpmPackage(value = "date-fns", version = "4.1.0")
@JsModule("./lib/chartjs-adapter-date-fns.js")
@Route(DateAdapterDemo.NAV)
public class DateAdapterDemo extends VerticalLayout
{
	public static final String NAV = "/date-adapter";
	
	public DateAdapterDemo()
	{
		this.setSizeFull();
		final ChartContainer chart = new ChartContainer();
		chart.setHeight("50%");
		
		this.add(chart);
		
		chart.showChart(new LineChart(new LineData()
			.addLabels(
				"2020-01-01",
				"2020-01-02",
				"2020-01-03")
			.addDataset(new LineDataset()
				.setBackgroundColor("#c02222")
				.setLabel("A")
				.addData(5)
				.addData(1)
				.addData(2))
			.addDataset(new LineDataset()
				.setBackgroundColor("orange")
				.setLabel("B")
				.addData(1)
				.addData(2)
				.addData(4)))
			.setOptions(new LineOptions()
				.setResponsive(true)
				.setMaintainAspectRatio(false)
				.setScales(new Scales()
					.addScale(
						Scales.ScaleAxis.X, new TimeScaleOptions()
							.setTime(new TimeScaleOptions.TimeOptions()
								.setParser(new JavaScriptFunction("s => window.dateFns.parseISO(s)"))
								.setTooltipFormat("eee, dd.MM.yyyy")
								.setUnit("day"))
							.setTitle(new AbstractCartesianScaleOptions.Title()
								.setDisplay(true)
								.setText("Date"))
					)))
			.toJson());
	}
}
