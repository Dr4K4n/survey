package com.prodyna.ted.survey.edit;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.extensions.wizard.WizardStep;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;

import com.prodyna.ted.survey.entity.QuestionEntity;
import com.prodyna.ted.survey.entity.SurveyEntity;
import com.prodyna.ted.survey.form.FeedbackEvent;
import com.prodyna.ted.survey.form.FormComponentFeedback;

/**
 * Input Panel for survey data.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
public abstract class SurveyInputPanel extends WizardStep {

	private static final long serialVersionUID = 1L;

	public SurveyInputPanel(final IModel<SurveyEntity> surveyModel) {
		setOutputMarkupId(true);
		Form<Void> form = new Form<Void>("form");
		add(form);
		
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
		form.add(feedbackPanel);
		
		form.add(new Label("nameLabel", new ResourceModel("nameLabel")));
		TextField<String> nameTextField = new TextField<String>("nameTextField", new PropertyModel<String>(surveyModel, "name"));
//		nameTextField.add(new PropertyValidator<String>());
		nameTextField.setRequired(true);
		form.add(nameTextField);
		form.add(new FormComponentFeedback(nameTextField));
		
		form.add(new Label("fromDateLabel", new ResourceModel("fromDateLabel")));
		form.add(new TextField<String>("fromDateTextField", new PropertyModel<String>(surveyModel, "fromDate")).add(new DatePicker()));
		
		form.add(new Label("toDateLabel", new ResourceModel("toDateLabel")));
		form.add(new TextField<String>("toDateTextField", new PropertyModel<String>(surveyModel, "toDate")).add(new DatePicker()));
		
		final WebMarkupContainer listViewContainer = new WebMarkupContainer("container");
		listViewContainer.setOutputMarkupId(true);
		form.add(listViewContainer);
		final ListView<QuestionEntity> questionView = new ListView<QuestionEntity>("questionView", new PropertyModel<List<QuestionEntity>>(surveyModel, "questions")) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<QuestionEntity> item) {
				item.add(new Label("questionLabel", new StringResourceModel("questionLabel", SurveyInputPanel.this, null, item.getIndex() + 1)).setOutputMarkupId(true));
				TextField<String> questionTextField = new TextField<String>("questionTextField", new PropertyModel<String>(item.getModel(), "question"));
				item.add(new FormComponentFeedback(questionTextField));
				questionTextField.setRequired(true);
				item.add(questionTextField.setOutputMarkupId(true));
			}
		};
		questionView.setOutputMarkupId(true);
		listViewContainer.add(questionView);
		
		form.add(new Button("saveSurveyButton") {

			private static final long serialVersionUID = 1L;
			
			@Override
			public void onSubmit() {
				super.onSubmit();
				SurveyInputPanel.this.doAction();
			}
		});
		form.add(new AddQuestionButton("addQuestionButton", form, surveyModel));
		
	}
	
	private final class AddQuestionButton extends AjaxFallbackButton {
		private final IModel<SurveyEntity> surveyModel;
		private static final long serialVersionUID = 1L;

		private AddQuestionButton(String id, Form<?> form,
				IModel<SurveyEntity> surveyModel) {
			super(id, form);
			this.surveyModel = surveyModel;
		}

		@Override
		protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
			super.onSubmit(target, form);
			surveyModel.getObject().getQuestions().add(new QuestionEntity());
			target.add(SurveyInputPanel.this.get("form:container"));
		}

		@Override
		protected void onError(AjaxRequestTarget target, Form<?> form) {
			super.onError(target, form);
			send(getPage(), Broadcast.BREADTH, new FeedbackEvent(target));
		}
		
		@Override
		public void detachModels() {
			super.detachModels();
			surveyModel.detach();
		}
	}
	
	protected abstract void doAction();
	
}
