package org.sgpat.controller;

import javax.validation.Valid;

import org.sgpat.account.AccountService;
import org.sgpat.entity.Proprio;
import org.sgpat.form.AgentForm;
import org.sgpat.form.DocumentForm;
import org.sgpat.form.ProprioForm;
import org.sgpat.service.AdminService;
import org.sgpat.service.ClientService;
import org.sgpat.service.ProprioService;
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
@RequestMapping("admin")
public class AdminController {

	private final static String CREER_COMPTE_VIEW = "administration/creationComptes";
	private final static String LISTE_PROFILE = "administration/listeProfiles";
	private final static String PARTENAIRES = "administration/partenaires";
	private final static String PROFILE_PARTENAIRES = "administration/profile_partenaire";
	private static final String PROPRIO_VIEW = "administration/partenaires";
	private static final String DOCUMENTS = "administration/document";
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	ProprioService proprioService;
	
	@Autowired
	VehiculeService vehiculeService;
	
	@Autowired
	ClientService clientService;
	
	@RequestMapping(value = "nouveau", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String creer(Model model) {
		model.addAttribute(new AgentForm());
		return CREER_COMPTE_VIEW;
	}
	
	@RequestMapping(value = "document", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String document(Model model) {
		model.addAttribute(new DocumentForm());
		model.addAttribute("clients", clientService.getAll());
		model.addAttribute("vehicules", vehiculeService.getAll());
		return DOCUMENTS;
	}
	
	@RequestMapping(value = "profile", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String profile(Model model) {
		model.addAttribute("accounts", accountService.getALL());
		return LISTE_PROFILE;
	}
	
	@RequestMapping(value = "partenaires", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String partenaires(Model model) {
		model.addAttribute(new ProprioForm());
		model.addAttribute("proprios", proprioService.findAll());
		return PARTENAIRES;
	}
	
	@RequestMapping(value = "partenaire/profile", params = { "codeProprio" }, method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String profilePartenaires(Model model, @Param("codeProprio") String codeProprio) {
		Proprio proprio = proprioService.findByCode(codeProprio);
		model.addAttribute("proprio", proprio);
		model.addAttribute("vehicules", vehiculeService.findByProprio(proprio));
		model.addAttribute("virements", adminService.findByProprio(proprio.getCodeProprio()));
		return PROFILE_PARTENAIRES;
	}
	
	@RequestMapping(value = "nouveau", params = { "controller" }, method = RequestMethod.POST)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String newDocument(@Valid @ModelAttribute AgentForm agentForm, Errors errors, Model model,
			RedirectAttributes ra, @RequestParam("controller") String controller) {
		if (errors.hasErrors()) {
			return CREER_COMPTE_VIEW;
		}
		
		try {
			if(controller.equals("agent"))
				adminService.creerAgent(agentForm);
			model.addAttribute("success", "Utilisateur créer avec succès");
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("errorMessage", "Erreur :"+e.getMessage());
			e.printStackTrace();
			return CREER_COMPTE_VIEW;
		}
		
		return CREER_COMPTE_VIEW;
	}
	
	@RequestMapping(value = "partenaires/new", method = RequestMethod.POST)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String maintenance(@Valid @ModelAttribute ProprioForm proprioForm, Errors errors, Model model,
			RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return PROPRIO_VIEW;
		}
		
		try {
			proprioService.create(proprioForm);
			model.addAttribute("success", "Le Compte Enregistré avec succès");
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("errorMessage", "Erreur :"+e.getMessage());
			e.printStackTrace();
		}
		
		model.addAttribute("proprios", proprioService.findAll());
		return PROPRIO_VIEW;
	}
	
	@RequestMapping(value = "document", params = { "profile" }, method = RequestMethod.POST)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String document(@Valid @ModelAttribute DocumentForm documentForm, Errors errors, Model model,
			RedirectAttributes ra, @RequestParam("profile") String profile, @RequestParam("code")String code) {
		if (errors.hasErrors()) {
			model.addAttribute("clients", clientService.getAll());
			model.addAttribute("vehicules", vehiculeService.getAll());
			return DOCUMENTS;
		}
		
		try {
			adminService.creerDocument(documentForm, profile);
			model.addAttribute("success", "Le document à été Enregistré avec succès");
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("errorMessage", "Erreur :"+e.getMessage());
			e.printStackTrace();
		}
		
		return DOCUMENTS;
	}
	
	
}
