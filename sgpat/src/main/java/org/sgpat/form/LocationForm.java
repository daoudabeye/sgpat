package org.sgpat.form;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.hibernate.validator.constraints.NotBlank;
import org.sgpat.entity.Location;
import org.springframework.util.Assert;

public class LocationForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	
	@NotBlank(message = LocationForm.NOT_BLANK_MESSAGE)
	private String codeClient;
	
	private String categorieVehicule;
	
	@NotBlank(message = LocationForm.NOT_BLANK_MESSAGE)
	private String codeVehicule;
	
	@NotBlank(message = LocationForm.NOT_BLANK_MESSAGE)
	private String chauffeur;
	
	private String observations;
	
	@NotBlank(message = LocationForm.NOT_BLANK_MESSAGE)
	private String depard;
	
	@NotBlank(message = LocationForm.NOT_BLANK_MESSAGE)
	private String retour;
	
	@NotBlank(message = LocationForm.NOT_BLANK_MESSAGE)
	private String heure;
	
	private Integer remise;
	
	@NotBlank(message = LocationForm.NOT_BLANK_MESSAGE)
	private String caution;
	
	private Double valeurCaution;
	
	
	private String codeDocument;
	private int duree; // par heure par jour ou par mois
	private String facturation;
	
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
		
		Location location = new Location(dateDepart, dateRetour, heure, caution, valeurCaution, hasChauffeur(), remise,
				facturation);
		location.setAdresseArrive("n/a");
		location.setAdresseDepard("n/a");
		return location;
	}
	
	public TimeUnit getTimeUnit(){
		if(facturation == null) return TimeUnit.DAYS;
		if(facturation.equals("HEURE")) return TimeUnit.HOURS;
		if(facturation.equals("JOURS")) return TimeUnit.DAYS;
		
		return TimeUnit.DAYS;
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
	public String getObservations() {
		return observations;
	}
	public void setObservations(String observations) {
		this.observations = observations;
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
	public Integer getRemise() {
		return remise;
	}
	public void setRemise(Integer remise) {
		this.remise = remise;
	}
	public String getCaution() {
		return caution;
	}
	public void setCaution(String caution) {
		this.caution = caution;
	}
	public Double getValeurCaution() {
		return valeurCaution;
	}
	public void setValeurCaution(Double valeurCaution) {
		this.valeurCaution = valeurCaution;
	}

	public String getCategorieVehicule() {
		return categorieVehicule;
	}

	public void setCategorieVehicule(String categorieVehicule) {
		this.categorieVehicule = categorieVehicule;
	}

	public String getCodeDocument() {
		return codeDocument;
	}

	public void setCodeDocument(String codeDocument) {
		this.codeDocument = codeDocument;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public String getFacturation() {
		return facturation;
	}

	public void setFacturation(String facturation) {
		this.facturation = facturation;
	}
	
}
