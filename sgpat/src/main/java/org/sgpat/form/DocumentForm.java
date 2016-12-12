package org.sgpat.form;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.sgpat.entity.Document;

public class DocumentForm {
	
	private String designation;
	private String numero;
	private String dateEmission;
	private String dateExpiration;
	private String paysEmetteur;
	private String codeClient;
	
	public Document createDocument(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date emission = sdf.parse(dateEmission);
			Date expiration = sdf.parse(dateExpiration);
			return new Document(numero, designation, emission, expiration, paysEmetteur);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	public String getDateEmission() {
		return dateEmission;
	}
	public void setDateEmission(String dateEmission) {
		this.dateEmission = dateEmission;
	}
	public String getDateExpiration() {
		return dateExpiration;
	}
	public void setDateExpiration(String dateExpiration) {
		this.dateExpiration = dateExpiration;
	}
	public String getCodeClient() {
		return codeClient;
	}
	public void setCodeClient(String codeClient) {
		this.codeClient = codeClient;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getPaysEmetteur() {
		return paysEmetteur;
	}
	public void setPaysEmetteur(String paysEmetteur) {
		this.paysEmetteur = paysEmetteur;
	}
}
