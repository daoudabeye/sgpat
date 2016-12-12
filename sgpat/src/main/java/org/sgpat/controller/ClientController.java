package org.sgpat.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.sgpat.entity.Client;
import org.sgpat.entity.Document;
import org.sgpat.entity.Location;
import org.sgpat.form.ClientForm;
import org.sgpat.form.DocumentForm;
import org.sgpat.service.ClientService;
import org.sgpat.service.LocationService;
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
@RequestMapping("client")
public class ClientController {
	
	private final static String CREER_CLIENT_VIEW = "client/creer";
	private final static String UPDATE_CLIENT_VIEW = "client/update";
	private final static String LISTE_CLIENT_VIEW = "client/liste";
	private final static String PROFILE_CLIENT_VIEW = "client/profile";
	private final static String DOCUMENT_VIEW = "client/document";
	private final static String FACTURE_VIEW = "client/facture";
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	LocationService locationService;

	@RequestMapping(value = "creer", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String creer(Model model) {
		model.addAttribute("clientForm", new ClientForm());
		return CREER_CLIENT_VIEW;
	}
	
	@RequestMapping(value = "update",params = { "codeClient" }, method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String update(Model model, @RequestParam("codeClient") String codeClient) {
		Client client;
		try {
			client = clientService.findByCode(codeClient);
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("errorMessage", "Aucun resultat");
			return "redirect:/client/liste?page=0&size=20";
		}
		model.addAttribute("clientForm", new ClientForm(client));
		return UPDATE_CLIENT_VIEW;
	}
	
	@RequestMapping(value = "liste", params = { "page" , "size" }, method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String listeClient(Model model, @RequestParam("page") int page, @RequestParam("size") int size) {
		List<Client> clients = clientService.getAll();
		model.addAttribute("clients", clients);
		return LISTE_CLIENT_VIEW;
	}
	
	@RequestMapping(value = "profile", params = { "page" , "size" , "code" }, method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String profile(Model model, @RequestParam("page") int page, @RequestParam("size") int size,
			@RequestParam("code") String code) {
		try {
			Client client = clientService.findByCode(code);
			model.addAttribute("client", client);
			model.addAttribute("documents", clientService.getDocuments(client));
			model.addAttribute("nbrLocations", locationService.countByClient(client.getCodeClient()));
			model.addAttribute("nbrCourses", locationService.countCourses(client.getCodeClient()));
			model.addAttribute("nbrReservations", locationService.countReservation(client.getCodeClient()));
		} catch (Exception e) {
			// TODO: handle exception
			return "redirect:/client/liste?page=0&size=20";
		}
		return PROFILE_CLIENT_VIEW;
	}
	
	@RequestMapping(value = "document", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String documents(Model model) {
		model.addAttribute("documentForm", new DocumentForm());
		return DOCUMENT_VIEW;
	}
	
	@RequestMapping(value = "facture", params = "codeClient", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String facture(Model model, @RequestParam("codeClient") String codeClient) {
		List<Location> locations = locationService.findByClient(codeClient, "LI");
		Double somme=0.0;
		for(Location l : locations){
			somme += l.getCout();
		}
		model.addAttribute("locations", locations);
		model.addAttribute("total", somme);
		model.addAttribute("client", clientService.findByCode(codeClient));
		model.addAttribute("date", new Date());
		return FACTURE_VIEW;
	}
	
	@RequestMapping(value = "creer", params = { "controller" , "codeClient" , "update"}, method = RequestMethod.POST)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String nouveau(@Valid @ModelAttribute ClientForm clientForm, Errors errors, Model model,
			RedirectAttributes ra, @RequestParam("controller") String controller,
			@RequestParam("update") String update ,@RequestParam("codeClient") String codeClient) {
		if (errors.hasErrors()) {
			return CREER_CLIENT_VIEW;
		}
		
		Client client = null;
		try {
			if(update.equals("false")){
				client = clientService.creer(clientForm);
				model.addAttribute("client", client);
				model.addAttribute("success", "Le compte à été créer avec succès");
			}
			else if(update.equals("true")){
				Boolean updated = clientService.update(clientForm, codeClient);
				if(!updated) model.addAttribute("errorMessage", "Echec de la mise à jour");
				return LISTE_CLIENT_VIEW;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("errorMessage", "Erreur :"+e.getMessage());
			model.addAttribute("clientForm", clientForm);
			e.printStackTrace();
			return CREER_CLIENT_VIEW;
		}
		
		return CREER_CLIENT_VIEW;
	}
	
	@RequestMapping(value = "document", params = { "controller" }, method = RequestMethod.POST)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String newDocument(@Valid @ModelAttribute DocumentForm documentForm, Errors errors, Model model,
			RedirectAttributes ra, @RequestParam("controller") String controller) {
		if (errors.hasErrors()) {
			return DOCUMENT_VIEW;
		}
		
		try {
			Document document = clientService.creer(documentForm);
			model.addAttribute("document", document);
			model.addAttribute("success", "Document créer avec succès");
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("errorMessage", "Erreur :"+e.getMessage());
			e.printStackTrace();
			return DOCUMENT_VIEW;
		}
		
		return DOCUMENT_VIEW;
	}
}
