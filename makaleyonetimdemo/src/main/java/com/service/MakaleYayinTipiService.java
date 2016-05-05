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
			yayinTipi = em.merge(yayinTipi);
			System.out.println("mevcut yayýn tipi:" + yayinTipi.toString());
			yayinTipi.setBildiri(false);
			yayinTipi.setKitap(false);
			yayinTipi.setKonferans(false);
			yayinTipi.setTez(false);
			//System.out.println("bulunan MakaleYayinTipi:" + MakaleYayinTipi.getMakaleYayinTipibasSayfaNo());
			if (isBildiri == true){
				yayinTipi.setBildiri(isBildiri);
				System.out.println("yayýn türü:" + "bildiri");
			}else{ if(isKonferans == true){
					yayinTipi.setKonferans(isKonferans);
					System.out.println("yayýn türü:" + "konferans");
				  }
				  else {if(isTez == true){
						yayinTipi.setTez(isTez);
						System.out.println("yayýn türü:" + "tez");
				  }
				  else{
					yayinTipi.setKitap(isKitap);
					System.out.println("yayýn türü:" + "kitap");
				  }
				  }
			}
		//	Makale makale = em.find(Makale.class, makaleId);
			em.getTransaction().commit();
			EtkinlikService ets = new EtkinlikService();
			if(isBildiri != false || isKonferans != false || isTez != false || isKitap != false)
				makale.getKullanicilar().getEtkinlikler().add(ets.createEtkinlik(makale.getKullanicilar().getId(), "makaleYayintip",makale.getMakaleAdi() + "yayin tipi güncellendi"));
			
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
