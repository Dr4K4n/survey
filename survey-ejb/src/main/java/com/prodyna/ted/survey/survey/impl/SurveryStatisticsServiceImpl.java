package com.prodyna.ted.survey.survey.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.prodyna.ted.survey.entity.QuestionEntity;
import com.prodyna.ted.survey.entity.Rating;
import com.prodyna.ted.survey.entity.SurveyEntity;
import com.prodyna.ted.survey.statistics.entity.SurveyQuestionAnswerStatistic;
import com.prodyna.ted.survey.statistics.entity.SurveyQuestionRatingStatistic;
import com.prodyna.ted.survey.survey.SurveryStatisticsService;

public class SurveryStatisticsServiceImpl implements SurveryStatisticsService {

    @Inject
    private EntityManager entityManager;

    @Override
    public SurveyQuestionRatingStatistic getQuestionRatingStatisticForSurvey(long surveyId) {

        SurveyQuestionRatingStatistic statistic = new SurveyQuestionRatingStatistic();
        Map<QuestionEntity, Rating> questionRatingMap = new HashMap<QuestionEntity, Rating>();

        SurveyEntity surveyEntity = entityManager.find(SurveyEntity.class, surveyId);

        List<QuestionEntity> questions = surveyEntity.getQuestions();

        for (QuestionEntity question : questions) {

            String jpql = "SELECT rating, count(*) FROM AnswerEntity WHERE questionEntity = :questionEntity GROUP BY rating ";

            Query query = entityManager.createQuery(jpql);
            query.setParameter("questionEntity", question);

            List<Object[]> resultList = query.getResultList();

            long maxCount = 0;
            Rating maxRating = null;

            for (Object[] objects : resultList) {

                Rating rating = (Rating) objects[0];
                long count = (Long) objects[1];

                if (count > maxCount) {
                    maxCount = count;
                    maxRating = rating;
                }
            }

            questionRatingMap.put(question, maxRating);
        }

        statistic.setSurvey(surveyEntity);
        statistic.setQuestionRatingMap(questionRatingMap);

        return statistic;
    }

    @Override
    public SurveyQuestionAnswerStatistic getQuestionAnswerStatisticForSurvey(long surveyId) {
        // TODO Auto-generated method stub
        return null;
    }

}
