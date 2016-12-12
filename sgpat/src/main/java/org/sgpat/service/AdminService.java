package org.sgpat.service;

import java.util.List;

import org.sgpat.account.Account;
import org.sgpat.account.AccountService;
import org.sgpat.entity.Employee;
import org.sgpat.entity.Operation;
import org.sgpat.form.AgentForm;
import org.sgpat.repository.AgentRepository;
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
		return operationRepository.findByCodeProprioOrderByIdDesc(codeProprio);
	}
	
}
