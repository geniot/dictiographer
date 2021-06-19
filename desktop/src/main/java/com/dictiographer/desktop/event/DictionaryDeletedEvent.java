package com.dictiographer.desktop.event;

import com.dictiographer.desktop.DictionaryToggleButton;

public class DictionaryDeletedEvent implements Event {
    private String indexLanguage;
    private String contentsLanguage;
    private DictionaryToggleButton dictionaryToggleButton;

    public DictionaryDeletedEvent(String il, String cl, DictionaryToggleButton b) {
        this.indexLanguage = il;
        this.contentsLanguage = cl;
        this.dictionaryToggleButton = b;
    }

    public String getIndexLanguage() {
        return indexLanguage;
    }

    public String getContentsLanguage() {
        return contentsLanguage;
    }

    public DictionaryToggleButton getDictionaryToggleButton() {
        return dictionaryToggleButton;
    }
}
