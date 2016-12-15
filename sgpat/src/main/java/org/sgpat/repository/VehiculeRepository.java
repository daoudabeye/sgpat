package org.sgpat.repository;

import java.util.Date;
import java.util.List;

import org.sgpat.entity.Proprio;
import org.sgpat.entity.Vehicule;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface VehiculeRepository extends CrudRepository<Vehicule, Integer>{

	@Query("SELECT v FROM Vehicule v")
	List<Vehicule> getAll();
	
	Vehicule findByCode(String code);
		
	Vehicule findFirst1ByChauffeurCodeChauffeur(String codeChauffeur);
	
	List<Vehicule> findByProprio(Proprio proprio);
	
	@Modifying
	@Query("update Vehicule v set v.code=:code where v.id=:id")
	int updateCode(@Param("id")Integer id, @Param("code")String code);
	
	@Modifying
	@Query("update Vehicule v set v.immatriculation=:immatriculation, v.marque=:marque, v.type=:type, v.dateMiseEnService=:dateMiseEnService, "
			+ "v.couleur=:couleur, v.classe=:classe, v.prixAchat=:prixAchat, v.numeroSerie=:numeroSerie, v.kilometrageActuel=:kilometrageActuel, "
			+ " v.niveauCarburant=:niveauCarburant, v.roueDeSecours=:roueSecours, "
			+ "v.energie=:energie, v.observations=:observations, v.nombreDePlace=:nbrPlace where v.code=:codeVehicule")
	int update(@Param("codeVehicule") String codeVehicule, @Param("immatriculation") String matricule, @Param("marque") String marque, @Param("type") String type,
			@Param("dateMiseEnService") Date miseEnService, @Param("couleur") String couleur, @Param("classe") String classe,
			@Param("prixAchat") Double prixAchat, @Param("numeroSerie") String numeroSerie, @Param("kilometrageActuel") String kilometrageActuel,
			@Param("niveauCarburant") String niveauCarburant,
			@Param("roueSecours") Boolean roueSecours, @Param("energie") String energie, @Param("observations") String observations,
			@Param("nbrPlace") int nbrPlace);
	
	@Modifying
	@Query(value = "update vehicule v set v.proprio_id =:idProprio, v.chauffeur_id=:idChauffeur, v.categorie_id=:idCategorie where v.id=:id", nativeQuery=true)
	int update(@Param("id") Integer id, @Param("idProprio") Integer idProprio,@Param("idCategorie") Integer idCategorie, @Param("idChauffeur") Integer idChauffeur);
	
	@Modifying
	@Query(value = "update vehicule v set v.proprio_id =:idProprio, v.categorie_id=:idCategorie where v.id=:id", nativeQuery=true)
	int update(@Param("id") Integer id, @Param("idProprio") Integer idProprio,@Param("idCategorie") Integer idCategorie);
	
	@Query(value = "select count(*) from vehicule v", nativeQuery=true)
	Long nombreVehicule();
	
	@Query(value = "select count(*) from vehicule v where v.classe=:classe", nativeQuery=true)
	Long nombreVehicule(@Param("classe") String classe);
	
	@Query(value = "select count(*) from vehicule v "
			+ "where v.proprio_id=(select proprio.id from proprio where proprio.code_proprio=:codeProprio)", nativeQuery=true)
	Long countProprio(@Param("codeProprio") String codeProprio);
	
	@Query(value = "select count(*) from vehicule v where v.etat=:etat", nativeQuery=true)
	Long countByEtat(@Param("etat")String etat);
	
	@Modifying
	@Query("update Vehicule v set v.etat=:etat where v.id=:id")
	int updateEtat(@Param("id")Integer id, @Param("etat")String etat);
	
	List<Vehicule> findByClasseAndEtat(String classe, String etat);
}
