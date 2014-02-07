package com.prodyna.ted.survey.condition;

import org.apache.wicket.model.IModel;

public abstract class AbstractCondition<T> implements Condition<T> {

	private static final long serialVersionUID = 1L;
	private Object model;

	public AbstractCondition(T model) {
		this.model = model;
	}
	
	public AbstractCondition(IModel<T> model) {
		this.model = model;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
	public T getObject() {
		if (model instanceof IModel) {
			return (T) ((IModel) model).getObject();
		}
		return (T) model;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
	public void setObject(T object) {
		if (model instanceof IModel) {
			((IModel) model).setObject(object);
		}
		model = object;
	}

	@SuppressWarnings("rawtypes")
    @Override
	public void detach() {
		if (model instanceof IModel) {
			((IModel) model).detach();
		}
	}
	
	@Override
	public boolean isFulfilled() {
		return AbstractCondition.this.isFulfilled(getObject());
	}
	
	protected abstract boolean isFulfilled(T modelObject);
}