package com.dictiographer.shared.model;

import io.github.geniot.indexedtreemap.IndexedTreeSet;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ZipDictionaryTest {
    @Test
    public void testCreate() {
        File f = new File("ziptest.zip");
        try {
            if (f.exists()) {
                f.delete();
            }
            f.deleteOnExit();
            IDictionary dictionary = new ZipDictionary(URI.create("jar:" + new File("ziptest.zip").toURI()));
            assertEquals(0, dictionary.getIndex().size());
            //test create
            dictionary.createOrUpdate("1", "test");
            assertEquals(1, dictionary.getIndex().size());
            assertEquals("test", dictionary.read("1"));
            assertEquals(1, dictionary.getIndex().size());
            //test delete
            dictionary.delete("1");
            assertEquals(0, dictionary.getIndex().size());
            assertEquals(0, dictionary.getIndex().size());
            //test update
            dictionary.createOrUpdate("1", "test");
            dictionary.createOrUpdate("2", "test2");
            assertEquals(2, dictionary.getIndex().size());
            assertEquals(2, dictionary.getIndex().size());
            assertEquals("test2", dictionary.read("2"));

            dictionary.createOrUpdate("2", "test22");
            assertEquals(2, dictionary.getIndex().size());
            assertEquals(2, dictionary.getIndex().size());
            assertEquals("test22", dictionary.read("2"));

        } catch (Exception ex) {
            fail(ex.getMessage(), ex);
        }
    }

    @Test
    public void testSpaces() {
        File f = new File("ziptest.zip");
        try {
            if (f.exists()) {
                f.delete();
            }
            f.deleteOnExit();
            IDictionary dictionary = new ZipDictionary(URI.create("jar:" + new File("ziptest.zip").toURI()));
            assertEquals(0, dictionary.getIndex().size());

            dictionary.createOrUpdate("1 2", "test 1 2");
            assertEquals(1, dictionary.getIndex().size());
            assertEquals("test 1 2", dictionary.read("1 2"));

            dictionary.createOrUpdate("1\t2", "test 1\t2");
            assertEquals(2, dictionary.getIndex().size());
            assertEquals("test 1\t2", dictionary.read("1\t2"));

            dictionary.createOrUpdate("1\n2", "test 1\n2");
            assertEquals(3, dictionary.getIndex().size());
            assertEquals("test 1\n2", dictionary.read("1\n2"));

            dictionary.createOrUpdate("1/2", "test 1/2");
            assertEquals(4, dictionary.getIndex().size());
            assertEquals("test 1/2", dictionary.read("1/2"));

            IndexedTreeSet<String> index = dictionary.getIndex();
            assertEquals(4, index.size());
            String[] expectedHeadwords = new String[]{"1\t2", "1\n2", "1 2", "1/2"};
            String[] headwords = index.toArray(new String[4]);
            assertArrayEquals(expectedHeadwords, headwords);

        } catch (Exception ex) {
            fail(ex.getMessage(), ex);
        }
    }

    @Test
    public void testSearch() {
        File f = new File("ziptest.zip");
        try {
            if (f.exists()) {
                f.delete();
            }
            f.deleteOnExit();
            IDictionary dictionary = new ZipDictionary(URI.create("jar:" + new File("ziptest.zip").toURI()));
            assertEquals(0, dictionary.getIndex().size());

            dictionary.createOrUpdate("mother", "brother father");
            dictionary.createOrUpdate("sister", "cousin grandpa");

            IndexedTreeSet<SearchResult> result = dictionary.search("brother");
            assertEquals(1, result.size());
            SearchResult sr = result.iterator().next();
            assertEquals("mother", sr.getHeadword());

            dictionary.delete("mother");
            result = dictionary.search("brother");
            assertEquals(0, result.size());


        } catch (Exception ex) {
            fail(ex.getMessage(), ex);
        }
    }

    @Test
    public void testProperties() {
        File f = new File("ziptest.zip");
        try {
            if (f.exists()) {
                f.delete();
            }
            f.deleteOnExit();
            IDictionary dictionary = new ZipDictionary(URI.create("jar:" + new File("ziptest.zip").toURI()));

            String name = "Some dictionary name";
            String annotation = "Some dictionary annotation";
            String indexLanguage = "en";
            String contentsLanguage = "en";

            Map<String, Serializable> properties1 = new HashMap<>();
            properties1.put(IDictionary.DictionaryProperty.NAME.name(), name);
            properties1.put(IDictionary.DictionaryProperty.ANNOTATION.name(), annotation);
            properties1.put(IDictionary.DictionaryProperty.INDEX_LANGUAGE.name(), indexLanguage);
            properties1.put(IDictionary.DictionaryProperty.CONTENTS_LANGUAGE.name(), contentsLanguage);
            properties1.put(IDictionary.DictionaryProperty.ICON.name(), new byte[]{0, 1, 2, 3, 4, 5, 6, 7});
            dictionary.setProperties(properties1);

            Map<String, Serializable> properties2 = dictionary.getProperties();
            assertEquals(name, properties2.get(IDictionary.DictionaryProperty.NAME.name()));
            assertEquals(annotation, properties2.get(IDictionary.DictionaryProperty.ANNOTATION.name()));
            assertEquals(indexLanguage, properties2.get(IDictionary.DictionaryProperty.INDEX_LANGUAGE.name()));
            assertEquals(contentsLanguage, properties2.get(IDictionary.DictionaryProperty.CONTENTS_LANGUAGE.name()));
            byte[] iconBytes = (byte[]) properties2.get(IDictionary.DictionaryProperty.ICON.name());
            assertEquals(8, iconBytes.length);

        } catch (Exception ex) {
            fail(ex.getMessage(), ex);
        }
    }

}
