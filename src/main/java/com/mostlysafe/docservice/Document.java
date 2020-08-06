package com.mostlysafe.docservice;

import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nonnull;

public class Document {
    private final UUID id;
    private final String content;

    public Document(@Nonnull final UUID id, @Nonnull final String content){
        this.id = id;
        this.content = content;
    }

    public UUID getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Document{"
                + "id=" + id
                + " : " + content
                + '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Document that = (Document) o;
        return id.equals(that.id)
                && content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content);
    }

}
