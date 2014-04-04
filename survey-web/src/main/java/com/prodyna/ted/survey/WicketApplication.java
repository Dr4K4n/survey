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
import com.prodyna.ted.survey.statistic.SelectSurveyPage;
import com.prodyna.ted.survey.update.UpdateSurveyPage;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.agilecoders.wicket.webjars.WicketWebjars;
import de.agilecoders.wicket.webjars.collectors.VfsJarAssetPathCollector;
import de.agilecoders.wicket.webjars.settings.WebjarsSettings;

/**
 * Settings and configuration of application.
 * 
 * @author Daniel Knipping, PRODYNA AG
 */
public class WicketApplication extends WebApplication {

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends WebPage> getHomePage() {
        return CreateSurveyPage.class;
    }

    /**
     * @see org.apache.wicket.Application#init()
     */
    @Override
    public void init() {
        super.init();

        configureCdi();

        if (getConfigurationType() == RuntimeConfigurationType.DEVELOPMENT) {
            getDebugSettings().setAjaxDebugModeEnabled(true);
        }

        // Mount Pages
        mountPage("/survey", SurveyPage.class);
        mountPage("/surveyAdmin", UpdateSurveyPage.class);
        mountPage("/statistic", SelectSurveyPage.class);

        // Configure Error Page
        getApplicationSettings().setInternalErrorPage(ErrorPage.class);
        getMarkupSettings().setStripWicketTags(true);

        WebjarsSettings webjarsSettings = new WebjarsSettings();
        webjarsSettings.assetPathCollectors(new VfsJarAssetPathCollector());

        WicketWebjars.install(this, webjarsSettings);
        BootstrapSettings settings = new BootstrapSettings();

        Bootstrap.install(this, settings);
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
