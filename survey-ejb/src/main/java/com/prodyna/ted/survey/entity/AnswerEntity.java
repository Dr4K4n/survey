package com.prodyna.ted.survey.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Answer Entity.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
@Entity
@Table(name = "ANSWER")
public class AnswerEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "TEXT", length = 100)
	private String text;
	
	@Column(name = "RATING", nullable = false)
	@Enumerated(EnumType.STRING)
	private Rating rating;
	
	@OneToOne
	@JoinColumn(name = "ANSWER_ID")
	private QuestionEntity questionEntity;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public QuestionEntity getQuestionEntity() {
		return questionEntity;
	}

	public void setQuestionEntity(QuestionEntity questionEntity) {
		this.questionEntity = questionEntity;
	}
	
}
