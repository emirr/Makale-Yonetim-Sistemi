package com.service;

import com.entity.Dergi;
import com.entity.Kullanici;
import com.entity.Makale;
import com.entity.MakaleMetadata;
import com.entity.MakalePath;
import com.entity.MakaleYayinTipi;
import com.entity.Referans;
import com.entity.Yazar;

public class MiniSistem {
	KullaniciService ks1 = new KullaniciService();
	MakaleService ms1 = new MakaleService();
	YazarService ys1 = new YazarService();
	ReferansService rs1 = new ReferansService();
	DergiService ds1 = new DergiService();
	MakaleYayinTipiService myts1 = new MakaleYayinTipiService();
	MakalePathService mps1 = new MakalePathService();
	MakaleMetadataService mmds1 = new MakaleMetadataService();
	
	Kullanici k5,k6,k7,k8;
	Makale m4,m5,m6,m7;
	MakaleYayinTipi myt8,myt5,myt6,myt7;
	MakaleMetadata mmd5;
	Dergi d4;
	Referans ref4;
	Yazar y6;
	MakalePath mp6;
	
	//admin kullan�c� onaylama sayfas�ndaki onayla butonunu taklit eder.
	void kullaniciOnayla(int adminId, int bekleyenId){
		ks1.kullaniciOnayla(adminId, bekleyenId);
	}
	
	boolean isReferansNew(Referans r){
		/*�nce kullan�c�n�n bu makale referans�na sahip olup olmad��� sorgulan�r.E�er sahipse makaleyi daha �nce zaten y�klemi�sin uyar�s� verilir.
		 * E�er sahip de�ilse bu referans�n sisteme ba�ka kullan�c�lar taraf�ndan daha �nce y�klenip y�klenmedi�i kontrol edilecek.
		 * e�er ba�ka bir kullanici bu referans� daha �nce y�klemi�se kullan�c� sadece makale ad,keyword bilgilerini girer.
		 * e�er bu referans ilk defa y�klenmi�se referans,path,tip bilgileri de ek olarak girilir.
		 */ 
		
//			Referans ref = rs1.findReferans(r.getYil(), r.getSayi(), r.getCiltNo(), r.getBasSayfaNo(), r.getSonSayfaNo(),r.getDergi());
		Referans ref = rs1.findReferans(r.getYil(), r.getSayi(), r.getCiltNo(), r.getBasSayfaNo(), r.getSonSayfaNo(),r.getDergi());
			if(ref != null){
				System.out.println("ref bilgileri:" + ref.getDergi().getDergiAdi() + ref.getYil() );
				System.out.println("sadece makale ad ve keyword girilecek.Ref-path-tip bilgileri zaten sistemde mevcut");
				return false;//
			}
			else{
				System.out.println("ref null ��kt�");
				return true;
			}
				
	}
	//true d�nerse kullan�c� bu referansa sahip
	boolean hasThisReferans(Referans r, int kullaniciId){
		//List<Referans> kullaniciRefList = rs1.kullaniciHasThisMakaleReferans(kullaniciId);
		if(rs1.kullaniciSahipOlduguReferanslar(kullaniciId) != null){
			if(rs1.kullaniciSahipOlduguReferanslar(kullaniciId).contains(r) ){
				System.out.println("zaten bu makaleye sahipsin");//ger�ek sistemde buras� alert olacak.
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
	boolean isDergiNew(Dergi d){
		if(ds1.findExistDergi(d) == null)
			return true;
		else
			return false;
		
	}
}
