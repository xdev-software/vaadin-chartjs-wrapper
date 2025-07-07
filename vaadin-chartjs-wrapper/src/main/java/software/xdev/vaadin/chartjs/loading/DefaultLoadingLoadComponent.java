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
package software.xdev.vaadin.chartjs.loading;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


@CssImport(LoadingStyles.LOCATION)
public class DefaultLoadingLoadComponent extends Composite<VerticalLayout> implements HasStyle
{
	public DefaultLoadingLoadComponent()
	{
		// Clock
		final Div divLoader = new Div();
		divLoader.addClassName(LoadingStyles.LOADER_CLOCK);
		
		final Span spLoading = new Span("Loading...");
		this.getContent().add(divLoader, spLoading);
		this.getContent().addClassName(LoadingStyles.LOAD_CONTAINER);
		this.getContent().setSizeFull();
		this.getContent().setSpacing(false);
		this.getContent().setPadding(false);
	}
}
