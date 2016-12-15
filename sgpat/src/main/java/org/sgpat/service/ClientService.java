package org.sgpat.service;

import java.util.List;

import javax.transaction.Transactional;

import org.sgpat.account.Account;
import org.sgpat.account.AccountService;
import org.sgpat.entity.Client;
import org.sgpat.entity.Document;
import org.sgpat.form.ClientForm;
import org.sgpat.form.DocumentForm;
import org.sgpat.repository.ClientRepository;
import org.sgpat.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ClientService {

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	AccountService accountService;
	
	@Autowired
	DocumentRepository documentRepository;

	public List<Client> getAll(){
		return clientRepository.getClients();
	}
	public Client find(Integer id){
		return clientRepository.findOne(id);
	}

	public Client findByCode(String code){
		Client client = clientRepository.findByCodeClient(code);
		Assert.notNull(client, "Aucun resultat trouvé");
		return client;
	}
	
	public List<Document> getDocuments(Client client){
		return documentRepository.findByClient(client);
	}

	@Transactional
	public Client creer(ClientForm clientForm){
		Client client = clientForm.getClient();
		client = clientRepository.save(client);

		String codeClient = client.getId()+"";
		int lenth = codeClient.length();
		for(int i=4; i>0; i--, lenth--){
			if(lenth <= 0)
				codeClient = "0"+codeClient;
		}
		codeClient = "CL"+codeClient;
		Account acc = null;
		if(clientForm.getAccesInternet().equals("OUI")){
			acc = accountService.save(new Account(codeClient, "taxiplusclient", "ROLE_CLIENT"));
			clientRepository.updateAccount(client.getId(), acc.getId().intValue());
		}

		clientRepository.update(client.getId(), codeClient);
		
		Document doc = clientForm.createDocument();
		doc.setClient(client);
		documentRepository.save(doc);
		
		return client;
	}
	
	@Transactional
	public Boolean update(ClientForm clientForm, String codeClient){
		Client client = clientForm.getClient();
		Integer updated = clientRepository.update(codeClient, client.getNom(), client.getPrenom(), client.getDateNaissance(), client.getAdresse(), client.getEmail(), 
				client.getTelephone(), client.getPermis(), client.getPays(), client.getVille(), client.getObservations());

		if(updated > 0)
			return true;
		return false;
	}

	public void supprimer(String code){
		Client client = findByCode(code);
		clientRepository.delete(client.getId());
	}

	public void supprimer(int id){
		clientRepository.delete(id);
	}

	public Boolean Bloquer(String code){
		return false;
	}
	public Document creer(DocumentForm documentForm) {
		// TODO Auto-generated method stub
		return null;
	}
}
