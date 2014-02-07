package com.prodyna.ted.survey.persistence;

import com.prodyna.ted.survey.entity.BaseEntity;
import com.prodyna.ted.survey.exception.PersistenceException;

/**
 * Basic Persistence Services.
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
public interface PersistenceService {

	<T extends BaseEntity> T create(T entity) throws PersistenceException;
	<T extends BaseEntity> T update(T entity) throws PersistenceException;
	<T extends BaseEntity> T delete(T entity) throws PersistenceException;
	<T extends BaseEntity> PersistenceQuery<T> createQuery(String queryString, Class<T> type)
			throws PersistenceException;
}
