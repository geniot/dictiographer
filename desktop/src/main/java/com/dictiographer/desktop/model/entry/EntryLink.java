package com.dictiographer.desktop.model.entry;

import java.io.Serializable;

public class EntryLink implements Serializable, Comparable {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int compareTo(Object o) {
        return text.compareTo(((EntryLink) o).getText());
    }



}
