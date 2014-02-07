package com.prodyna.ted.survey.update;

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
 * Wizard to update survey.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
public class UpdateSurveyWizard extends Wizard {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(UpdateSurveyWizard.class);
	
	public UpdateSurveyWizard(String id, final IModel<SurveyEntity> model) {
		super(id);
		setOutputMarkupId(true);
		WizardModel wizardModel = new WizardModel();
		wizardModel.add(new SurveyInputPanelImpl(model, model));
		wizardModel.add(new SurveyReceiptPanel(model));
		init(wizardModel);
	}
	
	private static final class SurveyInputPanelImpl extends SurveyInputPanel {
		private static final long serialVersionUID = 1L;

		private final IModel<SurveyEntity> model;

		@Inject
		private SurveyService surveyService;

		private SurveyInputPanelImpl(IModel<SurveyEntity> surveyModel,
				IModel<SurveyEntity> model) {
			super(surveyModel);
			this.model = model;
		}

		@Override
		protected void doAction() {
			try {
				SurveyEntity updated = surveyService.updateSurvey(model.getObject());
				model.setObject(updated);
				info("Survey updated successfully!");
				IWizardStep step = getWizardModel().getActiveStep();
				step.applyState();
				if (step.isComplete()) {
					getWizardModel().next();
				}
			} catch (FunctionalException e) {
				error("Error during update survey!");
				LOG.error("Error during update survey!", e);
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
