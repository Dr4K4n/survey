package com.prodyna.ted.survey.perform;

import static com.prodyna.ted.survey.condition.ComponentModifier.setInvisibleIf;
import static com.prodyna.ted.survey.condition.ComponentModifier.setVisibleIf;
import static com.prodyna.ted.survey.condition.ConditionOperation.isNull;
import static com.prodyna.ted.survey.condition.ConditionOperation.or;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.apache.wicket.util.string.StringValueConversionException;

import com.prodyna.ted.survey.entity.AnswerEntity;
import com.prodyna.ted.survey.entity.QuestionEntity;
import com.prodyna.ted.survey.entity.Rating;
import com.prodyna.ted.survey.entity.SurveyEntity;
import com.prodyna.ted.survey.exception.FunctionalException;
import com.prodyna.ted.survey.page.SurveyBasePage;
import com.prodyna.ted.survey.survey.SurveyService;

/**
 * Page to perform a survey.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
public class SurveyPage extends SurveyBasePage {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private SurveyService service;
	
	public SurveyPage(PageParameters parameters) {
		StringValue idValue = parameters.get("id");
		IModel<SurveyEntity> model = new Model<SurveyEntity>();
		if (!idValue.isNull()) {
			try {
				Long longObject = idValue.toLongObject();
				model = new LoadSurveyModel(longObject);
			} catch (StringValueConversionException e) {
			}
		}
		
		Label noSurveyFound = new Label("noSurveyFound", new ResourceModel("noSurveyFround"));
		noSurveyFound.add(setVisibleIf(or(isNull(Model.of(idValue)), isNull(new PropertyModel<Long>(model, "id")))));
		add(noSurveyFound);
		
		Form<Void> form = new Form<Void>("form");
		form.add(setInvisibleIf(or(isNull(Model.of(idValue)), isNull(new PropertyModel<Long>(model, "id")))));
		add(form);
		
		form.add(new FeedbackPanel("feedbackPanel"));
		form.add(new Label("nameLabel", new PropertyModel<String>(model, "name")));
		
	    final IModel<List<AnswerEntity>> answerModel = new AnswerModel(new PropertyModel<List<QuestionEntity>>(model, "questions"));
		ListView<AnswerEntity> answers = new AnswersListView("answers", answerModel);
		form.add(answers);
		
		form.add(new SubmitSurveyButton("submit", answerModel));
	}
	
	private static final class AnswersListView extends ListView<AnswerEntity> {
		private static final long serialVersionUID = 1L;

		private AnswersListView(String id,
				IModel<? extends List<? extends AnswerEntity>> model) {
			super(id, model);
		}

		@Override
		protected void populateItem(ListItem<AnswerEntity> item) {
			item.add(new Label("questionLabel", new PropertyModel<String>(item.getModel(), "questionEntity.question")));
			RadioGroup<Rating> radioGroup = new RadioGroup<Rating>("ratingRadioGroup", new PropertyModel<Rating>(item.getModel(), "rating"));
			radioGroup.add(new Radio<Rating>("one", Model.of(Rating.ONE), radioGroup));
			radioGroup.add(new Radio<Rating>("two", Model.of(Rating.TWO), radioGroup));
			radioGroup.add(new Radio<Rating>("three", Model.of(Rating.THREE), radioGroup));
			radioGroup.add(new Radio<Rating>("four", Model.of(Rating.FOUR), radioGroup));
			radioGroup.add(new Radio<Rating>("fife", Model.of(Rating.FIFE), radioGroup));
			item.add(radioGroup);
		}
	}

	private final class SubmitSurveyButton extends Button {
		private final IModel<List<AnswerEntity>> answerModel;
		private static final long serialVersionUID = 1L;

		private SubmitSurveyButton(String id,
				IModel<List<AnswerEntity>> answerModel) {
			super(id);
			this.answerModel = answerModel;
		}

		@Override
		public void onSubmit() {
			super.onSubmit();
			try {
				service.persistAllAnswer(answerModel.getObject());
				info("Survey successful!");
			} catch (FunctionalException e) {
				error("Could not save survey");
				e.printStackTrace();
			}
		}
		
		@Override
		public void detachModels() {
			super.detachModels();
			answerModel.detach();
		}
	}

	private static class AnswerModel extends LoadableDetachableModel<List<AnswerEntity>> {
		private static final long serialVersionUID = 1L;
		
		private IModel<List<QuestionEntity>> questions;

		public AnswerModel(IModel<List<QuestionEntity>> questions) {
			this.questions = questions;
		}
		
		@Override
		protected List<AnswerEntity> load() {
			List<AnswerEntity> answers = new ArrayList<AnswerEntity>();
			for (QuestionEntity question : questions.getObject()) {
				AnswerEntity answer = new AnswerEntity();
				answer.setQuestionEntity(question);
				answers.add(answer);
			}
			return answers;
		}
		
		@Override
		protected void onDetach() {
			super.onDetach();
			questions.detach();
		}
	}

	@Override
	protected Component createTitle(String id) {
		return new Label(id, new ResourceModel("title"));
	}
}

