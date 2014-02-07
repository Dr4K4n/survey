package com.prodyna.ted.survey.condition;

import org.apache.wicket.model.IModel;

public class ConditionOperation {

	public static <T> Condition<Boolean> isTrue(IModel<Boolean> model) {
		return new AbstractCondition<Boolean>(model) {
			private static final long serialVersionUID = 1L;

			@Override
			protected boolean isFulfilled(Boolean modelObject) {
				return modelObject;
			}
		};
	}

	public static <T> Condition<Boolean> isFalse(IModel<Boolean> model) {
		return new AbstractCondition<Boolean>(model) {
			private static final long serialVersionUID = 1L;
			
			@Override
			protected boolean isFulfilled(Boolean modelObject) {
				return !modelObject;
			}
		};
	}
	
	public static <T> Condition<T> isNull(IModel<T> model) {
		return new AbstractCondition<T>(model) {
			private static final long serialVersionUID = 1L;
			
			@Override
			protected boolean isFulfilled(T modelObject) {
				return null == modelObject;
			}
		};
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static <T> Condition<Condition[]> and(Condition... model) {
		return new AbstractCondition<Condition[]>(model) {
			private static final long serialVersionUID = 1L;
			
			@Override
			protected boolean isFulfilled(Condition[] modelObject) {
				for (int i = 0; i < modelObject.length; i++) {
					if (!modelObject[i].isFulfilled()) {
						return false;
					}
				}
				return true;
			}
		};
	}

	@SuppressWarnings({ "rawtypes" })
    public static <T> Condition<Condition[]> or(Condition... model) {
		return new AbstractCondition<Condition[]>(model) {
            private static final long serialVersionUID = 1L;

			@Override
            protected boolean isFulfilled(Condition[] modelObject) {
				for (int i = 0; i < modelObject.length; i++) {
					if (modelObject[i].isFulfilled()) {
						return true;
					}
				}
	            return false;
            }
		};
	}
}
