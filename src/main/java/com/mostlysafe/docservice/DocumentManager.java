package com.mostlysafe.docservice;

import com.mostlysafe.docservice.model.Document;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface DocumentManager {
    @Nonnull
    Set<UUID> getKeys();

    @Nonnull
    List<Document> getAllDocuments();

    @Nonnull
    UUID addDocument(@Nonnull String content);

    @Nonnull
    UUID addDocument(@Nonnull UUID key, @Nonnull String content);

    @Nullable
    Document getDocument(@Nonnull UUID key);

    @Nullable
    UUID removeDocument(@Nonnull UUID key);
}
