package org.sgpat.repository;

import java.util.List;

import org.sgpat.entity.Proprio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProprioRepository extends CrudRepository<Proprio, Integer> {

	Proprio findByCodeProprio(String code);
	
	@Query("SELECT p FROM Proprio p")
	List<Proprio> findAll();
}
