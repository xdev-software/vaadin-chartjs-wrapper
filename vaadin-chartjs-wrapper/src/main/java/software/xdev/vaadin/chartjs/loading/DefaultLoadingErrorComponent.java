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
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


@CssImport(LoadingStyles.LOCATION)
public class DefaultLoadingErrorComponent extends Composite<VerticalLayout> implements HasStyle, LoadingErrorComponent
{
	private final Span spanMsg = new Span();
	
	public DefaultLoadingErrorComponent()
	{
		final Icon iconError = VaadinIcon.WARNING.create();
		iconError.addClassName(LoadingStyles.LOAD_ERROR_ICON);
		
		final Span span = new Span("Error while loading :(");
		span.addClassName(LoadingStyles.LOAD_ERROR_TITLE);
		
		this.spanMsg.addClassName(LoadingStyles.LOAD_ERROR_MSG);
		this.spanMsg.setVisible(false);
		
		final VerticalLayout root = this.getContent();
		
		root.add(iconError, span, this.spanMsg);
		root.addClassName(LoadingStyles.LOAD_CONTAINER);
		root.setSpacing(false);
		root.setSizeFull();
	}
	
	@Override
	public void setVisible(final boolean visible)
	{
		this.setVisible(visible, null);
	}
	
	@Override
	public void setVisible(final boolean visible, final String errorMessage)
	{
		super.setVisible(visible);
		
		if(errorMessage == null || errorMessage.isBlank())
		{
			this.spanMsg.setVisible(false);
			return;
		}
		
		this.spanMsg.setVisible(true);
		this.spanMsg.setText(errorMessage);
	}
}
