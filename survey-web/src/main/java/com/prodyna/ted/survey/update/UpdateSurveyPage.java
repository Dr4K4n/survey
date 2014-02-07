package com.prodyna.ted.survey.update;

import static com.prodyna.ted.survey.condition.ComponentModifier.setInvisibleIf;
import static com.prodyna.ted.survey.condition.ComponentModifier.setVisibleIf;
import static com.prodyna.ted.survey.condition.ConditionOperation.isNull;
import static com.prodyna.ted.survey.condition.ConditionOperation.or;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import com.prodyna.ted.survey.page.SurveyBasePage;
import com.prodyna.ted.survey.perform.LoadSurveyModel;

/**
 * Page to update existing survey.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
public class UpdateSurveyPage extends SurveyBasePage {

	private static final long serialVersionUID = 1L;
	
	public UpdateSurveyPage(PageParameters pageParameters) {
		StringValue idValue = pageParameters.get("id");
		LoadSurveyModel model = new LoadSurveyModel(idValue.toLongObject());
		UpdateSurveyWizard updateSurveyWizard = new UpdateSurveyWizard("surveyWizard", model);
		updateSurveyWizard.add(setInvisibleIf(or(isNull(Model.of(idValue)), isNull(new PropertyModel<Long>(model, "id")))));
		add(updateSurveyWizard);
		
		Label noSurveyFound = new Label("noSurveyFound", new ResourceModel("noSurveyFround"));
		noSurveyFound.add(setVisibleIf(or(isNull(Model.of(idValue)), isNull(new PropertyModel<Long>(model, "id")))));
		add(noSurveyFound);
	}
	
	
	@Override
	protected Component createTitle(String id) {
		return new Label(id, new ResourceModel("title"));
	}

}
