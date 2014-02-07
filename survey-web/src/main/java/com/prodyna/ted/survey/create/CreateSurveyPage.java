package com.prodyna.ted.survey.create;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.ResourceModel;

import com.prodyna.ted.survey.page.SurveyBasePage;

/**
 * Page to create a survey.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
public class CreateSurveyPage extends SurveyBasePage {

	private static final long serialVersionUID = 1L;
	
	public CreateSurveyPage() {
		add(new CreateSurveyWizard("surveyWizard", new SurveyModel()));
	}
	
	
	@Override
	protected Component createTitle(String id) {
		return new Label(id, new ResourceModel("title"));
	}

}
