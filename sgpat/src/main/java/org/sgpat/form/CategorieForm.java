package org.sgpat.form;

import org.sgpat.entity.Categorie;

public class CategorieForm {

	private String nomCategorie;
	private Double prixJours;
	private Double prixHeure;
	private Double penaliteHeure;
	private Integer poucentagePoprio;
	private String note;
	
	public Categorie create(){
		return new Categorie(nomCategorie, prixHeure, prixJours, penaliteHeure, poucentagePoprio, note);
	}
	public String getNomCategorie() {
		return nomCategorie;
	}
	public void setNomCategorie(String nomCategorie) {
		this.nomCategorie = nomCategorie;
	}
	public Double getPrixJours() {
		return prixJours;
	}
	public void setPrixJours(Double prixJours) {
		this.prixJours = prixJours;
	}
	public Double getPrixHeure() {
		return prixHeure;
	}
	public void setPrixHeure(Double prixHeure) {
		this.prixHeure = prixHeure;
	}
	public Double getPenaliteHeure() {
		return penaliteHeure;
	}
	public void setPenaliteHeure(Double penaliteHeure) {
		this.penaliteHeure = penaliteHeure;
	}
	public Integer getPoucentagePoprio() {
		return poucentagePoprio;
	}
	public void setPoucentagePoprio(Integer poucentagePoprio) {
		this.poucentagePoprio = poucentagePoprio;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
