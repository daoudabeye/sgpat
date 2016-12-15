package org.sgpat.form;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.sgpat.entity.Operation;

public class OperationForm {
	
	private String codeBenef;
	
	private String type;
	
	private Double montant;
	
	private String note;
	
	private String  beneficiaire;
	
	private String codeProprio;
	
	private String statut;
	
	private Double avance;
	
	private String motif;
	
	private String dateProchain;
	
	private String dateComptable;
	
	public OperationForm(){}
	
	public Operation create(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dateP = new Date();
		Date dateC = new Date();
		try {
			if(dateProchain != null )dateP = sdf.parse(dateProchain);
			if(dateComptable != null )dateC = sdf.parse(dateComptable);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return new Operation(beneficiaire, dateP, dateC, montant,
				note, type, 0, avance, motif);
	}

	public String getCodeBenef() {
		return codeBenef;
	}

	public void setCodeBenef(String codeBenef) {
		this.codeBenef = codeBenef;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getBeneficiaire() {
		return beneficiaire;
	}

	public void setBeneficiaire(String beneficiaire) {
		this.beneficiaire = beneficiaire;
	}

	public String getCodeProprio() {
		return codeProprio;
	}

	public void setCodeProprio(String codeProprio) {
		this.codeProprio = codeProprio;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public Double getAvance() {
		return avance;
	}

	public void setAvance(Double avance) {
		this.avance = avance;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	public String getDateProchain() {
		return dateProchain;
	}

	public void setDateProchain(String dateProchain) {
		this.dateProchain = dateProchain;
	}

	public String getDateComptable() {
		return dateComptable;
	}

	public void setDateComptable(String dateComptable) {
		this.dateComptable = dateComptable;
	}

}
