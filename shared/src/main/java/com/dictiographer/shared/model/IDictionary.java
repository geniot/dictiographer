package com.dictiographer.shared.model;

import com.dictiographer.shared.collections.IndexedTreeSet;

import java.io.Serializable;
import java.util.Map;
import java.util.SortedMap;

public interface IDictionary extends Comparable<IDictionary>{

    void createOrUpdate(String headword, String entry);

    void bulkCreateOrUpdate(SortedMap<String, String> entries);

    String getId();

    String getIndexLanguage();

    String getContentsLanguage();

    String read(String headword);

    void delete(String headword);

    IndexedTreeSet<String> getIndex();

    IndexedTreeSet<SearchResult> search(String query);

    Map<String, Serializable> getProperties();

    void setProperties(Map<String,Serializable> properties);

    public enum DictionaryProperty {
        NAME, ANNOTATION, INDEX_LANGUAGE, CONTENTS_LANGUAGE, ICON
    }
}
