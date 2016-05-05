package com.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.entity.Kullanici;
import com.entity.Makale;
import com.entity.MakaleMetadata;



public class MakaleMetadataService extends AbstractService<MakaleMetadata> {

	public MakaleMetadataService() {
		super(MakaleMetadata.class);
		// TODO Auto-generated constructor stub
	}

	// test edildi
	public void createMakaleMetadata(MakaleMetadata makaleMetadata,int makaleId) {
		EntityManager em = getEmf().createEntityManager();

		try {
			em.getTransaction().begin();
			Makale makale = em.find(Makale.class,makaleId);
			//Kullanici kullanici = em.find(Kullanici.class,kullaniciId);
			makaleMetadata.setMakale(makale);
			//makaleMetadata.setKullanici(kullanici);
			makale.setMakaleMetadata(makaleMetadata);
			//kullanici.getMetadata().add(makaleMetadata);
			em.persist(makaleMetadata);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	
	
	public void updateMakaleMetadata(int makaleId,int kullaniciId,String makaleNot,int makalePuan,String makaleKonu) {
		EntityManager em = getEmf().createEntityManager();

		try {
			em.getTransaction().begin();
			Kullanici kullanici = em.find(Kullanici.class, kullaniciId);
			MakaleMetadata metadata= findSpecMakaleMetadata(makaleId);
			metadata = em.merge(metadata);
			//System.out.println("bulunan MakaleMetadata:" + MakaleMetadata.getMakaleMetadatabasSayfaNo());
			String etkinlikBilgi = "";
			if(makaleNot != null){
				metadata.setMakaleNot(makaleNot);
				etkinlikBilgi+="Not";
			}
			if(makalePuan != 0){
				metadata.setMakalePuan(makalePuan);
				etkinlikBilgi+=",Puan";
			}
			if(makaleKonu != null){
				metadata.setMakaleKonu(makaleKonu);	
				etkinlikBilgi+=",Konu";
			}
			
			//Makale makale = em.find(Makale.class, makaleId);
			EtkinlikService ets = new EtkinlikService();
			if(makaleKonu != null || makaleNot != null || makalePuan != 0)
				kullanici.getEtkinlikler().add(ets.createEtkinlik(kullaniciId, "kullanici","makale metadata" +etkinlikBilgi + " bilgileri güncellendi"));
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	public MakaleMetadata findSpecMakaleMetadata(int makaleId){
		EntityManager em = getEmf().createEntityManager();
		try {
			Query query = em.createQuery("select mmd from com.entity.MakaleMetadata mmd where mmd.makale.id=:makId");
			// join m.kullanicilar k where k.kullanicilar_id=:k_Id");
			query.setParameter("makId", makaleId);
			//query.setParameter("k_Id", kullaniciId);
			return (MakaleMetadata)query.getSingleResult();
			
		} catch (NoResultException e) {
			// No matching result so return null
			return null;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		
	}
	//bir kullanýcýnýn bütün makalelerine ait metadalarý siler
	public void deleteMakaleMetadatalar(Kullanici kullanici){
		EntityManager em = getEmf().createEntityManager();

		try {
			em.getTransaction().begin();
			MakaleService ms = new MakaleService();
			System.out.println(""+kullanici.getKullaniciAd() + "kullanýcýs");
			List<Makale> makaleler = ms.makaleByKullaniciId(kullanici.getId());
			System.out.println("makale sayýsýs:"+ makaleler.size());
			for(Makale makale : makaleler){
				MakaleMetadata mm = findSpecMakaleMetadata(makale.getId());
				if(mm != null)
					em.remove(em.merge(mm));
				
			}
			em.getTransaction().commit();
		}finally {
			if (em != null) {
				em.close();
			}
		}
		
	}
	//bir kullanici bir makalesine ait metadatasýný silmek isterse bu metod çaðýrýlýr.
	public void deleteMakaleMetadata(MakaleMetadata mm){
		EntityManager em = getEmf().createEntityManager();

		try {
			em.getTransaction().begin();
			//Kullanici kullanici = em.find(Kullanici.class, kullaniciId);
			//Makale makale = em.find(Makale.class, makaleId);
			//MakaleMeta
			//MakaleMetadata mm = findSpecMakaleMetadata(kullaniciId, makaleId);
			//makale.getMakaleMetadata().remove(mm);
			//kullanici.getMetadata().remove(mm);
			//makale.getKullanicilar().remove(kullanici);
			em.remove(em.merge(mm));
//			EtkinlikService ets = new EtkinlikService();
//			kullanici.getEtkinlikler().add(ets.createEtkinlik(kullanici.getId(), "makalemetadata",makale.getMakaleAdi()+  "metadatasý silindi"));
		
			
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
}
