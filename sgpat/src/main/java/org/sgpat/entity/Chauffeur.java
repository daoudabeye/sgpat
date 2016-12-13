package org.sgpat.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the chauffeur database table.
 * 
 */
@Entity
@Table(name="chauffeur")
@NamedQuery(name="Chauffeur.findAll", query="SELECT c FROM Chauffeur c")
public class Chauffeur implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String adresse;

	@Column(name="code_chauffeur")
	private String codeChauffeur;

	@Temporal(TemporalType.DATE)
	@Column(name="date_naissance")
	private Date dateNaissance;

	@Temporal(TemporalType.DATE)
	@Column(name="date_creation")
	private Date dateCreation;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="expiration_permis")
	private Date expirationPermis;

	@Column(name="lieu_naissance")
	private String lieuNaissance;

	private String nom;

	@Column(name="numero_permis")
	private String numeroPermis;

	private String prenom;

	private String profile;

	private String statut;

	private String telephone;
	
	@Column(name = "date_debut_activite")
	private Date dateDebutActivite;

	private Double salaire;
	
	private Double caution;
	
	private String sexe;

	@Column(name = "etat_civil")
	private String etatCivil;

	@Column(name = "niveau_etude")
	private String niveauEtude;

	private String experience;

	@Column(name = "numero_urgence")
	private String numeroUrgence;

	private String langue;

	private String nationnalite;

	//bi-directional many-to-one association to Vehicule
	@OneToMany(mappedBy="chauffeur")
	private List<Vehicule> vehicules;

	//bi-directional many-to-one association to Recette
	@OneToMany(mappedBy="vehicule")
	private List<Recette> recettes;

	public Chauffeur() {
		this.dateCreation = new Date();
	}

	public Chauffeur(String nom, String prenom, String adresse, String profile, Date dateNaissance, Double salaire,
			Date expirationPermis, String lieuNaissance, String numeroPermis, String statut, String telephone,
			Date dateDebutActivite , Double caution, String sexe, String etatCivile, String niveauEtude, String experience, String urgence,
			String langue, String nationnalite ) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.profile = profile;
		this.dateNaissance = dateNaissance;
		this.expirationPermis = expirationPermis;
		this.lieuNaissance = lieuNaissance;
		this.numeroPermis = numeroPermis;
		this.statut = statut;
		this.telephone = telephone;
		this.salaire = salaire;
		this.dateCreation = new Date();
		this.dateDebutActivite = dateDebutActivite;
		this.caution = caution;
		this.sexe = sexe;
		this.etatCivil = etatCivile;
		this.niveauEtude = niveauEtude;
		this.experience = experience;
		this.numeroUrgence = urgence;
		this.langue = langue;
		this.nationnalite = nationnalite;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdresse() {
		return this.adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getCodeChauffeur() {
		return this.codeChauffeur;
	}

	public void setCodeChauffeur(String codeChauffeur) {
		this.codeChauffeur = codeChauffeur;
	}

	public Date getDateNaissance() {
		return this.dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public Date getExpirationPermis() {
		return this.expirationPermis;
	}

	public void setExpirationPermis(Date expirationPermis) {
		this.expirationPermis = expirationPermis;
	}

	public String getLieuNaissance() {
		return this.lieuNaissance;
	}

	public void setLieuNaissance(String lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNumeroPermis() {
		return this.numeroPermis;
	}

	public void setNumeroPermis(String numeroPermis) {
		this.numeroPermis = numeroPermis;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getProfile() {
		return this.profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getStatut() {
		return this.statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public List<Vehicule> getVehicules() {
		return this.vehicules;
	}

	public void setVehicules(List<Vehicule> vehicules) {
		this.vehicules = vehicules;
	}

	public Vehicule addVehicule(Vehicule vehicule) {
		getVehicules().add(vehicule);
		vehicule.setChauffeur(this);

		return vehicule;
	}

	public Vehicule removeVehicule(Vehicule vehicule) {
		getVehicules().remove(vehicule);
		vehicule.setChauffeur(null);

		return vehicule;
	}
	
	public List<Recette> getRecettes() {
		return this.recettes;
	}

	public void setRecettes(List<Recette> recettes) {
		this.recettes = recettes;
	}
	
	public Recette addRecette(Recette recette) {
		getRecettes().add(recette);
		recette.setChauffeur(this);

		return recette;
	}

	public Recette removeRecette(Recette recette) {
		getRecettes().remove(recette);
		recette.setVehicule(null);

		return recette;
	}

	public Double getSalaire() {
		return salaire;
	}
	public void setSalaire(Double salaire) {
		this.salaire = salaire;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Date getDateDebutActivite() {
		return dateDebutActivite;
	}

	public void setDateDebutActivite(Date dateDebutActivite) {
		this.dateDebutActivite = dateDebutActivite;
	}

	public Double getCaution() {
		return caution;
	}

	public void setCaution(Double caution) {
		this.caution = caution;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public String getEtatCivil() {
		return etatCivil;
	}

	public void setEtatCivil(String etatCivil) {
		this.etatCivil = etatCivil;
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

	public String getNumeroUrgence() {
		return numeroUrgence;
	}

	public void setNumeroUrgence(String numeroUrgence) {
		this.numeroUrgence = numeroUrgence;
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

	

}