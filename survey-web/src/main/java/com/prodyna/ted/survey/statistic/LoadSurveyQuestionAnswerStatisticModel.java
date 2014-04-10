package com.prodyna.ted.survey.statistic;

import javax.inject.Inject;

import org.apache.wicket.cdi.CdiContainer;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.util.string.StringValueConversionException;

import com.prodyna.ted.survey.statistics.entity.SurveyQuestionAnswerStatistic;
import com.prodyna.ted.survey.survey.SurveryStatisticsService;

/**
 * Load survey by id.
 * 
 * @author Daniel Knipping, PRODYNA AG
 */
public class LoadSurveyQuestionAnswerStatisticModel extends LoadableDetachableModel<SurveyQuestionAnswerStatistic> {

    private static final long serialVersionUID = 1L;

    private final Long id;

    @Inject
    private SurveryStatisticsService surveyStatisticsService;

    public LoadSurveyQuestionAnswerStatisticModel(Long id) {
        this.id = id;
        CdiContainer.get().getNonContextualManager().inject(this);
    }

    @Override
    protected SurveyQuestionAnswerStatistic load() {
        try {
            if (id == null) {
                return null;
            }
            return surveyStatisticsService.getQuestionAnswerStatisticForSurvey(id);
        } catch (StringValueConversionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
