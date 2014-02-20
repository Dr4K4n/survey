package com.prodyna.ted.survey.wizard;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;

public class Wizard extends Panel implements IWizard {

	private static final long serialVersionUID = 1L;
	private static final String FORM_ID = "form";

	private IWizardController controller;
	
	public Wizard(String id, IModel<?> model) {
		super(id, model);
		init();
	}
	
	public Wizard(String id) {
		super(id);
		init();
	}
	
	private void init() {
		Form<Void> form = new Form<Void>(FORM_ID);
		form.setOutputMarkupId(true);
		add(form);
		form.add(new EmptyPanel(WizardStep.WIZARD_STEP_ID));
		controller = newWizardController();
	}
	
	public void start() {
		getForm().addOrReplace(controller.getStep());
		AjaxRequestTarget target = getTarget();
		if (target != null) {
			target.add(getForm());
		}
	}
	
	public final void updateWizard() {
		AjaxRequestTarget target = getTarget();
		if (target != null) {
			target.add(getForm());
		}
	}
	
	public final void nextStep() {
		controller.nextStep();
		getForm().addOrReplace(controller.getStep());
		
	}
	
	public final void previousStep() {
		controller.previousStep();
		getForm().addOrReplace(controller.getStep());
	}
	
	public Form<?> getForm() {
		return (Form<?>) get(FORM_ID);
	}
	
	private AjaxRequestTarget getTarget() {
		return RequestCycle.get().find(AjaxRequestTarget.class);
	}
	
	protected IWizardController newWizardController() {
		return new WizardController();
	}
	
	public final IWizardController getController() {
		return controller;
	}
}
