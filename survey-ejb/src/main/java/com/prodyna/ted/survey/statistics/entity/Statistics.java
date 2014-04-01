package com.prodyna.ted.survey.statistics.entity;

import java.io.Serializable;

public class Statistics implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String description;

	public Statistics(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
