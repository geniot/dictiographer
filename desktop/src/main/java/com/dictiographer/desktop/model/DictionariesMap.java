package com.dictiographer.desktop.model;

import com.dictiographer.shared.model.IDictionary;

import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class DictionariesMap extends HashMap<String, IDictionary> {
    public SortedSet<String> getIndexLanguages() {
        SortedSet<String> indexLanguages = new TreeSet<>();
        for (IDictionary dictionary : values()) {
            indexLanguages.add(dictionary.getProperties().get(IDictionary.DictionaryProperty.INDEX_LANGUAGE.name()).toString().toUpperCase());
        }
        return indexLanguages;
    }
}
