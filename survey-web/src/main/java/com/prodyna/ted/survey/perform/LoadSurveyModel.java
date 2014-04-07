package com.prodyna.ted.survey.perform;

import javax.inject.Inject;

import org.apache.wicket.cdi.CdiContainer;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.util.string.StringValueConversionException;

import com.prodyna.ted.survey.entity.SurveyEntity;
import com.prodyna.ted.survey.survey.SurveyService;

/**
 * Load survey by id.
 * 
 * @author Daniel Knipping, PRODYNA AG
 */
public class LoadSurveyModel extends LoadableDetachableModel<SurveyEntity> {

    private static final long serialVersionUID = 1L;

    private final Long id;

    @Inject
    private SurveyService service;

    public LoadSurveyModel(Long id) {
        this.id = id;
        CdiContainer.get().getNonContextualManager().inject(this);
    }

    @Override
    protected SurveyEntity load() {
        try {
            if (id == null) {
                return null;
            }
            return service.findSurveyById(id).getResult();
        } catch (StringValueConversionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
