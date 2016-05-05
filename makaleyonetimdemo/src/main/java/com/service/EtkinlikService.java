package com.service;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.entity.Etkinlik;
import com.entity.Kullanici;

public class EtkinlikService extends AbstractService<Etkinlik> {
	public EtkinlikService() {
		super(Etkinlik.class);
		// TODO Auto-generated constructor stub
	}
	public Etkinlik createEtkinlik(int kullaniciId, String etkinlikTabloAdi, String etkinlikTuru){
		EntityManager em = getEmf().createEntityManager();

		try {
			em.getTransaction().begin();
			long etkinlikZamani = System.currentTimeMillis();
			Kullanici kullanici = em.find(Kullanici.class, kullaniciId);
			Etkinlik etkinlik = new Etkinlik(etkinlikTabloAdi,etkinlikTuru,etkinlikZamani,kullanici);
			
			em.persist(etkinlik);
			em.getTransaction().commit();
			System.out.println("etkinlik servicede:"+etkinlik.getId());
			
			return etkinlik;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	public List<Etkinlik> retrieveEtkinlikByIdDesc(int kId) {
		EntityManager em = getEmf().createEntityManager();
		try {
			// Kullanici kullanici = em.find(Kullanici.class, ad);
			// return kullanici;
			Query query = em.createQuery("select e from com.entity.Etkinlik e JOIN e.kullanici k where k.id=e.kullanici.id and k.id=:k_Id ORDER BY e.id DESC");
			query.setParameter("k_Id", kId);
			return  query.getResultList();
		}catch (NoResultException e) {
			// No matching result so return null
			return null;
		}  finally {
			if (em != null) {
				em.close();
			}
		}
	}
}
