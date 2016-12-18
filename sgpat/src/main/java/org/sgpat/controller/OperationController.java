package org.sgpat.controller;

import java.util.List;

import javax.validation.Valid;

import org.sgpat.entity.Chauffeur;
import org.sgpat.entity.Operation;
import org.sgpat.form.OperationForm;
import org.sgpat.service.ChauffeurService;
import org.sgpat.service.OperationService;
import org.sgpat.service.ProprioService;
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
@RequestMapping("/operation")
public class OperationController {

	private static final String SALAIRE_VIEW = "operation/liste_salaire";
	private final static String CAISSE = "operation/caisse";
	private final static String VIREMENT = "operation/virement";
	private final static String SALAIRE = "operation/salaire";
	private final static String RECETTE = "operation/recette";
	private final static String SEARCH_OPERATION = "operation/historique";

	@Autowired 
	OperationService operationService;

	@Autowired
	ChauffeurService chauffeurService;

	@Autowired
	ProprioService proprioService;

	@RequestMapping(value = "caisse", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String operation(Model model) {
		model.addAttribute(new OperationForm());
		return CAISSE;
	}

	@RequestMapping(value = "virement", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String virement(Model model) {
		model.addAttribute(new OperationForm());
		model.addAttribute("proprios", proprioService.findAll());
		return VIREMENT;
	}

	@RequestMapping(value = "salaire", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String salaire(Model model) {
		model.addAttribute(new OperationForm());
		model.addAttribute("chauffeurs", chauffeurService.getAll());
		return SALAIRE;
	}
	
	@RequestMapping(value = "recette", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String recette(Model model) {
		model.addAttribute(new OperationForm());
		model.addAttribute("chauffeurs", chauffeurService.getAll());
		return RECETTE;
	}

	@RequestMapping(value = "salaire", params ={ "codeChauffeur"}, method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String salaire(Model model, @RequestParam("codeChauffeur") String codeChauffeur){

		try {
			Chauffeur chauffeur = chauffeurService.findByCode(codeChauffeur);

			model.addAttribute("codeChauffeur", chauffeur.getCodeChauffeur());
			model.addAttribute("nom", chauffeur.getPrenom()+" "+chauffeur.getNom());
			model.addAttribute("telephone", chauffeur.getTelephone());
			model.addAttribute("salaire", chauffeur.getSalaire());
			model.addAttribute("caution", chauffeur.getCaution());

			model.addAttribute("salaires", operationService.findByCode(codeChauffeur, "SALAIRE"));
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("errorMessage", "Erreur lors de l'enregistrement");
		}
		return SALAIRE_VIEW;
	}
	
	@RequestMapping(value = "historique", params ={ "date" , "date2" , "type" , "beneficiaire" , "statut" }, method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String searchOperation(Model model,  @RequestParam(value = "date" ,  defaultValue = "") String date,
			@RequestParam(value = "date2" ,  defaultValue = "") String date2,
			@RequestParam(value = "beneficiaire",  defaultValue = "") String beneficiaire,
			@RequestParam(value = "type",  defaultValue = "") String type, 
			@RequestParam( value = "statut",  defaultValue = "")String statut){
		List<Operation> recettes;
		
		recettes = operationService.find(date, date2, type , beneficiaire, statut);
		
		model.addAttribute("chauffeurs", chauffeurService.getAll());
		model.addAttribute("recettes", recettes);
		return SEARCH_OPERATION;
	}

	@RequestMapping(value = "confirmer", params ={ "type" , "vue"}, method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String paiementSalaire(@Valid @ModelAttribute OperationForm operationForm, Errors errors, RedirectAttributes ra, 
			Model model, @RequestParam("type") String type,	@RequestParam("vue") String vue){

		if (errors.hasErrors()) {
			model.addAttribute("errorMessage", errors.toString());
			return vue;
		}

		try {
			switch (type) {
			case "ENCAISSEMENT":
				operationService.encaissement(operationForm);
				break;
			case "DECAISSEMENT":
				operationService.decaissement(operationForm);
				break;
			case "VIREMENT":
				operationService.makeVirement(operationForm);
				break;
			case "SALAIRE":
				operationService.makeSalaire(operationForm);
				break;
			case "RECETTE":
				operationService.makeRecette(operationForm);
				break;

			default:
				break;
			}
			model.addAttribute("success", "Opération effectuée");
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("errorMessage", "Erreur lors de l'enregistrement :"+e.getMessage());
			return vue;
		}
		return vue;
	}

}
