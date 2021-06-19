package com.dictiographer.desktop.event;

import com.dictiographer.desktop.Constants;
import com.dictiographer.desktop.model.DictionaryEntry;
import com.dictiographer.shared.model.IDictionary;

public class EntryEvent extends BaseEvent {

    private DictionaryEntry dictionaryEntry;
    private IDictionary dictionary;

    public EntryEvent(DictionaryEntry de, IDictionary d, Constants.EventType ea) {
        super(ea);
        this.dictionaryEntry = de;
        this.dictionary = d;
    }

    public DictionaryEntry getDictionaryEntry() {
        return dictionaryEntry;
    }

    public IDictionary getDictionary() {
        return dictionary;
    }
}
