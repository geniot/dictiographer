package com.dictiographer.model;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ZipDictionaryTest {
    @Test
    public void testCreate() {
        try {
            File f = new File("ziptest.zip");
            IDictionary dictionary = new ZipDictionary(f);
            dictionary.createOrUpdate("1", "test");
            assertEquals(1, dictionary.getSize());
        } catch (Exception ex) {
            fail(ex.getMessage(), ex);
        }
    }

}
