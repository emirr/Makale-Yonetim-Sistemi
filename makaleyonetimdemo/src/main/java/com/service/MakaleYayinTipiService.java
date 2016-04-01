package com.service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.entity.Makale;
import com.entity.MakaleYayinTipi;



public class MakaleYayinTipiService extends AbstractService<MakaleYayinTipi> {

	public MakaleYayinTipiService() {
		super(MakaleYayinTipi.class);
		// TODO Auto-generated constructor stub
	}

	// test edildi
	public void createMakaleYayinTipi(MakaleYayinTipi makaleYayinTipi) {
		EntityManager em = getEmf().createEntityManager();

		try {
			em.getTransaction().begin();
			//makale.setTip eklenebilir
			em.persist(makaleYayinTipi);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	
	
	public void updateMakaleYayinTipi(int makaleId,boolean isBildiri, boolean isKonferans, boolean isTez, boolean isKitap) {
		EntityManager em = getEmf().createEntityManager();

		try {
			em.getTransaction().begin();
			//MakaleYayinTipi yayinTipi = em.find(MakaleYayinTipi.class, yayinTipiId);;
			//yayinTipi = em.merge(yayinTipi);
			Makale makale = em.find(Makale.class, makaleId);
			MakaleYayinTipi yayinTipi = makale.getMakaleYayinTipi();
			//System.out.println("bulunan MakaleYayinTipi:" + MakaleYayinTipi.getMakaleYayinTipibasSayfaNo());
			if (isBildiri == true){
				yayinTipi.setBildiri(isBildiri);
			}else{ if(isKonferans == true){
					yayinTipi.setKonferans(isKonferans);
				  }
				  else {if(isTez == true){
						yayinTipi.setTez(isTez);
				  }
				  else
					yayinTipi.setKitap(isKitap);
				  }
			}
		//	Makale makale = em.find(Makale.class, makaleId);
			EtkinlikService ets = new EtkinlikService();
			makale.getKullanicilar().getEtkinlikler().add(ets.createEtkinlik(makale.getKullanicilar().getId(), "makaleYayintip",makale.getMakaleAdi() + "yayin tipi güncelleme"));
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	//denenecek
	public String tipForMakale(int makaleId){
		EntityManager em = getEmf().createEntityManager();
		try {
			Makale makale = em.find(Makale.class, makaleId);
			
			// query.setParameter("isOnayli", true);
			return makale.getMakaleYayinTipi().toString();
		} 
		finally {
			if (em != null) {
				em.close();
			}
		}
	}
	public void deleteMakaleYayinTipi(MakaleYayinTipi myt){
		EntityManager em = getEmf().createEntityManager();
		try {
			em.getTransaction().begin();
			//Makale makale = em.find(Makale.class, makaleId);
			//MakaleYayinTipi myt = makale.getMakaleYayinTipi();
			// query.setParameter("isOnayli", true);
			myt = em.merge(myt);
			em.remove(myt);
			em.getTransaction().commit();
			//return makale.getMakaleYayinTipi().toString();
		}  finally {
			if (em != null) {
				em.close();
			}
		}
	}
}
