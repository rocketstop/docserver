package com.mostlysafe.docservice;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.UUID;

import com.mostlysafe.docservice.model.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DocumentTest {

    @BeforeEach
    public void setup() {

    }

    @Test
    public void test_getters () {
        UUID one = UUID.randomUUID();
        String content = "test content";
        Document document = new Document(one, content, null, null);

        assertThat(document.getId(), is(one));
        assertThat(document.getContent(), is(content));
    }

    @Test
    public void testEqual_same() {
        UUID id = UUID.randomUUID();
        String content = "test content";
        Document one = new Document(id, content, null, null);

        assertEquals(one, one);
    }

    @Test
    public void testToString() {
        UUID id = UUID.randomUUID();
        String content = "test content";
        Document one = new Document(id, content, null, null);
        String expected = "Document{id="+id+" : content="+ content+"}";

        String output = one.toString();

        assertThat(output, is(expected));

    }

    @Test
    public void testEqual() {
        UUID id = UUID.randomUUID();
        String content = "test content";
        Document one = new Document(id, content, null, null);
        Document two = new Document(id, content, null, null);

        assertEquals(one, two);
    }

    @Test
    public void testEqual_idNotEqual() {
        UUID id = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        String content = "test content";
        Document one = new Document(id, content, null, null);
        Document two = new Document(id2, content, null, null);

        assertNotEquals(one, two);
    }

    @Test
    public void testEqual_contentNotEqual() {
        UUID id = UUID.randomUUID();
        String content = "test content";
        String content2 = "more test content";
        Document one = new Document(id, content, null, null);
        Document two = new Document(id, content2, null, null);

        assertNotEquals(one, two);
    }

    @Test
    public void testEqual_differentTypes() {
        UUID id = UUID.randomUUID();
        String content = "test content";
        Document one = new Document(id, content, null, null);

        assertNotEquals(one, "a random thing");
    }

    @Test
    public void testEqual_null() {
        UUID id = UUID.randomUUID();
        String content = "test content";
        Document one = new Document(id, content, null, null);

        assertNotEquals(one, null);
    }


}
