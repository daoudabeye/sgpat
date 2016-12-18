package org.sgpat.controller;

import java.util.List;

import javax.validation.Valid;

import org.sgpat.entity.Chauffeur;
import org.sgpat.entity.Operation;
import org.sgpat.entity.Vehicule;
import org.sgpat.form.ConducteurForm;
import org.sgpat.service.ChauffeurService;
import org.sgpat.service.OperationService;
import org.sgpat.service.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/conducteur")
public class ConducteurController {

	private static final String CHAUFFEUR_VIEW = "conducteur/nouveau";
	private static final String LISTE_VIEW = "conducteur/liste";
	private static final String PROFILE_VIEW = "conducteur/profile_chauffeur";
	private static final String RECETTE = "vehicule/recettes";
	private static final String SEARCH_RECETTE = "conducteur/recettes_search";

	@Autowired
	ChauffeurService chauffeurService;

	@Autowired
	VehiculeService vehiculeService;

	@Autowired
	OperationService operationService;
	
	@RequestMapping(value = "nouveau", params = "message", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String chauffeur(Model model, @RequestParam("message")String message){
		model.addAttribute(new ConducteurForm());
		model.addAttribute("update", "false");
		if(message.equals("success"))
			model.addAttribute("success", "Opération effectuée avec succès");
		else if(message.equals("error"))
			model.addAttribute("errorMessage", "Echec de l'operation");
		
			model.addAttribute("codeChauffeur", "codeChauffeur");
		return CHAUFFEUR_VIEW;
	}
	
	@RequestMapping(value = "recette/search", params ={ "date" , "date2" , "codeChauffeur" , "statut" }, method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String searchRecette(Model model,  @RequestParam(value = "date" ,  defaultValue = "") String date,
			@RequestParam(value = "date2" ,  defaultValue = "") String date2,
			@RequestParam(value = "codeChauffeur",  defaultValue = "") String codeChauffeur, 
			@RequestParam( value = "statut",  defaultValue = "")String statut){
		List<Operation> recettes;
		
		recettes = operationService.find(date, date2, "RECETTE" , codeChauffeur, statut);
		
		model.addAttribute("chauffeurs", chauffeurService.getAll());
		model.addAttribute("recettes", recettes);
		return SEARCH_RECETTE;
	}
	
	@RequestMapping(value = "profile", params = "codeChauffeur", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String profile(Model model, @RequestParam("codeChauffeur") String codeChauffeur){
		Chauffeur chauffeur = chauffeurService.findByCode(codeChauffeur);
		Vehicule vehicule = vehiculeService.findVehicule(chauffeur);
		model.addAttribute("chauffeur", chauffeur);
		model.addAttribute("recettes", operationService.findByCode(chauffeur.getCodeChauffeur(), "RECETTE"));
		model.addAttribute("vehicule", vehicule);
		model.addAttribute("salaires", operationService.findByCode(chauffeur.getCodeChauffeur(), "SALAIRE"));
		model.addAttribute("maintenances", vehiculeService.findMaintenance(vehicule == null ? "" : vehicule.getCode()));
		return PROFILE_VIEW;
	}
	
	@RequestMapping(value = "modifier", params = "codeChauffeur", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String modifier(Model model, @RequestParam("codeChauffeur") String codeChauffeur){
		Chauffeur chauffeur = chauffeurService.findByCode(codeChauffeur);
		model.addAttribute(new ConducteurForm(chauffeur));
		model.addAttribute("codeChauffeur", chauffeur.getCodeChauffeur());
		model.addAttribute("update", "true");
		return CHAUFFEUR_VIEW;
	}
	
	@RequestMapping(value = "supprimer", params = "codeChauffeur", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_ADMIN"})
	public String supprimer(Model model, @RequestParam("codeChauffeur") String codeChauffeur){
		try {
			chauffeurService.remove(codeChauffeur);
			model.addAttribute("success", "Compte "+codeChauffeur+" supprimé avec succès");
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("errorMessage", "Impossible de supprimer le compte");
		}
		
		model.addAttribute("chauffeurs", chauffeurService.getAll());
		return LISTE_VIEW;
	}

	@RequestMapping(value = "liste", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String liste(Model model){
		model.addAttribute("chauffeurs", chauffeurService.getAll());
		return LISTE_VIEW;
	}

	@RequestMapping(value = "retrieve", params = { "codeChauffeur" , "path", "fragment"}, method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String retrieve(Model model, @RequestParam("codeChauffeur") String codeChauffeur
			, @RequestParam("path") String path, @RequestParam("fragment") String fragment){
		Chauffeur chauffeur = chauffeurService.findByCode(codeChauffeur);
		Vehicule vehicule = vehiculeService.findVehicule(chauffeur);

		if(chauffeur == null || vehicule == null){
			model.addAttribute("errorMessage", "Code chauffeur incorrect, ou aucun véhicule attribué");
			return path;
		}

		model.addAttribute("chauffeur", chauffeur);
		model.addAttribute("vehicule", vehicule);
		model.addAttribute("codeVehicule", vehicule.getCode());
		model.addAttribute("montantRecette", vehicule.getCategorie().getPrixParJours());
		model.addAttribute("chauffeurs", chauffeurService.getAll());

		if(fragment.equals("false"))
			return path;

		return path +" :: " +fragment ;
	}

	@RequestMapping(value = "nouveau", params = { "controller" , "update", "codeChauffeur"}, method = RequestMethod.POST)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String nouveau(@Valid @ModelAttribute ConducteurForm conducteurForm, Errors errors, Model model,
			RedirectAttributes ra, @RequestParam("controller") String controller,
			@RequestParam("update") String update ,@RequestParam("codeChauffeur") String  codeChauffeur) {
		if (errors.hasErrors()) {
			model.addAttribute("errorMessage", errors.toString());
			return CHAUFFEUR_VIEW;
		}
		try {
			if(update.equals("false")){
				chauffeurService.create(conducteurForm);
				model.addAttribute("success", "Le compte à été créer avec succès");
			}
			else if(update.equals("true")){
				Boolean updated = chauffeurService.update(codeChauffeur, conducteurForm);
				if(updated)
					model.addAttribute("success", "Le compte à été mise à jours");
				else
					model.addAttribute("errorMessage", "Impossible de faire la mise à jour.");
			}
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("errorMessage", "Erreur :"+e.getMessage());
			model.addAttribute("clientForm", conducteurForm);
			e.printStackTrace();
			return CHAUFFEUR_VIEW;
		}

		return "redirect:/conducteur/nouveau?message=success";
	}
	
	@RequestMapping(value = "recettes", params = { "date" , "codeChauffeur"}, method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String recette(Model model, @RequestParam("date") String date,
			@RequestParam("codeChauffeur") String codeChauffeur){
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Chauffeur chauffeur = chauffeurService.findByCode(codeChauffeur);
		model.addAttribute("chauffeur", chauffeur);
		if(chauffeur == null) return "redirect:/conducteur/liste";
		
		List<Operation> operations = operationService.findByCode(codeChauffeur, "RECETTE");
		Double totalPayer = 0.0;
		Double totalImpayer = 0.0;
		for(Operation o : operations){
			totalPayer += o.getMontant();
			totalImpayer += (o.getMontantDus() - o.getMontant());
		}
		
		model.addAttribute("operations", operations);
		model.addAttribute("totalPayer", totalPayer);
		model.addAttribute("totalImpayer", totalImpayer);
		model.addAttribute("chauffeurs", chauffeurService.getAll());
				
		return RECETTE;
	}
}
