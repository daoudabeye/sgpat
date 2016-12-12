package org.sgpat.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the vehicule database table.
 * 
 */
@Entity
@Table(name="vehicule")
@NamedQuery(name="Vehicule.findAll", query="SELECT v FROM Vehicule v")
public class Vehicule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String classe;

	private String code;

	private String couleur;

	private String etat;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_mise_en_service")
	private Date dateMiseEnService;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_creation")
	private Date dateCreation;

	private String energie;

	private String immatriculation;

	@Column(name="kilometrage_actuel")
	private String kilometrageActuel;

	@Column(name="kilometrage_initial")
	private String kilometrageInitial;

	private String marque;

	@Column(name="niveau_carburant")
	private String niveauCarburant;

	@Column(name="nombre_de_place")
	private int nombreDePlace;

	@Column(name="numero_serie")
	private String numeroSerie;

	private String observations;

	@Column(name="prix_achat")
	private double prixAchat;

	@Column(name="roue_de_secours")
	private Boolean roueDeSecours;

	private String type;

	//bi-directional many-to-one association to DocumentVehicule
	@OneToMany(mappedBy="vehicule")
	private List<DocumentVehicule> documentVehicules;

	//bi-directional many-to-one association to Location
	@OneToMany(mappedBy="vehicule")
	private List<Location> locations;

	//bi-directional many-to-one association to Maintenance
	@OneToMany(mappedBy="vehicule")
	private List<Maintenance> maintenances;

	//bi-directional many-to-one association to Piece
	@OneToMany(mappedBy="vehicule")
	private List<Piece> pieces;

	//bi-directional many-to-one association to Reservation
	@OneToMany(mappedBy="vehicule")
	private List<Reservation> reservations;

	//bi-directional many-to-one association to StatutVehicule
	@OneToMany(mappedBy="vehicule")
	private List<StatutVehicule> statutVehicules;

	//bi-directional many-to-one association to Category
	@ManyToOne
	private Categorie categorie;

	//bi-directional many-to-one association to Chauffeur
	@ManyToOne
	private Chauffeur chauffeur;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	private Employee employee;

	//bi-directional many-to-one association to Proprio
	@ManyToOne
	private Proprio proprio;

	//bi-directional many-to-one association to Recette
	@OneToMany(mappedBy="vehicule")
	private List<Recette> recettes;

	public Vehicule() {
		this.setDateCreation(new Date());
	}

	public Vehicule(String classe, String marque, String couleur, int nombreDePlace, String immatriculation,
			String niveauCarburant, String energie, String kilometrageInitial, String kilometrageActuel,
			Boolean roueDeSecours, Date dateMiseEnService, String numeroSerie, String observations, double prixAchat,
			String type) {
		super();
		this.classe = classe;
		this.marque = marque;
		this.couleur = couleur;
		this.nombreDePlace = nombreDePlace;
		this.immatriculation = immatriculation;
		this.niveauCarburant = niveauCarburant;
		this.energie = energie;
		this.kilometrageInitial = kilometrageInitial;
		this.kilometrageActuel = kilometrageActuel;
		this.roueDeSecours = roueDeSecours;
		this.dateMiseEnService = dateMiseEnService;
		this.numeroSerie = numeroSerie;
		this.observations = observations;
		this.prixAchat = prixAchat;
		this.type = type;
		this.setDateCreation(new Date());
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClasse() {
		return this.classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCouleur() {
		return this.couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	public Date getDateMiseEnService() {
		return this.dateMiseEnService;
	}

	public void setDateMiseEnService(Date dateMiseEnService) {
		this.dateMiseEnService = dateMiseEnService;
	}

	public String getEnergie() {
		return this.energie;
	}

	public void setEnergie(String energie) {
		this.energie = energie;
	}

	public String getImmatriculation() {
		return this.immatriculation;
	}

	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}

	public String getKilometrageActuel() {
		return this.kilometrageActuel;
	}

	public void setKilometrageActuel(String kilometrageActuel) {
		this.kilometrageActuel = kilometrageActuel;
	}

	public String getKilometrageInitial() {
		return this.kilometrageInitial;
	}

	public void setKilometrageInitial(String kilometrageInitial) {
		this.kilometrageInitial = kilometrageInitial;
	}

	public String getMarque() {
		return this.marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	public String getNiveauCarburant() {
		return this.niveauCarburant;
	}

	public void setNiveauCarburant(String niveauCarburant) {
		this.niveauCarburant = niveauCarburant;
	}

	public int getNombreDePlace() {
		return this.nombreDePlace;
	}

	public void setNombreDePlace(int nombreDePlace) {
		this.nombreDePlace = nombreDePlace;
	}

	public String getNumeroSerie() {
		return this.numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public String getObservations() {
		return this.observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public double getPrixAchat() {
		return this.prixAchat;
	}

	public void setPrixAchat(double prixAchat) {
		this.prixAchat = prixAchat;
	}

	public Boolean getRoueDeSecours() {
		return this.roueDeSecours;
	}

	public void setRoueDeSecours(Boolean roueDeSecours) {
		this.roueDeSecours = roueDeSecours;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<DocumentVehicule> getDocumentVehicules() {
		return this.documentVehicules;
	}

	public void setDocumentVehicules(List<DocumentVehicule> documentVehicules) {
		this.documentVehicules = documentVehicules;
	}

	public DocumentVehicule addDocumentVehicule(DocumentVehicule documentVehicule) {
		getDocumentVehicules().add(documentVehicule);
		documentVehicule.setVehicule(this);

		return documentVehicule;
	}

	public DocumentVehicule removeDocumentVehicule(DocumentVehicule documentVehicule) {
		getDocumentVehicules().remove(documentVehicule);
		documentVehicule.setVehicule(null);

		return documentVehicule;
	}

	public List<Location> getLocations() {
		return this.locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public Location addLocation(Location location) {
		getLocations().add(location);
		location.setVehicule(this);

		return location;
	}

	public Location removeLocation(Location location) {
		getLocations().remove(location);
		location.setVehicule(null);

		return location;
	}

	public List<Maintenance> getMaintenances() {
		return this.maintenances;
	}

	public void setMaintenances(List<Maintenance> maintenances) {
		this.maintenances = maintenances;
	}

	public Maintenance addMaintenance(Maintenance maintenance) {
		getMaintenances().add(maintenance);
		maintenance.setVehicule(this);

		return maintenance;
	}

	public Maintenance removeMaintenance(Maintenance maintenance) {
		getMaintenances().remove(maintenance);
		maintenance.setVehicule(null);

		return maintenance;
	}

	public List<Piece> getPieces() {
		return this.pieces;
	}

	public void setPieces(List<Piece> pieces) {
		this.pieces = pieces;
	}

	public Piece addPiece(Piece piece) {
		getPieces().add(piece);
		piece.setVehicule(this);

		return piece;
	}

	public Piece removePiece(Piece piece) {
		getPieces().remove(piece);
		piece.setVehicule(null);

		return piece;
	}

	public List<Reservation> getReservations() {
		return this.reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public Reservation addReservation(Reservation reservation) {
		getReservations().add(reservation);
		reservation.setVehicule(this);

		return reservation;
	}

	public Reservation removeReservation(Reservation reservation) {
		getReservations().remove(reservation);
		reservation.setVehicule(null);

		return reservation;
	}

	public List<StatutVehicule> getStatutVehicules() {
		return this.statutVehicules;
	}

	public void setStatutVehicules(List<StatutVehicule> statutVehicules) {
		this.statutVehicules = statutVehicules;
	}

	public StatutVehicule addStatutVehicule(StatutVehicule statutVehicule) {
		getStatutVehicules().add(statutVehicule);
		statutVehicule.setVehicule(this);

		return statutVehicule;
	}

	public StatutVehicule removeStatutVehicule(StatutVehicule statutVehicule) {
		getStatutVehicules().remove(statutVehicule);
		statutVehicule.setVehicule(null);

		return statutVehicule;
	}

	public Categorie getCategorie() {
		return this.categorie;
	}

	public void setCategorie(Categorie category) {
		this.categorie = category;
	}

	public Chauffeur getChauffeur() {
		return this.chauffeur;
	}

	public void setChauffeur(Chauffeur chauffeur) {
		this.chauffeur = chauffeur;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Proprio getProprio() {
		return this.proprio;
	}

	public void setProprio(Proprio proprio) {
		this.proprio = proprio;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}
	
	public List<Recette> getRecettes() {
		return this.recettes;
	}

	public void setRecettes(List<Recette> recettes) {
		this.recettes = recettes;
	}
	
	public Recette addRecette(Recette recette) {
		getRecettes().add(recette);
		recette.setVehicule(this);

		return recette;
	}

	public Recette removeRecette(Recette recette) {
		getRecettes().remove(recette);
		recette.setVehicule(null);

		return recette;
	}

}