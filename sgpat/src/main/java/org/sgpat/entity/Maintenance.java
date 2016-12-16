package org.sgpat.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the maintenance database table.
 * 
 */
@Entity
@Table(name="maintenance")
@NamedQuery(name="Maintenance.findAll", query="SELECT m FROM Maintenance m")
public class Maintenance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="cout_revision")
	private double coutRevision;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_prochain_revision")
	private Date dateProchainRevision;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_revision")
	private Date dateRevision;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_sortie")
	private Date dateSortie;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_entre")
	private Date dateEntre;

	private String designation;

	private String statut;

	private String type;
	
	private String motif;

	//bi-directional many-to-one association to Vehicule
	@ManyToOne
	private Vehicule vehicule;

	public Maintenance() {
	}

	public Maintenance( double coutRevision, Date dateProchainRevision, String designation, String statut,
			String type, String motif) {
		super();
		this.coutRevision = coutRevision;
		this.dateProchainRevision = dateProchainRevision;
		this.designation = designation;
		this.statut = statut;
		this.type = type;
		this.dateRevision = new Date();
		this.motif = motif;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public double getCoutRevision() {
		return this.coutRevision;
	}

	public void setCoutRevision(double coutRevision) {
		this.coutRevision = coutRevision;
	}

	public Date getDateProchainRevision() {
		return this.dateProchainRevision;
	}

	public void setDateProchainRevision(Date dateProchainRevision) {
		this.dateProchainRevision = dateProchainRevision;
	}

	public Date getDateRevision() {
		return this.dateRevision;
	}

	public void setDateRevision(Date dateRevision) {
		this.dateRevision = dateRevision;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getStatut() {
		return this.statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Vehicule getVehicule() {
		return this.vehicule;
	}

	public void setVehicule(Vehicule vehicule) {
		this.vehicule = vehicule;
	}

	public Date getDateSortie() {
		return dateSortie;
	}

	public void setDateSortie(Date dateSortie) {
		this.dateSortie = dateSortie;
	}

	public Date getDateEntre() {
		return dateEntre;
	}

	public void setDateEntre(Date dateEntre) {
		this.dateEntre = dateEntre;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

}