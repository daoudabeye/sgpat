package org.sgpat.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the location database table.
 * 
 */
@Entity
@Table(name="location")
@NamedQuery(name="Location.findAll", query="SELECT l FROM Location l")
public class Location implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="caution_type")
	private String cautionType;

	private Boolean chauffeur;

	@Column(name="code_location")
	private String codeLocation;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_creation")
	private Date dateCreation;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_debut")
	private Date dateDebut;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_retour")
	private Date dateRetour;

	private int remise;

	private String statut;

	@Column(name="statut_paiement")
	private String statutPaiement;

	private String heure;

	private String facturation;

	private int duree;

	@Column(name="valeur_caution")
	private double valeurCaution;

	private Double cout;

	private Double payer;

	@Column(name="adresse_depard")
	private String adresseDepard;

	@Column(name="adresse_retour")
	private String adresseArrive;

	//bi-directional many-to-one association to Client
	@ManyToOne
	private Client client;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	private Employee employee;

	//bi-directional many-to-one association to Vehicule
	@ManyToOne
	private Vehicule vehicule;

	public Location() {
	}

	public Location(Date dateDebut, Date dateRetour, String heure, String cautionType, double valeurCaution, Boolean chauffeur,
			int remise, String facturation) {
		super();
		this.setHeure(heure);
		this.dateDebut = dateDebut;
		this.dateRetour = dateRetour;
		this.cautionType = cautionType;
		this.valeurCaution = valeurCaution;
		this.chauffeur = chauffeur;
		this.remise = remise;
		this.setFacturation(facturation);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCautionType() {
		return this.cautionType;
	}

	public void setCautionType(String cautionType) {
		this.cautionType = cautionType;
	}

	public Boolean getChauffeur() {
		return this.chauffeur;
	}

	public void setChauffeur(Boolean chauffeur) {
		this.chauffeur = chauffeur;
	}

	public String getCodeLocation() {
		return this.codeLocation;
	}

	public void setCodeLocation(String codeLocation) {
		this.codeLocation = codeLocation;
	}

	public Date getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Date getDateDebut() {
		return this.dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateRetour() {
		return this.dateRetour;
	}

	public void setDateRetour(Date dateRetour) {
		this.dateRetour = dateRetour;
	}

	public int getRemise() {
		return this.remise;
	}

	public void setRemise(int remise) {
		this.remise = remise;
	}

	public String getStatut() {
		return this.statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public double getValeurCaution() {
		return this.valeurCaution;
	}

	public void setValeurCaution(double valeurCaution) {
		this.valeurCaution = valeurCaution;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Vehicule getVehicule() {
		return this.vehicule;
	}

	public void setVehicule(Vehicule vehicule) {
		this.vehicule = vehicule;
	}

	public String getHeure() {
		return heure;
	}

	public void setHeure(String heure) {
		this.heure = heure;
	}

	public String getFacturation() {
		return facturation;
	}

	public void setFacturation(String facturation) {
		this.facturation = facturation;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public Double getCout() {
		return cout;
	}

	public void setCout(Double cout) {
		this.cout = cout;
	}

	public Double getPayer() {
		return payer;
	}

	public void setPayer(Double payer) {
		this.payer = payer;
	}

	public String getStatutPaiement() {
		return statutPaiement;
	}

	public void setStatutPaiement(String statutPaiement) {
		this.statutPaiement = statutPaiement;
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