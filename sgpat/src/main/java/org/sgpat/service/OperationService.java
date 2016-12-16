package org.sgpat.service;

import java.util.List;

import org.sgpat.account.Account;
import org.sgpat.account.AccountService;
import org.sgpat.entity.Categorie;
import org.sgpat.entity.Chauffeur;
import org.sgpat.entity.Employee;
import org.sgpat.entity.Operation;
import org.sgpat.entity.Proprio;
import org.sgpat.entity.Vehicule;
import org.sgpat.form.OperationForm;
import org.sgpat.repository.EmployeeRepository;
import org.sgpat.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class OperationService {

	@Autowired
	OperationRepository operationRepository;
	
	@Autowired
	ChauffeurService chauffeurService;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	ProprioService proprioService;
	
	@Autowired
	VehiculeService vehiculeService;
	
	public List<Operation> findByCode(String codeBeneficiare){
		return operationRepository.findByBeneficiaire(codeBeneficiare);
	}
	
	public List<Operation> findByCode(String codeBeneficiare, String typeOperation){
		return operationRepository.findByBeneficiaireAndType(codeBeneficiare, typeOperation);
	}
	
	public Operation makeSalaire(OperationForm operationForm){
		Operation op = operationForm.create();
		op.setType("SALAIRE");
		
		Chauffeur chauffeur = chauffeurService.findByCode(operationForm.getBeneficiaire());
		Assert.notNull(chauffeur, "Code chauffeur incorrect");
		
		op.setIdBenef(chauffeur.getId());
		op.setBeneficiaire(chauffeur.getCodeChauffeur());
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Account current = accountService.findByUsername(username);
		Employee employee = employeeRepository.findFirst1ByAccountId(current.getId());
		
		op.setEmployee(employee);
		
		op = operationRepository.save(op);
		return op;
	}
	
	public Operation makeVirement(OperationForm operationForm){
		Operation op = operationForm.create();
		op.setType("VIREMENT");
		
		Proprio proprio = proprioService.findByCode(operationForm.getBeneficiaire());
		Assert.notNull(proprio, "Code Proprio incorrect");
		
		op.setIdBenef(proprio.getId());
		op.setBeneficiaire(proprio.getCodeProprio());
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Account current = accountService.findByUsername(username);
		Employee employee = employeeRepository.findFirst1ByAccountId(current.getId());
		
		op.setEmployee(employee);

		op = operationRepository.save(op);
		return op;
	}
	
	public Operation decaissement(OperationForm operationForm){
		Operation op = operationForm.create();
		op.setType("DECAISSEMENT");
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Account current = accountService.findByUsername(username);
		Employee employee = employeeRepository.findFirst1ByAccountId(current.getId());
		
		op.setEmployee(employee);
		
		op = operationRepository.save(op);
		return op;
	}
	
	public Operation encaissement(OperationForm operationForm){
		Operation op = operationForm.create();
		op.setType("ENCAISSEMENT");
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Account current = accountService.findByUsername(username);
		Employee employee = employeeRepository.findFirst1ByAccountId(current.getId());
		
		op.setEmployee(employee);
		op = operationRepository.save(op);
		return op;
	}
	
	public Operation makeRecette(OperationForm operationForm){
		Operation op = operationForm.create();
		op.setType("RECETTE");
		
		Chauffeur chauffeur = chauffeurService.findByCode(operationForm.getBeneficiaire());
		Assert.notNull(chauffeur, "Code chauffeur incorrect");
		
		Vehicule vehicule = vehiculeService.findVehicule(chauffeur);
		Assert.notNull(vehicule, "Aucun véhicule pour ce chauffeur");
		Categorie categorie = vehicule.getCategorie();
		
		Double restApayer = categorie.getPrixParJours() - op.getMontant() - operationForm.getMontantCeder();
		
		op.setIdBenef(chauffeur.getId());
		op.setBeneficiaire(chauffeur.getCodeChauffeur());
		op.setMontantDus(restApayer);
		op.setMontantCeder(operationForm.getMontantCeder());
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Account current = accountService.findByUsername(username);
		Employee employee = employeeRepository.findFirst1ByAccountId(current.getId());
		
		op.setEmployee(employee);
		
		Operation last = operationRepository.findLast(operationForm.getBeneficiaire(), 
				op.getDateComptable());
		Double montantDus = 0.0;
		if(last != null){
			montantDus += (last.getMontantDus() - op.getMontant());
			op.setMontantDus(montantDus);
		}
		
		op = operationRepository.save(op);
		return op;
	}
}
