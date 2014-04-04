package com.prodyna.ted.survey.statistics.entity;

import java.util.List;
import java.util.Map;

import com.prodyna.ted.survey.entity.AnswerEntity;
import com.prodyna.ted.survey.entity.QuestionEntity;
import com.prodyna.ted.survey.entity.SurveyEntity;

public class SurveyQuestionAnswerStatistic extends Statistics {

	private static final long serialVersionUID = -4592000382351985895L;

	private SurveyEntity survey;
	private Map<QuestionEntity, List<AnswerEntity>> questionToAnserMap;

	public SurveyQuestionAnswerStatistic() {
		super("List of questions and referring answers for a specific survey.");
	}

	public SurveyEntity getSurvey() {
		return survey;
	}

	public void setSurvey(SurveyEntity survey) {
		this.survey = survey;
	}

	public Map<QuestionEntity, List<AnswerEntity>> getQuestionToAnserMap() {
		return questionToAnserMap;
	}

	public void setQuestionToAnserMap(
			Map<QuestionEntity, List<AnswerEntity>> questionToAnserMap) {
		this.questionToAnserMap = questionToAnserMap;
	}
}
