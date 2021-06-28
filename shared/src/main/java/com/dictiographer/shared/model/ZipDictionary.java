package com.dictiographer.shared.model;


import com.dictiographer.shared.model.lucene.SerializableRAMDirectory;
import io.github.geniot.indexedtreemap.IndexedTreeSet;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ZipDictionary implements IDictionary {
    private URI uri;
    private Map<String, String> env;

    private Base64.Encoder encoder = Base64.getUrlEncoder();
    private Base64.Decoder decoder = Base64.getUrlDecoder();

    private static String ENTRIES = "/entries/";
    private static String INDEX = "/index.ser";
    private static String FT_INDEX = "/ft-index.ser";
    private static String PROPS = "/properties.ser";


    public ZipDictionary(URI u) {
        this.uri = u;
        FileSystem zipFileSystem = null;
        try {
            env = new HashMap<>();
            HashMap envCreate = new HashMap<>();
            envCreate.put("create", "true");
            zipFileSystem = FileSystems.newFileSystem(uri, envCreate);
            Path pathInZipFile = zipFileSystem.getPath(ENTRIES);
            Files.createDirectories(pathInZipFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (zipFileSystem != null) {
                try {
                    zipFileSystem.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void createOrUpdate(String headword, String entry) {
        String encodedHeadword = encoder.encodeToString(headword.getBytes(StandardCharsets.UTF_8));
        FileSystem zipFileSystem = null;
        try {
            zipFileSystem = FileSystems.newFileSystem(uri, env);
            Path pathInZipFile = zipFileSystem.getPath(ENTRIES + encodedHeadword);
            Files.deleteIfExists(pathInZipFile);
            Files.write(pathInZipFile, entry.getBytes(StandardCharsets.UTF_8));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (zipFileSystem != null) {
                try {
                    zipFileSystem.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        updateIndex(new HashSet<>(Arrays.asList(headword)), false);

        SortedMap<String, String> entries = new TreeMap<>();
        entries.put(headword, entry);
        updateFullTextIndex(entries, false);
    }

    @Override
    public void bulkCreateOrUpdate(SortedMap<String, String> entries) {
        FileSystem zipFileSystem = null;
        try {
            zipFileSystem = FileSystems.newFileSystem(uri, env);
            for (Map.Entry<String, String> entry : entries.entrySet()) {
                String headword = entry.getKey();
                String content = entry.getValue();
                String encodedHeadword = encoder.encodeToString(headword.getBytes(StandardCharsets.UTF_8));
                Path pathInZipFile = zipFileSystem.getPath(ENTRIES + encodedHeadword);
                Files.write(pathInZipFile, content.getBytes(StandardCharsets.UTF_8));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (zipFileSystem != null) {
                try {
                    zipFileSystem.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        updateIndex(entries.keySet(), false);
        updateFullTextIndex(entries, false);
    }

    @Override
    public String getId() {
        String[] splits = uri.toString().split("\\/");
        return splits[splits.length - 1];
    }

    @Override
    public String getIndexLanguage() {
        return getProperties().get(IDictionary.DictionaryProperty.INDEX_LANGUAGE.name()).toString().toUpperCase();
    }

    @Override
    public String getContentsLanguage() {
        return getProperties().get(DictionaryProperty.CONTENTS_LANGUAGE.name()).toString().toUpperCase();
    }

    @Override
    public String read(String headword) {
        FileSystem zipFileSystem = null;
        try {
            zipFileSystem = FileSystems.newFileSystem(uri, env);
            String encodedHeadword = encoder.encodeToString(headword.getBytes(StandardCharsets.UTF_8));
            Path pathInZipFile = zipFileSystem.getPath(ENTRIES + encodedHeadword);
            if (!Files.exists(pathInZipFile)) {
                return null;
            }
            String article = new String(Files.readAllBytes(pathInZipFile), StandardCharsets.UTF_8);
            return article;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (zipFileSystem != null) {
                try {
                    zipFileSystem.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void delete(String headword) {
        FileSystem zipFileSystem = null;
        try {
            zipFileSystem = FileSystems.newFileSystem(uri, env);
            String encodedHeadword = encoder.encodeToString(headword.getBytes(StandardCharsets.UTF_8));
            Path pathInZipFile = zipFileSystem.getPath(ENTRIES + encodedHeadword);
            Files.deleteIfExists(pathInZipFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (zipFileSystem != null) {
                try {
                    zipFileSystem.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        updateIndex(new HashSet<>(Arrays.asList(headword)), true);

        SortedMap<String, String> entries = new TreeMap<>();
        entries.put(headword, null);
        updateFullTextIndex(entries, true);
    }

    @Override
    public IndexedTreeSet<String> getIndex() {
        IndexedTreeSet<String> index = new IndexedTreeSet<>();
        FileSystem zipFileSystem = null;
        try {
            zipFileSystem = FileSystems.newFileSystem(uri, env);
            Path pathInZipFile = zipFileSystem.getPath(INDEX);
            if (Files.exists(pathInZipFile)) {
                index = (IndexedTreeSet<String>) Utils.deserialize(Files.readAllBytes(pathInZipFile));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (zipFileSystem != null) {
                try {
                    zipFileSystem.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return index;
    }

    private void updateIndex(Set<String> headwords, boolean isDelete) {
        FileSystem zipFileSystem = null;
        try {
            IndexedTreeSet<String> index = new IndexedTreeSet<>();
            zipFileSystem = FileSystems.newFileSystem(uri, env);
            Path pathInZipFile = zipFileSystem.getPath(INDEX);
            if (Files.exists(pathInZipFile)) {
                index = (IndexedTreeSet<String>) Utils.deserialize(Files.readAllBytes(pathInZipFile));
            }
            if (isDelete) {
                index.removeAll(headwords);
            } else {
                index.addAll(headwords);
            }
            Files.deleteIfExists(pathInZipFile);
            Files.write(pathInZipFile, Utils.serialize(index));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (zipFileSystem != null) {
                try {
                    zipFileSystem.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void updateFullTextIndex(SortedMap<String, String> entries, boolean isDelete) {
        FileSystem zipFileSystem = null;
        try {
            SerializableRAMDirectory directory = new SerializableRAMDirectory();
            zipFileSystem = FileSystems.newFileSystem(uri, env);
            Path pathInZipFile = zipFileSystem.getPath(FT_INDEX);
            if (Files.exists(pathInZipFile)) {
                directory = Utils.deserializeIndex(Files.readAllBytes(pathInZipFile));
            }
            StandardAnalyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            IndexWriter writer = new IndexWriter(directory, indexWriterConfig);

            if (isDelete) {
                for (Map.Entry<String, String> entry : entries.entrySet()) {
                    writer.deleteDocuments(new Term("headword", entry.getKey()));
                }
            } else {
                for (Map.Entry<String, String> entry : entries.entrySet()) {
                    Document document = new Document();
                    document.add(new TextField("headword", entry.getKey(), Field.Store.YES));
                    document.add(new TextField("contents", entry.getValue(), Field.Store.NO));
                    writer.addDocument(document);
                }
            }
            writer.close();
            Files.deleteIfExists(pathInZipFile);
            Files.write(pathInZipFile, Utils.serializeIndex(directory));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (zipFileSystem != null) {
                try {
                    zipFileSystem.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public IndexedTreeSet<SearchResult> search(String queryString) {
        IndexedTreeSet<SearchResult> searchResults = new IndexedTreeSet<>();
        SerializableRAMDirectory directory = new SerializableRAMDirectory();
        FileSystem zipFileSystem = null;
        try {
            zipFileSystem = FileSystems.newFileSystem(uri, env);
            Path pathInZipFile = zipFileSystem.getPath(FT_INDEX);
            if (Files.exists(pathInZipFile)) {
                directory = Utils.deserializeIndex(Files.readAllBytes(pathInZipFile));
            }

            StandardAnalyzer analyzer = new StandardAnalyzer();
            Query query = new QueryParser("contents", analyzer).parse(queryString);
            IndexReader indexReader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(indexReader);
            TopDocs topDocs = searcher.search(query, 10);
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                SearchResult searchResult = new SearchResult();
                searchResult.setScore(scoreDoc.score);
                String headword = searcher.doc(scoreDoc.doc).get("headword");
                searchResult.setHeadword(headword);
                String encodedHeadword = encoder.encodeToString(headword.getBytes(StandardCharsets.UTF_8));
                pathInZipFile = zipFileSystem.getPath(ENTRIES + encodedHeadword);
                //todo prepare article text for full-text results presentation
                searchResult.setText(new String(Files.readAllBytes(pathInZipFile), StandardCharsets.UTF_8));
                searchResults.add(searchResult);
            }
            return searchResults;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (zipFileSystem != null) {
                try {
                    zipFileSystem.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return searchResults;
    }

    @Override
    public Map<String, Serializable> getProperties() {
        Map<String, Serializable> properties = new HashMap<>();
        FileSystem zipFileSystem = null;
        try {
            zipFileSystem = FileSystems.newFileSystem(uri, env);
            Path pathInZipFile = zipFileSystem.getPath(PROPS);
            if (Files.exists(pathInZipFile)) {
                properties = (Map<String, Serializable>) Utils.deserialize(Files.readAllBytes(pathInZipFile));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (zipFileSystem != null) {
                try {
                    zipFileSystem.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

    @Override
    public void setProperties(Map<String, Serializable> properties) {
        FileSystem zipFileSystem = null;
        try {
            zipFileSystem = FileSystems.newFileSystem(uri, env);
            Path pathInZipFile = zipFileSystem.getPath(PROPS);
            Files.deleteIfExists(pathInZipFile);
            Files.write(pathInZipFile, Utils.serialize(properties));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (zipFileSystem != null) {
                try {
                    zipFileSystem.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int compareTo(IDictionary o) {
        return this.uri.toString().compareTo(o.getId());
    }

    @Override
    public String toString() {
        return (String) getProperties().get(DictionaryProperty.NAME.name());
    }
}
