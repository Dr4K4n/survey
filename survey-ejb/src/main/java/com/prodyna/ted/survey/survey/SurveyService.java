package com.prodyna.ted.survey.survey;

import java.util.List;

import com.prodyna.ted.survey.entity.AnswerEntity;
import com.prodyna.ted.survey.entity.SurveyEntity;
import com.prodyna.ted.survey.exception.FunctionalException;

/**
 * CRUD Services for {@link SurveyEntity}.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
public interface SurveyService {

	/**
	 * Creates a Survey.
	 * 
	 * @param survey Survey to create.
	 * @return persisted Survey.
	 * @throws FunctionalException if an error occurs.
	 */
    SurveyEntity createSurvey(SurveyEntity survey) throws FunctionalException;
    
    /**
     * Deletes the given Survey.
     * 
     * @param survey Survey to delete
     * @return deleted survey.
     * @throws FunctionalException if an error occurs.
     */
    SurveyEntity deleteSurvey(SurveyEntity survey) throws FunctionalException;
    
    /**
     * Updates the given Survey.
     * 
     * @param survey Survey to Update.
     * @return updated Survey.
     * @throws FunctionalException if an error occurs.
     */
    SurveyEntity updateSurvey(SurveyEntity survey) throws FunctionalException;
    
    /**
     * Find Survey by id.
     * 
     * @param id Id of Survey.
     * @return the id matching Survey.
     * @throws FunctionalException if an error occurs.
     */
    SurveyEntity findSurveyById(Long id) throws FunctionalException;
    
    /**
     * Find all Surveys.
     * 
     * @return List of all Surveys.
     * @throws FunctionalException if an error occurs.
     */
    List<SurveyEntity> findAllSurvey() throws FunctionalException;
    
    /**
     * Persists the given Answers.
     * 
     * @param answerList Answers to persist.
     * @throws FunctionalException if an error occurs.
     */
    void persistAllAnswer(List<AnswerEntity> answerList) throws FunctionalException;
}

