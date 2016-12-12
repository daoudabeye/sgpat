package org.sgpat.service;

import java.util.List;

import org.sgpat.entity.Categorie;
import org.sgpat.form.CategorieForm;
import org.sgpat.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategorieService {
	
	@Autowired
	CategorieRepository categorieRepository;
	
	public Categorie findByNom(String nom){
		return categorieRepository.findByNom(nom);
	}
	
	public List<Categorie> getAll(){
		return categorieRepository.getAll();
	}
	
	public Categorie create(CategorieForm categorieForm){
		Categorie  categorie = categorieForm.create();
		categorie = categorieRepository.save(categorie);
		return categorie;
	}
}
