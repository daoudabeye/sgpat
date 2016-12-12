package org.sgpat.repository;

import java.util.List;

import org.sgpat.entity.Chauffeur;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChauffeurRepository extends CrudRepository<Chauffeur, Integer> {

	Chauffeur findByCodeChauffeur(String code);
	
	@Modifying
	@Query("update Chauffeur c set c.codeChauffeur=:code where c.id=:id")
	int updateCode(@Param("id")Integer id, @Param("code")String code);

	@Query("SELECT c FROM Chauffeur c order by c.id DESC ")
	List<Chauffeur> getAll();
}
