package com.prodyna.ted.survey.wizard;

import java.util.ArrayList;
import java.util.List;

public class WizardController implements IWizardController {

	private static final long serialVersionUID = 1L;

	private List<WizardStep> stack = new ArrayList<WizardStep>();

	private int currentStep = 0;
	
	@Override
	public void nextStep() {
		if (currentStep <= stack.size()) {
			currentStep++;
		}
	}

	@Override
	public void previousStep() {
		if (currentStep > 0) {
			currentStep--;
		}
	}

	@Override
	public void add(WizardStep step) {
		stack.add(step);
	}

	public WizardStep getStep() {
		return stack.get(currentStep);
	}

}
