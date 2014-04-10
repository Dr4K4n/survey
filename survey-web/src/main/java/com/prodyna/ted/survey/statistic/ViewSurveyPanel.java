package com.prodyna.ted.survey.statistic;

import static com.prodyna.ted.survey.condition.ComponentModifier.setVisibleIf;
import static com.prodyna.ted.survey.condition.ConditionOperation.isNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.apache.wicket.ajax.json.JSONArray;
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
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
import com.prodyna.ted.survey.statistics.entity.SurveyQuestionAnswerStatistic;

public class ViewSurveyPanel extends Panel {

    private static final long serialVersionUID = 1L;

    public ViewSurveyPanel(final String wicketid, final IModel<SurveyEntity> surveyModel) {
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

        RadarDataModel radarDataModel = new RadarDataModel(model);

        radarChartPanel.getChart().setData(radarDataModel.getObject());
        GraphLabelModel labelsModel = new GraphLabelModel();
        radarChartPanel.getChart().getData().setLabels(labelsModel.getObject());

    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(JavaScriptReferenceHeaderItem.forReference(new JavaScriptResourceReference(ViewSurveyPage.class, "jquery.flot.js"), true));
        response.render(JavaScriptReferenceHeaderItem.forReference(new JavaScriptResourceReference(ViewSurveyPage.class, "jquery.flot.stack.js"),
            true));

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
        answers[Rating.FIFE.ordinal()] = new int[questionCount][];

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

    private static class RadarDataModel extends LoadableDetachableModel<RadarChartData<RadarDataSet>> {
        private static final long serialVersionUID = 1L;

        private final IModel<SurveyQuestionAnswerStatistic> questionAnswerStatistic;

        public RadarDataModel(IModel<SurveyQuestionAnswerStatistic> questionAnswerStatistic) {
            this.questionAnswerStatistic = questionAnswerStatistic;
        }

        @Override
        protected RadarChartData<RadarDataSet> load() {
            RadarChartData<RadarDataSet> radarChartData = new RadarChartData<RadarDataSet>();

            int i = 0;
            for (List<AnswerEntity> answerEntities : questionAnswerStatistic.getObject().getQuestionToAnserMap().values()) {
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

    }

    private static class GraphLabelModel extends AbstractReadOnlyModel<List<String>> {
        private static final long serialVersionUID = 1L;

        @Override
        public List<String> getObject() {
            List<String> result = new ArrayList<String>();
            for (Rating rating : Rating.values()) {
                result.add(rating.name());
            }
            return result;
        }
    }

}