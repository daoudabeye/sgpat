package org.sgpat.repository;

import org.sgpat.entity.Employee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AgentRepository extends CrudRepository<Employee, Integer> {

	@Modifying
	@Query("update Employee c set c.code=:newcode where c.id=:id")
	int update(@Param("id") int id,@Param("newcode") String newcode);
}
