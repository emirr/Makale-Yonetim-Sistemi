package com.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.entity.Makale;
import com.entity.MakaleMetadata;
import com.service.MakaleMetadataService;
import com.service.MakaleService;

@ManagedBean
@ViewScoped
public class KullaniciAnaSayfaController implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MakaleService ms = new MakaleService();
	private List<Makale> makaleler;
	private MakaleMetadataService mds = new MakaleMetadataService();
	//private HashMap<Makale,MakaleMetadata> metadatalar = new HashMap<>();
	private List<MakaleMetadata> metadata = new ArrayList();
//	private String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
//            .get("kullaniciId");
	@ManagedProperty("#{kullaniciController}")
	private KullaniciController kullaniciController;
	
	public KullaniciController getKullaniciController() {
		return kullaniciController;
	}
//
	public void setKullaniciController(KullaniciController kullaniciController) {
		this.kullaniciController = kullaniciController;
	}
//	public KullaniciAnaSayfaController() {
//		// TODO Auto-generated constructor stub
//		System.out.println("idc"+kullaniciController.getKullaniciAd());
//	}

	@PostConstruct
	public void init() {
		makaleler = ms.makaleByKullaniciId(kullaniciController.getKullanici().getId());
		System.out.println("kullaniciid:"+kullaniciController.getKullanici().getId());
		System.out.println("makaleler size:"+ makaleler.size());
		//System.out.println("kullanici adi nedir:" + kullaniciController.getKullanici().getKullaniciAd());
		for(Makale makale : makaleler){
			System.out.println("idinit:"+makale.getId());
			metadata.add(mds.findSpecMakaleMetadata(makale.getId()));
		}
	}
	
	public List<Makale> getMakaleler() {
		return makaleler;
	}
	public List<MakaleMetadata> getMakaleMetaData(){
		return metadata;
	}
}
