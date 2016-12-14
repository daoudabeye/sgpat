package org.sgpat.repository;

import java.util.List;

import org.sgpat.entity.Maintenance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MaintenanceRepository extends CrudRepository<Maintenance, Integer> {

	@Query("SELECT m FROM Maintenance m order by m.id DESC")
	public List<Maintenance> findAll();
	
	List<Maintenance> findByVehiculeCode(String codeVehicule);
}
