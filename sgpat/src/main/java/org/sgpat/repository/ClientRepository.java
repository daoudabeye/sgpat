package org.sgpat.repository;

import java.util.Date;
import java.util.List;

import org.sgpat.entity.Client;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {
	
	@Query("select c from Client c")
	List<Client> getClients();
	
	Client findByCodeClient(String codeclient);
	
	Client findById(int id);
	
	@Modifying
	@Query("update Client c set c.nom=:nom, c.prenom=:prenom, c.adresse=:adresse, c.dateNaissance=:dateNaissance, c.email=:email, c.telephone=:telephone, "
			+ "c.permis=:permis, c.pays=:pays, c.ville=:ville, c.observations=:observations "
			+ "where c.codeClient=:codeClient ")
	int update(@Param("codeClient") String codeClient, @Param("nom") String nom, @Param("prenom")String prenom, @Param("dateNaissance") Date dateNaissance,
			@Param("adresse") String adresse, @Param("email") String email, @Param("telephone") String telephone,
			@Param("permis") Boolean permis, @Param("pays") String pays, @Param("ville") String ville, @Param("observations") String observations);
	
	@Modifying
	@Query("update Client c set c.codeClient=:newcode where c.id=:idClient")
	int update(@Param("idClient") int id,@Param("newcode") String newcode);
	
	@Modifying
	@Query("update Client c set c.codeClient=:newcode where c.codeClient=:oldcode")
	int update(@Param("oldcode") String oldcode, @Param("newcode") String newcode);
	
	@Modifying
	@Query("delete Client c where c.codeClient=:code")
	int delete(@Param("code") String code);
	
	@Modifying
	@Query(value = "update client set account_id =:accountId where id=:idClient", nativeQuery = true)
	int updateAccount(@Param("idClient") int idClient, @Param("accountId") int idAccount);
	
}
