package org.sgpat.form;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.sgpat.entity.Maintenance;

public class MaintenanceForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	@NotBlank(message = MaintenanceForm.NOT_BLANK_MESSAGE)
	private String vehicule;
	
	@NotBlank(message = MaintenanceForm.NOT_BLANK_MESSAGE)
	private String natureOperation;
	
	@NotBlank(message = MaintenanceForm.NOT_BLANK_MESSAGE)
	private String designation;
	
	@NotBlank(message = MaintenanceForm.NOT_BLANK_MESSAGE)
	private String statut;
	
	private Double cout;
	
	@NotBlank(message = MaintenanceForm.NOT_BLANK_MESSAGE)
	private String dateProchain;

	public Maintenance getMaintenance(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dateProchainRevision = new Date();
		try {
			dateProchainRevision = sdf.parse(dateProchain);
		} catch (Exception e) {
			// TODO: handle exception
		}
		Maintenance m = new Maintenance(cout, dateProchainRevision, designation, statut, natureOperation);
		return m;
	}
	public String getVehicule() {
		return vehicule;
	}

	public void setVehicule(String vehicule) {
		this.vehicule = vehicule;
	}

	public String getNatureOperation() {
		return natureOperation;
	}

	public void setNatureOperation(String natureOperation) {
		this.natureOperation = natureOperation;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public Double getCout() {
		return cout;
	}

	public void setCout(Double cout) {
		this.cout = cout;
	}

	public String getDateProchain() {
		return dateProchain;
	}

	public void setDateProchain(String dateProchain) {
		this.dateProchain = dateProchain;
	}
	
}
