package com.service;


import javax.persistence.EntityManager;
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
			System.out.println("etkinlik servicede:"+etkinlik.getId());
			em.getTransaction().commit();
			return etkinlik;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

}
