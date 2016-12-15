package org.sgpat.service;

import java.util.List;

import org.sgpat.account.Account;
import org.sgpat.account.AccountService;
import org.sgpat.entity.Chauffeur;
import org.sgpat.entity.Employee;
import org.sgpat.entity.Operation;
import org.sgpat.entity.Proprio;
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
}
