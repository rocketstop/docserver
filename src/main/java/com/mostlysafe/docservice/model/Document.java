package com.mostlysafe.docservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import javax.annotation.Nonnull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@org.springframework.data.mongodb.core.mapping.Document
public class Document extends RepresentationModel<Document> {

    @Id
    private UUID id;
    private String content;


//    @JsonCreator
//    public Document(@Nonnull @JsonProperty("id") final UUID id,
//                    @Nonnull @JsonProperty("content") final String content){
//        this.id = id;
//        this.content = content;
//    }
//
//    public UUID getId() {
//        return id;
//    }
//
//    public String getContent() {
//        return content;
//    }

}
