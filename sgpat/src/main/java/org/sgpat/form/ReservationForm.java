package org.sgpat.form;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.sgpat.entity.Location;
import org.sgpat.entity.Reservation;
import org.springframework.util.Assert;

public class ReservationForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	
	@NotBlank(message = ReservationForm.NOT_BLANK_MESSAGE)
	private String codeClient;
	
	private String categorieVehicule;
	
	@NotBlank(message = ReservationForm.NOT_BLANK_MESSAGE)
	private String codeVehicule;
	
	private String chauffeur;
		
	@NotBlank(message = ReservationForm.NOT_BLANK_MESSAGE)
	private String depard;
	
	@NotBlank(message = ReservationForm.NOT_BLANK_MESSAGE)
	private String retour;
	
	@NotBlank(message = ReservationForm.NOT_BLANK_MESSAGE)
	private String heure;
	
	private String adresseDepard;
	
	private String adresseArrive;
	
	private Double cout;
	
	private Double fraisChauffeur;
	
	private int remise;
	
	
	public Reservation createReservation(){
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		Date dateDepart = new Date();
		Date dateRetour = new Date();
		try {
			dateDepart = sf.parse(depard);
			dateRetour = sf.parse(retour);
		} catch (Exception e) {
			// TODO: handle exception
			Assert.isTrue(false, "Format Date incorrect");
			
		}
		Reservation r = new Reservation(hasChauffeur(), dateDepart, new Date(), dateRetour, "", "NC");
		r.setHeure(heure);
		return r;
	}
	
	public Location createLocaton(){
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		Date dateDepart = new Date();
		Date dateRetour = new Date();
		try {
			dateDepart = sf.parse(depard);
			dateRetour = sf.parse(retour);
		} catch (Exception e) {
			// TODO: handle exception
			Assert.isTrue(false, "Format Date incorrect");
		}
		
		Location location = new Location(dateDepart, dateRetour, heure, "n/a", 0.0, hasChauffeur(), remise,
				"COURSE");
		location.setAdresseDepard(adresseDepard);
		location.setAdresseArrive(adresseArrive);
		return location;
	}
	
	public Boolean hasChauffeur(){
		if(chauffeur == null) return false;
		if(chauffeur.equals("CHAUFFEUR")) return true;
		return false;
	}
	
	public String getCodeClient() {
		return codeClient;
	}
	public void setCodeClient(String codeClient) {
		this.codeClient = codeClient;
	}
	public String getCodeVehicule() {
		return codeVehicule;
	}
	public void setCodeVehicule(String codeVehicule) {
		this.codeVehicule = codeVehicule;
	}
	public String getChauffeur() {
		return chauffeur;
	}
	public void setChauffeur(String chauffeur) {
		this.chauffeur = chauffeur;
	}
	public String getDepard() {
		return depard;
	}
	public void setDepard(String depard) {
		this.depard = depard;
	}
	public String getRetour() {
		return retour;
	}
	public void setRetour(String retour) {
		this.retour = retour;
	}
	public String getHeure() {
		return heure;
	}
	public void setHeure(String heure) {
		this.heure = heure;
	}

	public String getCategorieVehicule() {
		return categorieVehicule;
	}

	public void setCategorieVehicule(String categorieVehicule) {
		this.categorieVehicule = categorieVehicule;
	}

	public Double getCout() {
		return cout;
	}

	public void setCout(Double cout) {
		this.cout = cout;
	}

	public Double getFraisChauffeur() {
		return fraisChauffeur;
	}

	public void setFraisChauffeur(Double fraisChauffeur) {
		this.fraisChauffeur = fraisChauffeur;
	}

	public int getRemise() {
		return remise;
	}

	public void setRemise(int remise) {
		this.remise = remise;
	}

	public String getAdresseDepard() {
		return adresseDepard;
	}

	public void setAdresseDepard(String adresseDepard) {
		this.adresseDepard = adresseDepard;
	}

	public String getAdresseArrive() {
		return adresseArrive;
	}

	public void setAdresseArrive(String adresseArrive) {
		this.adresseArrive = adresseArrive;
	}
	
}
