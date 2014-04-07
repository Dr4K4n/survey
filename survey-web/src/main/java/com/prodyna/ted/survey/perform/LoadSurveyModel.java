package com.prodyna.ted.survey.perform;

import javax.inject.Inject;

import org.apache.wicket.cdi.CdiContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.StringValueConversionException;

import com.prodyna.ted.survey.entity.SurveyEntity;
import com.prodyna.ted.survey.exception.FunctionalException;
import com.prodyna.ted.survey.survey.SurveyService;

/**
 * Load survey by id.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
public class LoadSurveyModel implements IModel<SurveyEntity> {

	private static final long serialVersionUID = 1L;

	private Long id;

	@Inject
	private SurveyService service;
	
	private SurveyEntity surveyEntity;
	
	public LoadSurveyModel(Long id) {
		this.id = id;
		CdiContainer.get().getNonContextualManager().inject(this);
	}
	
	@Override
	public SurveyEntity getObject() {
		try {
			if (id == null) {
				return null;
			}
			// Load survey once from server and save reference
			if (surveyEntity == null) {
			    surveyEntity = service.findSurveyById(id);
			}
            return surveyEntity;
		} catch (StringValueConversionException e) {
			e.printStackTrace();
		} catch (FunctionalException e) {
			e.printStackTrace();
		}
		return null;
	}

    @Override
    public void detach() {
        // nothing to detach
    }

    @Override
    public void setObject(SurveyEntity object) {
        this.surveyEntity = object;
    }
}
