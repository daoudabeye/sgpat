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
 * The persistent class for the proprio database table.
 * 
 */
@Entity
@Table(name="proprio")
@NamedQuery(name="Proprio.findAll", query="SELECT p FROM Proprio p")
public class Proprio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String adresse;

	@Column(name="code_proprio")
	private String codeProprio;

	private String designation;

	private String email;

	private String telephone;
	
	private String banque;
	
	private String mensualite;
	
	@Column(name="numero_compte")
	private String numeroCompte;
	
	@Temporal(TemporalType.DATE)
	@Column(name="date_inscription")
	private Date dateInscription;

	//bi-directional many-to-one association to Account
	@ManyToOne
	private Account account;

	//bi-directional many-to-one association to Vehicule
	@OneToMany(mappedBy="proprio")
	private List<Vehicule> vehicules;

	public Proprio() {
	}
	
	public Proprio(String adresse, String designation, String email, String telephone,
			String banque, String mensualite) {
		super();
		this.adresse = adresse;
		this.designation = designation;
		this.email = email;
		this.telephone = telephone;
		this.banque = banque;
		this.mensualite = mensualite;
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

	public String getCodeProprio() {
		return this.codeProprio;
	}

	public void setCodeProprio(String codeProprio) {
		this.codeProprio = codeProprio;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Vehicule> getVehicules() {
		return this.vehicules;
	}

	public void setVehicules(List<Vehicule> vehicules) {
		this.vehicules = vehicules;
	}

	public Vehicule addVehicule(Vehicule vehicule) {
		getVehicules().add(vehicule);
		vehicule.setProprio(this);

		return vehicule;
	}

	public Vehicule removeVehicule(Vehicule vehicule) {
		getVehicules().remove(vehicule);
		vehicule.setProprio(null);

		return vehicule;
	}

	public Date getDateInscription() {
		return dateInscription;
	}

	public void setDateInscription(Date dateInscription) {
		this.dateInscription = dateInscription;
	}


	public String getBanque() {
		return banque;
	}


	public void setBanque(String banque) {
		this.banque = banque;
	}


	public String getNumeroCompte() {
		return numeroCompte;
	}


	public void setNumeroCompte(String numeroCompte) {
		this.numeroCompte = numeroCompte;
	}

	public String getMensualite() {
		return mensualite;
	}

	public void setMensualite(String mensualite) {
		this.mensualite = mensualite;
	}

}