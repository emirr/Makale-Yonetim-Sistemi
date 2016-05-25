package com.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.entity.Kullanici;
import com.entity.Makale;
import com.entity.MakaleMetadata;
import com.entity.MakalePath;
import com.entity.MakaleYayinTipi;
import com.entity.Referans;
import com.entity.Yazar;

public class MakaleService extends AbstractService<Makale> {

	public MakaleService() {
		super(Makale.class);
		// TODO Auto-generated constructor stub
	}

	// test edildi
	public void createMakaleForKullanici(Makale makale, int kullaniciId, MakalePath mp, MakaleYayinTipi myt,
			Referans ref) {
		EntityManager em = getEmf().createEntityManager();

		try {
			em.getTransaction().begin();
			Kullanici kullanici = em.find(Kullanici.class, kullaniciId);
			makale.setKullanicilar(kullanici);
			makale.setMakalePath(mp);
			makale.setMakaleYayinTipi(myt);
			makale.setReferans(ref);
			//kullanici.getMakaleler().add(makale);
			EtkinlikService ets = new EtkinlikService();
			kullanici.getEtkinlikler()
					.add(ets.createEtkinlik(kullaniciId, "makale", makale.getMakaleAdi() + " makalesi eklendi"));
			em.persist(makale);
			System.out.println("makale eklendi");
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	// idli olabilir 1-->denenecek 1
	public void deleteMakale(Makale makale) {
		EntityManager em = getEmf().createEntityManager();

		try {
			em.getTransaction().begin();
			Kullanici kullanici = makale.getKullanicilar();
			//kullanici.getMakaleler().remove(makale);
			//makale.setKullanicilar(null);
			MakaleMetadata mmd = makale.getMakaleMetadata();
			MakaleMetadataService mmds = new MakaleMetadataService();
			mmds.deleteMakaleMetadata(mmd);
			Referans ref = makale.getReferans();
			MakaleYayinTipi myt = makale.getMakaleYayinTipi();
			MakalePath mp = makale.getMakalePath();
			System.out.println("makale silinecek");
			em.remove(em.merge(makale));
			System.out.println("makale silindi");
			em.getTransaction().commit();
			System.out.println("transactionbitti.");
			ReferansService rs = new ReferansService();
			if (rs.makaleAdetForRef(ref.getId()) == 0) {
				em.getTransaction().begin();
			//	System.out.println("hhh");
				System.out.println("makaleadetforref:"+rs.makaleAdetForRef(ref.getId()));
				//System.out.println("hhhll");
				MakaleYayinTipiService myts = new MakaleYayinTipiService();
				//System.out.println("hhhmmm");
				myts.deleteMakaleYayinTipi(myt);
				//System.out.println("hhhnnnn");
				// ReferansService rs = new ReferansService();
				rs.deleteMakaleReferans(ref);
				MakalePathService mps = new MakalePathService();
				
				mps.deleteMakalePath(mp);
				EtkinlikService ets = new EtkinlikService();
				kullanici.getEtkinlikler().add(
						ets.createEtkinlik(kullanici.getId(), "makale", makale.getMakaleAdi() + "makalesi silindi"));
				em.getTransaction().commit();
			} else {
				System.out.println("makaleadetforref:"+rs.makaleAdetForRef(ref.getId()));
				EtkinlikService ets = new EtkinlikService();
				kullanici.getEtkinlikler().add(
						ets.createEtkinlik(kullanici.getId(), "makale", makale.getMakaleAdi() + "makalesi silindi"));
			}

			//em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	// makalenin kullanicisi silinirse bu metod �a��r�l�r.
	public void deleteMakaleler(List<Makale> makaleler) {
		EntityManager em = getEmf().createEntityManager();

		try {
			
			for (Makale makale : makaleler) {
				
				System.out.println(""+makale.getMakaleAdi() + " silinmek �zere.");
				deleteMakale(makale);
				
			}

			//em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	// idli olabilir 2-->denenecek 2
	public void updateMakale(int id, String ad, String anahtarKeliemler) {
		EntityManager em = getEmf().createEntityManager();

		try {
			em.getTransaction().begin();
//			Makale makale = em.find(Makale.class, id);
			//makale = em.merge(makale);
			Makale makale = findMakaleById(id);
			makale = em.merge(makale);
			 em.flush() ;
			String etkinlikBilgi = " ";
			if(ad != null){
				makale.setMakaleAdi(ad);
				System.out.println("ad:" + ad);
				etkinlikBilgi+="Ad";
			}
			if(anahtarKeliemler != null){
				makale.setAnahtarKelimeler(anahtarKeliemler);
				System.out.println("anahtar kelimeler:" + anahtarKeliemler);

				etkinlikBilgi+=", AnahtarKelimeler";
			}
			System.out.println("etkinlik bilgi:" + etkinlikBilgi);
			// Query q = em.createQuery("update com.entity.Makale m set m.makaleAdi:ad m.maka")
			// makale.setMakaleMetadata(md);
			// makale.setMakalePath(path);
			// makale.setMakaleYayinTipi(yayinTipi);
			// makale.setReferans(ref);
			//String eskiisim = makale.getMakaleAdi();
			em.getTransaction().commit();
			EtkinlikService ets = new EtkinlikService();
			if(ad != null || anahtarKeliemler != null){
				makale.getKullanicilar().getEtkinlikler().add(ets.createEtkinlik(makale.getKullanicilar().getId(), "makale",
						makale.getMakaleAdi() + etkinlikBilgi + "bilgileri g�ncellendi."));
			}
			
			
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	// findBy metodlar� search alanlar� i�in-->id,ad ve yay�ntipine g�re arama
	// yap�labilecek
	// ayr�ca findbyid sayesinde makaledetaysayfas�na veri gidecek.
	public Makale findMakaleById(int id) {
		EntityManager em = getEmf().createEntityManager();
		try {
			Makale makale = em.find(Makale.class, id);
			return makale;
		} catch (NoResultException e) {
			// No matching result so return null
			return null;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public Makale findMakaleByAd(String ad) {
		EntityManager em = getEmf().createEntityManager();
		try {
			Query query = em.createQuery("select m from com.entity.Makale m where m.makaleAdi=:ad");
			query.setParameter("ad", ad);
			return (Makale) query.getSingleResult();
		} catch (NoResultException e) {
			// No matching result so return null
			return null;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Makale> findMakaleByYayinTipi(MakaleYayinTipi yayinTipi) {
		EntityManager em = getEmf().createEntityManager();
		try {
			TypedQuery<Makale> query = em
					.createQuery("select makale from com.entity.Makale where makaleYayinTipi=:yayintip", Makale.class);
			query.setParameter("yayinTipi", yayinTipi);
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

	// adminin makaleSil sayfas�n�n controlleri bu metodu b�t�n makaleleri
	// g�rmek i�in kullan�r.
	public List<Makale> tumMakaleler() {
		EntityManager em = getEmf().createEntityManager();
		try {
			Query  query = em.createQuery("select makale from com.entity.Makale makale");
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

	// �zel bir kullaniciya ait makalelerin listesi-->test edildi
	public List<Makale> makaleByKullaniciId(int kullaniciId) {
		EntityManager em = getEmf().createEntityManager();
		try {
			 Query query = em.createQuery("select m from com.entity.Makale m JOIN m.kullanicilar k where k.id=m.kullanicilar.id and k.id=:k_Id");
			 query.setParameter("k_Id", kullaniciId);
			 return query.getResultList();
//			KullaniciService ks = new KullaniciService();
//			Kullanici k = ks.findKullaniciById(kullaniciId);
//			return k.getMakaleler();
		} catch (NoResultException e) {
			// No matching result so return null
			return null;
		}finally {
			if (em != null) {
				em.close();
			}
		}
	}

	// istenen bir kullan�c�yla makale payla��m�-->denenecek 3
	public void makalePaylas(int sahipKullaniciId, int makaleId, int digerKullanicId) {
		EntityManager em = getEmf().createEntityManager();
		try {
			em.getTransaction().begin();
			Makale makale = em.find(Makale.class, makaleId);
			Kullanici sahipKullanici = em.find(Kullanici.class, sahipKullaniciId);// kullan�c�Etkinlik
																					// i�in
				if(makale.getReferans() == null){
					System.out.println("makale ref null");
				}
				else
					System.out.println("makale ref null de�il");

			Kullanici digerKullanici = em.find(Kullanici.class, digerKullanicId);
			if(digerKullanici == null){
				System.out.println("di�erki null");

			}
			else
				System.out.println("di�erki null de�il");

			// if kullan�c� zaten makaleye sahip de�ilse
			ReferansService rs = new ReferansService();
			if (rs.kullaniciSahipOlduguReferanslar(digerKullanicId) != null) {
				if (!rs.kullaniciSahipOlduguReferanslar(digerKullanicId).contains(makale.getReferans())) {
					System.out.println("kullan�c� g�nderilecek makaleye sahip de�il");
					Makale yeniMakale = new Makale();
					yeniMakale.setAnahtarKelimeler(makale.getAnahtarKelimeler());
					yeniMakale.setMakaleAdi(makale.getMakaleAdi());
					
					// yeniMakale.setMakalePath(makale.getMakalePath());
					// yeniMakale.setMakaleYayinTipi(makale.getMakaleYayinTipi());
					// yeniMakale.setReferans(makale.getReferans());
					createMakaleForKullanici(yeniMakale, digerKullanicId, makale.getMakalePath(),
							makale.getMakaleYayinTipi(), makale.getReferans());
					MakaleMetadata mm = new MakaleMetadata(makale.getMakaleMetadata().getMakaleNot(), 0, makale.getMakaleMetadata().getMakaleKonu());
					MakaleMetadataService mms = new MakaleMetadataService();
					mms.createMakaleMetadata(mm, yeniMakale.getId());
					//yeniMakale.setMakaleMetadata(mm);
					// etkinlik bilgisi eklenecek.
					EtkinlikService ets = new EtkinlikService();
					sahipKullanici.getEtkinlikler()
							.add(ets.createEtkinlik(sahipKullanici.getId(), "kullanici", digerKullanici.getKullaniciAd()
									+ " kullan�c�s� ile " + makale.getMakaleAdi() + " makalesini payla�t�n�z."));
					digerKullanici.getEtkinlikler()
							.add(ets.createEtkinlik(digerKullanici.getId(), "kullanici", sahipKullanici.getKullaniciAd()
									+ " kullan�c�s� sizinle " + makale.getMakaleAdi() + " makalesini payla�t�."));

				} else
					System.out.println("payla�mak istedi�in kullan�c� makaleye zaten sahip");
			} else {
				//makalenin g�nderilece�i kullan�c�n�n hi� makalesi yoksa bu kol �al���r.
				Makale yeniMakale = new Makale();
				yeniMakale.setAnahtarKelimeler(makale.getAnahtarKelimeler());
				yeniMakale.setMakaleAdi(makale.getMakaleAdi());
				//MakaleMetadata mm = new MakaleMetadata("not yok", 0, makale.getMakaleMetadata().getMakaleKonu());
				//yeniMakale.setMakaleMetadata(mm);
				// yeniMakale.setMakalePath(makale.getMakalePath());
				// yeniMakale.setMakaleYayinTipi(makale.getMakaleYayinTipi());
				// yeniMakale.setReferans(makale.getReferans());
				createMakaleForKullanici(yeniMakale, digerKullanicId, makale.getMakalePath(),
						makale.getMakaleYayinTipi(), makale.getReferans());
				MakaleMetadata mm = new MakaleMetadata(makale.getMakaleMetadata().getMakaleNot(), 0, makale.getMakaleMetadata().getMakaleKonu());
				MakaleMetadataService mms1 = new MakaleMetadataService();
				mms1.createMakaleMetadata(mm, yeniMakale.getId());
				// etkinlik bilgisi eklenecek.
				EtkinlikService ets = new EtkinlikService();
				sahipKullanici.getEtkinlikler()
						.add(ets.createEtkinlik(sahipKullanici.getId(), "kullanici", digerKullanici.getKullaniciAd()
								+ " kullan�c�s� ile " + makale.getMakaleAdi() + " makalesini payla�t�n�z."));
				digerKullanici.getEtkinlikler()
						.add(ets.createEtkinlik(digerKullanici.getId(), "kullanici", sahipKullanici.getKullaniciAd()
								+ " kullan�c�s� sizinle " + makale.getMakaleAdi() + " makalesini payla�t�."));
			}

			// makale.getKullanicilar().add(digerKullanici);
			// digerKullanici.getMakaleler().add(makale);
			em.getTransaction().commit();

		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	// denenecek 4--> 
	public Makale findMakaleByReferans(Referans ref) {
		EntityManager em = getEmf().createEntityManager();
		try {
			// System.out.println("refId:" + ref.getId());
			// Referans ref = em.find(Referans.class, refId);
			System.out.println("refId:" + ref.getId());
			TypedQuery<Makale> query = em.createQuery("select m from com.entity.Makale m WHERE m.referans=:reference",
					Makale.class);
			query.setParameter("reference", ref);
			query.setFirstResult(0);
			query.setMaxResults(1);
			return (Makale) query.getSingleResult();

		} catch (NoResultException e) {
			// No matching result so return null
			return null;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	public List<Makale> findAllMakaleForReferans(Referans ref) {
		EntityManager em = getEmf().createEntityManager();
		try {
			// System.out.println("refId:" + ref.getId());
			// Referans ref = em.find(Referans.class, refId);
			System.out.println("refId:" + ref.getId());
			TypedQuery<Makale> query = em.createQuery("select m from com.entity.Makale m WHERE m.referans=:reference",
					Makale.class);
			query.setParameter("reference", ref);
			
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
	public Makale findExistMakaleAdKeywordForKullanici(Makale makale,int kullaniciId){
		EntityManager em = getEmf().createEntityManager();

		try {
			System.out.println("mservice makale ad-keyword:" + makale.getMakaleAdi()+"-" + makale.getAnahtarKelimeler());
			Query query = em
					.createQuery("select m from com.entity.Makale m WHERE m.makaleAdi=:ad and m.anahtarKelimeler=:keywords and m.kullanicilar.id=:k_Id");
			query.setParameter("ad", makale.getMakaleAdi());
			query.setParameter("keywords", makale.getAnahtarKelimeler());
			query.setParameter("k_Id",kullaniciId);
			return (Makale) query.getSingleResult();

		} catch (NoResultException e) {
			// No matching result so return null
			return null;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
//	public void deleteMakaleForKullanici(int makaleId,int kullaniciId){
//		EntityManager em = getEmf().createEntityManager();
//
//		try {
//			
//		}finally {
//			if (em != null) {
//				em.close();
//			}
//		}
//	}
}
