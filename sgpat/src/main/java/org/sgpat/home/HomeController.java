package org.sgpat.home;

import java.security.Principal;
import java.util.Date;

import org.sgpat.service.LocationService;
import org.sgpat.service.VehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	@Autowired
	LocationService locationService;
	
	@Autowired
	VehiculeService vehiculeService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Principal principal) {
		return principal != null ? "redirect:/home" : "signin/signin";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Model model){
				
		Double chiffreAffaire = locationService.gains(new Date());
		Long nombreVehicule = vehiculeService.nombreVehicule();
		Long nombreTaxi = vehiculeService.nombreVehicule("TAXI");
		Long mesVehicules = vehiculeService.count("PR0001");
		
		model.addAttribute("chiffreAffaire", chiffreAffaire);
		model.addAttribute("nombreVehicule", nombreVehicule);
		model.addAttribute("nombreTaxi", nombreTaxi);
		model.addAttribute("mesVehicules", mesVehicules);
		model.addAttribute("vehiculesPartenaires", nombreVehicule - mesVehicules );
		
		model.addAttribute("nombreContrats", locationService.count());
		model.addAttribute("contratEnCours", locationService.count("CE"));
		model.addAttribute("contratProlonger", locationService.count("CP"));
		model.addAttribute("contratOnorer", locationService.count("CO"));
		model.addAttribute("contratAnnuler", locationService.count("CA"));
		
		model.addAttribute("VehiculeEnActiviter", vehiculeService.count("VA"));
		model.addAttribute("VehiculeEnPanne", vehiculeService.count("VP"));
		model.addAttribute("VehiculeReserver", vehiculeService.count("VR"));
		model.addAttribute("VehiculeDisponible", vehiculeService.count("VD"));
		
		return "home/home";
	}
}
