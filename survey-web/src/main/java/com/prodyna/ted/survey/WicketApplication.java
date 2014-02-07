package com.prodyna.ted.survey;

import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.bean.validation.BeanValidationConfiguration;
import org.apache.wicket.cdi.CdiConfiguration;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;

import com.prodyna.ted.survey.create.CreateSurveyPage;
import com.prodyna.ted.survey.page.ErrorPage;
import com.prodyna.ted.survey.perform.SurveyPage;
import com.prodyna.ted.survey.update.UpdateSurveyPage;

/**
 * Settings and configuration of application.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
public class WicketApplication extends WebApplication
{    	

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return CreateSurveyPage.class;
	}
	

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();
		
        configureCdi();
        
        if (getConfigurationType() == RuntimeConfigurationType.DEVELOPMENT) {
        	getDebugSettings().setAjaxDebugModeEnabled(true);
        }
		
		// Mount Pages 
		mountPage("/survey", SurveyPage.class);
		mountPage("/surveyAdmin", UpdateSurveyPage.class);
		
		// Configure Error Page
		getApplicationSettings().setInternalErrorPage(ErrorPage.class);
	}


	private void configureCdi() {
		BeanManager manager = null;
		try {
			manager = (BeanManager) new InitialContext().lookup("java:comp/BeanManager");
			new CdiConfiguration(manager).configure(this);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		new BeanValidationConfiguration().configure(this);
	}
}
