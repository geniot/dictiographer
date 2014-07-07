package com.dictiographer.model.entry;

import java.io.Serializable;

/**
 * User: Vitaly Sazanovich
 * Date: 30/11/12
 * Time: 16:29
 * Email: Vitaly.Sazanovich@gmail.com
 */
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
