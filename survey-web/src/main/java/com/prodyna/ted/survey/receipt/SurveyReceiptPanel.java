package com.prodyna.ted.survey.receipt;

import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.prodyna.ted.survey.entity.QuestionEntity;
import com.prodyna.ted.survey.entity.SurveyEntity;
import com.prodyna.ted.survey.perform.SurveyPage;
import com.prodyna.ted.survey.update.UpdateSurveyPage;
import com.prodyna.ted.survey.wizard.WizardStep;

/**
 * Receipt Panel for survey inputs.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
public class SurveyReceiptPanel extends WizardStep {

	private static final long serialVersionUID = 1L;

	public SurveyReceiptPanel(final IModel<SurveyEntity> model) {
		Form<Void> form = new Form<Void>("form");
		add(form);
		
		form.add(new Label("name", new PropertyModel<String>(model, "name")));
		form.add(new DateLabel("fromDate", new PropertyModel<Date>(model, "fromDate"), new PatternDateConverter("dd.MM.yyyy HH:mm", false)));
		form.add(new DateLabel("toDate", new PropertyModel<Date>(model, "toDate"), new PatternDateConverter("dd.MM.yyyy HH:mm", false)));
		Link<String> adminLink = new AjaxFallbackLink<String>("adminLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(new UpdateSurveyPage(new PageParameters().add("id", model.getObject().getId())));
			}
		};
		adminLink.add(new Label("adminLinkText", new StringResourceModel("adminLinkText", SurveyReceiptPanel.this, null, new PropertyModel<Long>(model, "id"))));
		form.add(adminLink);
		Link<String> surveyLink = new AjaxFallbackLink<String>("surveyLink") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(new SurveyPage(new PageParameters().add("id", model.getObject().getId())));
			}
		};
		surveyLink.add(new Label("surveyLinkText", new StringResourceModel("surveyLinkText", SurveyReceiptPanel.this, null, new PropertyModel<Long>(model, "id"))));
		form.add(surveyLink);
		final ListView<QuestionEntity> questionView = new ListView<QuestionEntity>("questionView", new PropertyModel<List<QuestionEntity>>(model, "questions")) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<QuestionEntity> item) {
				item.add(new Label("questionLabel", new StringResourceModel("questionLabel", SurveyReceiptPanel.this, null, item.getIndex() + 1)));
				item.add(new Label("question", new PropertyModel<String>(item.getModel(), "question")));
			}
		};
		form.add(questionView);
	}

}
