package org.sgpat.repository;

import org.sgpat.entity.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

	Employee findFirst1ByAccountId(Long accountid);
}
