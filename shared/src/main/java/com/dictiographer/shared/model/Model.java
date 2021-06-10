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
}
