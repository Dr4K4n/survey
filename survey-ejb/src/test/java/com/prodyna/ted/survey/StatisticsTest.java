package com.prodyna.ted.survey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.ted.survey.entity.AnswerEntity;
import com.prodyna.ted.survey.entity.QuestionEntity;
import com.prodyna.ted.survey.entity.Rating;
import com.prodyna.ted.survey.entity.SurveyEntity;
import com.prodyna.ted.survey.exception.FunctionalException;
import com.prodyna.ted.survey.statistics.entity.SurveyQuestionRatingStatistic;
import com.prodyna.ted.survey.survey.SurveryStatisticsService;
import com.prodyna.ted.survey.survey.SurveyService;

@RunWith(Arquillian.class)
public class StatisticsTest {

    @Inject
    private SurveryStatisticsService service;

    @Inject
    private SurveyService surveyService;

    @Deployment
    public static Archive<?> createDeployment() {
        JavaArchive archive =
            ShrinkWrap.create(JavaArchive.class, "StatisticsServiceTest.jar").addPackages(true, "com.prodyna.ted.survey")
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml").addAsManifestResource("META-INF/beans.xml", "beans.xml");
        return archive;
    }

    @Test
    public void testQuestionRatingStatisticForSurvey() throws FunctionalException {

        SurveyEntity entity = new SurveyEntity();
        entity.setName("Test");
        QuestionEntity question = new QuestionEntity();
        question.setQuestion("first Question?");
        entity.getQuestions().add(question);
        // question = new QuestionEntity();
        // question.setQuestion("second Question?");
        // entity.getQuestions().add(question);
        SurveyEntity survey = surveyService.createSurvey(entity);
        Assert.assertNotNull(survey.getId());
        Assert.assertEquals(1, survey.getQuestions().size());

        List<AnswerEntity> answers = new ArrayList<AnswerEntity>();
        AnswerEntity answer = new AnswerEntity();
        answer.setQuestionEntity(entity.getQuestions().get(0));
        answer.setRating(Rating.ONE);
        answers.add(answer);
        AnswerEntity answer1 = new AnswerEntity();
        answer1.setQuestionEntity(entity.getQuestions().get(0));
        answer1.setRating(Rating.ONE);
        answers.add(answer1);
        AnswerEntity answer3 = new AnswerEntity();
        answer3.setQuestionEntity(entity.getQuestions().get(0));
        answer3.setRating(Rating.TWO);
        answers.add(answer3);

        // AnswerEntity answer2 = new AnswerEntity();
        // answer2.setQuestionEntity(entity.getQuestions().get(1));
        // answer2.setRating(Rating.FOUR);
        // answers.add(answer2);
        surveyService.persistAllAnswer(answers);

        SurveyQuestionRatingStatistic statisticForSurvey = this.service.getQuestionRatingStatisticForSurvey(entity.getId());
        String description = statisticForSurvey.getDescription();
        SurveyEntity statisticSurvey = statisticForSurvey.getSurvey();
        Assert.assertNotNull(description);
        Assert.assertEquals(statisticSurvey.getId(), entity.getId());

        Map<QuestionEntity, Rating> questionRatingMap = statisticForSurvey.getQuestionRatingMap();
        Assert.assertNotNull(questionRatingMap);
        Assert.assertFalse(questionRatingMap.isEmpty());
        Assert.assertTrue(questionRatingMap.size() == 1);

        for (Entry<QuestionEntity, Rating> entry : questionRatingMap.entrySet()) {

            QuestionEntity questionEntity = entry.getKey();

            Assert.assertEquals(entity.getQuestions().get(0), questionEntity);
            Rating rating = entry.getValue();
            Assert.assertEquals(rating, Rating.ONE);

        }

    }
}
