package org.sgpat.service;

import java.util.List;

import org.sgpat.entity.Proprio;
import org.sgpat.repository.ProprioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
}
