package org.sgpat.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.sgpat.entity.Categorie;
import org.sgpat.entity.Chauffeur;
import org.sgpat.entity.Recette;
import org.sgpat.entity.Vehicule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleService {

	private static final Logger log = LoggerFactory.getLogger(ScheduleService.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    @Autowired
    VehiculeService vehiculeService;
    
    @Autowired
    RecetteService recetteService;

	
	@Scheduled(cron ="0 1 1 * * ?")//code executer 1:1 0 du matin
    public void recetteSchedule() {
        log.info("The time is now {}", dateFormat.format(new Date()));
        
        List<Vehicule> vehicules = vehiculeService.findByEtat("TAXI", "ES");
        for(Vehicule taxi : vehicules){
        	try {
        		Chauffeur taximan = taxi.getChauffeur();
            	Categorie categorie = taxi.getCategorie();
            	if(taximan == null || categorie == null)
            		continue;
            	Recette recette = new Recette();
            	recette.setMontantDus(categorie.getPrixParJours());
            	recette.setDate(new Date());
            	recette.setMontantPayer(0.0);
            	recette.setVehicule(taxi);
            	recette.setStatut("RI");
            	recette.setChauffeur(taximan);
            	recetteService.save(recette);
            	log.info("Calculer pour { "+ new Date() +" } "+taximan.getCodeChauffeur(), dateFormat.format(new Date()));
			} catch (Exception e) {
				// TODO: handle exception
				log.info("erreur Calcule recette : "+taxi.getCode(), dateFormat.format(new Date()));
				e.printStackTrace();
			}
        	
        }
    }
	
}
