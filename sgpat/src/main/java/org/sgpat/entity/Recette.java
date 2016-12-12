package org.sgpat.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the recette database table.
 * 
 */
@Entity
@Table(name="recette")
@NamedQuery(name="Recette.findAll", query="SELECT r FROM Recette r")
public class Recette implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(name="montant_dus")
	private double montantDus;

	@Column(name="montant_payer")
	private double montantPayer;
	
	private String statut;

	//bi-directional many-to-one association to Chauffeur
	@ManyToOne
	private Vehicule vehicule;

	//bi-directional many-to-one association to Chauffeur
	@ManyToOne
	private Chauffeur chauffeur;

	public Recette() {
	}

	public Recette(Date date, double montantDus, double montantPayer, String statut, Vehicule vehicule,
			Chauffeur chauffeur) {
		super();
		this.date = date;
		this.montantDus = montantDus;
		this.montantPayer = montantPayer;
		this.statut = statut;
		this.vehicule = vehicule;
		this.chauffeur = chauffeur;
	}

	public void payer(Double montant){
		this.montantPayer += montant;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getMontantDus() {
		return this.montantDus;
	}

	public void setMontantDus(double montantDus) {
		this.montantDus = montantDus;
	}

	public double getMontantPayer() {
		return this.montantPayer;
	}

	public void setMontantPayer(double montantPayer) {
		this.montantPayer = montantPayer;
	}

	public Vehicule getVehicule() {
		return vehicule;
	}

	public void setVehicule(Vehicule vehicule) {
		this.vehicule = vehicule;
	}

	public Chauffeur getChauffeur() {
		return chauffeur;
	}

	public void setChauffeur(Chauffeur chauffeur) {
		this.chauffeur = chauffeur;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

}