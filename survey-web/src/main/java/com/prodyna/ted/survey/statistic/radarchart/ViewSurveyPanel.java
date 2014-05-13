package com.prodyna.ted.survey.statistic.radarchart;

import static com.prodyna.ted.survey.condition.ComponentModifier.setInvisibleIf;
import static com.prodyna.ted.survey.condition.ComponentModifier.setVisibleIf;
import static com.prodyna.ted.survey.condition.ConditionOperation.isNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import com.pingunaut.wicket.chartjs.chart.impl.Radar;
import com.pingunaut.wicket.chartjs.core.panel.RadarChartPanel;
import com.pingunaut.wicket.chartjs.data.RadarChartData;
import com.pingunaut.wicket.chartjs.data.sets.RadarDataSet;
import com.prodyna.ted.survey.condition.ComponentModifier;
import com.prodyna.ted.survey.entity.AnswerEntity;
import com.prodyna.ted.survey.entity.Rating;
import com.prodyna.ted.survey.entity.SurveyEntity;
import com.prodyna.ted.survey.statistic.LoadSurveyQuestionAnswerStatisticModel;
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
        ComponentModifier<Long> setInvisibleIf2 = setInvisibleIf(isNull(Model.of(id)));
        add(new Label("surveyDescription", new PropertyModel<String>(model, "survey.name")).add(setInvisibleIf2));
        // add(new Label("noOfQuestions", new PropertyModel<String>(model, "numberOfQuestions")).add(setInvisibleIf(isNull(Model.of(id)))));
        // add(new Label("noOfAnswers", new PropertyModel<String>(model, "numberOfAnswers")).add(setInvisibleIf2));

        RadarChartPanel radarChartPanel = new RadarChartPanel("radarChartPanel", Model.of(new Radar()));
        add(radarChartPanel);

        RadarDataModel radarDataModel = new RadarDataModel(model);

        radarChartPanel.getChart().setData(radarDataModel.getObject());
        GraphLabelModel labelsModel = new GraphLabelModel();
        radarChartPanel.getChart().getData().setLabels(labelsModel.getObject());

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
                        values.set(ae.getRating().ordinal(), values.get(ae.getRating().ordinal()) + 1);
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