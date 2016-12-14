package org.sgpat.repository;

import java.util.List;

import org.sgpat.entity.Piece;
import org.springframework.data.repository.CrudRepository;

public interface PieceRepository extends CrudRepository<Piece, Integer> {
	
	List<Piece> findByVehiculeCode(String codeVehicule);

}
