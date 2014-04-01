package com.prodyna.ted.survey.survey;

import java.util.List;

import com.prodyna.ted.survey.entity.SurveyEntity;
import com.prodyna.ted.survey.statistics.entity.Statistics;
import com.prodyna.ted.survey.statistics.entity.SurveyStatistics1;
import com.prodyna.ted.survey.statistics.entity.SurveyStatistics2;

public interface SurveryStatisticsService {

	/**
	 * Get the {@link List} of all available {@link Statistics} provided by the
	 * service.
	 * 
	 * @return
	 */
	public List<Statistics> getAllStatistics();

	/**
	 * Get a {@link Statistics} object containing the number of questions and
	 * answers for a specific survey.
	 * 
	 * @param survey
	 * @return
	 */
	public SurveyStatistics1 getStatistics1ForSurvey(SurveyEntity survey);

	/**
	 * Get a {@link Statistics} object containing all questions and referring
	 * answers for a specific survey.
	 * 
	 * @param survey
	 * @return
	 */
	public SurveyStatistics2 getStatistics2ForSurvey(SurveyEntity survey);
}
