package org.sgpat.repository;

import java.util.List;

import org.sgpat.entity.Salaire;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SalaireRepository extends CrudRepository<Salaire, Integer> {

	List<Salaire> findByChauffeurCodeChauffeur(String codeChauffeur);
	
	@Query("select s from Salaire s")
	List<Salaire> findAll();
}
