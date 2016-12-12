package org.sgpat.repository;

import org.sgpat.entity.Alert;
import org.springframework.data.repository.CrudRepository;

public interface AlertRepository extends CrudRepository<Alert, Integer> {

	Alert findByStatut(String statut);
	
	
}
