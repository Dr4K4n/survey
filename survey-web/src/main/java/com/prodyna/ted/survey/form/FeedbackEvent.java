package com.prodyna.ted.survey.form;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * Event for feedbacks.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
public class FeedbackEvent {

	private AjaxRequestTarget target;

	public FeedbackEvent(AjaxRequestTarget target) {
		this.target = target;
	}
	
	public AjaxRequestTarget getTarget() {
		return target;
	}
}
