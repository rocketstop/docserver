package com.mostlysafe.docservice.backend.mongo;

import com.mostlysafe.docservice.model.Document;
import com.mostlysafe.docservice.DocumentManager;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DocumentManagerMongo implements DocumentManager {
    static final Logger logger = LoggerFactory.getLogger(DocumentManagerMongo.class);

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentManagerMongo(DocumentRepository documentRepository) {
        super();
        logger.debug("New Document Manager, Saving to Mongo.");
        this.documentRepository = documentRepository;
    }

    @Nonnull
    @Override
    public Set<UUID> getKeys() {
        return documentRepository.findAll().stream().map(Document::getId).collect(Collectors.toSet());
    }

    @Nonnull
    @Override
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    @Nonnull
    @Override
    public UUID addDocument(Document document) {
        Document doc = documentRepository.save(document);
        return doc.getId();
    }

    @Nullable
    @Override
    public Document getDocument(@Nonnull final UUID key) {
        return documentRepository.findById(key).orElse(null);
    }

    @Nullable
    @Override
    public UUID removeDocument(@Nonnull final UUID key) {

        documentRepository.deleteById(key);
        return key;
    }
}
