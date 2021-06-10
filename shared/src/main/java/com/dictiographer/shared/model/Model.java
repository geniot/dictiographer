package com.dictiographer.shared.model;

import java.util.*;

public class Model extends Observable {
    public Properties properties;
    public ResourceBundle resourceBundle;
    private Set<IDictionary> dictionaries;

    public void setDictionaries(Set<IDictionary> dictionaries) {
        this.dictionaries = dictionaries;
    }

    public Set<IDictionary> getDictionaries() {
        return dictionaries;
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


}
