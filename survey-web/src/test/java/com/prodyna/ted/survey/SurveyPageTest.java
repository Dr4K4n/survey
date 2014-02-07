package com.prodyna.ted.survey;

import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.wicket.bean.validation.BeanValidationConfiguration;
import org.apache.wicket.cdi.CdiConfiguration;
import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

import com.prodyna.ted.survey.perform.SurveyPage;

public class SurveyPageTest {

	@Test
	public void testSurveyPage() {
		WicketTester tester = new WicketTester(new TestApplication());
		tester.startPage(new SurveyPage(new PageParameters().add("id", 1L)));
	}
	
	private static class TestApplication extends MockApplication {
		@Override
		protected void init() {
			super.init();
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
}
