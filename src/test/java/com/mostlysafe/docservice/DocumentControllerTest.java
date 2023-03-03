package com.mostlysafe.docservice;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import com.mostlysafe.docservice.backend.internal.DocumentManagerInternal;
import com.mostlysafe.docservice.model.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class DocumentControllerTest {

    private static final String CONTROLLER_MAPPING = "/api/doc/";

    private static final UUID ID = UUID.randomUUID();
    private static final String CONTENT = "test content";

    @Mock
    DocumentManagerInternal managerMock;

    private DocumentController controller;
    private final HashMap<UUID, String> documents = new HashMap<>();

    @BeforeEach
    public void setUp() {
        controller = new DocumentController(managerMock);

        documents.clear();
        documents.put(UUID.randomUUID(), "some stuff");
        documents.put(UUID.randomUUID(), "some other stuff");
        documents.put(ID, CONTENT);
    }

    @AfterEach
    public void tearDown() {
        Mockito.validateMockitoUsage();
    }

    @Test
    public void testGetDocumentKeys() {
        Set<UUID> expectedKeys = documents.keySet();
        when(managerMock.getKeys()).thenReturn(expectedKeys);

        ResponseEntity<Set<UUID>> response = controller.getDocumentKeys();

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(expectedKeys));
    }

    @Test
    public void testGetDocument() {

        Document expectedDocument = new Document(ID, CONTENT);
        when(managerMock.getDocument(ID)).thenReturn(expectedDocument);

        ResponseEntity<Document> response = controller.getDocument(ID);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(expectedDocument));
    }

    @Test
    public void testGetDocument_notFound() {
        UUID bad = UUID.randomUUID();
        when(managerMock.getDocument(bad)).thenReturn(null);

        ResponseEntity<Document> response = controller.getDocument(bad);

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void testGetDocument_nullId() {
        ResponseEntity<Document> response = controller.getDocument(null);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void testAddDocument() {
        UUID newId = UUID.randomUUID();
        when(managerMock.addDocument(any(String.class))).thenReturn(newId);

        ResponseEntity<Document> response = controller.addDocument("some content");

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getHeaders().get("location").stream().findFirst().get(),
                   is(CONTROLLER_MAPPING + newId.toString()));
    }

    @Test
    public void testAddDocument_nullContent_String() {

        ResponseEntity<Document> response = controller.addDocument((String) null);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void testAddDocument_asDocument() {
        UUID newId = UUID.randomUUID();
        String content = "some test content.";
        when(managerMock.addDocument(any(UUID.class), any(String.class))).thenReturn(newId);

        Document document = new Document(newId, content);
        ResponseEntity<Document> response = controller.addDocument(document);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getHeaders().get("location").stream().findFirst().get(),
                   is(CONTROLLER_MAPPING + newId.toString()));

    }

    @Test
    public void testAddDocument_nullContent_Document() {

        ResponseEntity<Document> response = controller.addDocument((Document) null);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void testAddDocument_withId() {
        UUID newId = UUID.randomUUID();
        when(managerMock.addDocument(any(UUID.class), any(String.class))).thenReturn(newId);

        ResponseEntity<Document> response = controller.addDocument(newId,"some content");

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getHeaders().get("location").stream().findFirst().get(),
                   is(CONTROLLER_MAPPING + newId.toString()));
    }

    @Test
    public void testAddDocument_withIdNullContent() {
        UUID newId = UUID.randomUUID();

        ResponseEntity<Document> response = controller.addDocument(newId,null);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void testRemoveDocument() {
        UUID newId = UUID.randomUUID();
        when(managerMock.removeDocument(newId)).thenReturn(newId);

        ResponseEntity<UUID> response = controller.removeDocument(newId);
        assertThat(response.getStatusCode(), is(HttpStatus.ACCEPTED));
    }

    @Test
    public void testRemoveDocument_nullId() {
        ResponseEntity<UUID> response = controller.removeDocument(null);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }
}
