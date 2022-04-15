package com.mostlysafe.docservice.mongo;

import com.mostlysafe.docservice.Document;
import com.mostlysafe.docservice.DocumentManager;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DocumentManagerMongo implements DocumentManager {
    static final Logger logger = LoggerFactory.getLogger(DocumentManagerMongo.class);

    private DocumentRepository documentRepository;

    @Autowired
    public DocumentManagerMongo(DocumentRepository documentRepository) {
        super();
        logger.debug("New Document Manager, Saving to Mongo.");
        this.documentRepository = documentRepository;
    }

    @Nonnull
    @Override
    public Set<UUID> getKeys() {
        return null;
    }

    @Nonnull
    @Override
    public UUID addDocument(@Nonnull final String content) {
        return null;
    }

    @Nonnull
    @Override
    public UUID addDocument(@Nonnull final UUID key, @Nonnull final String content) {
        return null;
    }

    @Nullable
    @Override
    public Document getDocument(@Nonnull final UUID key) {
        return documentRepository.findById(key).orElse(null);
    }

    @Nullable
    @Override
    public UUID removeDocument(@Nonnull final UUID key) {
        return null;
    }
}
