package com.dictiographer.shared.model;

import com.dictiographer.shared.collections.IndexedTreeSet;
import com.dictiographer.shared.model.IDictionary;
import com.dictiographer.shared.model.ZipDictionary;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ZipDictionaryPerformanceTest {
    @Test
    public void testImport() {

        File f = new File("ziptest.zip");
        try {
            if (f.exists()) {
                f.delete();
            }
            f.deleteOnExit();

            String article = new String(Files.readAllBytes(Paths.get(this.getClass().getClassLoader().getResource("sample.txt").toURI())), StandardCharsets.UTF_8);
            IDictionary dictionary = new ZipDictionary(new File("ziptest.zip"));
            assertEquals(0, dictionary.getIndex().size());
            SortedMap<String, String> entries = new TreeMap<>();
            int size = 10000;
            for (int i = 1; i <= size; i++) {
                entries.put("test " + i, article);
            }
            long t1 = System.currentTimeMillis();
            dictionary.bulkCreateOrUpdate(entries);
            long t2 = System.currentTimeMillis() - t1;
            System.out.println("Adding " + size + " entries took: " + t2);
            System.out.println("Resulting file size: " + f.length());

            t1 = System.currentTimeMillis();
            IndexedTreeSet<String> index = dictionary.getIndex();
            assertEquals(size, index.size());
            t2 = System.currentTimeMillis() - t1;
            System.out.println("Getting index took: " + t2);

            t1 = System.currentTimeMillis();
            dictionary.getIndex().size();
            t2 = System.currentTimeMillis() - t1;
            System.out.println("Getting size took: " + t2);

            t1 = System.currentTimeMillis();
            dictionary.search("anxious");
            t2 = System.currentTimeMillis() - t1;
            System.out.println("Full-text search took: " + t2);

        } catch (Exception ex) {
            fail(ex.getMessage(), ex);
        }
    }
}
