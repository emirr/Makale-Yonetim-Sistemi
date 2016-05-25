package com.service;

import java.security.SecureRandom;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.entity.Kullanici;
import com.entity.Makale;
import com.entity.Rol;

public class KullaniciService extends AbstractService<Kullanici> {

	public KullaniciService() {
		super(Kullanici.class);
		// TODO Auto-generated constructor stub
	}

	// test edildi
	public void createKullanici(Kullanici kullanici) {
		EntityManager em = getEmf().createEntityManager();

		try {
			em.getTransaction().begin();
			em.persist(kullanici);
			// burada kullanicietkinlik'e eklemeler yap�lacak,kullan�c�Onaylama
			// alan� true yap�lacak
			em.getTransaction().commit();
			// em.getTransaction().begin();
			EtkinlikService ets = new EtkinlikService();
			kullanici.getEtkinlikler()
					.add(ets.createEtkinlik(kullanici.getId(), "kullanici", "sisteme kayıt oldunuz."));
			// em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	// makaleleri ve di�er ili�kili s�n�flar� silmiyor.d�zeltilecek
	// 1-->d�zeltildi
	public void deleteKullanici(Kullanici silinenKullanici,Kullanici adminkullanici) {
		EntityManager em = getEmf().createEntityManager();

		try {
			em.getTransaction().begin();
			// em.remove(em.merge(kullanici));
			silinenKullanici = em.merge(silinenKullanici);
			// YazarService ys = new YazarService();
			 MakaleService ms = new MakaleService();
			// System.out.println("yazar service olu�turuldu.");
			 //�nce makale ve yazar tablolar�n�n ili�kisi kesilir.
			 for(Makale makale : ms.makaleByKullaniciId(silinenKullanici.getId())){
			// ys.removeMakaleFromYazarlar(makale);
				System.out.println("kaldırılan makale:" + makale.getMakaleAdi());
			 	ms.deleteMakale(makale);
			 }
			EtkinlikService ets = new EtkinlikService();
//			MakaleMetadataService mms = new MakaleMetadataService();
//			mms.deleteMakaleMetadatalar(silinenKullanici);
//			MakaleService ms = new MakaleService();
//			System.out.println("ba�la art�k");
//			List<Makale> makaleler = ms.makaleByKullaniciId(silinenKullanici.getId());
//			System.out.println("ba�l�yor");
			//ms.deleteMakaleler(makaleler);
			
			// System.out.println("fjdfj");
			// art�k kullan�c� silinebilir.
			//ets.deleteKullaniciEtkinlik(silinenKullanici);
			em.remove(silinenKullanici);
			
			
			em.getTransaction().commit();
			//EtkinlikService ets = new EtkinlikService();
			adminkullanici.getEtkinlikler().add(ets.createEtkinlik(adminkullanici.getId(), "kullanici",
					silinenKullanici.getKullaniciAd() + " kullanicisinin kaydini sildin."));
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	// denenecek 2-->test edildi
	public void updateKullanici(int kullaniciId, String sifre, String ad, String unvan, String okul, String mail) {
		EntityManager em = getEmf().createEntityManager();

		try {
			em.getTransaction().begin();
			String etkinlikBilgi = " ";
			Kullanici kullanici = findKullaniciById(kullaniciId);
			kullanici = em.merge(kullanici);
			System.out.println("bulunan kullanici:" + kullanici.getKullaniciAd());
			if (sifre != null) {
				kullanici.setSifre(sifre);
				etkinlikBilgi += "sifre";
			}
			if (mail != null) {
				kullanici.setMail(mail);
				etkinlikBilgi += ",mail";
			}
			if (unvan != null) {
				System.out.println("unvan:" + unvan);
				kullanici.setUnvan(unvan);
				System.out.println("kullanicigetunvsan:" + kullanici.getUnvan());
				etkinlikBilgi += ",unvan";
			}
			if (ad != null) {
				kullanici.setKullaniciAd(ad);
				etkinlikBilgi += ",ad";
			}
			if (okul != null) {
				kullanici.setOkul(okul);
				etkinlikBilgi += ",okul";
			}
			// Makale makale = em.find(Makale.class, makaleId);
			EtkinlikService ets = new EtkinlikService();
			kullanici.getEtkinlikler()
					.add(ets.createEtkinlik(kullaniciId, "kullanici", etkinlikBilgi + " bilgileri güncellendi"));
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	// denenecek 3-->test edildi
	public void kullaniciyaSifreVer(Kullanici adminkullanici, Kullanici bekleyenKullanici, String sifre) {
		EntityManager em = getEmf().createEntityManager();

		try {
			em.getTransaction().begin();
			bekleyenKullanici = em.merge(bekleyenKullanici);
			bekleyenKullanici.setSifre(sifre);
			EtkinlikService ets = new EtkinlikService();
			adminkullanici.getEtkinlikler().add(ets.createEtkinlik(adminkullanici.getId(), "kullanici",
					adminkullanici.getKullaniciAd() + " kullanıcısının kaydı onaylandı."));
			bekleyenKullanici.getEtkinlikler()
					.add(ets.createEtkinlik(bekleyenKullanici.getId(), "kullanici", " hesabınız onaylandı."));
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	private void kullaniciyaSifreVer(int adminkullaniciId, int bekleyenId, String sifre) {
		EntityManager em = getEmf().createEntityManager();

		try {
			em.getTransaction().begin();
			Kullanici adminkullanici = findKullaniciById(adminkullaniciId);
			adminkullanici = em.merge(adminkullanici);
			Kullanici bekleyenKullanici = findKullaniciById(bekleyenId);
			bekleyenKullanici = em.merge(bekleyenKullanici);
			bekleyenKullanici.setOnayli(true);
			bekleyenKullanici.setSifre(sifre);
			bekleyenKullanici.setRol(Rol.KULLANICI);
			EtkinlikService ets = new EtkinlikService();
			adminkullanici.getEtkinlikler().add(ets.createEtkinlik(adminkullanici.getId(), "kullanici",
					bekleyenKullanici.getKullaniciAd() + " kullanicisinin kaydini onayladin."));
			bekleyenKullanici.getEtkinlikler()
					.add(ets.createEtkinlik(bekleyenKullanici.getId(), "kullanici", " hesabınız onaylandı."));
			// kullanici.getEtkinlikler().add(ets.createEtkinlik(kullanici.getId(),
			// "kullanici",kullanici.getKullaniciAd()+" kullan�c�s�n�n kayd�
			// onayland�."));
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void kullaniciOnayla(int adminKullaniciId, int onaylanacakKullaniciId) {
		EntityManager em = getEmf().createEntityManager();

		try {
			em.getTransaction().begin();
			// String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			// System.out.println("verilen �ifre:"+uuid);

			final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
			SecureRandom rnd = new SecureRandom();
			int len = 6;
			StringBuilder sb = new StringBuilder(len);
			for (int i = 0; i < len; i++) {
				sb.append(AB.charAt(rnd.nextInt(AB.length())));
			}
			System.out.println("verilen şifre:" + sb.toString());

			kullaniciyaSifreVer(adminKullaniciId, onaylanacakKullaniciId, sb.toString());
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	// public void removeKullanici-->kullanici ve ilgil b�t�n verileri silinecek
	// test edildi.
	public Kullanici findKullaniciById(int id) {
		EntityManager em = getEmf().createEntityManager();
		try {
			Kullanici kullanici = em.find(Kullanici.class, id);
			return kullanici;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	
	// test edildi.
	public List<Kullanici> findKullaniciByAd(String ad) {
		EntityManager em = getEmf().createEntityManager();
		try {
			// Kullanici kullanici = em.find(Kullanici.class, ad);
			// return kullanici;
			Query query = em.createQuery("select k from com.entity.Kullanici k WHERE k.kullaniciAd=:isim");
			query.setParameter("isim", ad);
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
	public Kullanici findKullaniciByAdSifre(String ad,String sifre) {
		EntityManager em = getEmf().createEntityManager();
		try {
			// Kullanici kullanici = em.find(Kullanici.class, ad);
			// return kullanici;
			Query query = em.createQuery("select k from com.entity.Kullanici k WHERE k.kullaniciAd=:isim and k.sifre=:sifre");
			query.setParameter("isim", ad);
			query.setParameter("sifre", sifre);
			return (Kullanici) query.getSingleResult();
		}catch (NoResultException e) {
			// No matching result so return null
			return null;
		}  finally {
			if (em != null) {
				em.close();
			}
		}
	}
	
	public Kullanici findKullaniciByMailSifre(String mail, String sifre) {
		EntityManager em = getEmf().createEntityManager();
		try {
			// Kullanici kullanici = em.find(Kullanici.class, ad);
			// return kullanici;
			Query query = em.createQuery("select k from com.entity.Kullanici k WHERE k.mail=:mail and k.sifre=:sifre");
			query.setParameter("mail", mail);
			query.setParameter("sifre", sifre);
			return (Kullanici) query.getSingleResult();
		}catch (NoResultException e) {
			// No matching result so return null
			return null;
		}  finally {
			if (em != null) {
				em.close();
			}
		}
	}
	
	// denenecek
	public Kullanici findKullaniciByMail(String mail) {
		EntityManager em = getEmf().createEntityManager();
		try {
			// Kullanici kullanici = em.find(Kullanici.class, ad);
			// return kullanici;
			Query query = em.createQuery("select k from com.entity.Kullanici k WHERE k.mail=:mail");
			query.setParameter("mail", mail);
			return (Kullanici) query.getSingleResult();
		}catch (NoResultException e) {
			// No matching result so return null
			return null;
		}  finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Kullanici> listOnayliKullanicilar() {
		EntityManager em = getEmf().createEntityManager();
		try {
			Query query = em.createQuery("SELECT k FROM com.entity.Kullanici k WHERE k.isOnayli=:onayDurumu");
			query.setParameter("onayDurumu", true);
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
	public List<Kullanici> listDigerKullanicilar(int kId) {
		EntityManager em = getEmf().createEntityManager();
		try {
			Query query = em.createQuery("SELECT k FROM com.entity.Kullanici k WHERE k.isOnayli=:onayDurumu and k.id!=:KID");
			query.setParameter("onayDurumu", true);
			query.setParameter("KID", kId);

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
	// test edildi.
	public List<Kullanici> listOnayBekleyenKullanicilar() {
		EntityManager em = getEmf().createEntityManager();
		try {
			Query query = em.createQuery("SELECT k FROM com.entity.Kullanici k WHERE k.isOnayli<>:onayDurumu and k.id<>:k_Id");
			query.setParameter("onayDurumu", true);
			query.setParameter("k_Id", 1);

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
	public void setKullaniciMesaj(Kullanici k,int msgStat, String msg){
		EntityManager em = getEmf().createEntityManager();
		try {
			em.getTransaction().begin();
			k = em.merge(k);
			k.setMessagestat(msgStat);
			k.setMessage(msg);
			em.getTransaction().commit();

		
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
}
