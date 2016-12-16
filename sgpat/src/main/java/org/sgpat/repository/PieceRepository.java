package org.sgpat.repository;

import java.util.List;

import org.sgpat.entity.Piece;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PieceRepository extends CrudRepository<Piece, Integer> {
	
	List<Piece> findByVehiculeCode(String codeVehicule);

	@Query("SELECT p from Piece p order by p.id desc")
	List<Piece> getAll();
	
	@Modifying
	@Query( value = "update pieces p set p.vehicule_id =:idVehicule where p.id=:idPiece" , nativeQuery = true)
	int updateVehicule(@Param("idPiece") Integer idPiece, @Param("idVehicule") Integer idVehicule);
	
	@Modifying
	@Query( value = "update pieces p set p.fournisseur_id =:idFournisseur where p.id=:idPiece" , nativeQuery = true)
	int updateFournisseur(@Param("idPiece") Integer idPiece, @Param("idFournisseur") Integer idFournisseur);

}
