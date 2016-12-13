package org.sgpat.service;

import java.util.List;

import org.sgpat.entity.Proprio;
import org.sgpat.form.ProprioForm;
import org.sgpat.repository.ProprioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProprioService {

	@Autowired
	ProprioRepository proprioRepository;
	
	public Proprio findByCode(String code){
		return proprioRepository.findByCodeProprio(code);
	}
	
	public List<Proprio> findAll(){
		return proprioRepository.findAll();
	}
	
	@Transactional
	public Proprio create(ProprioForm proprioForm){
		Proprio proprio = proprioForm.create();
		
		proprio = proprioRepository.save(proprio);
		
		String code = proprio.getId()+"";
		int lenth = code.length();
		for(int i=2; i>0; i--, lenth--){
			if(lenth <= 0)
				code = "0"+code;
		}
		code = "PR"+code;
		proprioRepository.updateCode(proprio.getId(), code);
		return proprio;
	}
	
}
