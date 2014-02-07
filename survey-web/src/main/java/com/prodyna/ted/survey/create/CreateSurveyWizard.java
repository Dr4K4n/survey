package com.prodyna.ted.survey.create;

import javax.inject.Inject;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.wizard.IWizardStep;
import org.apache.wicket.extensions.wizard.Wizard;
import org.apache.wicket.extensions.wizard.WizardModel;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodyna.ted.survey.edit.SurveyInputPanel;
import com.prodyna.ted.survey.entity.SurveyEntity;
import com.prodyna.ted.survey.exception.FunctionalException;
import com.prodyna.ted.survey.receipt.SurveyReceiptPanel;
import com.prodyna.ted.survey.survey.SurveyService;

/**
 * Wizard to create a survey.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
public class CreateSurveyWizard extends Wizard {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(CreateSurveyWizard.class);
	
	public CreateSurveyWizard(String id, final IModel<SurveyEntity> model) {
		super(id);
		setOutputMarkupId(true);
		WizardModel wizardModel = new WizardModel();
		wizardModel.add(new SurveyInputPanelImpl(model, model));
		wizardModel.add(new SurveyReceiptPanel(model));
		init(wizardModel);
	}
	
	private static final class SurveyInputPanelImpl extends SurveyInputPanel {
		private static final long serialVersionUID = 1L;

		@Inject
		private SurveyService surveyService;
		
		private final IModel<SurveyEntity> model;

		private SurveyInputPanelImpl(IModel<SurveyEntity> surveyModel,
				IModel<SurveyEntity> model) {
			super(surveyModel);
			this.model = model;
		}

		@Override
		protected void doAction() {
			try {
				SurveyEntity createSurvey = surveyService.createSurvey(model.getObject());
				model.setObject(createSurvey);
				info("Survey erfolgreich angelegt");
				IWizardStep step = getWizardModel().getActiveStep();
				step.applyState();
				if (step.isComplete()) {
					getWizardModel().next();
				}
			} catch (FunctionalException e) {
				error("Could not create survey!");
				LOG.error("Could not create survey!", e);
			}
		}
		
		@Override
		public void detachModels() {
			super.detachModels();
			model.detach();
		}
	}
	
	@Override
	protected Component newButtonBar(String id) {
		return new EmptyPanel(id);
	}
	
	@Override
	protected Component newFeedbackPanel(String id) {
		return new EmptyPanel(id);
	}

}
