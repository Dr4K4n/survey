package com.prodyna.ted.survey.survey.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.prodyna.ted.survey.common.Message;
import com.prodyna.ted.survey.common.Message.Severity;
import com.prodyna.ted.survey.common.ServiceResult;
import com.prodyna.ted.survey.entity.AnswerEntity;
import com.prodyna.ted.survey.entity.SurveyEntity;
import com.prodyna.ted.survey.exception.FunctionalRuntimeException;
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
    public ServiceResult<SurveyEntity> createSurvey(SurveyEntity survey) {
        try {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<SurveyEntity>> validate = validator.validate(survey);
            List<Message> messages = new ArrayList<Message>();
            for (ConstraintViolation<SurveyEntity> constraintViolation : validate) {
                messages.add(new Message(constraintViolation.getMessage(), constraintViolation.getMessage(), Severity.WARN));
            }
            SurveyEntity surveyEntity;
            if (messages.isEmpty()) {
                surveyEntity = persistenceService.create(survey);
            } else {
                surveyEntity = survey;
            }
            return ServiceResult.createServiceResult(surveyEntity, messages);

        } catch (PersistenceException e) {
            throw new FunctionalRuntimeException(e.getMessage());
        }
    }

    @Override
    public ServiceResult<SurveyEntity> deleteSurvey(SurveyEntity survey) {
        try {
            ServiceResult<SurveyEntity> findSurveyById = findSurveyById(survey.getId());
            SurveyEntity delete;
            if (findSurveyById.getMessages().isEmpty()) {
                delete = persistenceService.delete(findSurveyById.getResult());
            } else {
                delete = survey;
            }
            return ServiceResult.createServiceResult(delete, findSurveyById.getMessages());
        } catch (PersistenceException e) {
            throw new FunctionalRuntimeException(e.getMessage());
        }
    }

    @Override
    public ServiceResult<SurveyEntity> updateSurvey(SurveyEntity survey) {
        try {
            SurveyEntity update = persistenceService.update(survey);
            return ServiceResult.createServiceResultNoMessage(update);
        } catch (PersistenceException e) {
            throw new FunctionalRuntimeException(e.getMessage());
        }
    }

    @Override
    public ServiceResult<SurveyEntity> findSurveyById(Long id) {
        try {
            PersistenceQuery<SurveyEntity> query = persistenceService.createQuery("Select s from SurveyEntity s where id = :id", SurveyEntity.class);
            query.setParameter("id", id);
            return ServiceResult.createServiceResultNoMessage(query.getSingleResult());
        } catch (PersistenceException e) {
            throw new FunctionalRuntimeException(e.getMessage());
        }
    }

    @Override
    public ServiceResult<List<SurveyEntity>> findAllSurvey() {
        try {
            List<SurveyEntity> resultList = persistenceService.createQuery("Select s from SurveyEntity s", SurveyEntity.class).getResultList();
            return ServiceResult.createServiceResultNoMessage(resultList);
        } catch (PersistenceException e) {
            throw new FunctionalRuntimeException(e.getCause());
        }
    }

    @Override
    public void persistAllAnswer(List<AnswerEntity> answerList) {
        for (AnswerEntity answerEntity : answerList) {
            try {
                persistenceService.create(answerEntity);
            } catch (PersistenceException e) {
                throw new FunctionalRuntimeException(e.getMessage());
            }
        }
    }
}
