package org.sgpat.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.sgpat.entity.Chauffeur;
import org.sgpat.entity.Salaire;
import org.sgpat.form.ConducteurForm;
import org.sgpat.repository.ChauffeurRepository;
import org.sgpat.repository.SalaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChauffeurService {

	@Autowired
	ChauffeurRepository chauffeurRepository;
	
	@Autowired
	SalaireRepository salaireRepository;
	
	public Chauffeur findByCode(String code){
		return chauffeurRepository.findByCodeChauffeur(code);
	}
	
	public List<Chauffeur> getAll(){
		return chauffeurRepository.getAll();
	}
	
	public List<Salaire> findSalaire(String codeChauffeur){
		return salaireRepository.findByChauffeurCodeChauffeur(codeChauffeur);
	}
	public List<Salaire> findAllSalaire(){
		return salaireRepository.findAll();
	}
	
	@Transactional
	public Salaire saveSalaire(Double montant, String codeChauffeur, String date){
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Salaire salaire;
		try {
			Date dateSalire = sdf.parse(date);
			salaire = new Salaire(dateSalire, montant, "SP");
			Chauffeur chauffeur = this.findByCode(codeChauffeur);
			if(chauffeur == null) return new Salaire();
			salaire.setChauffeur(chauffeur);
			return salaireRepository.save(salaire);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return null;
	}
	
public Chauffeur createSalaire(Double montant, String codeChauffeur, String date){
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Salaire salaire;
		Chauffeur chauffeur= null;
		try {
			Date dateSalire = sdf.parse(date);
			salaire = new Salaire(dateSalire, montant, "SP");
			chauffeur = this.findByCode(codeChauffeur);
			if(chauffeur == null) return null;
			salaire.setChauffeur(chauffeur);
			salaire = salaireRepository.save(salaire);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return chauffeur;
	}
	
	@Transactional
	public Chauffeur create(ConducteurForm conducteurForm) {
		Chauffeur chauffeur = conducteurForm.create();
		chauffeur  = chauffeurRepository.save(chauffeur);
		chauffeur.setProfile("TAXI");
		
		String code = chauffeur.getId()+"";
		
		int lenth = code.length();
		for(int i=3; i>0; i--, lenth--){
			if(lenth <= 0)
				code = "0"+code;
		}
		code = "CH"+code;
		
		chauffeurRepository.updateCode(chauffeur.getId(), code);
		
		return chauffeur;
	}
}
