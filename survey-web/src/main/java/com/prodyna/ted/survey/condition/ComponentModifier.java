package com.prodyna.ted.survey.condition;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.request.cycle.RequestCycle;

public abstract class ComponentModifier<T> extends Behavior {

	private static final long serialVersionUID = 1L;

	protected Condition<T> condition;

	public ComponentModifier(Condition<T> condition) {
		this.condition = condition;
	}

	@Override
	public void bind(Component component) {
		if (component == null) {
			throw new IllegalArgumentException("Component cannot be null");
		}
	}
	
	@Override
	public void detach(Component component) {
		super.detach(component);
		if (condition != null) {
			condition.detach();
		}
	}

	@Override
	public void onConfigure(Component component) {
		applyConditions(RequestCycle.get().find(AjaxRequestTarget.class), component);
	}

	protected abstract void applyConditions(AjaxRequestTarget target, Component component);

	public static <T> ComponentModifier<T> setVisibleIf(Condition<T> condition) {
		return new ComponentModifier<T>(condition) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void applyConditions(AjaxRequestTarget target, Component component) {
				component.setVisibilityAllowed(condition.isFulfilled());
			}
		};
	}

	public static <T> ComponentModifier<T> setInvisibleIf(Condition<T> condition) {
		return new ComponentModifier<T>(condition) {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void applyConditions(AjaxRequestTarget target, Component component) {
				component.setVisibilityAllowed(!condition.isFulfilled());
			}
		};
	}

	public static <T> ComponentModifier<T> setEnableIf(Condition<T> condition) {
		return new ComponentModifier<T>(condition) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void applyConditions(AjaxRequestTarget target, Component component) {
				component.setEnabled(condition.isFulfilled());
			}
		};
	}

	public static <T> ComponentModifier<T> setDisableIf(Condition<T> condition) {
		return new ComponentModifier<T>(condition) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void applyConditions(AjaxRequestTarget target, Component component) {
				component.setEnabled(!condition.isFulfilled());
			}
		};
	}

	public static <T> ComponentModifier<T> setFocusIf(Condition<T> condition) {
		return new ComponentModifier<T>(condition) {
			
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void applyConditions(AjaxRequestTarget target, Component component) {
				if (condition.isFulfilled()) {
					if (target != null) {
						target.focusComponent(component);
					}
				}
			}
		};
	}
}
