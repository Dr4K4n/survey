package com.prodyna.ted.survey.statistic.ratingstable;

import static com.prodyna.ted.survey.condition.ComponentModifier.setInvisibleIf;
import static com.prodyna.ted.survey.condition.ComponentModifier.setVisibleIf;
import static com.prodyna.ted.survey.condition.ConditionOperation.isNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import com.prodyna.ted.survey.condition.ComponentModifier;
import com.prodyna.ted.survey.entity.QuestionEntity;
import com.prodyna.ted.survey.entity.SurveyEntity;
import com.prodyna.ted.survey.statistic.LoadSurveyQuestionRatingStatisticModel;
import com.prodyna.ted.survey.statistics.entity.SurveyQuestionRatingStatistic;

public class ViewQuestionRatingPanel extends Panel {

    private static final long serialVersionUID = 1L;

    IModel<SurveyQuestionRatingStatistic> model;

    public ViewQuestionRatingPanel(final String wicketid, final IModel<SurveyEntity> surveyModel) {
        super(wicketid, surveyModel);
        setOutputMarkupId(true);

        Long id = ((SurveyEntity) getDefaultModelObject()).getId();

        model = Model.of();
        if (((SurveyEntity) getDefaultModelObject()).getId() != null) {
            model = new LoadSurveyQuestionRatingStatisticModel(id);
        }

        Label noSurveyFound = new Label("noSurveyQuestionRatingStatisticFound", new ResourceModel("noSurveyQuestionAnswerStatisticFound"));
        noSurveyFound.add(setVisibleIf(isNull(Model.of(id))));
        add(noSurveyFound);
        ComponentModifier<Long> setInvisibleIf2 = setInvisibleIf(isNull(Model.of(id)));

        LoadableDetachableModel<List<QuestionEntity>> listModel = new LoadableDetachableModel<List<QuestionEntity>>() {
            private static final long serialVersionUID = 1L;

            @Override
            protected List<QuestionEntity> load() {
                Set<QuestionEntity> keySet = model.getObject().getQuestionRatingMap().keySet();

                return new ArrayList<QuestionEntity>(keySet);
            }

        };
        ListView<QuestionEntity> listView = new ListView<QuestionEntity>("surveys", listModel) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final ListItem<QuestionEntity> item) {

                item.add(new Label("question", new PropertyModel<String>(item.getModelObject(), "question")));
                item.add(new Label("rating", model.getObject().getQuestionRatingMap().get(item.getDefaultModelObject())));
            }

        };
        listView.add(setInvisibleIf2);
        add(listView);

    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(CssHeaderItem.forUrl("//cdn.datatables.net/1.10.0/css/jquery.dataTables.css"));
        response.render(JavaScriptHeaderItem.forUrl("//cdn.datatables.net/1.10.0/js/jquery.dataTables.js"));
        response.render(JavaScriptHeaderItem.forScript("$(document).ready(function(){$('.myDataTable').dataTable();});", "instantiateDataTable"));
    }
}