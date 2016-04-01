package com.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class AbstractService<T> {
	
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("MakaleYonetimPersistenceUnit");
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	private EntityManager em ;
	private Class<T> entityClass;
	
	public AbstractService(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

//	public void save(T entity) {
//        em.persist(entity);
//    }
	public void create(T entity) {
		em.persist(entity);
	}
	
	public void update(T entity) {
		em.merge(entity);
	}

	public void remove(T entity) {
		em.remove(em.merge(entity));
	}

	public T find(Object id) {
		return em.find(entityClass, id);
	}

	public List<T> findAll() {
		javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return em.createQuery(cq).getResultList();
	}
}
