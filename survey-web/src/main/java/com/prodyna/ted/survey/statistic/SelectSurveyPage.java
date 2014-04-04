package com.prodyna.ted.survey.statistic;

import javax.inject.Inject;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import com.prodyna.ted.survey.entity.SurveyEntity;
import com.prodyna.ted.survey.exception.FunctionalException;
import com.prodyna.ted.survey.page.SurveyBasePage;
import com.prodyna.ted.survey.survey.SurveyService;

public class SelectSurveyPage extends SurveyBasePage {
    private static final long serialVersionUID = -1862972283822977217L;

    @Inject
    private SurveyService surveyService;

    public SelectSurveyPage() {
        try {
            add(new ListView<SurveyEntity>("surveys", surveyService.findAllSurvey()) {
                private static final long serialVersionUID = 1L;

                @Override
                protected void populateItem(final ListItem<SurveyEntity> item) {
                    item.add(new AjaxLink<String>("link", new PropertyModel<String>(item.getModelObject(), "name")) {

                        @Override
                        public void onClick(AjaxRequestTarget target) {
                            setResponsePage(new ViewSurveyPage(item.getModel()));

                        }
                    });
                    item.add(new Label("name", new PropertyModel<String>(item.getModelObject(), "name")));
                }

            });
        } catch (FunctionalException fe) {

        }
    }

    @Override
    protected Component createTitle(String id) {
        // TODO Auto-generated method stub
        return new Label(id, new ResourceModel("title"));
    }

}
