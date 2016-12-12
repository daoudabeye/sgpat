package org.sgpat.repository;

import java.util.Date;
import java.util.List;

import org.sgpat.entity.Location;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location, Integer> {

	@Query("SELECT l from Location l where l.facturation not like 'COURSE' order by l.id DESC")
	List<Location> getAll();
	
	@Query("SELECT l from Location l where l.facturation='COURSE' order by l.id DESC")
	List<Location> getAllCourses();
	
	@Modifying
	@Query("update Location l set l.codeLocation=:code where l.id=:id")
	int updateCode(@Param("id") int id, @Param("code") String code);
	
	@Modifying
	@Query("update Location l set l.dateDebut=:dateDebut, l.dateRetour=:dateRetour, l.duree=:duree "
			+ "where l.id =:idLocation")
	int updateLocation(@Param("idLocation") int id, @Param("dateDebut") Date debut, @Param("dateRetour") Date retour, @Param("duree") int duree);
	
	@Query(value = "select sum(cout) from location where date_creation BETWEEN :debut AND :fin", nativeQuery=true)
	Double somme(@Param("debut") Date debut, @Param("fin") Date fin);
	
	@Query( value = "select count(*) from Location l where l.statut=:statut")
	Long countByStatut(@Param("statut") String statut);
	
	@Query( value = "select count(*) from Location l where l.client.codeClient=:codeClient")
	Long countByClient(@Param("codeClient") String codeClient);

	@Query( value = "select count(*) from Location l where l.client.codeClient=:codeClient and l.facturation='COURSE'")
	Long countCourses(@Param("codeClient") String codeClient);
	
	List<Location> findByClientCodeClientAndStatut(String codeClient, String statut);
}
