package com.mostlysafe.docservice.mongo;

import com.mostlysafe.docservice.Document;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentRepository extends MongoRepository<Document, UUID> {

    public List<Document> findByContent(String content);

}
