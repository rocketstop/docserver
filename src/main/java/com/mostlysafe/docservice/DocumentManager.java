package com.mostlysafe.docservice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DocumentManager {
    static final Logger logger = LoggerFactory.getLogger(DocumentManager.class);

    private final Map<UUID, String> documents = new ConcurrentHashMap<>();

    public DocumentManager(){
        logger.debug("New Document Manager.");
    }

    @Nonnull
    public Set<UUID> getKeys() {
        return documents.keySet();
    }

    @Nonnull
    public UUID addDocument(@Nonnull final String content) {
        UUID newId = UUID.randomUUID();
        this.addDocument(newId, content);
        return newId;
    }

    @Nonnull
    public UUID addDocument(@Nonnull final UUID key, @Nonnull final String content) {
        documents.put(key, content);
        return key;
    }

    @Nullable
    public Document getDocument(@Nonnull final UUID key) {
        if (null == key) {
            return null;
        }

        if (!documents.keySet().contains(key)) {
            return null;
        }

        return new Document(key, documents.get(key));
    }

    @Nullable
    public UUID removeDocument(@Nonnull final UUID key) {
        if (null == key) {
            return null;
        }

        if (!documents.keySet().contains(key)) {
            return null;
        }

        documents.remove(key);
        return key;
    }

}
