package org.sgpat.service;

import java.util.List;

import org.sgpat.entity.Chauffeur;
import org.sgpat.form.ConducteurForm;
import org.sgpat.repository.ChauffeurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class ChauffeurService {

	@Autowired
	ChauffeurRepository chauffeurRepository;

	public Chauffeur findByCode(String code){
		return chauffeurRepository.findByCodeChauffeur(code);
	}

	public List<Chauffeur> getAll(){
		return chauffeurRepository.getAll();
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
	
	@Transactional
	public Boolean update(String codeChauffeur, ConducteurForm conducteurForm) {
		Chauffeur chauffeur = conducteurForm.create();
		int update = chauffeurRepository.update(codeChauffeur, chauffeur.getNom(), chauffeur.getPrenom(), chauffeur.getAdresse(), chauffeur.getProfile(), chauffeur.getDateNaissance(), chauffeur.getSalaire(),
				chauffeur.getExpirationPermis(), chauffeur.getLieuNaissance(), chauffeur.getNumeroPermis(), chauffeur.getStatut(), chauffeur.getTelephone(),
				chauffeur.getDateDebutActivite(), chauffeur.getCaution(), chauffeur.getSexe(), chauffeur.getEtatCivil(), chauffeur.getNiveauEtude(), chauffeur.getExperience(),
				chauffeur.getNumeroUrgence(), chauffeur.getLangue(), chauffeur.getNationnalite());
		if(update > 0)
			return true;
		else
			return false;
	}
	
	@Transactional
	public void remove(String codeChauffeur){
		Chauffeur chauffeur = chauffeurRepository.findByCodeChauffeur(codeChauffeur);
		Assert.notNull(chauffeur, "Aucun resultat");
		chauffeurRepository.delete(chauffeur);
	}
}
