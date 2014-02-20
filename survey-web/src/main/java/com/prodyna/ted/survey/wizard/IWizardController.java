package com.prodyna.ted.survey.wizard;

import java.io.Serializable;


public interface IWizardController extends Serializable {

	void nextStep();
	
	void previousStep();
	
	void add(WizardStep step);
	
	WizardStep getStep();
}
