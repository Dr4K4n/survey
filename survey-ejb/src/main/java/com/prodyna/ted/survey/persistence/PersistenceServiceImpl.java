package com.prodyna.ted.survey.persistence;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.prodyna.ted.survey.entity.BaseEntity;
import com.prodyna.ted.survey.exception.PersistenceException;

/**
 * Generic implementation CRUD Services. 
 * 
 * @author Daniel Knipping, PRODYNA AG
 *
 */
public class PersistenceServiceImpl implements PersistenceService {

	@Inject
	private EntityManager em;

	@Override
	public <T extends BaseEntity> T create(T entity)
			throws PersistenceException {
		try {
			em.persist(entity);
			return entity;
		} catch (Exception e) {
			throw new PersistenceException("Failed to persist Entity" + e.getClass(), e);
		}
	}

	@Override
	public <T extends BaseEntity> T update(T entity) throws PersistenceException {
		try {
		em.merge(entity);
		return entity;
		} catch (Exception e) {
			throw new PersistenceException("Failed to update Entity" + entity.getClass(), e);
		}
	}

	@Override
	public <T extends BaseEntity> T delete(T entity) throws PersistenceException {
		try {
			em.remove(entity);
		} catch (Exception e) {
			throw new PersistenceException("Failed to delete Entity" + entity.getClass(), e);
		}
		return entity;
	}
	
	@Override
	public <T extends BaseEntity> PersistenceQuery<T> createQuery(String queryString, Class<T> type)
			throws PersistenceException {

		try {
			return new JpaQuery<T>(this.em.createQuery(queryString, type), queryString);
		} catch (Exception e) {
			throw new PersistenceException("Cannot create Query '" + type + "' for Entity '" + type.getCanonicalName()
					+ "'.", e);
		}
	}


}
