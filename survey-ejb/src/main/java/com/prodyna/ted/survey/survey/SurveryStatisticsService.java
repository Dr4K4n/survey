package com.prodyna.ted.survey.survey;


import com.prodyna.ted.survey.statistics.entity.Statistics;
import com.prodyna.ted.survey.statistics.entity.SurveyQuestionRatingStatistic;
import com.prodyna.ted.survey.statistics.entity.SurveyQuestionAnswerStatistic;

public interface SurveryStatisticsService {

	/**
	 * Get a {@link Statistics} object containing the number of questions and
	 * answers for a specific survey.
	 * 
	 * @param surveyId
	 * @return
	 */
	public SurveyQuestionRatingStatistic getQuestionRatingStatisticForSurvey(long surveyId);

	/**
	 * Get a {@link Statistics} object containing all questions and referring
	 * answers for a specific survey.
	 * 
	 * @param surveyId
	 * @return
	 */
	public SurveyQuestionAnswerStatistic getQuestionAnswerStatisticForSurvey(long surveyId);
}
