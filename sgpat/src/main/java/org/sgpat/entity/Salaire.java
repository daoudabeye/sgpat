package org.sgpat.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the salaire database table.
 * 
 */
@Entity
@Table(name="salaire")
@NamedQuery(name="Salaire.findAll", query="SELECT s FROM Salaire s")
public class Salaire implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private double montant;
	
	private double avance;

	private String statut;
	
	@Column(name="mode_paiement")
	private String modePaiement;

	//bi-directional many-to-one association to Chauffeur
	@ManyToOne
	private Chauffeur chauffeur;

	public Salaire() {
	}

	
	public Salaire(Date date, double montant, String statut) {
		super();
		this.date = date;
		this.montant = montant;
		this.statut = statut;
	}


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getMontant() {
		return this.montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getStatut() {
		return this.statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public Chauffeur getChauffeur() {
		return this.chauffeur;
	}

	public void setChauffeur(Chauffeur chauffeur) {
		this.chauffeur = chauffeur;
	}


	public double getAvance() {
		return avance;
	}


	public void setAvance(double avance) {
		this.avance = avance;
	}


	public String getModePaiement() {
		return modePaiement;
	}


	public void setModePaiement(String modePaiement) {
		this.modePaiement = modePaiement;
	}

}