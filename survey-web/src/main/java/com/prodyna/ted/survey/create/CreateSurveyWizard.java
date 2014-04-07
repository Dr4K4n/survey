package com.prodyna.ted.survey.create;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.model.IModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.prodyna.ted.survey.common.Message;
import com.prodyna.ted.survey.common.ServiceResult;
import com.prodyna.ted.survey.edit.SurveyInputPanel;
import com.prodyna.ted.survey.entity.SurveyEntity;
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
            ServiceResult<SurveyEntity> createSurvey = surveyService.createSurvey(model.getObject());
            model.setObject(createSurvey.getResult());
            info("Survey erfolgreich angelegt");
            IWizard wizard = getWizard();
            wizard.nextStep();
            wizard.updateWizard();

            List<Message> messages = createSurvey.getMessages();
            for (Message msg : messages) {
                switch (msg.getSeverity()) {
                    case INFO:
                        info(msg.getDefaultMessage());
                        break;
                    case WARN:
                        warn(msg.getDefaultMessage());
                        break;
                    default:
                        error(msg.getDefaultMessage());
                        break;
                }
            }
        }

        @Override
        public void detachModels() {
            super.detachModels();
            model.detach();
        }
    }

}
