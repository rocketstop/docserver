package com.mostlysafe.docservice;

import java.util.Set;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface DocumentManager {
    @Nonnull
    Set<UUID> getKeys();

    @Nonnull
    UUID addDocument(@Nonnull String content);

    @Nonnull
    UUID addDocument(@Nonnull UUID key, @Nonnull String content);

    @Nullable
    Document getDocument(@Nonnull UUID key);

    @Nullable
    UUID removeDocument(@Nonnull UUID key);
}
