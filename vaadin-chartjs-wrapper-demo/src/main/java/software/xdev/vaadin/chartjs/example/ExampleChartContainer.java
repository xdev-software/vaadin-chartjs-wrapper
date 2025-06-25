package software.xdev.vaadin.chartjs.example;

import software.xdev.chartjs.model.charts.BarChart;
import software.xdev.chartjs.model.data.BarData;
import software.xdev.chartjs.model.dataset.BarDataset;
import software.xdev.chartjs.model.options.BarOptions;
import software.xdev.chartjs.model.options.Plugins;
import software.xdev.chartjs.model.options.Title;
import software.xdev.vaadin.chartjs.ChartContainer;


public class ExampleChartContainer extends ChartContainer
{
	public void show()
	{
		final BarData barData = new BarData()
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
				.addData(5));
		
		final BarChart bc = new BarChart(barData)
			.setOptions(new BarOptions()
				.setResponsive(true)
				.setPlugins(new Plugins()
					.setTitle(new Title()
						.setText("Age")
						.setDisplay(true)))
				.setMaintainAspectRatio(false)
				.setResponsive(true));
		
		this.displayChart(bc.toJson());
	}
}
