package com.mostlysafe.docservice.backend.internal;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mostlysafe.docservice.DocumentManager;
import com.mostlysafe.docservice.model.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DocumentManagerInternal implements DocumentManager {
    static final Logger logger = LoggerFactory.getLogger(DocumentManagerInternal.class);

    private final Map<UUID, String> documents = new ConcurrentHashMap<>();

    public DocumentManagerInternal(){
        super();
        logger.debug("New Document Manager, Internal ConcurrentHashMap.");
    }

    @Override
    @Nonnull
    public Set<UUID> getKeys() {
        return documents.keySet();
    }

    @Override
    @Nonnull
    public UUID addDocument(@Nonnull final String content) {
        UUID newId = UUID.randomUUID();
        this.addDocument(newId, content);
        return newId;
    }

    @Override
    @Nonnull
    public UUID addDocument(@Nonnull final UUID key, @Nonnull final String content) {
        documents.put(key, content);
        return key;
    }

    @Override
    @Nullable
    public Document getDocument(@Nonnull final UUID key) {
        if (null == key) {
            return null;
        }

        if (!documents.containsKey(key)) {
            return null;
        }

        return new Document(key, documents.get(key));
    }

    @Override
    @Nullable
    public UUID removeDocument(@Nonnull final UUID key) {
        if (null == key) {
            return null;
        }

        if (!documents.containsKey(key)) {
            return null;
        }

        documents.remove(key);
        return key;
    }

}
