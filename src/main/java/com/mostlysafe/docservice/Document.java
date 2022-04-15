package com.mostlysafe.docservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nonnull;
import org.springframework.data.annotation.Id;
import org.springframework.hateoas.RepresentationModel;

@org.springframework.data.mongodb.core.mapping.Document
public class Document extends RepresentationModel<Document> {

    @Id
    private final UUID id;
    private final String content;

    @JsonCreator
    public Document(@Nonnull @JsonProperty("id") final UUID id,
                    @Nonnull @JsonProperty("content") final String content){
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
                + " : content=" + content
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
