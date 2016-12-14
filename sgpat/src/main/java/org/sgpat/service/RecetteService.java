package org.sgpat.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.sgpat.entity.Categorie;
import org.sgpat.entity.Chauffeur;
import org.sgpat.entity.Recette;
import org.sgpat.entity.Vehicule;
import org.sgpat.repository.RecetteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class RecetteService {

	@Autowired
	RecetteRepository recetteRepository;
	
	@Autowired
	ChauffeurService chauffeurService;
	
	@Autowired
	VehiculeService vehiculeService;
	
	@Autowired
	CategorieService categorieService;
	
	public Recette save(Recette recette){
		return recetteRepository.save(recette);
	}
	
	public List<Recette> find(String statut){
		return recetteRepository.findByStatut(statut);
	}
	
	public List<Recette> findByChauffeur(String codeChauffeur){
		return recetteRepository.findByChauffeurCodeChauffeur(codeChauffeur);
	}
	
	public List<Recette> getAll(){
		return recetteRepository.getAll();
	}
	
	public List<Recette> findForTodaye(){
		return recetteRepository.findByDate(new Date());
	}
	
	public List<Recette> find(Date date, String codeChauffeur){
		return recetteRepository.findByDateAndChauffeurCodeChauffeur(date, codeChauffeur);
	}
	public List<Recette> findByCodeChauffeur(String codeChauffeur){
		return recetteRepository.findByDateAndChauffeurCodeChauffeur(codeChauffeur);
	}
	
	public List<Recette> find(Date date){
		return recetteRepository.findByDate( date );
	}
	
	@Transactional
	public Recette paiement(String codeChauffeur, String date, Double montant){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date datePaiement = new Date();
			try {
				datePaiement = sdf.parse(date);
			} catch (Exception e) {
				// TODO: handle exception
				Assert.isTrue(false , "Format date incorrect");
			}
			
			Recette recette = recetteRepository.findFirst1ByDateAndChauffeurCodeChauffeur(datePaiement, codeChauffeur);
			if(recette == null){
				Chauffeur chauffeur = chauffeurService.findByCode(codeChauffeur);
				Vehicule vehicule = vehiculeService.findVehicule(chauffeur);
				if(vehicule == null)
					Assert.isTrue(false , "Aucun vehicule attribué à ce chauffeur");
				Categorie categorie = vehicule.getCategorie();
				Double prix = categorie.getPrixParJours();
				String statut = montant >= prix ? "RP" : "RI";
				recette = new Recette(datePaiement, vehicule.getCategorie().getPrixParJours(), montant, statut, vehicule, chauffeur);
				recetteRepository.save(recette);
				return recette;
			}
			
			recette.payer(montant);
			String statut = recette.getMontantPayer() >= recette.getMontantDus()? "RP" : "RI";
			recetteRepository.updateMontantPayer(recette.getId(), recette.getMontantPayer(), statut);
			return recette;
		} catch (Exception e) {
			// TODO: handle exception
			Assert.isTrue(false , e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
