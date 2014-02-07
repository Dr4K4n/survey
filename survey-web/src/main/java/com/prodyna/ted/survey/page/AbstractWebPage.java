package com.prodyna.ted.survey.page;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * 
 * AbstractWebPage
 * 
 * @author Daniel Knipping
 *
 */
public abstract class AbstractWebPage extends WebPage {

	private static final long serialVersionUID = 1L;

	
	public AbstractWebPage() {
		super();
		init();
	}

	public AbstractWebPage(PageParameters pageParameters) {
		super(pageParameters);
		init();
	}
	
	private void init() {
		add(createTitle("title"));
		add(createHeader("header"));
		add(createNavigation("navigation"));
		add(createFooter("footer"));
	}
	
	
	protected abstract Component createHeader(String id);
	protected abstract Component createNavigation(String id);
	protected abstract Component createFooter(String id);
	protected abstract Component createTitle(String id);
}
