package com.prodyna.ted.survey.statistic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.apache.wicket.ajax.json.JSONArray;
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import com.pingunaut.wicket.chartjs.chart.impl.Line;
import com.pingunaut.wicket.chartjs.chart.impl.Radar;
import com.pingunaut.wicket.chartjs.core.panel.LineChartPanel;
import com.pingunaut.wicket.chartjs.core.panel.RadarChartPanel;
import com.pingunaut.wicket.chartjs.data.LineChartData;
import com.pingunaut.wicket.chartjs.data.RadarChartData;
import com.pingunaut.wicket.chartjs.data.sets.LineDataSet;
import com.pingunaut.wicket.chartjs.data.sets.RadarDataSet;
import com.prodyna.ted.survey.entity.AnswerEntity;
import com.prodyna.ted.survey.entity.QuestionEntity;
import com.prodyna.ted.survey.entity.Rating;
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

        LineChartPanel lineChartPanel = new LineChartPanel("lineChartPanel", Model.of(new Line()));
        add(lineChartPanel);
        List<String> labels = new ArrayList<String>();
        labels.add("jan");
        labels.add("feb");
        labels.add("mar");
        labels.add("apr");

        List<Integer> values = new ArrayList<Integer>();
        values.add(4);
        values.add(2);
        values.add(6);
        values.add(7);

        LineChartData<LineDataSet> lineData = new LineChartData<LineDataSet>();
        lineData.getDatasets().add(new LineDataSet(values));

        lineChartPanel.getChart().setData(lineData);
        lineData.setLabels(labels);

        RadarChartPanel radarChartPanel = new RadarChartPanel("radarChartPanel", Model.of(new Radar()));
        add(radarChartPanel);

        AbstractReadOnlyModel<RadarChartData<RadarDataSet>> radarChartData = new AbstractReadOnlyModel<RadarChartData<RadarDataSet>>() {
            private static final long serialVersionUID = 1L;

            @Override
            public RadarChartData<RadarDataSet> getObject() {
                RadarChartData<RadarDataSet> radarChartData = new RadarChartData<RadarDataSet>();
                SurveyStatistics2 sust2 = surveyStatisticsService.getStatistics2ForSurvey(getModelObject());

                int i = 0;
                for (List<AnswerEntity> answerEntities : sust2.getQuestionToAnserMap().values()) {
                    List<Integer> values = Arrays.asList(new Integer[]{0, 0, 0, 0, 0});
                    for (AnswerEntity ae : answerEntities) {
                        if (ae != null && ae.getRating() != null) {
                            values.set(ae.getRating().ordinal(), values.get(ae.getRating().ordinal()) + 100);
                        }
                    }
                    RadarDataSet radarDataSet = new RadarDataSet(values);
                    radarDataSet.setFillColor(getColor(i));
                    radarChartData.getDatasets().add(radarDataSet);
                    i++;
                }
                return radarChartData;
            }
        };
        radarChartPanel.getChart().setData(radarChartData.getObject());
        AbstractReadOnlyModel<List<String>> labelsModel = new AbstractReadOnlyModel<List<String>>() {
            private static final long serialVersionUID = 1L;

            @Override
            public List<String> getObject() {
                // SurveyStatistics2 sust2 = surveyStatisticsService.getStatistics2ForSurvey(getModelObject());
                // List<String> result = new ArrayList<String>();
                // for (QuestionEntity qe : sust2.getQuestionToAnserMap().keySet()) {
                // result.add(qe.getQuestion());
                // }
                // return result;
                List<String> result = new ArrayList<String>();
                for (Rating rating : Rating.values()) {
                    result.add(rating.name());
                }
                return result;
            }
        };
        radarChartPanel.getChart().getData().setLabels(labelsModel.getObject());

    }

    protected String getColor(int i) {
        switch (i) {
            case 0:
                return "rgba(220,220,220,0.5)";
            case 1:
                return "rgba(110,110,110,0.5)";
            case 2:
                return "rgba(55,55,55,0.5)";
            case 3:
                return "rgba(165,165,165,0.5)";
            default:
                return "rgba(0,0,0,0.5)";
        }
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(JavaScriptReferenceHeaderItem.forReference(new JavaScriptResourceReference(ViewSurveyPage.class, "jquery.flot.js"), true));
        response.render(JavaScriptReferenceHeaderItem.forReference(new JavaScriptResourceReference(ViewSurveyPage.class, "jquery.flot.stack.js"),
            true));

        List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
        SurveyStatistics2 sust2 = surveyStatisticsService.getStatistics2ForSurvey(getModelObject());

        int[][][] answers = new int[5][][];

        int questionCount = sust2.getQuestionToAnserMap().size();
        answers[Rating.ONE.ordinal()] = new int[questionCount][];
        answers[Rating.TWO.ordinal()] = new int[questionCount][];
        answers[Rating.THREE.ordinal()] = new int[questionCount][];
        answers[Rating.FOUR.ordinal()] = new int[questionCount][];
        answers[Rating.FIFE.ordinal()] = new int[questionCount][];

        int index = 0;
        for (Entry<QuestionEntity, List<AnswerEntity>> entry : sust2.getQuestionToAnserMap().entrySet()) {
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
                jsonAnswers.put(i, new JSONArray(answers[i]));
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

    private SurveyEntity getModelObject() {
        return (SurveyEntity) getDefaultModelObject();
    }

}