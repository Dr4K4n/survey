package com.prodyna.ted.survey.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.Pattern;

/**
 * Survey Entity.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
@Entity
@Table(name = "SURVEY")
public class SurveyEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "SURVEY_ID")
	private List<QuestionEntity> questions = new ArrayList<QuestionEntity>();

	@Pattern(regexp="[a-zA-Z0-9?!,.]*")
	@Column(name = "NAME", length = 50, nullable = false)
	private String name;

	@Column(name = "LINK")
	private String link;

	@Column(name = "ADMIN_LINK")
	private String adminLink;

	@Column(name = "FROM_DATE")
	private Date fromDate;

	@Future
	@Column(name = "TO_DATE")
	private Date toDate;

	public List<QuestionEntity> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionEntity> questions) {
		this.questions = questions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getAdminLink() {
		return adminLink;
	}

	public void setAdminLink(String adminLink) {
		this.adminLink = adminLink;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

}
