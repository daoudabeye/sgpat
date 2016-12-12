package org.sgpat.repository;

import java.util.Date;
import java.util.List;

import org.sgpat.entity.Recette;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecetteRepository extends CrudRepository<Recette, Integer> {

	@Query("SELECT  r FROM Recette r order by r.id DESC")
	List<Recette> getAll();
	
	List<Recette> findByStatut(String statut);
	
	@Query(value = "SELECT r FROM Recette r where DATE(:date)=r.date order by r.id DESC")
	List<Recette> findByDate(@Param("date") Date date);
	
	@Query(value = "SELECT * FROM recette r where DATE(:date)=r.date and r.chauffeur_id=(select id from chauffeur where code_chauffeur=:codeChauffeur)", nativeQuery= true)
	List<Recette> findByDateAndChauffeurCodeChauffeur(@Param("date")Date date,@Param("codeChauffeur") String codeChauffeur);
	
	@Query(value = "SELECT * FROM recette r where r.chauffeur_id=(select id from chauffeur where code_chauffeur=:codeChauffeur)", nativeQuery= true)
	List<Recette> findByDateAndChauffeurCodeChauffeur(@Param("codeChauffeur") String codeChauffeur);
	
	@Query(value = "SELECT * FROM recette r where DATE(:date)=r.date and r.chauffeur_id=(select id from chauffeur where code_chauffeur=:codeChauffeur) limit 1", nativeQuery= true)
	Recette findFirst1ByDateAndChauffeurCodeChauffeur(@Param("date")Date date,@Param("codeChauffeur") String codeChauffeur);
	
	@Query(value = "SELECT count(*) FROM Recette r where r.statut=:statut order by r.id DESC")
	Long countByStatut(@Param("statut")String statut);
	
	@Modifying
	@Query("update Recette r set r.montantPayer=:montant, r.statut=:statut where r.id=:id")
	int updateMontantPayer(@Param("id")Integer id, @Param("montant") Double montant, @Param("statut") String statut);

}
