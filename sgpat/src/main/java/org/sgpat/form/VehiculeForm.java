package org.sgpat.form;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.sgpat.entity.Vehicule;
import org.springframework.util.Assert;

public class VehiculeForm {
	
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	@NotBlank(message = VehiculeForm.NOT_BLANK_MESSAGE)
	private String marque;
	
	@NotBlank(message = VehiculeForm.NOT_BLANK_MESSAGE)
	private String type;
	
	@NotBlank(message = VehiculeForm.NOT_BLANK_MESSAGE)
	private String immatriculation;
	
	@NotBlank(message = VehiculeForm.NOT_BLANK_MESSAGE)
	private String couleur;
	
	private Double prixAchat;
	
	private String kilometrage;
		
	private String observations;
	
	@NotBlank(message = VehiculeForm.NOT_BLANK_MESSAGE)
	private String nomCategorie;
	
	private String energie;
	
	private Integer nombreDePlace;
	
	@NotBlank(message = VehiculeForm.NOT_BLANK_MESSAGE)
	private String classe;
	
	private String niveauCarburant;
	
	private String kilometrageActuel;
	
	private String kilometrageInitial;

	private String roueSecours;
	
	private String dateMiseEnService;
	
	@NotBlank(message = VehiculeForm.NOT_BLANK_MESSAGE)
	private String numeroSerie;
	
	@NotBlank(message = VehiculeForm.NOT_BLANK_MESSAGE)
	private String proprio;
	
	private String chauffeur;
	
	public String getMarque() {
		return marque;
	}
	
	public Vehicule createVehicule(){
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
		Date dateMs = new Date();
		try {
			dateMs = sf.parse(dateMiseEnService);
		} catch (Exception e) {
			// TODO: handle exception
			Assert.isTrue(false, "Format Date incorrect");
		}
		
		Vehicule vehicule = new Vehicule(classe, marque, couleur, nombreDePlace, immatriculation,
				niveauCarburant, energie, kilometrageInitial, kilometrageActuel, hasRoue(),
						dateMs, numeroSerie, observations, prixAchat, type);
		
		return vehicule;
	}
	
	public Boolean hasRoue(){
		if(roueSecours == null) return false;
		if(roueSecours.equals("OUI")) return true;
		return false;
	}
	public void setMarque(String marque) {
		this.marque = marque;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImmatriculation() {
		return immatriculation;
	}
	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}
	public String getCouleur() {
		return couleur;
	}
	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}
	public Double getPrixAchat() {
		return prixAchat;
	}
	public void setPrixAchat(Double prixAchat) {
		this.prixAchat = prixAchat;
	}
	public String getKilometrage() {
		return kilometrage;
	}
	public void setKilometrage(String kilometrage) {
		this.kilometrage = kilometrage;
	}
	public String getObservations() {
		return observations;
	}
	public void setObservations(String observations) {
		this.observations = observations;
	}
	public String getNomCategorie() {
		return nomCategorie;
	}
	public void setNomCategorie(String nomCategorie) {
		this.nomCategorie = nomCategorie;
	}
	public String getEnergie() {
		return energie;
	}
	public void setEnergie(String energie) {
		this.energie = energie;
	}

	public Integer getNombreDePlace() {
		return nombreDePlace;
	}

	public void setNombreDePlace(Integer nombreDePlace) {
		this.nombreDePlace = nombreDePlace;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getNiveauCarburant() {
		return niveauCarburant;
	}

	public void setNiveauCarburant(String niveauCarburant) {
		this.niveauCarburant = niveauCarburant;
	}

	public String getKilometrageActuel() {
		return kilometrageActuel;
	}

	public void setKilometrageActuel(String kilometrageActuel) {
		this.kilometrageActuel = kilometrageActuel;
	}

	public String getKilometrageInitial() {
		return kilometrageInitial;
	}

	public void setKilometrageInitial(String kilometrageInitial) {
		this.kilometrageInitial = kilometrageInitial;
	}

	public String getRoueSercours() {
		return roueSecours;
	}

	public void setRoueSercours(String roueSercours) {
		this.roueSecours = roueSercours;
	}

	public String getDateMiseEnService() {
		return dateMiseEnService;
	}

	public void setDateMiseEnService(String dateMiseEnService) {
		this.dateMiseEnService = dateMiseEnService;
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public String getProprio() {
		return proprio;
	}

	public void setProprio(String proprio) {
		this.proprio = proprio;
	}

	public String getChauffeur() {
		return chauffeur;
	}

	public void setChauffeur(String chaufeur) {
		this.chauffeur = chaufeur;
	}

	public String getRoueSecours() {
		return roueSecours;
	}

	public void setRoueSecours(String roueSecours) {
		this.roueSecours = roueSecours;
	}
	
}
