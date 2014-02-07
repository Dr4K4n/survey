package com.prodyna.ted.survey.page;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

/**
 * Default error page.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
public class ErrorPage extends SurveyBasePage {

	private static final long serialVersionUID = 1L;

	@Override
	protected Component createTitle(String id) {
		return new Label(id, Model.of("Error"));
	}

}
