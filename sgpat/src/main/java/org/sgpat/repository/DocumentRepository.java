package org.sgpat.repository;

import java.util.List;

import org.sgpat.entity.Client;
import org.sgpat.entity.Document;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository  extends CrudRepository<Document, Integer> {

	List<Document> findByClient(Client client);
}
