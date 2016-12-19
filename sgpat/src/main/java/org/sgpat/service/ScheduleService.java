package org.sgpat.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.sgpat.entity.Operation;
import org.sgpat.entity.Vehicule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleService {

	private static final Logger log = LoggerFactory.getLogger(ScheduleService.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    @Autowired
    VehiculeService vehiculeService;
    
    @Autowired
    OperationService operationService;

	
	@Scheduled(cron ="0 1 1 * * ?")//code executer 1:1 0 du matin
    public void recetteSchedule() {
        log.info("The time is now {}", dateFormat.format(new Date()));
        
        List<Vehicule> vehicules = vehiculeService.findByEtat("TAXI", "ES");
        for(Vehicule taxi : vehicules){
        	try {
        		Operation recette = new Operation();
        		recette.setDateComptable(new Date());
        		recette.setMontantDus(taxi.getCategorie().getPrixParJours());
        		recette.setMontant(0.0);
        		recette.setNote("Recette du "+dateFormat.format(new Date())+", montant recette + "+taxi.getCategorie().getPrixParJours()+" "
        				+ "Vehicule "+taxi.getCode());
        		recette.setType("RECETTE");
        		recette.setStatut("IMPAYE");
        		recette.setBeneficiaire(taxi.getChauffeur().getCodeChauffeur());
        		recette.setIdBenef(taxi.getChauffeur().getId());
        		recette.setMotifOperation("paiement recette");
        		recette.setMontantCeder(0.0);
        		
        		operationService.save(recette);
            	log.info("Calculer pour { "+ new Date() +" } ", dateFormat.format(new Date()));
			} catch (Exception e) {
				// TODO: handle exception
				log.info("erreur Calcule recette : "+taxi.getCode(), dateFormat.format(new Date()));
				e.printStackTrace();
			}
        	
        }
    }
	
}
