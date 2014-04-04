package com.prodyna.ted.survey.survey.impl;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.prodyna.ted.survey.entity.AnswerEntity;
import com.prodyna.ted.survey.entity.SurveyEntity;
import com.prodyna.ted.survey.exception.FunctionalException;
import com.prodyna.ted.survey.exception.PersistenceException;
import com.prodyna.ted.survey.persistence.Logging;
import com.prodyna.ted.survey.persistence.PersistenceQuery;
import com.prodyna.ted.survey.persistence.PersistenceService;
import com.prodyna.ted.survey.survey.SurveyService;

/**
 * Implementation of {@link SurveyService}.
 * 
 * @author Daniel Knipping, PRODYNA AG
 */
@Stateless
@Logging
public class SurveyServiceImpl implements SurveyService {

    @Inject
    private PersistenceService persistenceService;

    @Override
    public SurveyEntity createSurvey(SurveyEntity survey) throws FunctionalException {
        try {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<SurveyEntity>> validate = validator.validate(survey);
            for (ConstraintViolation<SurveyEntity> constraintViolation : validate) {
                throw new FunctionalException(constraintViolation.getMessageTemplate() + constraintViolation.getMessage());
            }
            SurveyEntity surveyEntity = persistenceService.create(survey);
            return surveyEntity;
        } catch (PersistenceException e) {
            throw new FunctionalException();
        }
    }

    @Override
    public SurveyEntity deleteSurvey(SurveyEntity survey) throws FunctionalException {
        try {
            SurveyEntity findSurveyById = findSurveyById(survey.getId());
            SurveyEntity delete = persistenceService.delete(findSurveyById);
            return delete;
        } catch (PersistenceException e) {
            throw new FunctionalException();
        }
    }

    @Override
    public SurveyEntity updateSurvey(SurveyEntity survey) throws FunctionalException {
        try {
            SurveyEntity update = persistenceService.update(survey);
            return update;
        } catch (PersistenceException e) {
            throw new FunctionalException();
        }
    }

    @Override
    public SurveyEntity findSurveyById(Long id) throws FunctionalException {
        try {
            PersistenceQuery<SurveyEntity> query = persistenceService.createQuery("Select s from SurveyEntity s where id = :id", SurveyEntity.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (PersistenceException e) {
            throw new FunctionalException();
        }
    }

    @Override
    public List<SurveyEntity> findAllSurvey() throws FunctionalException {
        try {
            return persistenceService.createQuery("Select s from SurveyEntity s", SurveyEntity.class).getResultList();
        } catch (PersistenceException e) {
            throw new FunctionalException();
        }
    }

    @Override
    public void persistAllAnswer(List<AnswerEntity> answerList) throws FunctionalException {
        for (AnswerEntity answerEntity : answerList) {
            try {
                persistenceService.create(answerEntity);
            } catch (PersistenceException e) {
                throw new FunctionalException();
            }
        }
    }
}
