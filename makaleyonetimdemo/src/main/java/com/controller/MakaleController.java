package com.controller;

import java.util.List;

import com.entity.Makale;
import com.service.MakaleService;

public class MakaleController {
	private MakaleService ms = new MakaleService();
	
	public List<Makale> listKullaniciMakaleler(int kullaniciId){
		return ms.makaleByKullaniciId(kullaniciId);
	}

}
