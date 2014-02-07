package com.prodyna.ted.survey;

import java.util.ArrayList;
import java.util.List;

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
import com.prodyna.ted.survey.survey.SurveyService;

/**
 * Tests the {@link SurveyEntity}
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
@RunWith(Arquillian.class)
public class SurveyTest {

	@Inject
	private SurveyService surveyService;

	@Deployment
	public static Archive<?> createDeployment() {
		JavaArchive archive = ShrinkWrap
				.create(JavaArchive.class, "SurveyServiceTest.jar")
				.addPackages(true, "com.prodyna.ted.survey")
				.addAsManifestResource("META-INF/persistence.xml",
						"persistence.xml")
				.addAsManifestResource("META-INF/beans.xml", "beans.xml");
		return archive;
	}
	
	@Test
	public void testSurvey() throws FunctionalException {
		SurveyEntity entity = new SurveyEntity();
		entity.setName("Test");
		SurveyEntity survey = surveyService.createSurvey(entity);
		Assert.assertNotNull(survey.getId());
		survey.setName("Test2");
		SurveyEntity updateSurvey = surveyService.updateSurvey(survey);
		Assert.assertEquals(new Long(1), updateSurvey.getId());
		Assert.assertEquals("Test2", updateSurvey.getName());
		SurveyEntity deleteSurvey = surveyService.deleteSurvey(survey);
		boolean deleted = false;
		try {
			surveyService.findSurveyById(deleteSurvey.getId());
		} catch (FunctionalException e) {
			deleted = true;
		}
		Assert.assertTrue(deleted);
	}

	@Test
	public void testPersistQuestions() throws FunctionalException {
		SurveyEntity entity = new SurveyEntity();
		entity.setName("Test");
		QuestionEntity question = new QuestionEntity();
		question.setQuestion("first Question?");
		entity.getQuestions().add(question);
		question = new QuestionEntity();
		question.setQuestion("second Question?");
		entity.getQuestions().add(question);
		SurveyEntity survey = surveyService.createSurvey(entity);
		Assert.assertNotNull(survey.getId());
		Assert.assertEquals(2, survey.getQuestions().size());
		
		List<AnswerEntity> answers = new ArrayList<AnswerEntity>();
		AnswerEntity answer = new AnswerEntity();
		answer.setQuestionEntity(entity.getQuestions().get(0));
		answer.setRating(Rating.ONE);
		answers.add(answer);
		AnswerEntity answer2 = new AnswerEntity();
		answer2.setQuestionEntity(entity.getQuestions().get(1));
		answer2.setRating(Rating.FOUR);
		answers.add(answer2);
		surveyService.persistAllAnswer(answers);
	}
}
