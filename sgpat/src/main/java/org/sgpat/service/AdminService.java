package org.sgpat.service;

import java.util.List;

import org.sgpat.account.Account;
import org.sgpat.account.AccountService;
import org.sgpat.entity.Client;
import org.sgpat.entity.Document;
import org.sgpat.entity.Employee;
import org.sgpat.entity.Operation;
import org.sgpat.entity.Vehicule;
import org.sgpat.form.AgentForm;
import org.sgpat.form.DocumentForm;
import org.sgpat.repository.AgentRepository;
import org.sgpat.repository.DocumentRepository;
import org.sgpat.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

	@Autowired
	AgentRepository agentRepository;
	
	@Autowired
	ProprioService proprioService;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	OperationRepository operationRepository;
	
	@Autowired
	DocumentRepository documentRepository;
	
	@Autowired
	VehiculeService vehiculeService;
	
	@Autowired
	ClientService clientService;
	
	@Transactional
	public void creerAgent(AgentForm agentForm){
		Employee agent = agentForm.createAgent();
		agent = agentRepository.save(agent);
		
		String code = agent.getId()+"";
		int lenth = code.length();
		for(int i=2; i>0; i--, lenth--){
			if(lenth <= 0)
				code = "0"+code;
		}
		code = "AG"+code;
		agentRepository.update(agent.getId(), code);
		
		accountService.save(new Account(code, "taxiplusagent", "ROLE_AGENT"));;
	}
	
	public List<Operation> findByProprio(String codeProprio){
		return operationRepository.findByBeneficiaire(codeProprio);
	}
	
	public Document creerDocument(DocumentForm documentForm, String profile){
		Document document = documentForm.createDocument();
		
		switch (profile) {
		case "CLIENT":
			Client client = clientService.findByCode(documentForm.getCodeClient());
			document.setClient(client);
			documentRepository.save(document);
			break;
		
		case "VEHICULE":
			Vehicule vehicule = vehiculeService.findByCode(documentForm.getCodeClient());
			document.setVehicule(vehicule);
			documentRepository.save(document);
			break;

		default:
			break;
		}
		
		return document;
	}
	
}
