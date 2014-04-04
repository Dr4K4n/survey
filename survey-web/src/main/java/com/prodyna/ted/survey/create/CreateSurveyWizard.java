package com.prodyna.ted.survey.create;

import javax.inject.Inject;

import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodyna.ted.survey.edit.SurveyInputPanel;
import com.prodyna.ted.survey.entity.SurveyEntity;
import com.prodyna.ted.survey.exception.FunctionalRuntimeException;
import com.prodyna.ted.survey.receipt.SurveyReceiptPanel;
import com.prodyna.ted.survey.survey.SurveyService;
import com.prodyna.ted.survey.wizard.IWizard;
import com.prodyna.ted.survey.wizard.Wizard;

/**
 * Wizard to create a survey.
 * 
 * @author Daniel Knipping, PRODYNA AG
 */
public class CreateSurveyWizard extends Wizard {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(CreateSurveyWizard.class);

    public CreateSurveyWizard(String id, final IModel<SurveyEntity> model) {
        super(id);
        setOutputMarkupId(true);
        getController().add(new SurveyInputPanelImpl(model, model));
        getController().add(new SurveyReceiptPanel(model));
        start();
    }

    private static final class SurveyInputPanelImpl extends SurveyInputPanel {
        private static final long serialVersionUID = 1L;

        @Inject
        private SurveyService surveyService;

        private final IModel<SurveyEntity> model;

        private SurveyInputPanelImpl(IModel<SurveyEntity> surveyModel, IModel<SurveyEntity> model) {
            super(surveyModel);
            this.model = model;
        }

        @Override
        protected void doAction() {
                SurveyEntity createSurvey = surveyService.createSurvey(model.getObject());
                model.setObject(createSurvey);
                info("Survey erfolgreich angelegt");
                IWizard wizard = getWizard();
                wizard.nextStep();
                wizard.updateWizard();
                
                for (String errorStr : wrapper.getErrorList()) {
                    error(errorStr);
                }
        }

        @Override
        public void detachModels() {
            super.detachModels();
            model.detach();
        }
    }

}
