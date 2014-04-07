package com.prodyna.ted.survey.update;

import javax.inject.Inject;

import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodyna.ted.survey.common.ServiceResult;
import com.prodyna.ted.survey.edit.SurveyInputPanel;
import com.prodyna.ted.survey.entity.SurveyEntity;
import com.prodyna.ted.survey.receipt.SurveyReceiptPanel;
import com.prodyna.ted.survey.survey.SurveyService;
import com.prodyna.ted.survey.wizard.IWizard;
import com.prodyna.ted.survey.wizard.Wizard;

/**
 * Wizard to update survey.
 * 
 * @author Daniel Knipping, PRODYNA AG
 */
public class UpdateSurveyWizard extends Wizard {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(UpdateSurveyWizard.class);

    public UpdateSurveyWizard(String id, final IModel<SurveyEntity> model) {
        super(id);
        setOutputMarkupId(true);
        getController().add(new SurveyInputPanelImpl(model, model));
        getController().add(new SurveyReceiptPanel(model));
        start();
    }

    private static final class SurveyInputPanelImpl extends SurveyInputPanel {
        private static final long serialVersionUID = 1L;

        private final IModel<SurveyEntity> model;

        @Inject
        private SurveyService surveyService;

        private SurveyInputPanelImpl(IModel<SurveyEntity> surveyModel, IModel<SurveyEntity> model) {
            super(surveyModel);
            this.model = model;
        }

        @Override
        protected void doAction() {
            ServiceResult<SurveyEntity> updated = surveyService.updateSurvey(model.getObject());

            model.setObject(updated.getResult());
            info("Survey updated successfully!");
            IWizard wizard = getWizard();
            wizard.nextStep();
            wizard.updateWizard();
        }

        @Override
        public void detachModels() {
            super.detachModels();
            model.detach();
        }
    }
}
