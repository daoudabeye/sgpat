package org.sgpat.form;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.sgpat.entity.Client;
import org.sgpat.entity.Document;
import org.springframework.util.Assert;

public class ClientForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	private static final String EMAIL_MESSAGE = "{email.message}";

	@NotBlank(message = ClientForm.NOT_BLANK_MESSAGE)
	@Size(min=2 , max=20, message = "{name.size}")
	private String nom;
	
	@NotBlank(message = ClientForm.NOT_BLANK_MESSAGE)
	@Size(min=2 , max=20, message = "{name.size}")
	private String prenom;
	
	@Email(message = ClientForm.EMAIL_MESSAGE)
	private String email;
	
	@NotBlank(message = ClientForm.NOT_BLANK_MESSAGE)
	@Size(min=8 , message = "Minimum 8 caractères")
	private String telephone;
	
	@NotBlank(message = ClientForm.NOT_BLANK_MESSAGE)
	@Size(max=99, message = "Adresse trop long")
	private String adresse;
	
	@NotBlank(message = "Vous devez selectionner le profile : PARTICULIER ou SOCIETE")
	private String profile;
	
	@NotBlank(message = "Vous devez spécifié le type de document : PERMIS ou AUTRE")
	private String permis;
	
	@NotBlank(message = ClientForm.NOT_BLANK_MESSAGE)
	private String ville;
	
	@NotBlank(message = ClientForm.NOT_BLANK_MESSAGE)
	private String pays;
	
	private String typePieceId;
	
	@NotBlank(message = ClientForm.NOT_BLANK_MESSAGE)
	private String numeroPiece;
	
	@NotBlank(message = ClientForm.NOT_BLANK_MESSAGE)
	private String paysEmetteur;
	
	@NotBlank(message = ClientForm.NOT_BLANK_MESSAGE)
	private String dateEmission;
	
	@NotBlank(message = ClientForm.NOT_BLANK_MESSAGE)
	private String dateExpiration;
	
	@NotBlank(message = ClientForm.NOT_BLANK_MESSAGE)
	private String lieuNaissance;
	
	@NotBlank(message = ClientForm.NOT_BLANK_MESSAGE)
	private String dateNaissance;
	
	private String observations;
	
	@NotBlank(message = "Choisir si le client doit avoir un ACCES INTERNET ou NON")
	private String accesInternet;

	
	
	public ClientForm(Client client) {
		super();
		this.nom = client.getNom();
		this.prenom = client.getPrenom();
		this.email = client.getEmail();
		this.telephone = client.getTelephone();
		this.adresse = client.getAdresse();
		this.profile = client.getProfile();
		this.permis = client.getPermis() == true ? "OUI" : "NOM";
		this.ville = client.getVille();
		this.pays = client.getPays();
		this.typePieceId = "";
		this.numeroPiece = "";
		this.paysEmetteur = "";
		this.dateEmission = "";
		this.dateExpiration = "";
		this.lieuNaissance = client.getLieuNaissance();
		this.dateNaissance = client.getDateNaissance().toString();
		this.observations = client.getObservations();
		this.accesInternet = "";
	}

	public ClientForm() {
		// TODO Auto-generated constructor stub
	}

	public Client getClient(){
		SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy");
		Boolean getPermis = true;
		Date dateN = null;
		try {
			dateN = pattern.parse(dateNaissance);
		} catch (Exception e) {
			// TODO: handle exception
			Assert.isTrue(false, "Date Naissance Incorrect");
		}
		if(typePieceId == null){
			Assert.isTrue(permis.equals("OUI"), "Merci d'indiquer le type de document fournis.");
		}
		if(typePieceId.isEmpty()){
			Assert.isTrue(permis.equals("OUI"), "Merci d'indiquer le type de document fournis.");
		}
		
		Client client  = new Client(nom, prenom, dateN, adresse, email, telephone, profile, getPermis, pays, ville, observations);
		client.setProfile(profile);
		return client;

	}

	public Document createDocument(){
		SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy"); 
		Date dateEm = new Date();
		Date dateEx = new Date();
		try {
			dateEm = pattern.parse(dateEmission.trim());
			dateEx = pattern.parse(dateExpiration.trim());
		} catch (Exception e) {
			// TODO: handle exception
			Assert.isTrue(false, "Format date Incorrect");
			e.printStackTrace();
		}
		Document doc = new Document(numeroPiece, typePieceId, dateEm, dateEx, paysEmetteur);
		doc.setDesignation(hasPermis() ? "PERMIS" : typePieceId);
		return doc;
	}

	public Boolean hasPermis(){
		if(permis == null) return false;
		if(permis.equals("OUI"))
			return true;
		return false;
	}
	
	public Boolean hasInternetAccess(){
		if(permis == null) return false;
		if(permis.equals("OUI"))
			return true;
		return false;
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
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getPermis() {
		return permis;
	}
	public void setPermis(String permis) {
		this.permis = permis;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
	public String getTypePieceId() {
		return typePieceId;
	}
	public void setTypePieceId(String typePieceId) {
		this.typePieceId = typePieceId;
	}
	public String getNumeroPiece() {
		return numeroPiece;
	}
	public void setNumeroPiece(String numeroPiece) {
		this.numeroPiece = numeroPiece;
	}
	public String getPaysEmetteur() {
		return paysEmetteur;
	}
	public void setPaysEmetteur(String paysEmetteur) {
		this.paysEmetteur = paysEmetteur;
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
	public String getLieuNaissance() {
		return lieuNaissance;
	}
	public void setLieuNaissance(String lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
	}
	public String getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getObservations() {
		return observations;
	}
	public void setObservations(String observation) {
		this.observations = observation;
	}

	public String getAccesInternet() {
		return accesInternet;
	}

	public void setAccesInternet(String accesInternet) {
		this.accesInternet = accesInternet;
	}

}
