package software.xdev.vaadin.chartjs.demo;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import software.xdev.vaadin.chartjs.ChartContainer;


@Route(MinimalisticDemo.NAV)
public class MinimalisticDemo extends VerticalLayout
{
	public static final String NAV = "/minimalistic";
	
	public MinimalisticDemo()
	{
		this.setSizeFull();
		final ChartContainer chart = new ChartContainer();
		chart.setHeight("50%");
		
		this.add(chart);
		
		chart.showChart(
			"{\"data\":{\"labels\":[\"A\",\"B\"],\"datasets\":[{\"data\":[1,2],\"label\":\"X\"}]},\"type\":\"bar\"}");
	}
}
