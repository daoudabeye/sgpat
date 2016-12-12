package org.sgpat.controller;

import javax.validation.Valid;

import org.sgpat.form.CategorieForm;
import org.sgpat.form.ClientForm;
import org.sgpat.form.LocationForm;
import org.sgpat.form.ReservationForm;
import org.sgpat.form.VehiculeForm;
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
@RequestMapping("location")
public class LocationController {

	private static final String NEW_LOCATION_VIEW = "location/nouvelle_location";
	private static final String NEW_RESERVATION_VIEW = "location/nouvelle_reservation";
	private static final String NEW_COURSE_VIEW = "location/nouvelle_course";
	private static final String LISTE_LOCATION_VIEW = "location/liste_location";
	private static final String LISTE_RESERVATION_VIEW = "location/liste_reservation";
	private static final String LISTE_COURSES_VIEW = "location/liste_courses";

	@Autowired
	LocationService locationService;

	@RequestMapping(value = "new", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String location(Model model){
		model.addAttribute(new ClientForm());
		model.addAttribute(new LocationForm());
		model.addAttribute(new VehiculeForm());
		model.addAttribute(new CategorieForm());
		return NEW_LOCATION_VIEW;
	}

	@RequestMapping(value = "reservation", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public String reservation(Model model){
		model.addAttribute("reservationForm", new ReservationForm());
		return NEW_RESERVATION_VIEW;
	}

	@RequestMapping(value = "course", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public String courses(Model model){
		model.addAttribute("reservationForm", new ReservationForm());
		return NEW_COURSE_VIEW;
	}

	@RequestMapping(value = "liste", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public String liste(Model model){
		model.addAttribute("locations", locationService.getAll());
		return LISTE_LOCATION_VIEW;
	}

	@RequestMapping(value = "liste/reservation", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public String reservationListe(Model model){
		model.addAttribute("reservations", locationService.listeReservations());
		return LISTE_RESERVATION_VIEW;
	}

	@RequestMapping(value = "liste/courses", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public String coursesListe(Model model){
		model.addAttribute("courses", locationService.getAllCourses());
		return LISTE_COURSES_VIEW;
	}

	@RequestMapping(value = "new", params = { "controller" }, method = RequestMethod.POST)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String nouveau(@Valid @ModelAttribute LocationForm locationForm, Errors errors, Model model,
			RedirectAttributes ra, @RequestParam("controller") String controller) {

		if (errors.hasErrors()) {
			model.addAttribute("locationForm", locationForm);
			return NEW_LOCATION_VIEW;
		}

		try {

			locationService.creer(locationForm);
			model.addAttribute("success", "Le compte à été créer avec succès");
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("errorMessage", "Erreur :"+e.getMessage());
			model.addAttribute("locationForm", locationForm);
			e.printStackTrace();
			return NEW_LOCATION_VIEW;
		}

		return NEW_LOCATION_VIEW;
	}

	@RequestMapping(value = "reservation", params = { "controller" }, method = RequestMethod.POST)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String reservation(@Valid @ModelAttribute ReservationForm reservationForm, Errors errors, Model model,
			RedirectAttributes ra, @RequestParam("controller") String controller) {

		if (errors.hasErrors()) {
			return NEW_RESERVATION_VIEW;
		}

		try {
			locationService.creerReservation(reservationForm);
			model.addAttribute("success", "La réservation à été éffectuée");
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("errorMessage", "Erreur :"+e.getMessage());
			model.addAttribute("reservationForm", reservationForm);
			e.printStackTrace();
			return NEW_RESERVATION_VIEW;
		}

		return NEW_RESERVATION_VIEW;
	}
	
	@RequestMapping(value = "course", params = { "controller" }, method = RequestMethod.POST)
	@Secured({"ROLE_AGENT", "ROLE_ADMIN"})
	public String course(@Valid @ModelAttribute ReservationForm reservationForm, Errors errors, Model model,
			RedirectAttributes ra, @RequestParam("controller") String controller) {

		if (errors.hasErrors()) {
			return NEW_COURSE_VIEW;
		}

		try {
			locationService.creerCourse(reservationForm);
			model.addAttribute("success", "Course enregistrée");
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("errorMessage", "Erreur :"+e.getMessage());
			model.addAttribute("reservationForm", reservationForm);
			e.printStackTrace();
			return NEW_COURSE_VIEW;
		}

		return NEW_COURSE_VIEW;
	}
}
