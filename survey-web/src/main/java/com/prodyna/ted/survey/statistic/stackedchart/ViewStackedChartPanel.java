package com.prodyna.ted.survey.statistic.stackedchart;

import static com.prodyna.ted.survey.condition.ComponentModifier.setInvisibleIf;
import static com.prodyna.ted.survey.condition.ComponentModifier.setVisibleIf;
import static com.prodyna.ted.survey.condition.ConditionOperation.isNull;

import java.util.List;
import java.util.Map.Entry;

import org.apache.wicket.ajax.json.JSONArray;
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
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import com.prodyna.ted.survey.condition.ComponentModifier;
import com.prodyna.ted.survey.entity.AnswerEntity;
import com.prodyna.ted.survey.entity.QuestionEntity;
import com.prodyna.ted.survey.entity.Rating;
import com.prodyna.ted.survey.entity.SurveyEntity;
import com.prodyna.ted.survey.statistic.LoadSurveyQuestionAnswerStatisticModel;
import com.prodyna.ted.survey.statistics.entity.SurveyQuestionAnswerStatistic;

public class ViewStackedChartPanel extends Panel {

    private static final long serialVersionUID = 1L;

    public ViewStackedChartPanel(final String wicketid, final IModel<SurveyEntity> surveyModel) {
        super(wicketid, surveyModel);
        setOutputMarkupId(true);

        Long id = ((SurveyEntity) getDefaultModelObject()).getId();

        IModel<SurveyQuestionAnswerStatistic> model = Model.of();
        if (((SurveyEntity) getDefaultModelObject()).getId() != null) {
            model = new LoadSurveyQuestionAnswerStatisticModel(id);
        }

        Label noSurveyFound = new Label("noSurveyQuestionAnswerStatisticFound", new ResourceModel("noSurveyQuestionAnswerStatisticFound"));
        noSurveyFound.add(setVisibleIf(isNull(Model.of(id))));
        add(noSurveyFound);
        ComponentModifier<Long> setInvisibleIf2 = setInvisibleIf(isNull(Model.of(id)));
        add(new Label("surveyDescription", new PropertyModel<String>(model, "survey.name")).add(setInvisibleIf2));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(JavaScriptReferenceHeaderItem.forReference(new JavaScriptResourceReference(ViewStackedChartPanel.class, "jquery.flot.js"),
            true));
        response.render(JavaScriptReferenceHeaderItem.forReference(new JavaScriptResourceReference(ViewStackedChartPanel.class,
            "jquery.flot.stack.js"), true));

        IModel<SurveyQuestionAnswerStatistic> model = Model.of();
        if (((SurveyEntity) getDefaultModelObject()).getId() != null) {
            model = new LoadSurveyQuestionAnswerStatisticModel(((SurveyEntity) getDefaultModelObject()).getId());
        }
        int[][][] answers = new int[5][][];

        int questionCount = model.getObject().getQuestionToAnserMap().size();
        answers[Rating.ONE.ordinal()] = new int[questionCount][];
        answers[Rating.TWO.ordinal()] = new int[questionCount][];
        answers[Rating.THREE.ordinal()] = new int[questionCount][];
        answers[Rating.FOUR.ordinal()] = new int[questionCount][];
        answers[Rating.FIVE.ordinal()] = new int[questionCount][];

        int index = 0;
        for (Entry<QuestionEntity, List<AnswerEntity>> entry : model.getObject().getQuestionToAnserMap().entrySet()) {
            for (AnswerEntity answer : entry.getValue()) {
                int[][] answerCountArray = answers[answer.getRating().ordinal()];
                int[] answerCount = answerCountArray[index];
                if (answerCount == null) {
                    answerCount = new int[]{index, 1};
                } else {
                    answerCount[1]++;
                }
                answerCountArray[index] = answerCount;

            }
            index++;
        }

        JSONArray jsonAnswers = new JSONArray();
        try {
            for (int i = 0; i < answers.length; i++) {
                JSONObject dataObject = new JSONObject();
                dataObject.put("data", answers[i]);
                dataObject.put("label", Rating.values()[i]);
                jsonAnswers.put(i, dataObject);
                // JSONArray answerCount = new JSONArray();
                // for (int j = 0; j < answers[i].length; j++) {
                // int count = answers[i][j];
                // answerCount.put(j, count);
                // }
                // jsonAnswers.put(i, answerCount);
            }
            System.out.println(jsonAnswers.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        response.render(JavaScriptHeaderItem.forScript("var data = " + jsonAnswers.toString() + ";", System.currentTimeMillis() + ""));
    }

}