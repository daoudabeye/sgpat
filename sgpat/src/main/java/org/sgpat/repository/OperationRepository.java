package org.sgpat.repository;

import java.util.Date;
import java.util.List;

import org.sgpat.entity.Operation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OperationRepository extends CrudRepository<Operation, Integer> {
		
	List<Operation> findByBeneficiaire(String beneficiare);

	List<Operation> findByBeneficiaireAndType(String codeBeneficiare, String typeOperation);
	
	@Query(value = "SELECT o FROM Operation o where o.beneficiaire=:codeBeneficiare AND"
			+ " o.type=:type and DATE(:date)=o.date order by o.id DESC")
	List<Operation> findByBeneficiaireAndTypeAndDate(@Param("codeBeneficiare")String codeBeneficiare, 
			@Param("type")String typeOperation,@Param("date") Date date);
	
	@Query(value = "SELECT o FROM Operation o where o.beneficiaire=:codeBeneficiare AND"
			+ " DATE(:date)=o.dateComptable order by o.id DESC")
	List<Operation> findByDateComptable(@Param("codeBeneficiare")String codeBeneficiare,@Param("date") Date date);
	
	@Query(value = "SELECT * FROM operation o where o.beneficiaire=:codeBeneficiare AND"
			+ " DATE(:date)=o.date_comptable limit 1", nativeQuery=true)
	Operation findLast(@Param("codeBeneficiare")String codeBeneficiare,@Param("date") Date date);
	
	@Query(value = "SELECT o FROM Operation o where o.type =:type AND"
			+ " DATE(:date)=o.dateComptable order by o.id DESC")
	List<Operation> findByDateComptableAndType(@Param("date") Date date, @Param("type") String type);
	
	@Query(value = "SELECT o FROM Operation o where o.type =:type AND"
			+ " o.dateComptable BETWEEN DATE(:date) AND DATE(:date2) order by o.id DESC")
	List<Operation> findByDateComptableAndType(@Param("date") Date date, @Param("date2") Date date2, @Param("type") String type);
	
	@Query(value = "SELECT o FROM Operation o where o.type =:type AND o.statut=:statut AND"
			+ " DATE(:date)=o.dateComptable order by o.id DESC")
	List<Operation> findByDateComptableTypeStatut(@Param("date") Date date, @Param("type") String type, @Param("statut") String statut );
	
	@Query(value = "SELECT o FROM Operation o where o.type =:type AND o.statut=:statut AND"
			+ " o.dateComptable BETWEEN DATE(:date) AND DATE(:date2) order by o.id DESC")
	List<Operation> findByDateComptableTypeStatut(@Param("date") Date date, @Param("date2") Date date2, @Param("type") String type, @Param("statut") String statut );
	
	@Query(value = "SELECT o FROM Operation o where o.type =:type AND o.beneficiaire=:codeBeneficiare AND"
			+ " DATE(:date)=o.dateComptable order by o.id DESC")
	List<Operation> findByDateComptableAndType(@Param("date") Date date, @Param("type") String type, @Param("codeBeneficiare")String codeBeneficiare);
	
	@Query(value = "SELECT o FROM Operation o where o.type =:type AND o.beneficiaire=:codeBeneficiare AND"
			+ "  o.dateComptable BETWEEN DATE(:date) AND DATE(:date2) order by o.id DESC")
	List<Operation> findByDateComptableAndType(@Param("date") Date date, @Param("date2") Date date2, @Param("type") String type, @Param("codeBeneficiare")String codeBeneficiare);
	
	@Query(value = "SELECT o FROM Operation o where o.type =:type AND o.beneficiaire=:codeBeneficiare AND o.statut=:statut AND"
			+ " DATE(:date)=o.dateComptable order by o.id DESC")
	List<Operation> findByDateComptableTypeCodeStatut(@Param("date") Date date, @Param("type") String type, @Param("codeBeneficiare")String codeBeneficiare, @Param("statut") String statut);
	
	@Query(value = "SELECT o FROM Operation o where o.type =:type AND o.beneficiaire=:codeBeneficiare AND o.statut=:statut AND"
			+ " o.dateComptable BETWEEN DATE(:date) AND DATE(:date2) order by o.id DESC")
	List<Operation> findByDateComptableTypeCodeStatut(@Param("date") Date date, @Param("date2") Date date2, @Param("type") String type, @Param("codeBeneficiare")String codeBeneficiare, @Param("statut") String statut);

}
