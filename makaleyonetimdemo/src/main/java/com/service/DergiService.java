package com.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.entity.Dergi;
import com.entity.Makale;
import com.entity.Referans;
import com.entity.Yazar;

public class DergiService extends AbstractService<Dergi> {

	public DergiService() {
		super(Dergi.class);
		// TODO Auto-generated constructor stub
	}

	// test edildi
	public void createDergi(Dergi dergi) {
		EntityManager em = getEmf().createEntityManager();

		try {
			em.getTransaction().begin();
			em.persist(dergi);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void updateDergi(int dergiId, String dergiAd, int makaleId) {
		EntityManager em = getEmf().createEntityManager();

		try {
			em.getTransaction().begin();
			Dergi dergi = em.find(Dergi.class, dergiId);
			dergi = em.merge(dergi);
			if (dergiAd != null)
				dergi.setDergiAdi(dergiAd);
			Makale makale = em.find(Makale.class, makaleId);
			EtkinlikService ets = new EtkinlikService();
			makale.getKullanicilar().getEtkinlikler().add(ets.createEtkinlik(makale.getKullanicilar().getId(), "dergi",
					makale.getReferans().getDergi().getDergiAdi() + "dergi ad güncelleme"));
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public Dergi findExistDergi(Dergi d) {
		EntityManager em = getEmf().createEntityManager();

		try {
			Query query = em.createQuery("select d from com.entity.Dergi d WHERE d.dergiAdi=:ad");
			query.setParameter("ad", d.getDergiAdi());
			return (Dergi) query.getSingleResult();

		} catch (NoResultException e) {
			// No matching result so return null
			return null;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public Dergi findDergiByAd(String ad) {
		EntityManager em = getEmf().createEntityManager();
		try {
			// Kullanici kullanici = em.find(Kullanici.class, ad);
			// return kullanici;
			Query query = em.createQuery("select d from com.entity.Dergi d WHERE d.dergiAdi=:isim");
			query.setParameter("isim", ad);
			return (Dergi) query.getSingleResult();
		} catch (NoResultException e) {
			// No matching result so return null
			return null;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public long referansAdetForDergi(int dergiId) {
		EntityManager em = getEmf().createEntityManager();
		try {
			// Referans ref = em.find(Referans.class, refId);
			Query query = em.createQuery("select count(r.id) from com.entity.Referans r  WHERE r.DERGI.id=:d_Id");
			query.setParameter("d_Id", dergiId);
			// query.setParameter("isOnayli", true);
			return (long) query.getSingleResult();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	public void deleteDergi(Dergi d){
		EntityManager em = getEmf().createEntityManager();
		try {
			em.getTransaction().begin();
			em.remove(em.merge(d));
			em.getTransaction().commit();
		}finally {
			if (em != null) {
				em.close();
			}
		}
	}
}
