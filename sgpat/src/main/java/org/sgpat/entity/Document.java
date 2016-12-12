package org.sgpat.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the documents database table.
 * 
 */
@Entity
@Table(name="documents")
@NamedQuery(name="Document.findAll", query="SELECT d FROM Document d")
public class Document implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="date_emission")
	private Date dateEmission;

	@Temporal(TemporalType.DATE)
	@Column(name="date_expiration")
	private Date dateExpiration;

	private String designation;

	private String numero;

	@Column(name="pays_emetteur")
	private String paysEmetteur;

	//bi-directional many-to-one association to Client
	@ManyToOne
	private Client client;

	public Document() {
	}

	public Document(String numero, String designation, Date dateEmittion, Date dateExpiration,
			String paysEmetteur) {
		super();
		this.numero = numero;
		this.designation = designation == null ? designation : designation.toUpperCase();
		this.dateEmission = dateEmittion;
		this.dateExpiration = dateExpiration;
		this.paysEmetteur = paysEmetteur == null ? paysEmetteur : paysEmetteur.toUpperCase() ;
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateEmission() {
		return this.dateEmission;
	}

	public void setDateEmission(Date dateEmittion) {
		this.dateEmission = dateEmittion;
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

	public String getPaysEmetteur() {
		return this.paysEmetteur;
	}

	public void setPaysEmetteur(String paysEmetteur) {
		this.paysEmetteur = paysEmetteur;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}