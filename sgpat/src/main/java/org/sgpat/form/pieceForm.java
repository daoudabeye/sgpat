package org.sgpat.form;

import org.hibernate.validator.constraints.NotBlank;
import org.sgpat.entity.Piece;

public class pieceForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	@NotBlank(message = pieceForm.NOT_BLANK_MESSAGE)
	private String designation;
	
	@NotBlank(message = pieceForm.NOT_BLANK_MESSAGE)
	private String prix;
	
	@NotBlank(message = pieceForm.NOT_BLANK_MESSAGE)
	private String marque;
	
	private String reference ;

	private String etat ;

	public Piece create(){
		Piece piece = new Piece(designation, etat, marque, prix, reference, "N");
		return piece;
	}

	public String getPrix() {
		return prix;
	}

	public void setPrix(String prix) {
		this.prix = prix;
	}

	public String getMarque() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}


	public String getDesignation() {
		return designation;
	}


	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	
}
