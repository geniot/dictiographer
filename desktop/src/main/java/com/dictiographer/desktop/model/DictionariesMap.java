package com.dictiographer.desktop.model;

import com.dictiographer.shared.model.IDictionary;

import java.io.Serializable;
import java.util.*;

public class DictionariesMap extends HashMap<String, IDictionary> {

    public SortedMap<String, SortedSet<String>> getLanguages() {
        SortedMap<String, SortedSet<String>> languagesMap = new TreeMap<>();
        for (IDictionary dictionary : values()) {
            Map<String, Serializable> dictionaryProperties = dictionary.getProperties();
            String indexLanguage = dictionaryProperties.get(IDictionary.DictionaryProperty.INDEX_LANGUAGE.name()).toString().toUpperCase();
            String contentsLanguage = dictionaryProperties.get(IDictionary.DictionaryProperty.CONTENTS_LANGUAGE.name()).toString().toUpperCase();

            SortedSet<String> contentLanguagesSet = new TreeSet<>();
            if (languagesMap.containsKey(indexLanguage)) {
                contentLanguagesSet = languagesMap.get(indexLanguage);
            }
            contentLanguagesSet.add(contentsLanguage);
            languagesMap.put(indexLanguage, contentLanguagesSet);
        }
        return languagesMap;
    }

    public SortedSet<IDictionary> getShelfDictionaries(String from, String to) {
        SortedSet<IDictionary> dictionaries = new TreeSet<>();
        for (IDictionary dictionary : values()) {
            Map<String, Serializable> dictionaryProperties = dictionary.getProperties();
            String indexLanguage = dictionaryProperties.get(IDictionary.DictionaryProperty.INDEX_LANGUAGE.name()).toString().toUpperCase();
            String contentsLanguage = dictionaryProperties.get(IDictionary.DictionaryProperty.CONTENTS_LANGUAGE.name()).toString().toUpperCase();
            if (indexLanguage.equals(from) && contentsLanguage.equals(to)) {
                dictionaries.add(dictionary);
            }
        }
        return dictionaries;
    }
}
