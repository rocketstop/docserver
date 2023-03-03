package com.mostlysafe.docservice;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Set;
import java.util.UUID;
import javax.annotation.Nonnull;

import com.mostlysafe.docservice.backend.internal.DocumentManagerInternal;
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

    private final DocumentManagerInternal manager;

    @Autowired
    DocumentController(@Nonnull final DocumentManagerInternal manager) {
        this.manager = manager;
    }

    @GetMapping()
    public ResponseEntity<Set<UUID>> getDocumentKeys() {
        logger.debug("Fetching all document keys.");
        return ResponseEntity.ok(manager.getKeys());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocument(@PathVariable final UUID id) {
        logger.debug("Fetching document with id: {}", id);
        if (null == id) {
            logger.debug("Message Not Processed: No ID");
            return ResponseEntity.badRequest().build();
        }

        final Document document = manager.getDocument(id);

        logger.debug("manager returned content {}", document);
        if (null == document) {
            return ResponseEntity.notFound().build();
        }
        document.add(linkTo(methodOn(DocumentController.class).getDocument(document.getId())).withSelfRel());
        document.add(linkTo(methodOn(DocumentController.class).getDocumentKeys()).withRel("all documents"));

        return ResponseEntity.ok(document);
    }

    @PostMapping()
    public ResponseEntity<Document> addDocument(@RequestBody final Document document) {
        logger.debug("Posting new document.");
        if (null == document) {
            return ResponseEntity.badRequest().build();
        }

        UUID documentId = manager.addDocument(document.getId(), document.getContent());

        document.add(linkTo(methodOn(DocumentController.class).getDocument(document.getId())).withSelfRel());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(linkTo(methodOn(DocumentController.class).getDocument(documentId)).withSelfRel().toUri())
                .body(document);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Document> addDocument(@PathVariable final UUID id,
                                                @RequestBody final String content) {
        logger.debug("Posting new document.");
        if (null == content) {
            return ResponseEntity.badRequest().build();
        }

        UUID documentId = manager.addDocument(id, content);

        Document document = new Document(id, content);
        document.add(linkTo(methodOn(DocumentController.class).getDocument(document.getId())).withSelfRel());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(linkTo(methodOn(DocumentController.class).getDocument(documentId)).withSelfRel().toUri())
                .body(document);
    }

    @PostMapping("/create")
    public ResponseEntity<Document> addDocument(@RequestBody final String content) {
        logger.debug("Posting new document.");
        if (null == content) {
            return ResponseEntity.badRequest().build();
        }

        UUID documentId = manager.addDocument(content);

        Document document = new Document(documentId, content);
        document.add(linkTo(methodOn(DocumentController.class).getDocument(document.getId())).withSelfRel());

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
                .location(linkTo(methodOn(DocumentController.class).getDocument(removedId)).withSelfRel().toUri())
                .build();
    }

}
