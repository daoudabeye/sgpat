package org.sgpat.form;

import org.sgpat.entity.Employee;
import org.sgpat.entity.Proprio;

public class AgentForm {

	private String nom;
	private String prenom;
	private String adresse;
	private String designation;
	private String telephone;
	private String titre;
	private String email;
	
	public Employee createAgent(){
		return new Employee(adresse, nom, prenom, telephone, titre);
	}
	
	public Proprio createProprio(){
		return new Proprio(adresse, designation, email, telephone);
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
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
