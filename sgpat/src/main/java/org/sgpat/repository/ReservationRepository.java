package org.sgpat.repository;

import java.util.List;

import org.sgpat.entity.Reservation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

	@Query( value = "select count(*) from Reservation r where r.client.codeClient=:codeClient")
	Long countByClient(@Param("codeClient") String codeClient);
	
	@Modifying
	@Query("update Reservation r set r.codeReservation=:code where r.id=:id")
	int updateCode(@Param("id") int id, @Param("code") String code);
	
	@Query("SELECT l FROM Reservation l order by l.id DESC")
	List<Reservation> getAllReservation();
}
