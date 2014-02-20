package com.prodyna.ted.survey;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.markup.html.form.Form;

import com.prodyna.ted.survey.wizard.IWizard;
import com.prodyna.ted.survey.wizard.WizardStep;

public class Step extends WizardStep {

	public Step() {
		Form<Void> form = new Form<Void>("form");
		add(form);
		AjaxFallbackButton weiterButton = new AjaxFallbackButton("weiterButton", form) {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				IWizard wizard = getWizard();
				wizard.nextStep();
				wizard.updateWizard();
			}
		};
		form.add(weiterButton);
	}
}
