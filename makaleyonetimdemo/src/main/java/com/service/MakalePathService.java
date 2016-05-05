package com.service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.entity.Makale;
import com.entity.MakalePath;
import com.entity.Referans;
import com.util.S3DosyaIslemleri;



public class MakalePathService extends AbstractService<MakalePath> {

	public MakalePathService() {
		super(MakalePath.class);
		// TODO Auto-generated constructor stub
	}

	// test edildi
	public void createMakalePath(MakalePath MakalePath) {
		EntityManager em = getEmf().createEntityManager();

		try {
			em.getTransaction().begin();
			em.persist(MakalePath);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	
	// istenen bir kullanýcýyla MakalePath paylaþýmý-->gerekmeyebilir...
	public String MakalePathPaylas(int makaleId) {
		EntityManager em = getEmf().createEntityManager();
		try {
		
			Query query = em.createQuery("select p.makalePath from com.entity.MakalePath p WHERE p.id IN (Select m.makalePath from com.entity.Makale m where m.id=:makaleId)");
			query.setParameter("makaleId", makaleId);
			return (String) query.getSingleResult();
			

		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	//denenecek
	public MakalePath pathForMakale(int makaleId){
		EntityManager em = getEmf().createEntityManager();
		try {
			Makale makale = em.find(Makale.class, makaleId);
			
			// query.setParameter("isOnayli", true);
			return makale.getMakalePath();
		} catch (NoResultException e) {
			// No matching result so return null
			return null;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	public void deleteMakalePath(MakalePath mp){
			EntityManager em = getEmf().createEntityManager();
			try {
				em.getTransaction().begin();
				//Makale makale = em.find(Makale.class, makaleId);
				//MakalePath mp = makale.getMakalePath();
				// query.setParameter("isOnayli", true);
				mp = em.merge(mp);
//ÖNEMLÝ---->//--> buraya s3 den silme yapacak metod çaðrýsý eklenmeli.Metod parametre olarak path'i alacak.
				//deleteFromS3(mp.getPAth()) gibi
				S3DosyaIslemleri.s3DosyaSilme(mp.getMakalePath());
				em.remove(mp);
				em.getTransaction().commit();
				//return makale.getMakaleYayinTipi().toString();
			}  finally {
				if (em != null) {
					em.close();
				}
			}
		}
	
}
