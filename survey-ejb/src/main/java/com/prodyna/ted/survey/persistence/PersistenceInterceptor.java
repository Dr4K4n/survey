package com.prodyna.ted.survey.persistence;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interceptor for logging service calls.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
@Interceptor
@Logging
public class PersistenceInterceptor {

	private static final Logger LOG = LoggerFactory.getLogger(PersistenceInterceptor.class);
	
	@AroundInvoke
	public Object doLogging(InvocationContext context) throws Exception {
		Object proceed = null;
		try {
			long beforeMethodCall = System.currentTimeMillis();
			proceed = context.proceed();
			long afterMethodCall = System.currentTimeMillis();
			LOG.info("Calling Service " + context.getMethod().getName() + "  " + (afterMethodCall - beforeMethodCall) + " ms");
		} catch (Exception e) {
			LOG.error("Error during service call", e);
		}
		return proceed;
	}
}
