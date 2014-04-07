package com.prodyna.ted.survey.statistic;

import javax.inject.Inject;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import com.prodyna.ted.survey.entity.SurveyEntity;
import com.prodyna.ted.survey.page.SurveyBasePage;
import com.prodyna.ted.survey.perform.LoadSurveyModel;
import com.prodyna.ted.survey.survey.SurveyService;

public class ViewSurveyPage extends SurveyBasePage {
    private static final long serialVersionUID = -1862972283822977217L;

    @Inject
    private SurveyService surveyService;

    public ViewSurveyPage(PageParameters pageParameters) {
        StringValue idValue = pageParameters.get("id");
        LoadSurveyModel model = new LoadSurveyModel(idValue.toLongObject());
        add(new ViewSurveyPanel("panel", model));
    }

    public ViewSurveyPage(IModel<SurveyEntity> iModel) {
        add(new ViewSurveyPanel("panel", iModel));
    }

    @Override
    protected Component createTitle(String id) {
        // TODO Auto-generated method stub
        return new Label(id, new ResourceModel("title"));
    }

}
