package com.prodyna.ted.survey;

import org.apache.wicket.mock.MockApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

import com.prodyna.ted.survey.create.CreateSurveyPage;

public class CreateSurveyPageTest {

	@Test
	public void testSurveyPage() {
		WicketTester tester = new WicketTester(new MockApplication());
		tester.startPage(new CreateSurveyPage());
	}
}
