package org.sgpat.service;

import java.util.Date;
import java.util.List;

import org.sgpat.account.Account;
import org.sgpat.account.AccountService;
import org.sgpat.entity.Categorie;
import org.sgpat.entity.Chauffeur;
import org.sgpat.entity.Document;
import org.sgpat.entity.Employee;
import org.sgpat.entity.Maintenance;
import org.sgpat.entity.Piece;
import org.sgpat.entity.Proprio;
import org.sgpat.entity.Vehicule;
import org.sgpat.form.MaintenanceForm;
import org.sgpat.form.VehiculeForm;
import org.sgpat.form.pieceForm;
import org.sgpat.repository.DocumentRepository;
import org.sgpat.repository.EmployeeRepository;
import org.sgpat.repository.MaintenanceRepository;
import org.sgpat.repository.PieceRepository;
import org.sgpat.repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class VehiculeService {

	@Autowired
	VehiculeRepository vehiculeRepository;

	@Autowired
	CategorieService categorieService;

	@Autowired
	ProprioService proprioService;

	@Autowired
	ChauffeurService chauffeurService;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	MaintenanceRepository maintenanceRepository;
	
	@Autowired 
	DocumentRepository documentRepository;
	
	@Autowired
	PieceRepository pieceRepository;
	
	public List<Vehicule> getAll(){
		return vehiculeRepository.getAll();
	}
	
	public List<Piece> findPiece(String codeVehicule){
		return pieceRepository.findByVehiculeCode(codeVehicule);
	}
	
	public Vehicule findByCode(String code){
		return vehiculeRepository.findByCode(code);
	}
	
	public List<Document> findByVehicule(String codeVehicule){
		return documentRepository.findByVehiculeCode(codeVehicule);
	}
	
	public List<Vehicule> findByEtat(String classe, String etat){
		return vehiculeRepository.findByClasseAndEtat(classe, etat);
	}
	
	public Vehicule findVehicule(Chauffeur chauffeur){
		return vehiculeRepository.findFirst1ByChauffeurCodeChauffeur(chauffeur.getCodeChauffeur());
	}
	
	public List<Maintenance> findMaintenance(String codeVehicule){
		return maintenanceRepository.findByVehiculeCode(codeVehicule);
	}
	public Long count(){
		return vehiculeRepository.count();
	}
	
	public Long count(String etat){
		return vehiculeRepository.countByEtat(etat);
	}
	
	public List<Vehicule> findByProprio(String codeProprio){
		Proprio proprio = proprioService.findByCode(codeProprio);
		return vehiculeRepository.findByProprio(proprio);
	}
	
	public List<Vehicule> findByProprio(Proprio proprio){
		return vehiculeRepository.findByProprio(proprio);
	}
	
	public List<Piece> getAllPieces(){
		return pieceRepository.getAll();
	}
	
	public List<Piece> findPieceByVehicule(String codeVehicule){
		return pieceRepository.findByVehiculeCode(codeVehicule);
	}
	@Transactional
	public Vehicule create(VehiculeForm vehiculeForm) {
		// TODO Auto-generated method stub
		Categorie categorie = categorieService.findByNom(vehiculeForm.getNomCategorie());
		Assert.notNull(categorie, "Catégorie incorrect");

		Proprio proprio = proprioService.findByCode(vehiculeForm.getProprio());
		Assert.notNull(proprio, "Code Proprio incorrect");
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Account current = accountService.findByUsername(username);
		Employee employee = employeeRepository.findFirst1ByAccountId(current.getId());


		Vehicule vehicule = vehiculeForm.createVehicule();
		vehicule.setCategorie(categorie);
		vehicule.setProprio(proprio);
		vehicule.setKilometrageInitial(vehicule.getKilometrageActuel());
		vehicule.setEmployee(employee);
		vehicule.setEtat("VD");
		vehicule.setDateCreation(new Date());

		if(vehiculeForm.getChauffeur() != null && !vehiculeForm.getChauffeur().isEmpty()){
			Chauffeur ch = chauffeurService.findByCode(vehiculeForm.getChauffeur());
			Assert.notNull(ch, "Code chauffeur incorrect");
			vehicule.setChauffeur(ch);
		}

		vehicule = vehiculeRepository.save(vehicule);

		String code = vehicule.getId()+"";
		int lenth = code.length();
		for(int i=5; i>0; i--, lenth--){
			if(lenth <= 0)
				code = "0"+code;
		}
		code = "VH"+code;

		vehiculeRepository.updateCode(vehicule.getId(), code);
		return vehicule;
	}
	
	@Transactional
	public Boolean modifier(String codeVehicule, VehiculeForm vehiculeForm){
		Vehicule vehicule = vehiculeForm.createVehicule();
		
		
		int update = vehiculeRepository.update(codeVehicule, vehicule.getImmatriculation(), vehicule.getMarque(), vehicule.getType(),
				vehicule.getDateMiseEnService(), vehicule.getCouleur(), vehicule.getClasse(), vehicule.getPrixAchat(),
				vehicule.getNumeroSerie(), vehicule.getKilometrageActuel(), vehicule.getNiveauCarburant(),
				vehicule.getRoueDeSecours(), vehicule.getEnergie(), vehicule.getObservations(), vehicule.getNombreDePlace());
		
		Categorie categorie = categorieService.findByNom(vehiculeForm.getNomCategorie());
		Assert.notNull(categorie, "Catégorie incorrect");

		Proprio proprio = proprioService.findByCode(vehiculeForm.getProprio());
		Assert.notNull(proprio, "Code Proprio incorrect");
		
		Vehicule v = this.findByCode(codeVehicule);
		Assert.notNull(proprio, "Code Vehicule incorrect");
		
		if(vehiculeForm.getChauffeur() != null && !vehiculeForm.getChauffeur().isEmpty()){
			Chauffeur ch = chauffeurService.findByCode(vehiculeForm.getChauffeur());
			Assert.notNull(ch, "Code chauffeur incorrect");
			vehicule.setChauffeur(ch);
			update = vehiculeRepository.update(v.getId(), proprio.getId(), categorie.getId(), ch.getId());
		}
		
		update = vehiculeRepository.update(v.getId(), proprio.getId(), categorie.getId());
		
		if(update > 0) 
			return true;
		else
			return false;
		
	}
	
	@Transactional
	public Maintenance maintenance(MaintenanceForm maintenanceForm){
		Maintenance maintenance = maintenanceForm.getMaintenance();
		
		Vehicule vehicule = this.findByCode(maintenanceForm.getVehicule());
		
		maintenance.setVehicule(vehicule);
		maintenance = maintenanceRepository.save(maintenance);
		return maintenance;
	}
	
	@Transactional
	public Piece pieces(pieceForm pieceForm){
		Piece piece = pieceForm.create();
		
		piece = pieceRepository.save(piece);
		return piece;
	}
	
	@Transactional
	public Maintenance garage(MaintenanceForm maintenanceForm){
		Maintenance maintenance = maintenanceForm.getMaintenance();
		
		Vehicule vehicule = this.findByCode(maintenanceForm.getVehicule());
		
		maintenance.setVehicule(vehicule);
		maintenance = maintenanceRepository.save(maintenance);
		
		vehiculeRepository.updateEtat(vehicule.getId(), "G");
		return maintenance;
	}
	
	public void supprimer(String codeVehicule){
		Vehicule v = this.findByCode(codeVehicule);
		Assert.notNull(v, "Aucun resultat pour :"+codeVehicule);
		vehiculeRepository.delete(v);
	}
	public Long nombreVehicule(){
		return vehiculeRepository.nombreVehicule();
	}
	
	public Long nombreVehicule(String classe){
		return vehiculeRepository.nombreVehicule(classe);
	}
	
	public Long countByProprio(String codeProprio){
		return vehiculeRepository.countProprio(codeProprio);
	}

	@Transactional
	public void updateEtat(String codeVehicule, String etat) {
		Vehicule v = findByCode(codeVehicule);
		// TODO Auto-generated method stub
		String e = etat.equals("actif") ?  "VD" : "VI";
		vehiculeRepository.updateEtat(v.getId(), e);
	}
}
