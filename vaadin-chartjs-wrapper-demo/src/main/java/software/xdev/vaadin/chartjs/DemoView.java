package software.xdev.vaadin.chartjs;

import java.util.List;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import software.xdev.vaadin.chartjs.demo.ClientToServerUpdateableDemo;
import software.xdev.vaadin.chartjs.demo.DateAdapterDemo;
import software.xdev.vaadin.chartjs.demo.FunctionalityShowcaseDemo;
import software.xdev.vaadin.chartjs.demo.MinimalisticDemo;


@PageTitle("ChartJS + Vaadin demos")
@Route("")
public class DemoView extends Composite<VerticalLayout>
{
	private final Grid<Example> grExamples = new Grid<>();
	
	public DemoView()
	{
		this.grExamples
			.addColumn(new ComponentRenderer<>(example -> {
				final Anchor anchor = new Anchor(example.route(), example.name());
				
				final Span spDesc = new Span(example.desc());
				spDesc.getStyle().set("font-size", "90%");
				spDesc.getStyle().set("white-space", "pre");
				
				final VerticalLayout vl = new VerticalLayout(anchor, spDesc);
				vl.setSpacing(false);
				return vl;
			}))
			.setHeader("Available demos");
		
		this.grExamples.setSizeFull();
		this.grExamples.addThemeVariants(GridVariant.LUMO_COMPACT, GridVariant.LUMO_NO_BORDER);
		
		this.getContent().add(this.grExamples);
		this.getContent().setHeightFull();
	}
	
	@Override
	protected void onAttach(final AttachEvent attachEvent)
	{
		this.grExamples.setItems(List.of(
			new Example(
				MinimalisticDemo.NAV,
				"Minimalistic",
				"Showcasing the simplest form of using Chart.js"
			),
			new Example(
				FunctionalityShowcaseDemo.NAV,
				"Functionality showcase",
				"Showcases most functionality, including lazy-loaded data, displaying errors & problems "
					+ "and the dark mode 🌙"
			),
			new Example(
				ClientToServerUpdateableDemo.NAV,
				"Client to Server updateable",
				"Shows how charts can be dynamically requested by the client e.g. when using a Grid."
			),
			new Example(
				DateAdapterDemo.NAV,
				"DateAdapter",
				"Shows how a date adapter can be used."
			)
		));
	}
	
	record Example(String route, String name, String desc)
	{
	}
}
