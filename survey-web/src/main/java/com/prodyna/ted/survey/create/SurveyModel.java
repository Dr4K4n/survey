package com.prodyna.ted.survey.create;

import org.apache.wicket.model.IModel;

import com.prodyna.ted.survey.entity.QuestionEntity;
import com.prodyna.ted.survey.entity.SurveyEntity;

/**
 * Model to create an empty Survey.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
public class SurveyModel implements IModel<SurveyEntity> {

	private static final long serialVersionUID = 1L;

	private SurveyEntity entity;

	public SurveyModel() {
	}

	@Override
	public SurveyEntity getObject() {
		if (entity == null) {
			entity = new SurveyEntity();
			entity.getQuestions().add(new QuestionEntity());
		}
		return entity;
	}

	@Override
	public void setObject(SurveyEntity entity) {
		this.entity = entity;
	}

	@Override
	public void detach() {
	}
}
