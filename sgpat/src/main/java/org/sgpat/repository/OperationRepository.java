package org.sgpat.repository;

import java.util.List;

import org.sgpat.entity.Operation;
import org.springframework.data.repository.CrudRepository;

public interface OperationRepository extends CrudRepository<Operation, Integer> {
	
	List<Operation> findByCodeProprioOrderByIdDesc(String codeProprio);

}
