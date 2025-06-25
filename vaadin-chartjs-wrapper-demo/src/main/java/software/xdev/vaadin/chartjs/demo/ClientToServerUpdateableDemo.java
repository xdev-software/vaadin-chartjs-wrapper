package software.xdev.vaadin.chartjs.demo;

import java.util.function.BiFunction;
import java.util.stream.IntStream;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

import software.xdev.chartjs.model.charts.BarChart;
import software.xdev.chartjs.model.data.BarData;
import software.xdev.chartjs.model.dataset.BarDataset;
import software.xdev.chartjs.model.enums.IndexAxis;
import software.xdev.chartjs.model.options.BarOptions;
import software.xdev.chartjs.model.options.scale.Scales;
import software.xdev.chartjs.model.options.scale.cartesian.linear.LinearScaleOptions;
import software.xdev.vaadin.chartjs.ChartContainer;
import software.xdev.vaadin.chartjs.ClientToServerUpdateableChartContainer;


@Route(ClientToServerUpdateableDemo.NAV)
public class ClientToServerUpdateableDemo extends VerticalLayout
{
	public static final String NAV = "/client2server-update";
	
	private final Grid<Integer> grid = new Grid<>();
	
	public ClientToServerUpdateableDemo()
	{
		this.setSizeFull();
		
		final Checkbox chbxUseClientToServerUpdatable =
			new Checkbox("Use " + ClientToServerUpdateableChartContainer.class.getSimpleName());
		chbxUseClientToServerUpdatable.setHelperText(
			"If you disable this the default implementation ("
				+ ChartContainer.class.getSimpleName()
				+ ") is used, which will NOT be displayed correctly");
		this.add(this.grid, chbxUseClientToServerUpdatable);
		
		this.grid.setItems(IntStream.rangeClosed(1, 1_000).boxed().toList());
		
		chbxUseClientToServerUpdatable.addValueChangeListener(ev -> {
			final BiFunction<Integer, Integer, ChartContainer> chartContainerProvider =
				Boolean.TRUE.equals(ev.getValue())
					? WorkingChartContainer::new
					: BrokenChartContainer::new;
			
			this.grid.removeAllColumns();
			this.grid.addColumn(Object::toString);
			this.grid.addColumn(new ComponentRenderer<>(value -> {
				final ChartContainer chartContainer = chartContainerProvider.apply(
					value,
					this.grid.getDataProvider().size(new Query<>()));
				// Set a fix height, otherwise it will be dynamic for each chart
				chartContainer.setHeight("6em");
				return chartContainer;
			}));
		});
		// Init
		chbxUseClientToServerUpdatable.setValue(true);
	}
	
	static String buildChartPayload(final int value, final int max)
	{
		return new BarChart(new BarData()
			.addLabels("value")
			.addDataset(new BarDataset()
				.setBackgroundColor("#c02222")
				.setLabel("Dataset " + value)
				.addData(value)))
			.setOptions(new BarOptions()
				.setIndexAxis(IndexAxis.Y) // Horizontal
				.setResponsive(true)
				.setMaintainAspectRatio(false)
				.setAnimation(false)
				.setScales(new Scales()
					.addScale(
						Scales.ScaleAxis.X,
						new LinearScaleOptions()
							.setBeginAtZero(true)
							.setSuggestedMax(max))
				))
			.toJson();
	}
	
	static class BrokenChartContainer extends ChartContainer
	{
		public BrokenChartContainer(final int value, final int max)
		{
			this.showChart(buildChartPayload(value, max));
		}
	}
	
	
	static class WorkingChartContainer extends ClientToServerUpdateableChartContainer
	{
		private final String payload;
		
		public WorkingChartContainer(final int value, final int max)
		{
			this.payload = buildChartPayload(value, max);
			
			// Important
			// This tells the client to run the update function once it's rendered!
			this.scheduleUpdateViaScript();
		}
		
		@Override
		protected void update()
		{
			this.showChart(this.payload);
		}
	}
}
