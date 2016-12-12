package org.sgpat.repository;

import java.util.List;

import org.sgpat.entity.Categorie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieRepository extends CrudRepository<Categorie, String> {

	@Query("SELECT c FROM Categorie c")
	List<Categorie> getAll();
	
	Categorie findByNom(String nom);
	
}
