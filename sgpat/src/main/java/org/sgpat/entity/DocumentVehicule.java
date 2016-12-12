package org.sgpat.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the document_vehicule database table.
 * 
 */
@Entity
@Table(name="document_vehicule")
@NamedQuery(name="DocumentVehicule.findAll", query="SELECT d FROM DocumentVehicule d")
public class DocumentVehicule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private byte alert;

	@Column(name="code_document")
	private String codeDocument;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_creation")
	private Date dateCreation;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_expiration")
	private Date dateExpiration;

	private String designation;

	private String numero;

	//bi-directional many-to-one association to Vehicule
	@ManyToOne
	private Vehicule vehicule;

	public DocumentVehicule() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getAlert() {
		return this.alert;
	}

	public void setAlert(byte alert) {
		this.alert = alert;
	}

	public String getCodeDocument() {
		return this.codeDocument;
	}

	public void setCodeDocument(String codeDocument) {
		this.codeDocument = codeDocument;
	}

	public Date getDateCreation() {
		return this.dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Date getDateExpiration() {
		return this.dateExpiration;
	}

	public void setDateExpiration(Date dateExpiration) {
		this.dateExpiration = dateExpiration;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Vehicule getVehicule() {
		return this.vehicule;
	}

	public void setVehicule(Vehicule vehicule) {
		this.vehicule = vehicule;
	}

}