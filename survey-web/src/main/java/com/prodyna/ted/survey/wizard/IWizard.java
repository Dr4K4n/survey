package com.prodyna.ted.survey.wizard;

import java.io.Serializable;

import org.apache.wicket.markup.html.form.Form;

public interface IWizard extends Serializable {

	Form<?> getForm();
	
	void nextStep();
	
	void previousStep();
	
	void updateWizard();
}
