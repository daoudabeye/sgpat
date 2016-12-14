package org.sgpat.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.sgpat.entity.Recette;
import org.sgpat.entity.Vehicule;
import org.sgpat.form.CategorieForm;
import org.sgpat.form.MaintenanceForm;
import org.sgpat.form.VehiculeForm;
import org.sgpat.service.CategorieService;
import org.sgpat.service.ChauffeurService;
import org.sgpat.service.ProprioService;
import org.sgpat.service.RecetteService;
import org.sgpat.service.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
@RequestMapping("vehicule")
public class VehiculeController {
	
	private static final String NEW_VEHICULE_VIEW = "vehicule/nouveau";
	private static final String CATEGORIE_VIEW = "vehicule/categorie";
	private static final String MAINTENANCE_VIEW = "vehicule/maintenance";
	private static final String LISTE_VEHICULE_VIEW = "vehicule/liste_vehicule";
	private static final String RECETTE = "vehicule/recettes";
	private static final String PROFILE_VEHICULE = "vehicule/profile_vehicule";
	
	@Autowired
	VehiculeService vehiculeService;
	
	@Autowired
	ChauffeurService chauffeurService;
	
	@Autowired
	CategorieService categorieService;
	
	@Autowired
	RecetteService recetteService;
	
	@Autowired
	ProprioService proprioService;

	@RequestMapping(value = "new", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String location(Model model){
		model.addAttribute(new VehiculeForm());
		model.addAttribute(new CategorieForm());
		model.addAttribute("categories", categorieService.getAll());
		model.addAttribute("proprios", proprioService.findAll());
		model.addAttribute("chauffeurs", chauffeurService.getAll());
		return NEW_VEHICULE_VIEW;
	}
	
	@RequestMapping(value = "categorie", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String categorie(Model model){
		model.addAttribute(new CategorieForm());
		model.addAttribute("categories", categorieService.getAll());
		return CATEGORIE_VIEW;
	}
	
	@RequestMapping(value = "maintenance", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String maintenance(Model model){
		model.addAttribute("vehicules", vehiculeService.getAll());
		model.addAttribute(new MaintenanceForm());
		return MAINTENANCE_VIEW;
	}
	
	@RequestMapping(value = "liste", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String listeVehicule(Model model){
		model.addAttribute("vehicules", vehiculeService.getAll());
		return LISTE_VEHICULE_VIEW;
	}
	
	@RequestMapping(value = "profile", params={ "codeVehicule" } , method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String profile(Model model, @RequestParam("codeVehicule") String codeVehicule){
		Vehicule vehicule = vehiculeService.findByCode(codeVehicule);
		model.addAttribute("vehicule", vehicule);
		model.addAttribute("doculents", vehiculeService.findByVehicule(codeVehicule));
		model.addAttribute("chauffeurs", vehicule.getChauffeur());
		model.addAttribute("maintenances", vehiculeService.findMaintenance(codeVehicule));
		model.addAttribute("pieces", vehiculeService.findPiece(codeVehicule));
		return PROFILE_VEHICULE;
	}
	
	@RequestMapping(value = "payer", params = { "codeChauffeur" , "date" , "montant" }, method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String paiementRecette(Model model, @Param("date") String date, @Param("codeChauffeur") String codeChauffeur,
			@Param("montant") String montant){
		
		Recette recette = null;
		try {
			recette = recetteService.paiement(codeChauffeur, date, Double.valueOf(montant));
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("errorMessage", e.getMessage());
			e.printStackTrace();
		}
		
		
		List<Recette> recettes = new ArrayList<>();
		if(recette != null)recettes.add(recette);
		
		model.addAttribute("chauffeurs", chauffeurService.getAll());
		model.addAttribute("recettes", recettes);
		return RECETTE;
	}
	
	@RequestMapping(value = "recettes", params = {"date" , "fragment", "codeChauffeur"}, method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String recette(Model model, @RequestParam("date") String date, @RequestParam("fragment") String fragment,
			@RequestParam("codeChauffeur") String codeChauffeur){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		List<Recette> recettes = new ArrayList<>();
		
		if(date.equals("today")){
			if(codeChauffeur.equals("none"))
				recettes = recetteService.findForTodaye();
			else
				recettes = recetteService.find(new Date(), codeChauffeur);
			model.addAttribute("recettes", recettes);
		}
		else if (date.equals("ALL")){
			if(codeChauffeur.equals("none"))
				recettes = recetteService.getAll();
			else
				recettes = recetteService.findByCodeChauffeur(codeChauffeur);
			model.addAttribute("recettes", recettes);
		}
		else{
			try {
				Date time = sdf.parse(date);
				recettes = recetteService.find(time);
				model.addAttribute("recettes", recettes);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Double totalPayer = 0.0;
		Double totalImpayer = 0.0;
		for(Recette r : recettes){
			totalPayer += r.getMontantPayer();
			totalImpayer += (r.getMontantDus() - r.getMontantPayer());
		}
		
		model.addAttribute("totalPayer", totalPayer);
		model.addAttribute("totalImpayer", totalImpayer);
		model.addAttribute("chauffeurs", chauffeurService.getAll());
		
		if(fragment.equals("true")) return RECETTE + " :: tableau"; 
		
		return RECETTE;
	}
	
	@RequestMapping(value = "new", params = { "controller" }, method = RequestMethod.POST)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String nouveau(@Valid @ModelAttribute VehiculeForm vehiculeForm, Errors errors, Model model,
			RedirectAttributes ra, @RequestParam("controller") String controller) {
		if (errors.hasErrors()) {
			model.addAttribute("categories", categorieService.getAll());
			model.addAttribute("proprios", proprioService.findAll());
			model.addAttribute("chauffeurs", chauffeurService.getAll());
			return NEW_VEHICULE_VIEW;
		}
		
		try {
			vehiculeService.create(vehiculeForm);
			model.addAttribute("success", "Le compte à été créer avec succès");
		} catch (Exception e) {
			model.addAttribute("categories", categorieService.getAll());
			model.addAttribute("proprios", proprioService.findAll());
			model.addAttribute("chauffeurs", chauffeurService.getAll());
			model.addAttribute("errorMessage", "Erreur : "+e.getMessage());
			e.printStackTrace();
			return NEW_VEHICULE_VIEW;
		}
		return "redirect:/vehicule/new";
	}
	
	@RequestMapping(value = "maintenance", method = RequestMethod.POST)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String maintenance(@Valid @ModelAttribute MaintenanceForm maintenanceForm, Errors errors, Model model,
			RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return MAINTENANCE_VIEW;
		}
		
		try {
			vehiculeService.maintenance(maintenanceForm);
			model.addAttribute("success", "Enregistré");
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("errorMessage", "Erreur :"+e.getMessage());
			e.printStackTrace();
		}
		return MAINTENANCE_VIEW;
	}
	
	@RequestMapping(value = "categorie", params = { "controller" }, method = RequestMethod.POST)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String categorie(@Valid @ModelAttribute CategorieForm categorieForm, Errors errors, Model model,
			RedirectAttributes ra, @RequestParam("controller") String controller) {
		if (errors.hasErrors()) {
			return NEW_VEHICULE_VIEW;
		}
		
		try {
			categorieService.create(categorieForm);
			model.addAttribute("success", "La categorie à été ajouter avec succès");
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("errorMessage", "Erreur :"+e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/vehicule/categorie";
	}
}
