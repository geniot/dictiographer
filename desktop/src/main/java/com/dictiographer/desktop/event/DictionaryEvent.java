package com.dictiographer.desktop.event;

import com.dictiographer.desktop.Constants;
import com.dictiographer.shared.model.IDictionary;

public class DictionaryEvent extends BaseEvent {
    private IDictionary dictionary;

    public DictionaryEvent(IDictionary d, Constants.EventType action) {
        super(action);
        this.dictionary = d;
    }

    public IDictionary getDictionary() {
        return dictionary;
    }
}
