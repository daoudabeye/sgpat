package org.sgpat.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the categorie database table.
 * 
 */
@Entity
@Table(name="categorie")
@NamedQuery(name="Categorie.findAll", query="SELECT c FROM Categorie c")
public class Categorie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String nom;
	
	private String note;

	@Column(name="penalite_par_heurs")
	private Double penaliteParHeurs;

	@Column(name="poucentage_poprio")
	private Integer poucentagePoprio;

	@Column(name="prix_par_heurs")
	private Double prixParHeurs;

	@Column(name="prix_par_jours")
	private Double prixParJours;

	//bi-directional many-to-one association to Vehicule
	@OneToMany(mappedBy="categorie")
	private List<Vehicule> vehicules;

	public Categorie() {
	}
	
	public Categorie(String nom, Double prixParHeurs, Double prixParJours, Double penaliteParHeurs,
			Integer poucentagePoprio, String note) {
		super();
		this.nom = nom;
		this.prixParHeurs = prixParHeurs;
		this.prixParJours = prixParJours;
		this.penaliteParHeurs = penaliteParHeurs;
		this.poucentagePoprio = poucentagePoprio;
		this.note = note;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public double getPenaliteParHeurs() {
		return this.penaliteParHeurs;
	}

	public void setPenaliteParHeurs(double penaliteParHeurs) {
		this.penaliteParHeurs = penaliteParHeurs;
	}

	public Integer getPoucentagePoprio() {
		return this.poucentagePoprio;
	}

	public void setPoucentagePoprio(int poucentagePoprio) {
		this.poucentagePoprio = poucentagePoprio;
	}

	public Double getPrixParHeurs() {
		return this.prixParHeurs;
	}

	public void setPrixParHeurs(double prixParHeurs) {
		this.prixParHeurs = prixParHeurs;
	}

	public Double getPrixParJours() {
		return this.prixParJours;
	}

	public void setPrixParJours(double prixParJours) {
		this.prixParJours = prixParJours;
	}

	public List<Vehicule> getVehicules() {
		return this.vehicules;
	}

	public void setVehicules(List<Vehicule> vehicules) {
		this.vehicules = vehicules;
	}

	public Vehicule addVehicule(Vehicule vehicule) {
		getVehicules().add(vehicule);
		vehicule.setCategorie(this);

		return vehicule;
	}

	public Vehicule removeVehicule(Vehicule vehicule) {
		getVehicules().remove(vehicule);
		vehicule.setCategorie(null);

		return vehicule;
	}

}