package com.prodyna.ted.survey.condition;

import org.apache.wicket.model.IModel;

public interface Condition<T> extends IModel<T> {

	boolean isFulfilled();
}
