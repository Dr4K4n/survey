package com.prodyna.ted.survey.form;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.FeedbackMessages;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;

/**
 * Feedback Panel to display errors at component.
 * If an error occurs at the given {@link Component}, this Panel
 * catches the feedback and reports it.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
public class FormComponentFeedback extends Panel {

	private static final long serialVersionUID = 1L;

	private Component component;

	/**
	 * Creates a new FormComponentFeedback.
	 * 
	 * @param component to observe for feedbacks.
	 */
	public FormComponentFeedback(final Component component) {

		super(component.getId() + "FeedbackPanel", new Model<String>());
		this.component = component;
		Label feedbackLabel = new Label("feedback", getDefaultModel());
		component
				.add(new AttributeAppender("class", new ErrorClass(component), " "));
		add(feedbackLabel);
		setOutputMarkupId(true);
	}

	@Override
	protected void onBeforeRender() {

		super.onBeforeRender();
		FeedbackMessages feedbackMessages = component.getFeedbackMessages();
		if (feedbackMessages != null && feedbackMessages.size() > 0) {
			for (FeedbackMessage feedbackMessage : feedbackMessages) {
				setDefaultModelObject(feedbackMessage.getMessage());
				feedbackMessage.markRendered();
				break;
			}
		} else {
			setDefaultModelObject(null);
		}
	}

	@Override
	public void onEvent(IEvent<?> event) {
		super.onEvent(event);
		if (event.getPayload() instanceof FeedbackEvent) {
			((FeedbackEvent) event.getPayload()).getTarget().add(this);
		}
	}

	/**
	 * Style class that will be added to {@link Component} if an error occrus.
	 * 
	 * @author Daniel Knipping, PRODYNA AG
	 *
	 */
	private static final class ErrorClass extends AbstractReadOnlyModel<String> {

		private static final long serialVersionUID = 1L;

		private final Component component;

		private ErrorClass(Component component) {

			this.component = component;
		}

		@Override
		public String getObject() {

			if (component.getFeedbackMessages().size() == 0) {
				return "";
			}
			return "has-error";
		}
	}
}
