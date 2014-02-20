package com.prodyna.ted.survey.wizard;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class WizardStep extends Panel implements IWizardStep {
	
	public static final String WIZARD_STEP_ID = "wizardStep";
	private static final long serialVersionUID = 1L;
	
	public WizardStep(IModel<?> model) {
		super(WIZARD_STEP_ID, model);
	}

	public WizardStep() {
		super(WIZARD_STEP_ID);
	}
	
	public IWizard getWizard() {
		return findParent(IWizard.class);
	}

}
