package com.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.entity.Dergi;
import com.entity.Makale;
import com.entity.Referans;
import com.entity.Yazar;

public class YazarService extends AbstractService<Yazar> {

	public YazarService() {
		super(Yazar.class);
		// TODO Auto-generated constructor stub
	}
	//AYNI ÝSÝMLÝ ÝKÝ YAZAR SÝSTEMDE BARINMAMALI.
	public void createYazarForReferans(Referans referans, HashSet<Yazar> yazarlar1) {
		EntityManager em = getEmf().createEntityManager();

		try {
			
			for(Yazar yazar: yazarlar1){
				//System.out.println("yazar");
				
				String ad = yazar.getYazarAdi();
				//List<Yazar> yazarlar = tumYazarlar();
				Yazar yzr = findYazarByAd(ad);
				//List<Referans> ref=null;
				
				if(yzr!=null){
					//ref = yazar.getReferansler();
					em.getTransaction().begin();
					referans = em.merge(referans);
					yzr = em.merge(yzr);
					referans.getYazarlar().add(yzr);
					yzr.getReferansler().add(referans);
					em.getTransaction().commit();
					// if(findExistYazar(ad,ref) == null)
					// em.persist(yazar);
					// else
					System.out.println("bu ada  sahip bir yazar zaten var:" + yazar.getYazarAdi());
				}
				else{
					em.getTransaction().begin();
					referans = em.merge(referans);
					if(referans.getYazarlar() == null)
						System.out.println("yazar referanslarý null");
					referans.getYazarlar().add(yazar);
					yazar.getReferansler().add(referans);
					em.persist(yazar);
					em.getTransaction().commit();
					
				}
					
				
				//System.out.println("yazarlaradi:"+yazarlar.getYazarAdi());
				//System.out.println("yazar" + yazarlar.getYazarAdi());
				
//				if(findExistYazar(ad,ref) == null)
//					em.persist(yazar);
//				else
//					System.out.println("bu ada  sahip bir yazar zaten var:" + yazar.getYazarAdi());
				// makale.getKullanicilar().add(kullanici);
				// kullanici.getMakaleler().add(makale);

				// burada makaleetkinlik'e eklemeler yapýlacak,kullanýcýOnaylama
				// alaný true yapýlacak

			}
			
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	public void updateYazar(int yazarId,String yazarAd,int makaleId){
		EntityManager em = getEmf().createEntityManager();

		try {
			em.getTransaction().begin();
			Yazar yazar = em.find(Yazar.class, yazarId);
			yazar = em.merge(yazar);
			if(yazarAd != null)
				yazar.setYazarAdi(yazarAd);
			Makale makale = em.find(Makale.class, makaleId);
			EtkinlikService ets = new EtkinlikService();
			if(yazarAd != null)
				makale.getKullanicilar().getEtkinlikler().add(ets.createEtkinlik(makale.getKullanicilar().getId(), "yazar",makale.getMakaleAdi() + "yazar adý güncellendi"));
			em.getTransaction().commit();
		}finally {
			if (em != null) {
				em.close();
			}
		}
	}
	//select y.yazaradi from yazar y where y.id in (select yazarlar_id from yazar_makale where 1)
	//yazarbymakale sorgusu
	public Yazar findYazarByAd(String ad) {
		EntityManager em = getEmf().createEntityManager();
		try {
			// Kullanici kullanici = em.find(Kullanici.class, ad);
			// return kullanici;
			Query query = em.createQuery("select y from com.entity.Yazar y WHERE y.yazarAdi=:isim");
			query.setParameter("isim", ad);
			return (Yazar) query.getSingleResult();
		} catch (NoResultException e) {
			// No matching result so return null
			return null;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	public Yazar findYazarById(int id) {
		EntityManager em = getEmf().createEntityManager();
		try {
			Yazar yazar = em.find(Yazar.class, id);
			return yazar;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	public Yazar findExistYazar(String yazarAd,List<Referans> yazarinReferanslari){
		EntityManager em = getEmf().createEntityManager();

		try {
			
			Query query = em.createQuery(
					"select y from com.entity.Yazar y WHERE y.yazarAdi=:ad and y.referanslar IN :refs");
			query.setParameter("ad", yazarAd);
			query.setParameter("refs", yazarinReferanslari);
			System.out.println("yazar adi:"+yazarAd);
			if(yazarinReferanslari!=null)
				System.out.println("yazarrefs.syýsýs:" + yazarinReferanslari.size());
			return (Yazar) query.getSingleResult();
			
		}catch (NoResultException e) {
			// No matching result so return null
			return null;
		}finally {
			if (em != null) {
				em.close();
			}
		}
	
	}
	public long referansAdetForYazar(int yazarId) {
		EntityManager em = getEmf().createEntityManager();
		try {
			//Referans ref = em.find(Referans.class, refId);
			Query query = em.createQuery("select count(y.referanslar.id) from com.entity.Yazar y  WHERE y.id=:yzr");
			query.setParameter("yzr", yazarId);
			// query.setParameter("isOnayli", true);
			return (long) query.getSingleResult();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	public void deleteYazar(Yazar yazar){
		EntityManager em = getEmf().createEntityManager();
		try {
			em.getTransaction().begin();
			// Makale makale = em.find(Makale.class, makaleId);
			// Referans ref = makale.getReferans();
			// query.setParameter("isOnayli", true);
			em.remove(em.merge(yazar));
			em.getTransaction().commit();
		}finally {
			if (em != null) {
				em.close();
			}
		}
	}
	//select y.yazaradi from yazar y where y.id in (select yazarlar_id from yazar_makale where makaleler_id = 10)
		//yazarbymakale sql sorgusu
	public List<Yazar> findYazarByMakaleReferans(Referans ref) {
		EntityManager em = getEmf().createEntityManager();
		try {
			Query query = em.createQuery("select y from com.entity.Yazar y where y.referanslar=:makaleRef");
			query.setParameter("makaleRef", ref);
			return query.getResultList();
		}catch (NoResultException e) {
			// No matching result so return null
			return null;
		}  finally {
			if (em != null) {
				em.close();
			}
		}
	}
	//findByMakalenin ikamesi-->daha hýzlý çalýþaný seç-test et
	public Set<Yazar> yazarForMakale(int makaleId) {
		EntityManager em = getEmf().createEntityManager();
		try {
			Makale makale = em.find(Makale.class, makaleId);
			return makale.getReferans().getYazarlar();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	
	//test edildi
	public List<Yazar> tumYazarlar() {
		EntityManager em = getEmf().createEntityManager();
		try {
			Query query = em.createQuery("select y from com.entity.Yazar y");
			// query.setParameter("isOnayli", true);
			return query.getResultList();
		} catch (NoResultException e) {
			// No matching result so return null
			return null;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
}
