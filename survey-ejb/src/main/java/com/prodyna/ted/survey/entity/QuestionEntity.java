package com.prodyna.ted.survey.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity for Question.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
@Entity
@Table(name = "QUESTION")
public class QuestionEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name="QUESTION", length = 200)
	private String question;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
}
