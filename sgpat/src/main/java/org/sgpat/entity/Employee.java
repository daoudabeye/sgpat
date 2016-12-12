package org.sgpat.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.sgpat.account.Account;


/**
 * The persistent class for the employee database table.
 * 
 */
@Entity
@Table(name="employee")
@NamedQuery(name="Employee.findAll", query="SELECT e FROM Employee e")
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String adresse;

	private String code;

	private String nom;

	private String prenom;

	private String telephone;

	private String titre;
	
	@Temporal(TemporalType.DATE)
	@Column(name="date_inscription")
	private Date dateInscription;

	//bi-directional many-to-one association to Account
	@ManyToOne
	private Account account;

	//bi-directional many-to-one association to Location
	@OneToMany(mappedBy="employee")
	private List<Location> locations;

	//bi-directional many-to-one association to Operation
	@OneToMany(mappedBy="employee")
	private List<Operation> operations;

	//bi-directional many-to-one association to Vehicule
	@OneToMany(mappedBy="employee")
	private List<Vehicule> vehicules;

	public Employee() {
	}

	public Employee(String adresse, String nom, String prenom, String telephone, String titre) {
		super();
		this.adresse = adresse;
		this.nom = nom;
		this.prenom = prenom;
		this.telephone = telephone;
		this.titre = titre;
		this.dateInscription = new Date();
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

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getTitre() {
		return this.titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Location> getLocations() {
		return this.locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public Location addLocation(Location location) {
		getLocations().add(location);
		location.setEmployee(this);

		return location;
	}

	public Location removeLocation(Location location) {
		getLocations().remove(location);
		location.setEmployee(null);

		return location;
	}

	public List<Operation> getOperations() {
		return this.operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public Operation addOperation(Operation operation) {
		getOperations().add(operation);
		operation.setEmployee(this);

		return operation;
	}

	public Operation removeOperation(Operation operation) {
		getOperations().remove(operation);
		operation.setEmployee(null);

		return operation;
	}

	public List<Vehicule> getVehicules() {
		return this.vehicules;
	}

	public void setVehicules(List<Vehicule> vehicules) {
		this.vehicules = vehicules;
	}

	public Vehicule addVehicule(Vehicule vehicule) {
		getVehicules().add(vehicule);
		vehicule.setEmployee(this);

		return vehicule;
	}

	public Vehicule removeVehicule(Vehicule vehicule) {
		getVehicules().remove(vehicule);
		vehicule.setEmployee(null);

		return vehicule;
	}

	public Date getDateInscription() {
		return dateInscription;
	}

	public void setDateInscription(Date dateInscription) {
		this.dateInscription = dateInscription;
	}

}