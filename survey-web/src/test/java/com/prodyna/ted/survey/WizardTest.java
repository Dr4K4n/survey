package com.prodyna.ted.survey;

import junit.framework.Assert;

import org.apache.wicket.Component;
import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

import com.prodyna.ted.survey.wizard.Wizard;
import com.prodyna.ted.survey.wizard.WizardStep;

public class WizardTest {

	@Test
	public void testWizard() {
		WicketTester tester = new WicketTester(new MockApplication());
		Wizard wizard = new Wizard("wizard");
		wizard.getController().add(new Step1());
		wizard.getController().add(new Step2());
		wizard.start();
		tester.startComponentInPage(wizard);
		Component component = wizard.getForm().get(WizardStep.WIZARD_STEP_ID);
		Assert.assertEquals(Step1.class, component.getClass());
		FormTester ft = tester.newFormTester("wizard:form");
		ft.submit("wizardStep:form:weiterButton");
		component = wizard.getForm().get(WizardStep.WIZARD_STEP_ID);
		Assert.assertEquals(Step2.class, component.getClass());
		tester.assertNoErrorMessage();
	}
	
	private class Step1 extends Step {
		
	}
	
	private class Step2 extends Step {
		
	}
}
