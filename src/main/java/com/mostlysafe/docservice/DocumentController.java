package com.mostlysafe.docservice;

import java.net.URI;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    DocumentController(@Nonnull final DocumentManager manager){
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

        return ResponseEntity.ok(document);
    }

    @PostMapping()
    public ResponseEntity<UUID> addDocument(@RequestBody final Document document) {
        logger.debug("Posting new document.");
        if (null == document) {
            return ResponseEntity.badRequest().build();
        }

        UUID documentId = manager.addDocument(document.getId(), document.getContent());
        URI uri = URI.create(documentId.toString());

        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/create")
    public ResponseEntity<UUID> addDocument(@RequestBody final String contents) {
        logger.debug("Posting new document.");
        if (null == contents) {
            return ResponseEntity.badRequest().build();
        }

        UUID documentId = manager.addDocument(contents);
        URI uri = URI.create(documentId.toString());

        return ResponseEntity.created(uri).build();
    }


    @PostMapping("/{id}")
    public ResponseEntity<UUID> addDocument(@PathVariable final UUID id,
                                            @RequestBody final String content) {
        logger.debug("Posting new document.");
        if (null == content) {
            return ResponseEntity.badRequest().build();
        }

        UUID documentId = manager.addDocument(id, content);
        URI uri = URI.create(documentId.toString());

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> removeDocument(@PathVariable final UUID id) {
        logger.debug("Removing document with id: {}", id);
        if (null == id) {
            return ResponseEntity.badRequest().build();
        }

        UUID removedId = manager.removeDocument(id);

        return ResponseEntity.accepted()
                .header("location", removedId.toString())
                .build();
    }

}
