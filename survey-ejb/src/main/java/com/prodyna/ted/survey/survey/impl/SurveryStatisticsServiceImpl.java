package com.prodyna.ted.survey.survey.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.prodyna.ted.survey.entity.AnswerEntity;
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
        SurveyEntity surveyEntity = entityManager.find(SurveyEntity.class, surveyId);
        SurveyQuestionAnswerStatistic result = new SurveyQuestionAnswerStatistic();

        Map<QuestionEntity, List<AnswerEntity>> map = new HashMap<QuestionEntity, List<AnswerEntity>>();
        result.setQuestionToAnserMap(map);

        for (QuestionEntity qe : surveyEntity.getQuestions()) {
            map.put(qe, getAnswers(qe));
        }
        return result;
    }

    private List<AnswerEntity> getAnswers(QuestionEntity qe) {
        List<AnswerEntity> result = new ArrayList<AnswerEntity>();
        int count = (int) Math.random() * 10 + 5;
        for (int i = 0; i < count; i++) {
            AnswerEntity ae = new AnswerEntity();
            switch ((int) (Math.random() * 10) % 5) {
                case 0:
                    ae.setRating(Rating.ONE);
                    break;
                case 1:
                    ae.setRating(Rating.TWO);
                    break;
                case 2:
                    ae.setRating(Rating.THREE);
                    break;
                case 3:
                    ae.setRating(Rating.FOUR);
                    break;
                case 4:
                default:
                    ae.setRating(Rating.FIVE);
                    break;

            }

            ae.setQuestionEntity(qe);
            result.add(ae);
        }

        return result;
    }

}
