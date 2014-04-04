package com.prodyna.ted.survey.survey;

import java.util.List;

import com.prodyna.ted.survey.common.ServiceResult;
import com.prodyna.ted.survey.entity.AnswerEntity;
import com.prodyna.ted.survey.entity.SurveyEntity;
import com.prodyna.ted.survey.exception.FunctionalException;

/**
 * CRUD Services for {@link SurveyEntity}.
 * 
 * @author Daniel Knipping, PRODYNA AG
 */
public interface SurveyService {

    /**
     * Creates a Survey.
     * 
     * @param survey Survey to create.
     * @return persisted Survey.
     * @throws FunctionalException if an error occurs.
     */
    ServiceResult<SurveyEntity> createSurvey(SurveyEntity survey);

    /**
     * Deletes the given Survey.
     * 
     * @param survey Survey to delete
     * @return deleted survey.
     * @throws FunctionalException if an error occurs.
     */
    ServiceResult<SurveyEntity> deleteSurvey(SurveyEntity survey);

    /**
     * Updates the given Survey.
     * 
     * @param survey Survey to Update.
     * @return updated Survey.
     * @throws FunctionalException if an error occurs.
     */
    ServiceResult<SurveyEntity> updateSurvey(SurveyEntity survey);

    /**
     * Find Survey by id.
     * 
     * @param id Id of Survey.
     * @return the id matching Survey.
     * @throws FunctionalException if an error occurs.
     */
    ServiceResult<SurveyEntity> findSurveyById(Long id);

    /**
     * Find all Surveys.
     * 
     * @return List of all Surveys.
     * @throws FunctionalException if an error occurs.
     */
    ServiceResult<List<SurveyEntity>> findAllSurvey();

    /**
     * Persists the given Answers.
     * 
     * @param answerList Answers to persist.
     * @throws FunctionalException if an error occurs.
     */
    void persistAllAnswer(List<AnswerEntity> answerList);
}
