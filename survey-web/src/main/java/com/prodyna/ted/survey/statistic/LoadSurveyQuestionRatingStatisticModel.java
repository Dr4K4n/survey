package com.prodyna.ted.survey.statistic;

import javax.inject.Inject;

import org.apache.wicket.cdi.CdiContainer;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.util.string.StringValueConversionException;

import com.prodyna.ted.survey.statistics.entity.SurveyQuestionAnswerStatistic;
import com.prodyna.ted.survey.statistics.entity.SurveyQuestionRatingStatistic;
import com.prodyna.ted.survey.survey.SurveryStatisticsService;

/**
 * Load survey by id.
 * 
 * @author Jan Hilgenhoener, PRODYNA AG
 */
public class LoadSurveyQuestionRatingStatisticModel extends LoadableDetachableModel<SurveyQuestionRatingStatistic> {

    private static final long serialVersionUID = 1L;

    private final Long id;

    @Inject
    private transient SurveryStatisticsService surveyStatisticsService;

    public LoadSurveyQuestionRatingStatisticModel(Long id) {
        this.id = id;
        CdiContainer.get().getNonContextualManager().inject(this);
    }

    @Override
    protected SurveyQuestionRatingStatistic load() {
        try {
            if (id == null) {
                return null;
            }
            return surveyStatisticsService.getQuestionRatingStatisticForSurvey(id);
        } catch (StringValueConversionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
