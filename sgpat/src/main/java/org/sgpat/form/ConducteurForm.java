package org.sgpat.form;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.sgpat.entity.Chauffeur;
import org.springframework.util.Assert;

public class ConducteurForm {
	
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	@NotBlank(message = ConducteurForm.NOT_BLANK_MESSAGE)
	@Size(min=2 , max=20, message = "{name.size}")
	private String nom;
	
	@NotBlank(message = ConducteurForm.NOT_BLANK_MESSAGE)
	@Size(min=2 , max=20, message = "{name.size}")
	private String prenom;
	
	@NotBlank(message = ConducteurForm.NOT_BLANK_MESSAGE)
	private String dateNaissance;
	
	@NotBlank(message = ConducteurForm.NOT_BLANK_MESSAGE)
	private String lieuNaissance;
	
	@NotBlank(message = ConducteurForm.NOT_BLANK_MESSAGE)
	private String adresse;
	
	@NotBlank(message = ConducteurForm.NOT_BLANK_MESSAGE)
	private String telephone;
	
	@NotBlank(message = ConducteurForm.NOT_BLANK_MESSAGE)
	private String typePiece;
	
	@NotBlank(message = ConducteurForm.NOT_BLANK_MESSAGE)
	private String numeroPiece;
	
	@NotBlank(message = ConducteurForm.NOT_BLANK_MESSAGE)
	private String dateExpiration;
	
	@NotBlank(message = ConducteurForm.NOT_BLANK_MESSAGE)
	private String sexe;
	
	@NotBlank(message = ConducteurForm.NOT_BLANK_MESSAGE)
	private String etatcivil;
	
	@NotBlank(message = ConducteurForm.NOT_BLANK_MESSAGE)
	private String niveauEtude;
	
	@NotBlank(message = ConducteurForm.NOT_BLANK_MESSAGE)
	private String experience;
	
	@NotBlank(message = ConducteurForm.NOT_BLANK_MESSAGE)
	private String urgence;
	
	@NotBlank(message = ConducteurForm.NOT_BLANK_MESSAGE)
	private String langue;
	
	@NotBlank(message = ConducteurForm.NOT_BLANK_MESSAGE)
	private String nationnalite;
	
	@NotBlank(message = ConducteurForm.NOT_BLANK_MESSAGE)
	private String dateDebutActivite;
	
	private Double salaire;

	private String profile;
	
	private Double caution;

	public Chauffeur create(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dateN = null;
		Date dateE = null;
		Date dateA = null;
		try {
			dateN = sdf.parse(dateNaissance);
			dateE = sdf.parse(dateExpiration);
			dateA = sdf.parse(dateDebutActivite);
			
		} catch (Exception e) {
			// TODO: handle exception
			Assert.isTrue(false, "Date Naissance ou Date Expiration Incorrect");
		}
		
		return new Chauffeur(nom, prenom, adresse, profile, dateN, salaire, dateE, lieuNaissance,
				numeroPiece, "CD", telephone, dateA, caution);
	}
	
	public Boolean hasPermis(){
		if(this.typePiece.equals("PERMIS"))
			return true;
		return false;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	public String getLieuNaissance() {
		return lieuNaissance;
	}
	public void setLieuNaissance(String lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getTypePiece() {
		return typePiece;
	}
	public void setTypePiece(String typePiece) {
		this.typePiece = typePiece;
	}
	public String getNumeroPiece() {
		return numeroPiece;
	}
	public void setNumeroPiece(String numeroPiece) {
		this.numeroPiece = numeroPiece;
	}
	public String getDateExpiration() {
		return dateExpiration;
	}
	public void setDateExpiration(String dateExpiration) {
		this.dateExpiration = dateExpiration;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public Double getSalaire() {
		return salaire;
	}
	public void setSalaire(Double salaire) {
		this.salaire = salaire;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public String getEtatcivil() {
		return etatcivil;
	}

	public void setEtatcivil(String etatcivil) {
		this.etatcivil = etatcivil;
	}

	public String getNiveauEtude() {
		return niveauEtude;
	}

	public void setNiveauEtude(String niveauEtude) {
		this.niveauEtude = niveauEtude;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getUrgence() {
		return urgence;
	}

	public void setUrgence(String urgence) {
		this.urgence = urgence;
	}

	public String getLangue() {
		return langue;
	}

	public void setLangue(String langue) {
		this.langue = langue;
	}

	public String getNationnalite() {
		return nationnalite;
	}

	public void setNationnalite(String nationnalite) {
		this.nationnalite = nationnalite;
	}

	public String getDateDebutActivite() {
		return dateDebutActivite;
	}

	public void setDateDebutActivite(String dateDebutActivite) {
		this.dateDebutActivite = dateDebutActivite;
	}

	public Double getCaution() {
		return caution;
	}

	public void setCaution(Double caution) {
		this.caution = caution;
	}
}
