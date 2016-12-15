package org.sgpat.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the pieces database table.
 * 
 */
@Entity
@Table(name="pieces")
@NamedQuery(name="Piece.findAll", query="SELECT p FROM Piece p")
public class Piece implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_ajout")
	private Date dateAjout;

	private String designation;

	private String etat;

	private String marque;

	private String prix;

	private String reference;

	private String statut;

	//bi-directional many-to-one association to Fournisseur
	@ManyToOne
	private Fournisseur fournisseur;

	//bi-directional many-to-one association to Vehicule
	@ManyToOne
	private Vehicule vehicule;

	public Piece() {
	}

	public Piece(String designation, String etat, String marque, String prix, String reference,
			String statut) {
		super();
		this.dateAjout = new Date();
		this.designation = designation;
		this.etat = etat;
		this.marque = marque;
		this.prix = prix;
		this.reference = reference;
		this.statut = statut;
	}



	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateAjout() {
		return this.dateAjout;
	}

	public void setDateAjout(Date dateAjout) {
		this.dateAjout = dateAjout;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEtat() {
		return this.etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public String getMarque() {
		return this.marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	public String getPrix() {
		return this.prix;
	}

	public void setPrix(String prix) {
		this.prix = prix;
	}

	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getStatut() {
		return this.statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public Fournisseur getFournisseur() {
		return this.fournisseur;
	}

	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}

	public Vehicule getVehicule() {
		return this.vehicule;
	}

	public void setVehicule(Vehicule vehicule) {
		this.vehicule = vehicule;
	}

}