package org.sgpat.form;

import org.hibernate.validator.constraints.NotBlank;
import org.sgpat.entity.Proprio;

public class ProprioForm {
	
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	@NotBlank(message = ProprioForm.NOT_BLANK_MESSAGE)
	private String nom;
	
	private String prenom;
	
	@NotBlank(message = ProprioForm.NOT_BLANK_MESSAGE)
	private String adresse;
	
	private String email;
	
	@NotBlank(message = ProprioForm.NOT_BLANK_MESSAGE)
	private String telephone;
	
	private String numeroCompte;
	private String banque;
	private String mensualite;
	
	public ProprioForm(){}
	
	
	public Proprio create() {
		System.out.println(nom);
		System.out.println(prenom);
		System.out.println(adresse);
		System.out.println(numeroCompte);
		System.out.println(banque);
		String designation = this.nom + " "+ this.prenom;
		return new Proprio(adresse, designation, email, telephone, banque, mensualite);
	}


	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getNumeroCompte() {
		return numeroCompte;
	}
	public void setNumeroCompte(String numeroCompte) {
		this.numeroCompte = numeroCompte;
	}
	public String getBanque() {
		return banque;
	}
	public void setBanque(String banque) {
		this.banque = banque;
	}
	public String getMensualite() {
		return mensualite;
	}
	public void setMensualite(String mensualite) {
		this.mensualite = mensualite;
	}
}
