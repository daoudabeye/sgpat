package org.sgpat.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the operation database table.
 * 
 */
@Entity
@Table(name="operation")
@NamedQuery(name="Operation.findAll", query="SELECT o FROM Operation o")
public class Operation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column( name="date_prochain")
	private Date dateProchian;

	@Temporal(TemporalType.TIMESTAMP)
	@Column( name="date_comptable")
	private Date dateComptable;
	
	private Double montant;

	private String note;

	private String type;
	
	private String beneficiaire;
	
	@Column( name="id_benef")
	private Integer idBenef;
	
	private Double avance;
	
	@Column( name="motif_operation")
	private String motifOperation;
	
	@Column( name="montant_dus")
	private Double montantDus;
	
	@Column( name="montant_ceder")
	private Double montantCeder;
	
	//bi-directional many-to-one association to Employee
	@ManyToOne
	private Employee employee;

	public Operation() {
	}

	public Operation(String beneficiaire, Date dateProchain, Date dateComptable, double montant, String note, String type,
			 Integer idBenef, Double avance, String motifOperation) {
		super();
		this.beneficiaire = beneficiaire;
		this.montant = montant;
		this.note = note;
		this.type = type;
		this.idBenef = idBenef;
		this.avance = avance;
		this.motifOperation = motifOperation;
		this.date = new Date();
		this.dateProchian = dateProchain;
		this.dateComptable = dateComptable;
	}
	
	

	public Operation(Date date, Date dateProchian, Double montant, String note, String type, String beneficiaire,
			Integer idBenef, Double avance, String motifOperation, Double montantDus,Double montantCeder) {
		super();
		this.date = date;
		this.dateProchian = dateProchian;
		this.montant = montant;
		this.note = note;
		this.type = type;
		this.beneficiaire = beneficiaire;
		this.idBenef = idBenef;
		this.avance = avance;
		this.motifOperation = motifOperation;
		this.montantDus = montantDus;
		this.montantCeder = montantCeder;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getMontant() {
		return this.montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Integer getIdBenef() {
		return idBenef;
	}

	public void setIdBenef(Integer idBenef) {
		this.idBenef = idBenef;
	}

	public Double getAvance() {
		return avance;
	}

	public void setAvance(Double avance) {
		this.avance = avance;
	}

	public String getMotifOperation() {
		return motifOperation;
	}

	public void setMotifOperation(String motifOperation) {
		this.motifOperation = motifOperation;
	}

	public Date getDateProchian() {
		return dateProchian;
	}

	public void setDateProchian(Date dateProchian) {
		this.dateProchian = dateProchian;
	}

	public String getBeneficiaire() {
		return beneficiaire;
	}

	public void setBeneficiaire(String beneficiaire) {
		this.beneficiaire = beneficiaire;
	}

	public Double getMontantDus() {
		return montantDus;
	}

	public void setMontantDus(Double montantDus) {
		this.montantDus = montantDus;
	}

	public Double getMontantCeder() {
		return montantCeder;
	}

	public void setMontantCeder(Double montantCeder) {
		this.montantCeder = montantCeder;
	}

	public Date getDateComptable() {
		return dateComptable;
	}

	public void setDateComptable(Date dateComptable) {
		this.dateComptable = dateComptable;
	}

}