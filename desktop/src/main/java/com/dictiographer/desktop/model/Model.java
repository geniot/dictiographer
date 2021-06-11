package com.dictiographer.desktop.model;

import com.dictiographer.shared.model.IDictionary;
import com.dictiographer.shared.model.ZipDictionary;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Model extends Observable {
    public Properties properties;
    public ResourceBundle resourceBundle;

    public void addDictionary(Map<String, Serializable> properties) {
        String dictionaryFileName =
                Constants.DICT_DIR_NAME + File.separator +
                        properties.get(IDictionary.DictionaryProperty.INDEX_LANGUAGE.name()) + "_" +
                        properties.get(IDictionary.DictionaryProperty.CONTENTS_LANGUAGE.name()) + "_" +
                        System.currentTimeMillis() + ".zip";
        IDictionary dictionary = new ZipDictionary(URI.create("jar:" + new File(dictionaryFileName).toURI()));
        dictionary.setProperties(properties);
        setChanged();
        notifyObservers(dictionary);
    }

    public SortedSet<LanguageElement> getLangs() {
        SortedSet<LanguageElement> s = new TreeSet<>();
        for (String propertyName : properties.stringPropertyNames()) {
            if (propertyName.startsWith("LANGUAGE_")) {
                String languageCode = properties.getProperty(propertyName);
                String displayText = resourceBundle.getString("language." + languageCode);
                LanguageElement languageElement = new LanguageElement(displayText, languageCode);
                s.add(languageElement);
            }
        }
        return s;
    }

    public DictionariesMap getDictionaries() {
        DictionariesMap dictionaries = new DictionariesMap();
        try {
            Stream<Path> stream = Files.list(Paths.get(Constants.DICT_DIR_NAME));
            stream.forEach(new Consumer<Path>() {
                @Override
                public void accept(Path path) {
                    IDictionary dictionary = new ZipDictionary(URI.create("jar:" + new File(String.valueOf(path)).toURI()));
                    dictionaries.put(dictionary.getId(), dictionary);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dictionaries;
    }

    public void deleteDictionary(IDictionary dictionary) {
        try {
            Files.delete(Paths.get(Constants.DICT_DIR_NAME + File.separator + dictionary.getId()));
            setChanged();
            notifyObservers(dictionary);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
