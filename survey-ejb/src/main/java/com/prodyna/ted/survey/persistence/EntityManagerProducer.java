package com.prodyna.ted.survey.persistence;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Produces an {@link EntityManager}.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
public class EntityManagerProducer {

	@Produces
	@PersistenceContext(unitName = "surveyPu")
	private EntityManager entityManager;
}
