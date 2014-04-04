package com.prodyna.ted.survey.statistic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import com.prodyna.ted.survey.entity.AnswerEntity;
import com.prodyna.ted.survey.entity.QuestionEntity;
import com.prodyna.ted.survey.entity.SurveyEntity;
import com.prodyna.ted.survey.statistics.entity.SurveyStatistics1;
import com.prodyna.ted.survey.statistics.entity.SurveyStatistics2;
import com.prodyna.ted.survey.survey.SurveryStatisticsService;

public class ViewSurveyPanel extends Panel {

    private static final long serialVersionUID = 1L;

    @Inject
    private SurveryStatisticsService surveyStatisticsService;

    private final IModel<SurveyStatistics1> model = Model.of();

    public ViewSurveyPanel(final String id, final IModel<SurveyEntity> surveyModel) {
        super(id, surveyModel);
        setOutputMarkupId(true);
        model.setObject(surveyStatisticsService.getStatistics1ForSurvey(((SurveyEntity) getDefaultModelObject())));
        add(new Label("surveyDescription", new PropertyModel<String>(model, "survey.name")));
        add(new Label("noOfQuestions", new PropertyModel<String>(model, "numberOfQuestions")));
        add(new Label("noOfAnswers", new PropertyModel<String>(model, "numberOfAnswers")));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(JavaScriptReferenceHeaderItem.forReference(new JavaScriptResourceReference(ViewSurveyPage.class, "jquery.flot.js"), true));
        response
            .render(JavaScriptReferenceHeaderItem.forReference(new JavaScriptResourceReference(ViewSurveyPage.class, "jquery.flot.pie.js"), true));

        List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
        SurveyStatistics2 sust2 = surveyStatisticsService.getStatistics2ForSurvey(getModelObject());

        try {
            for (Entry<QuestionEntity, List<AnswerEntity>> entry : sust2.getQuestionToAnserMap().entrySet()) {

                JSONObject o = new JSONObject();
                o.append("label", entry.getKey().getQuestion());
                o.append("data", entry.getValue().size() + "");
                jsonObjectList.add(o);

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(jsonObjectList.toString());

        response.render(JavaScriptHeaderItem.forScript("var data = " + jsonObjectList.toString() + ";", System.currentTimeMillis() + ""));
    }

    private SurveyEntity getModelObject() {
        return (SurveyEntity) getDefaultModelObject();
    }

}