package com.mostlysafe.docservice;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nonnull;

import com.mostlysafe.docservice.model.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doc")
public class DocumentController {
    static final Logger logger = LoggerFactory.getLogger(DocumentController.class);

    private final DocumentManager manager;

    @Autowired
    DocumentController(@Nonnull final DocumentManager manager) {
        this.manager = manager;
    }

    @GetMapping()
    public ResponseEntity<List<Document>> getAllDocuments() {
        logger.debug("Fetching all documents.");
        return ResponseEntity.ok(manager.getAllDocuments());
    }

    @GetMapping("/keys")
    public ResponseEntity<Set<UUID>> getDocumentKeys() {
        logger.debug("Fetching all document keys.");
        return ResponseEntity.ok(manager.getKeys());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocument(@PathVariable final UUID id) {
        logger.debug("Fetching document with id: {}", id);
        if (null == id) {
            logger.debug("Request Not Processed: No ID");
            return ResponseEntity.badRequest().build();
        }

        final Document document = manager.getDocument(id);

        logger.debug("manager returned content {}", document);
        if (null == document) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(document);
    }

    @PostMapping()
    public ResponseEntity<Document> addDocument(@RequestBody final Document document) {
        logger.debug("Posting new document.");
        if (null == document) {
            return ResponseEntity.badRequest().build();
        }

        if (null == document.getId()){
            document.setId(UUID.randomUUID());
        }
        UUID documentId = manager.addDocument(document);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(linkTo(methodOn(DocumentController.class).getDocument(documentId)).withSelfRel().toUri())
                .body(document);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> removeDocument(@PathVariable final UUID id) {
        logger.debug("Removing document with id: {}", id);
        if (null == id) {
            return ResponseEntity.badRequest().build();
        }

        UUID removedId = manager.removeDocument(id);

        return ResponseEntity
                .accepted()
                .build();
    }

}
