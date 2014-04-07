package com.prodyna.ted.survey.survey.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.prodyna.ted.survey.entity.AnswerEntity;
import com.prodyna.ted.survey.entity.QuestionEntity;
import com.prodyna.ted.survey.entity.Rating;
import com.prodyna.ted.survey.entity.SurveyEntity;
import com.prodyna.ted.survey.statistics.entity.Statistics;
import com.prodyna.ted.survey.statistics.entity.SurveyStatistics1;
import com.prodyna.ted.survey.statistics.entity.SurveyStatistics2;
import com.prodyna.ted.survey.survey.SurveryStatisticsService;

public class SurveyStatisticsServiceImplMock implements SurveryStatisticsService, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public List<Statistics> getAllStatistics() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SurveyStatistics1 getStatistics1ForSurvey(SurveyEntity survey) {
        SurveyStatistics1 ss1 = new SurveyStatistics1();
        ss1.setNumberOfAnswers(23);
        ss1.setNumberOfQuestions(3);
        ss1.setSurvey(survey);
        return ss1;
    }

    @Override
    public SurveyStatistics2 getStatistics2ForSurvey(SurveyEntity survey) {
        SurveyStatistics2 ss2 = new SurveyStatistics2();
        ss2.setSurvey(survey);
        Map<QuestionEntity, List<AnswerEntity>> q2aMap = new HashMap<QuestionEntity, List<AnswerEntity>>();
        q2aMap.put(survey.getQuestions().get(0),
            Arrays.asList(getAnswer(), getAnswer(), getAnswer(), getAnswer(), getAnswer(), getAnswer(), getAnswer()));
        q2aMap.put(survey.getQuestions().get(1),
            Arrays.asList(getAnswer(), getAnswer(), getAnswer(), getAnswer(), getAnswer(), getAnswer(), getAnswer(), getAnswer(), getAnswer()));
        q2aMap.put(survey.getQuestions().get(2),
            Arrays.asList(getAnswer(), getAnswer(), getAnswer(), getAnswer(), getAnswer(), getAnswer(), getAnswer(), getAnswer(), getAnswer()));
        q2aMap.put(survey.getQuestions().get(3),
            Arrays.asList(getAnswer(), getAnswer(), getAnswer(), getAnswer(), getAnswer(), getAnswer(), getAnswer(), getAnswer(), getAnswer()));
        ss2.setQuestionToAnserMap(q2aMap);
        return ss2;
    }

    private AnswerEntity getAnswer() {
        AnswerEntity answerEntity = new AnswerEntity();
        answerEntity.setCreated(new Date());
        answerEntity.setModified(new Date());
        answerEntity.setRating(ratings[(int) ((Math.random() * 5) % 5)]);
        return answerEntity;
    }

    private static Rating[] ratings = new Rating[]{Rating.ONE, Rating.TWO, Rating.THREE, Rating.FOUR, Rating.FIFE};

}
