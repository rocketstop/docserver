package com.mostlysafe.docservice;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DocumentManagerTest {

    DocumentManager documentManager;

    @BeforeEach
    public void setup(){
        this.documentManager = new DocumentManager();
        this.documentManager.addDocument(UUID.randomUUID(), "some other document");
        this.documentManager.addDocument(UUID.randomUUID(), "another document");
    }

    @Test
    public void testAddDocument() {
        DocumentManager manager = new DocumentManager();

        UUID id = UUID.randomUUID();
        String content = "some new content";
        UUID actualId = manager.addDocument(id, content);

        assertThat(actualId, is(id));
    }

    @Test
    public void testAddDocument_contentOnly() {
        DocumentManager manager = new DocumentManager();

        String content = "some new content";
        UUID actualId = manager.addDocument(content);

        assertNotNull(actualId);
    }

    @Test
    public void testGetDocument() {
        UUID one = UUID.randomUUID();
        String testContent = "test document";
        Document expectedDocument = new Document(one, testContent);
        this.documentManager.addDocument(one, testContent);

        Document actualDocument = this.documentManager.getDocument(one);

        assertThat(actualDocument, is(expectedDocument));
    }

    @Test
    public void testGetDocument_notFound() {
        UUID one = UUID.randomUUID();

        // make sure the random key doesn't match
        while (this.documentManager.getKeys().contains(one)) {
            one = UUID.randomUUID();
        }

        Document actualDocument = this.documentManager.getDocument(one);

        assertThat(actualDocument, is(nullValue()));
    }

    @Test
    public void testGetDocument_nullKey() {
        Document actualDocument = this.documentManager.getDocument(null);

        assertThat(actualDocument, is(nullValue()));
    }

    @Test
    public void testRemoveDocument() {
        UUID one = UUID.randomUUID();
        String testContent = "test document";
        this.documentManager.addDocument(one, testContent);

        UUID actualId = this.documentManager.removeDocument(one);

        assertThat(actualId, is(one));

        assertThat(this.documentManager.getKeys().contains(one), is(false));
    }

    @Test
    public void testRemoveDocument_noKey() {
        UUID one = UUID.randomUUID();

        // make sure it doesnt exist
        while (this.documentManager.getKeys().contains(one)) {
            one = UUID.randomUUID();
        }

        UUID actualId = this.documentManager.removeDocument(one);

        assertThat(actualId, is(nullValue()));
    }

    @Test
    public void testRemoveDocument_nullKey() {
        UUID actualId = this.documentManager.removeDocument(null);

        assertThat(actualId, is(nullValue()));
    }

}
