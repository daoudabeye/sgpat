package org.sgpat.repository;

import java.util.Date;
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
	
	@Modifying
	@Query("update Chauffeur c set c.nom=:nom, c.prenom=:prenom, c.adresse=:adresse,c.profile=:profile, c.dateNaissance=:dateNaissance, c.salaire=:salaire,"
			+ " c.expirationPermis=:expirationPermis, c.lieuNaissance=:lieuNaissance, c.numeroPermis=:numeroPermis, c.statut=:statut, c.telephone=:telephone,"
			+ "c.dateDebutActivite=:dateDebutActivite , c.caution=:caution, c.sexe=:sexe, c.etatCivil=:etatCivil, c.niveauEtude=:niveauEtude,"
			+ " c.experience=:experience, c.numeroUrgence=:urgence,	c.langue=:langue, c.nationnalite=:nationnalite where c.codeChauffeur=:codeChauffeur")
	int update(@Param("codeChauffeur") String codeChauffeur, @Param("nom") String nom, @Param("prenom")String prenom, @Param("adresse") String adresse, @Param("profile") String profile,@Param("dateNaissance") Date dateNaissance, 
			@Param("salaire") Double salaire, @Param("expirationPermis") Date expirationPermis, @Param("lieuNaissance")String lieuNaissance, @Param("numeroPermis") String numeroPermis, 
			@Param("statut") String statut, @Param("telephone") String telephone,@Param("dateDebutActivite") Date dateDebutActivite , @Param("caution") Double caution, 
			@Param("sexe") String sexe, @Param("etatCivil") String etatCivile, @Param("niveauEtude")  String niveauEtude, @Param("experience") String experience, @Param("urgence") String urgence,
			@Param("langue") String langue,@Param("nationnalite")  String nationnalite );
}
