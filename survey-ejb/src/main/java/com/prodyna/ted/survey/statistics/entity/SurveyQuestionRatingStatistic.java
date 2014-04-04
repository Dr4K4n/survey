package com.prodyna.ted.survey.statistics.entity;

import java.util.Map;

import com.prodyna.ted.survey.entity.QuestionEntity;
import com.prodyna.ted.survey.entity.Rating;
import com.prodyna.ted.survey.entity.SurveyEntity;

public class SurveyQuestionRatingStatistic extends Statistics {

	private static final long serialVersionUID = 7033082962881002152L;

	private SurveyEntity survey;
	private Map<QuestionEntity, Rating> questionRatingMap;

	public SurveyQuestionRatingStatistic() {
		super("Number of questions and answers for a specific survey.");
	}

	public SurveyEntity getSurvey() {
		return survey;
	}

	public void setSurvey(SurveyEntity survey) {
		this.survey = survey;
	}

    public Map<QuestionEntity, Rating> getQuestionRatingMap() {
        return questionRatingMap;
    }

    public void setQuestionRatingMap(Map<QuestionEntity, Rating> questionRatingMap) {
        this.questionRatingMap = questionRatingMap;
    }

}
