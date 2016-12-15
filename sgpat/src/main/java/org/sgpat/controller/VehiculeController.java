package org.sgpat.controller;

import javax.validation.Valid;

import org.sgpat.entity.Vehicule;
import org.sgpat.form.CategorieForm;
import org.sgpat.form.MaintenanceForm;
import org.sgpat.form.VehiculeForm;
import org.sgpat.form.pieceForm;
import org.sgpat.service.CategorieService;
import org.sgpat.service.ChauffeurService;
import org.sgpat.service.OperationService;
import org.sgpat.service.ProprioService;
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
@RequestMapping("vehicule")
public class VehiculeController {
	
	private static final String NEW_VEHICULE_VIEW = "vehicule/nouveau";
	private static final String CATEGORIE_VIEW = "vehicule/categorie";
	private static final String MAINTENANCE_VIEW = "vehicule/maintenance";
	private static final String GARAGE_VIEW = "vehicule/garage";
	private static final String LISTE_VEHICULE_VIEW = "vehicule/liste_vehicule";
	private static final String PROFILE_VEHICULE = "vehicule/profile_vehicule";
	private static final String UPDATE_VEHICULE = "vehicule/update";
	private static final String PIECES_VIEW = "vehicule/gestion_pieces";
	
	@Autowired
	VehiculeService vehiculeService;
	
	@Autowired
	ChauffeurService chauffeurService;
	
	@Autowired
	CategorieService categorieService;
	
	@Autowired
	OperationService operationService;
	
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
	
	@RequestMapping(value = "update", params="codeVehicule",  method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String update(Model model, @RequestParam("codeVehicule")String codeVehicule){
		Vehicule v = vehiculeService.findByCode(codeVehicule);
		if(v == null)
			model.addAttribute("errorMessage", "Aucun resultat pour le code "+codeVehicule);
		model.addAttribute(new VehiculeForm(v));
		model.addAttribute(new CategorieForm());
		model.addAttribute("codeVehicule", v.getCode());
		model.addAttribute("categories", categorieService.getAll());
		model.addAttribute("proprios", proprioService.findAll());
		model.addAttribute("chauffeurs", chauffeurService.getAll());
		return UPDATE_VEHICULE;
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
	
	@RequestMapping(value = "gestion/pieces", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String pieces(Model model){
		model.addAttribute("pieces", vehiculeService.getAllPieces());
		model.addAttribute(new pieceForm());
		return PIECES_VIEW;
	}
	
	@RequestMapping(value = "garage", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String garage(Model model){
		model.addAttribute("vehicules", vehiculeService.getAll());
		model.addAttribute(new MaintenanceForm());
		return GARAGE_VIEW;
	}
	
	@RequestMapping(value = "liste", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String listeVehicule(Model model){
		model.addAttribute("vehicules", vehiculeService.getAll());
		return LISTE_VEHICULE_VIEW;
	}
	
	@RequestMapping(value = "supprimer", params = "codeVehicule",  method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_ADMIN"})
	public String supprimer(Model model, @RequestParam("codeVehicule")String codeVehicule){
		try {
			vehiculeService.supprimer(codeVehicule);
			model.addAttribute("success", "Vehicule "+codeVehicule+" supprimé avec succès");
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("errorMessage", "Impossible de supprimer le vehicule "+codeVehicule+", merci de reesayer.");
		}
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
	
	@RequestMapping(value = "update", params = { "codeVehicule" }, method = RequestMethod.POST)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String update(@Valid @ModelAttribute VehiculeForm vehiculeForm, Errors errors, Model model,
			RedirectAttributes ra, @RequestParam("codeVehicule") String codeVehicule) {
		if (errors.hasErrors()) {
			model.addAttribute("categories", categorieService.getAll());
			model.addAttribute("proprios", proprioService.findAll());
			model.addAttribute("chauffeurs", chauffeurService.getAll());
			return UPDATE_VEHICULE;
		}
		
		try {
			vehiculeService.modifier(codeVehicule, vehiculeForm);
			model.addAttribute("success", "Le compte à été créer avec succès");
		} catch (Exception e) {
			model.addAttribute("categories", categorieService.getAll());
			model.addAttribute("proprios", proprioService.findAll());
			model.addAttribute("chauffeurs", chauffeurService.getAll());
			model.addAttribute("errorMessage", "Erreur : "+e.getMessage());
			e.printStackTrace();
			return UPDATE_VEHICULE;
		}
		return "redirect:/vehicule/liste";
	}
	
	@RequestMapping(value = "etat", params = { "codeVehicule", "etat"}, method = RequestMethod.GET)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String etat(Model model, @RequestParam("codeVehicule") String codeVehicule,
			@RequestParam("etat") String etat) {
		
		vehiculeService.updateEtat(codeVehicule, etat);
		return "redirect:/vehicule/liste";
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
	
	@RequestMapping(value = "gestion/pieces", method = RequestMethod.POST)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String pieces(@Valid @ModelAttribute pieceForm pieceForm, Errors errors, Model model,
			RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return MAINTENANCE_VIEW;
		}
		
		try {
			vehiculeService.pieces(pieceForm);
			model.addAttribute("success", "Enregistré");
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("errorMessage", "Erreur :"+e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/vehicule/gestion/pieces";
	}
	
	@RequestMapping(value = "garage", method = RequestMethod.POST)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String garage(@Valid @ModelAttribute MaintenanceForm maintenanceForm, Errors errors, Model model,
			RedirectAttributes ra) {
		if (errors.hasErrors()) {
			model.addAttribute("vehicules", vehiculeService.getAll());
			return GARAGE_VIEW;
		}
		
		try {
			vehiculeService.garage(maintenanceForm);
			model.addAttribute("success", "Vehicule mise au garage.");
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("vehicules", vehiculeService.getAll());
			model.addAttribute("errorMessage", "Erreur :"+e.getMessage());
			e.printStackTrace();
		}
		return GARAGE_VIEW;
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
