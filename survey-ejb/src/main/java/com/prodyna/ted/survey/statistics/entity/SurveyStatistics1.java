package com.prodyna.ted.survey.statistics.entity;

import com.prodyna.ted.survey.entity.SurveyEntity;

public class SurveyStatistics1 extends Statistics {

	private static final long serialVersionUID = 7033082962881002152L;

	private SurveyEntity survey;
	private int numberOfQuestions;
	private int numberOfAnswers;

	public SurveyStatistics1() {
		super("Number of questions and answers for a specific survey.");
	}

	public SurveyEntity getSurvey() {
		return survey;
	}

	public void setSurvey(SurveyEntity survey) {
		this.survey = survey;
	}

	public int getNumberOfQuestions() {
		return numberOfQuestions;
	}

	public void setNumberOfQuestions(int numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}

	public int getNumberOfAnswers() {
		return numberOfAnswers;
	}

	public void setNumberOfAnswers(int numberOfAnswers) {
		this.numberOfAnswers = numberOfAnswers;
	}
}
