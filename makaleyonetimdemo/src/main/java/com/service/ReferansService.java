package com.service;

import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.entity.Dergi;
import com.entity.Kullanici;
import com.entity.Makale;
import com.entity.Referans;
import com.entity.Yazar;

public class ReferansService extends AbstractService<Referans> {

	public ReferansService() {
		super(Referans.class);
		// TODO Auto-generated constructor stub
	}

	// test edildi
	public void createReferans(Referans referans) {
		EntityManager em = getEmf().createEntityManager();

		try {
			em.getTransaction().begin();
			//referans.setDergi(d1);
			//System.out.println("dergi ad�:" + d1.getDergiAdi());
			em.persist(referans);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void updateReferans(int makaleId, long yil, int sayi, int ciltNo, int basSayfaNo, int sonSayfaNo, int ay, String kitapevi,String yer, String kurumad, String konferansad, String referansad, String dergiad) {
		EntityManager em = getEmf().createEntityManager();

		try {
			em.getTransaction().begin();
			Makale makale = em.find(Makale.class, makaleId);
			Referans referans = makale.getReferans();
			referans = em.merge(referans);
			// System.out.println("bulunan referans:" +
			// referans.getreferansbasSayfaNo());
			if (yil != 0)
				referans.setYil(yil);
			if (sayi != 0)
				referans.setSayi(sayi);
			if (ciltNo != 0) {
				// System.out.println("ciltNo:" + ciltNo);
				referans.setCiltNo(ciltNo);
				// System.out.println("referansgetunvsan:" +
				// referans.getciltNo());
			}
			if (basSayfaNo != 0)
				referans.setBasSayfaNo(basSayfaNo);
			if (sonSayfaNo != 0)
				referans.setSonSayfaNo(sonSayfaNo);
			if (ay != 0)
				referans.setAy(ay);
			if (kitapevi != null)
				referans.setKitapevi(kitapevi);
			if (yer != null)
				referans.setYer(yer);
			if (kurumad != null)
				referans.setKurumad(kurumad);
			if (konferansad != null)
				referans.setKonferansad(konferansad);
			if (referansad != null)
				referans.setReferansad(referansad);
			if (dergiad != null)
				referans.setDergiad(dergiad);
			// Makale makale = em.find(Makale.class, makaleId);
			EtkinlikService ets = new EtkinlikService();
			if(yil != 0 || sayi != 0 || ciltNo != 0 || basSayfaNo != 0 || sonSayfaNo != 0 || ay!=0 || kitapevi != null ||
					yer != null || kurumad != null || konferansad != null || referansad != null || dergiad != null){
				makale.getKullanicilar().getEtkinlikler().add(ets.createEtkinlik(makale.getKullanicilar().getId(),
					"referans", referans.getReferansad() + "referans bilgileri güncellendi."));
			}
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	public void duzeltReferans(Kullanici kullanici,Referans ref,long yil, int sayi, int ciltNo, int basSayfaNo, int sonSayfaNo, int ay, String kitapevi,String yer, String kurumad, String konferansad, String referansad, String dergiad) {
		EntityManager em = getEmf().createEntityManager();

		try {
			em.getTransaction().begin();
			//Makale makale = em.find(Makale.class, makaleId);
			Referans referans = em.merge(ref);
			
			// System.out.println("bulunan referans:" +
			// referans.getreferansbasSayfaNo());
			if (yil != 0)
				referans.setYil(yil);
			if (sayi != 0)
				referans.setSayi(sayi);
			if (ciltNo != 0) {
				// System.out.println("ciltNo:" + ciltNo);
				referans.setCiltNo(ciltNo);
				// System.out.println("referansgetunvsan:" +
				// referans.getciltNo());
			}
			if (basSayfaNo != 0)
				referans.setBasSayfaNo(basSayfaNo);
			if (sonSayfaNo != 0)
				referans.setSonSayfaNo(sonSayfaNo);
			if (ay != 0)
				referans.setAy(ay);
			if (kitapevi != null)
				referans.setKitapevi(kitapevi);
			if (yer != null)
				referans.setYer(yer);
			if (kurumad != null)
				referans.setKurumad(kurumad);
			if (konferansad != null)
				referans.setKonferansad(konferansad);
			if (referansad != null)
				referans.setReferansad(referansad);
			if (dergiad != null)
				referans.setDergiad(dergiad);
			// Makale makale = em.find(Makale.class, makaleId);
			EtkinlikService ets = new EtkinlikService();
			if(yil != 0 || sayi != 0 || ciltNo != 0 || basSayfaNo != 0 || sonSayfaNo != 0 || ay!=0 || kitapevi != null ||
					yer != null || kurumad != null || konferansad != null || referansad != null || dergiad != null){
				kullanici.getEtkinlikler().add(ets.createEtkinlik(kullanici.getId(),
					"referans", referans.getReferansad() + " referans bilgileri güncellendi."));
			}
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	public void updateReferansHata(Referans ref, int hataStat, String hatamsg){
		EntityManager em = getEmf().createEntityManager();

		try {
			em.getTransaction().begin();
			ref = em.merge(ref);
			ref.setReferanshata(hataStat);
			ref.setRefhatamsg(hatamsg);
			em.getTransaction().commit();

		}finally{
			if (em != null) {
				em.close();
			}
		}
	}
	public List<Referans> allRefs(){
		EntityManager em = getEmf().createEntityManager();
		try {
			Query query = em.createQuery("select r from com.entity.Referans r");
			
			return query.getResultList();
		}catch (NoResultException e) {
			System.out.println("null d�necek");
			return null;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	public List<Referans> hataliReferanslar(){
		EntityManager em = getEmf().createEntityManager();
		try {
			Query query = em.createQuery("select r from com.entity.Referans r WHERE r.referanshata=:refhata");
			query.setParameter("refhata", 1);
			return query.getResultList();
		}catch (NoResultException e) {
			System.out.println("null d�necek");
			return null;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	
	public List<Referans> findReferans(long yil, int sayi, int ciltNo, Dergi d){
		EntityManager em = getEmf().createEntityManager();
		try {
			Query query = em.createQuery(
					"select r from com.entity.Referans r WHERE r.yil=:refyil and r.sayi=:refsayi and r.ciltNo=:refcilt and r.DERGI.id=:d_Id");
			query.setParameter("refyil", yil);
			query.setParameter("refsayi", sayi);
			query.setParameter("refcilt", ciltNo);
			query.setParameter("d_Id", d.getId());
			return query.getResultList();

		} catch (NoResultException e) {
			System.out.println("null d�necek");
			return null;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	
	public Referans findReferans(String refAd) {
		EntityManager em = getEmf().createEntityManager();
		try {
			Query query = em.createQuery(
					"select r from com.entity.Referans r WHERE r.referansad=:refad");
			query.setParameter("refad", refAd);
			
			return (Referans) query.getSingleResult();

		} catch (NoResultException e) {
			// No matching result so return null
			return null;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	
	public Referans findReferans(long yil, int sayi, int ciltNo, int basSayfaNo, int sonSayfaNo, Dergi d) {
		EntityManager em = getEmf().createEntityManager();
		try {
			Query query = em.createQuery(
					"select r from com.entity.Referans r WHERE r.yil=:refyil and r.sayi=:refsayi and r.ciltNo=:refcilt and r.basSayfaNo=:rbas and r.sonSayfaNo=:rson and r.DERGI.id=:d_Id");
			query.setParameter("refyil", yil);
			query.setParameter("refsayi", sayi);
			query.setParameter("refcilt", ciltNo);
			query.setParameter("rbas", basSayfaNo);
			query.setParameter("rson", sonSayfaNo);
			query.setParameter("d_Id", d.getId());
			return (Referans) query.getSingleResult();

		} catch (NoResultException e) {
			// No matching result so return null
			return null;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public Referans referansForMakale(int makaleId) {
		EntityManager em = getEmf().createEntityManager();
		try {
			Makale makale = em.find(Makale.class, makaleId);

			// query.setParameter("isOnayli", true);
			return makale.getReferans();
		} catch (NoResultException e) {
			// No matching result so return null
			return null;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public long makaleAdetForRef(int refId) {
		EntityManager em = getEmf().createEntityManager();
		try {
			// Referans ref = em.find(Referans.class, refId);
			Query query = em.createQuery("select count(m.id) from com.entity.Makale m  WHERE m.referans.id=:ref");
			query.setParameter("ref", refId);
			// query.setParameter("isOnayli", true);
			return (long) query.getSingleResult();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void deleteMakaleReferans(Referans ref) {
		EntityManager em = getEmf().createEntityManager();
		try {
			em.getTransaction().begin();
			// Makale makale = em.find(Makale.class, makaleId);
			// Referans ref = makale.getReferans();
			// query.setParameter("isOnayli", true);
			ref = em.merge(ref);
//			removeMakaleReferansFromYazarlar(ref);
			//Dergi d = ref.getDergi();
			YazarService ys = new YazarService();
			List<Yazar> yazarList = ys.findYazarByMakaleReferans(ref);
			System.out.println("yazarlist size:"+ yazarList.size());
			HashSet<Yazar> yazarList1 = removeMakaleReferansFromYazarlar(ref);
			//List<Yazar> newYazarList = 
			//System.out.println("yazarlist size:"+ yazarList.size());
			for(Yazar yazar : yazarList1){
				if(yazar.getReferansler().size() == 0){
					System.out.println("referans� kalmayan yazar ad�:" + yazar.getYazarAdi());
					ys.deleteYazar(yazar);
				}
			}
			//y.getReferansler().remove(ref);
			em.remove(ref);
			em.getTransaction().commit();
			
			
//			DergiService ds = new DergiService();
//			if (ds.referansAdetForDergi(d.getId()) == 0){
//				System.out.println("dergi ad:"+d.getDergiAdi() +"reffordergi:" + ds.referansAdetForDergi(d.getId()));
//				ds.deleteDergi(d);
//			}
			// return makale.getMakaleYayinTipi().toString();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public HashSet<Yazar> removeMakaleReferansFromYazarlar(Referans ref) {
		EntityManager em = getEmf().createEntityManager();
		try {
//			em.getTransaction().begin();
			// Query query = em.createQuery("delete from yazar where
			// makaleler_id=:makaleid");
			// query.setParameter("makaleler_id", makale.getId());
			// query.executeUpdate();
			HashSet<Yazar> yzrList = new HashSet<>();
			for (Yazar yazarlar : ref.getYazarlar()) {
				em.getTransaction().begin();
				yazarlar = em.merge(yazarlar);
				yazarlar.getReferansler().remove(ref);
				///System.out.println("ref kald�r�lan yazar:" + );
				em.getTransaction().commit();
				yzrList.add(yazarlar);
				// System.out.println("kesin kald�r�lan makaleler:" + ref);
			}
			//em.getTransaction().commit();
			return yzrList;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Referans> kullaniciSahipOlduguReferanslar(int kullaniciId) {
		EntityManager em = getEmf().createEntityManager();
		try {
			Query query = em.createQuery(
					"select m.referans from com.entity.Makale m JOIN m.kullanicilar k where k.id=m.kullanicilar.id and k.id=:k_Id");
			query.setParameter("k_Id", kullaniciId);
			// Referans rf = (Referans) query.getSingleResult();
			// System.out.println("id:" + rf.getId());
			return query.getResultList();

		} catch (NoResultException e) {
			System.out.println("null d�necek");
			return null;
		} finally {
			if (em != null) {
				em.close();
			}
		}

	}
}
