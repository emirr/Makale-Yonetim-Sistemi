package com.service;

import java.util.ArrayList;
import java.util.List;

import com.entity.Dergi;
import com.entity.Kullanici;
import com.entity.Makale;
import com.entity.MakaleMetadata;
import com.entity.MakalePath;
import com.entity.MakaleYayinTipi;
import com.entity.Referans;
import com.entity.Yazar;

public class Test {
	public static void main(String[] args) {
		KullaniciService ks1 = new KullaniciService();
		MakaleService ms1 = new MakaleService();
		YazarService ys1 = new YazarService();
		ReferansService rs1 = new ReferansService();
		DergiService ds1 = new DergiService();
		MakaleYayinTipiService myts1 = new MakaleYayinTipiService();
		MakalePathService mps1 = new MakalePathService();
		MakaleMetadataService mmds1 = new MakaleMetadataService();

		Kullanici k5, k6, k7, k8;
		Makale m4, m5, m6, m7;
		MakaleYayinTipi myt8, myt5, myt6, myt7;
		MakaleMetadata mmd5;
		Dergi d4;
		Referans ref4;
		Yazar y6;
		MakalePath mp6;
		MiniSistem miniSis = new MiniSistem();
		
		// //1.0
		// Kullanici k1 = new
		// Kullanici("emir","emir2@gmail","prof","ytu","123");
		// ks1.createKullanici(k1);
		// //1.1
		// MakaleYayinTipi myt1 = new
		// MakaleYayinTipi(true,false,false,false);//bildiri olsun yayýn tipi
		// myts1.createMakaleYayinTipi(myt1);
		// //1.2
		// MakalePath mp1 = new MakalePath("pathispath");
		// mps1.createMakalePath(mp1);
		// //1.3
		//Dergi d1 = new Dergi("bilim dergisi");
		//Dergi d1 = new Dergi("falanfilan301");
		// ds1.createDergi(d1);
		// 1.4
		//Referans ref1 = new Referans(2014, 20, 1, 20, 34, d1);
		//Referans ref1 = new Referans(2501, 17, 2, 40, 54);
		// rs1.createReferans(ref1);
		// //1.5
		// Makale m1 = new Makale("btree","iyi kötü");
		//
		// ms1.createMakaleForKullanici(m1, k1.getId(),mp1,myt1,ref1);
		//// m1.setMakalePath(mp1);
		//// m1.setMakaleYayinTipi(myt1);
		//// m1.setReferans(ref1);
		//
		// //1.6
		// MakaleMetadata mmd1 = new MakaleMetadata("cok guzel bir
		// makale",5,"bulut bilisim");
		// mmds1.createMakaleMetadata(mmd1, m1.getId());
		// //1.7
		// Yazar y1 = new Yazar("dr.ali veli");
		// //Yazar y2 = new Yazar("dr.yusuf ali");
		// HashSet<Yazar> yazarList = new HashSet<>();
		// yazarList.add(y1);
		// ys1.createYazarForMakale(m1,yazarList);

		// ys1.updateYazar(12, "ali veli yusuf", 10);
		// k5 = new Kullanici("jon skeet", "jon@gmail.com", "doc", "cambridge");
		// ks1.createKullanici(k5);

		// k6 = ks1.findKullaniciById(1);
//		int kullaniciId;
//		int switchOn = 1;
//		if (switchOn == 1) {
//			k5 = ks1.findKullaniciById(12);
//			kullaniciId = k5.getId();
//			k6=ks1.findKullaniciById(1);
//		} else {
//
//			//Kullanici k0 = new Kullanici("admin", "", "", "1");
//			//k0.setRol(Rol.ADMIN);
//			//ks1.createKullanici(k0);
//			k6=ks1.findKullaniciById(1);
//			k5 = new Kullanici("saltuk", "salt@gmail.com", "student", "aof");
//			ks1.createKullanici(k5);
//			//miniSis.kullaniciOnayla(k6.getId(), k5.getId());
//			kullaniciId = k5.getId();
//		}
		
		// if(miniSis.makaleReferansYukle(ref1))
		// miniSis.makaleYukle(m1,kId,mp1,myt1,ref1);
		
//		if (miniSis.isDergiNew(d1))
//			ds1.createDergi(d1);
//		else{
//			System.out.println("bu ada sahip bir dergi zaten var:" + d1.getDergiAdi());
//			d1 = ds1.findExistDergi(d1);
//		}
//		ref1.setDergi(d1);
//		if (!miniSis.hasThisReferans(ref1, kullaniciId)) {
//			if (miniSis.isReferansNew(ref1)) {
//				// bu kolda yeni makale yüklenmesi yapýlýr.
//				// referans yeni olduðu için path-tip-makale ad,ketword-metadata
//				// alanlarýný sýrayla aktifleþtir.
//				System.out.println("yeni referans");
////				if (miniSis.isDergiNew(d1))
////					ds1.createDergi(d1);
////				else{
////					System.out.println("bu ada sahip bir dergi zaten var:" + d1.getDergiAdi());
////					d1 = ds1.findExistDergi(d1);
////				}
//				rs1.createReferans(ref1,d1);
//
//				MakaleYayinTipi myt1 = new MakaleYayinTipi(false, false, true, false);// bildiri
//																						// olsun
//																						// yayýn
//																						// tipi
//				myts1.createMakaleYayinTipi(myt1);
//
//				MakalePath mp1 = new MakalePath(ref1.getId());// burada path
//																// adý otomatik
//																// üretilecek
//																// þekilde
//																// yapýlsýn.
//				mps1.createMakalePath(mp1);
//
////				Yazar y1 = new Yazar("dr.pet norvig");
////				Yazar y2 = new Yazar("dr.yes");
////				Yazar y3 = new Yazar("dr. ahmet mehmet");
//				Yazar y1 = new Yazar("dr.herer schildt");
//				Yazar y2 = new Yazar("cagri");
//				//Yazar y3 = new Yazar("dr. ahmet mehmet");
//				HashSet<Yazar> yazarList = new HashSet<>();
//				// her yazar ekle tuþuna basýldýðýnda set'e yazar eklensin.
//				yazarList.add(y1);
//				yazarList.add(y2);
//				//yazarList.add(y3);
//				ys1.createYazarForReferans(ref1, yazarList);
//
//				Makale m1 = new Makale("yazilim sürecleri44000", "agile");
//
//				if (ms1.findExistMakaleAdKeywordForKullanici(m1,kullaniciId) == null) {
//					ms1.createMakaleForKullanici(m1, kullaniciId, mp1, myt1, ref1);
//
//					MakaleMetadata mmd1 = new MakaleMetadata("bes hafta icinde bitirilecek", 5, "yazilim gelistirme");
//					mmds1.createMakaleMetadata(mmd1, m1.getId());
//				} else
//					System.out.println("bu ad-keyword ikilisine sahip makale var :" + m1.getMakaleAdi() + "-"
//							+ m1.getAnahtarKelimeler() + "lutfen baþka bir isim veya keyword gir");
//			} else {
//				System.out.println("hazýr referans");
//				// ys1.createYazarForReferans(ref1,ref1.getYazarlar());
//				// System.out.println("refIdgjgjfjj:"+ref1.getId());
//				Referans ref = rs1.findReferans(ref1.getYil(), ref1.getSayi(), ref1.getCiltNo(), ref1.getBasSayfaNo(),
//						ref1.getSonSayfaNo(),ref1.getDergi());
//				Makale m1 = ms1.findMakaleByReferans(ref);
//				if (ms1.findExistMakaleAdKeywordForKullanici(m1,kullaniciId) == null){
//					Makale newMakale = new Makale(m1.getMakaleAdi(), m1.getAnahtarKelimeler());
//					System.out.println("makale ad:" + newMakale.getMakaleAdi());
//					ms1.createMakaleForKullanici(newMakale, kullaniciId, m1.getMakalePath(), m1.getMakaleYayinTipi(), ref);
//
//					MakaleMetadata mmd1 = new MakaleMetadata("uc hafta icinde bitirilecek", 5, m1.getMakaleMetadata().getMakaleKonu());
//					mmds1.createMakaleMetadata(mmd1, m1.getId());
//				}
//				else{
//					System.out.println("bu ad-keyword ikilisine sahip makale zaten var:" + m1.getMakaleAdi() + "-"
//							+ m1.getAnahtarKelimeler());
//				}
//				
//
//			}
//		} else
//			System.out.println("makaleyi zaten yüklemiþsin");// alert çýkar ve
																// yüklenme
																// sayfasýndan
																// çýk.
		//ms1.makalePaylas(5, 13, 8);
		//k5 = ks1.findKullaniciById(8);
		//Makale makale = ms1.findMakaleById(33);
		//ms1.deleteMakale(makale);
		List<Makale> makaleler = ms1.makaleByKullaniciId(2);
		List<MakaleMetadata> md = new ArrayList();
		for(Makale makale : makaleler){
			md.add(mmds1.findSpecMakaleMetadata(makale.getId()));
		}
		for(MakaleMetadata md1 : md){
			System.out.println("makale konu:"+md1.getMakaleKonu());
		}
		System.out.println("makale silinmiþ olabilir.");
		//ks1.deleteKullanici(k5, k6);
		AbstractService.getEmf().close();
		
		
		
		
		
		
		

	}

}
