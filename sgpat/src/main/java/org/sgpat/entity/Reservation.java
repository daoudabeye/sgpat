package org.sgpat.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the reservation database table.
 * 
 */
@Entity
@Table(name="reservation")
@NamedQuery(name="Reservation.findAll", query="SELECT r FROM Reservation r")
public class Reservation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private Boolean chauffeur;

	@Column(name="code_reservation")
	private String codeReservation;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_debut")
	private Date dateDebut;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_reservation")
	private Date dateReservation;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_retour")
	private Date dateRetour;

	private String note;
	
	private String heure;

	@Column(name="statut_reservation")
	private String statutReservation;

	//bi-directional many-to-one association to Client
	@ManyToOne
	private Client client;

	//bi-directional many-to-one association to Vehicule
	@ManyToOne
	private Vehicule vehicule;

	public Reservation() {
	}

	public Reservation(Boolean chauffeur, Date dateDebut, Date dateReservation, Date dateRetour,
			String note, String statutReservation) {
		super();
		this.chauffeur = chauffeur;
		this.dateDebut = dateDebut;
		this.dateReservation = dateReservation;
		this.dateRetour = dateRetour;
		this.note = note;
		this.statutReservation = statutReservation;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean getChauffeur() {
		return this.chauffeur;
	}

	public void setChauffeur(Boolean chauffeur) {
		this.chauffeur = chauffeur;
	}

	public String getCodeReservation() {
		return this.codeReservation;
	}

	public void setCodeReservation(String codeReservation) {
		this.codeReservation = codeReservation;
	}

	public Date getDateDebut() {
		return this.dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateReservation() {
		return this.dateReservation;
	}

	public void setDateReservation(Date dateReservation) {
		this.dateReservation = dateReservation;
	}

	public Date getDateRetour() {
		return this.dateRetour;
	}

	public void setDateRetour(Date dateRetour) {
		this.dateRetour = dateRetour;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStatutReservation() {
		return this.statutReservation;
	}

	public void setStatutReservation(String statutReservation) {
		this.statutReservation = statutReservation;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Vehicule getVehicule() {
		return this.vehicule;
	}

	public void setVehicule(Vehicule vehicule) {
		this.vehicule = vehicule;
	}

	public String getHeure() {
		return heure;
	}

	public void setHeure(String heure) {
		this.heure = heure;
	}

}