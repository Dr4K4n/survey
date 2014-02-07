package com.prodyna.ted.survey.page;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.panel.EmptyPanel;

/**
 * Base Page with default template.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
public abstract class SurveyBasePage extends AbstractWebPage {

	private static final long serialVersionUID = 1L;

	@Override
	protected Component createHeader(String id) {
		return new HeaderPanel(id);
	}

	@Override
	protected Component createNavigation(String id) {
		return new EmptyPanel(id);
	}

	@Override
	protected Component createFooter(String id) {
		return new EmptyPanel(id);
	}
	
	@Override
	public void renderHead(IHeaderResponse response) {
		response.render(CssHeaderItem.forUrl("css/styles.css"));
	}
}
