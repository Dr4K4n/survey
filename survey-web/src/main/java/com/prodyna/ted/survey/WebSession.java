package com.prodyna.ted.survey;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

/**
 * WebSession implementation.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
public class WebSession extends AuthenticatedWebSession {

	private static final long serialVersionUID = 1L;

	public static WebSession get() {
		return (WebSession) Session.get();
	}
	
	
	public WebSession(Request request) {
		super(request);
	}


	@Override
	public boolean authenticate(String arg0, String arg1) {
		return false;
	}


	@Override
	public Roles getRoles() {
		return null;
	}

}
