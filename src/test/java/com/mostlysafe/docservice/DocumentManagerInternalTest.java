package com.mostlysafe.docservice;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DocumentManagerInternalTest {

    DocumentManagerInternal documentManagerInternal;

    @BeforeEach
    public void setup(){
        this.documentManagerInternal = new DocumentManagerInternal();
        this.documentManagerInternal.addDocument(UUID.randomUUID(), "some other document");
        this.documentManagerInternal.addDocument(UUID.randomUUID(), "another document");
    }

    @Test
    public void testAddDocument() {
        DocumentManagerInternal manager = new DocumentManagerInternal();

        UUID id = UUID.randomUUID();
        String content = "some new content";
        UUID actualId = manager.addDocument(id, content);

        assertThat(actualId, is(id));
    }

    @Test
    public void testAddDocument_contentOnly() {
        DocumentManagerInternal manager = new DocumentManagerInternal();

        String content = "some new content";
        UUID actualId = manager.addDocument(content);

        assertNotNull(actualId);
    }

    @Test
    public void testGetDocument() {
        UUID one = UUID.randomUUID();
        String testContent = "test document";
        Document expectedDocument = new Document(one, testContent);
        this.documentManagerInternal.addDocument(one, testContent);

        Document actualDocument = this.documentManagerInternal.getDocument(one);

        assertThat(actualDocument, is(expectedDocument));
    }

    @Test
    public void testGetDocument_notFound() {
        UUID one = UUID.randomUUID();

        // make sure the random key doesn't match
        while (this.documentManagerInternal.getKeys().contains(one)) {
            one = UUID.randomUUID();
        }

        Document actualDocument = this.documentManagerInternal.getDocument(one);

        assertThat(actualDocument, is(nullValue()));
    }

    @Test
    public void testGetDocument_nullKey() {
        Document actualDocument = this.documentManagerInternal.getDocument(null);

        assertThat(actualDocument, is(nullValue()));
    }

    @Test
    public void testRemoveDocument() {
        UUID one = UUID.randomUUID();
        String testContent = "test document";
        this.documentManagerInternal.addDocument(one, testContent);

        UUID actualId = this.documentManagerInternal.removeDocument(one);

        assertThat(actualId, is(one));

        assertThat(this.documentManagerInternal.getKeys().contains(one), is(false));
    }

    @Test
    public void testRemoveDocument_noKey() {
        UUID one = UUID.randomUUID();

        // make sure it doesnt exist
        while (this.documentManagerInternal.getKeys().contains(one)) {
            one = UUID.randomUUID();
        }

        UUID actualId = this.documentManagerInternal.removeDocument(one);

        assertThat(actualId, is(nullValue()));
    }

    @Test
    public void testRemoveDocument_nullKey() {
        UUID actualId = this.documentManagerInternal.removeDocument(null);

        assertThat(actualId, is(nullValue()));
    }

}
