package com.prodyna.ted.survey.statistic;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import com.prodyna.ted.survey.entity.SurveyEntity;
import com.prodyna.ted.survey.page.SurveyBasePage;

public class ViewSurveyPage extends SurveyBasePage {
    private static final long serialVersionUID = -1862972283822977217L;

    public ViewSurveyPage(IModel<SurveyEntity> iModel) {
        add(new ViewSurveyPanel("panel", iModel));
    }

    @Override
    protected Component createTitle(String id) {
        // TODO Auto-generated method stub
        return new Label(id, new ResourceModel("title"));
    }

}
