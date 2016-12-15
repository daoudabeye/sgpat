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

}
