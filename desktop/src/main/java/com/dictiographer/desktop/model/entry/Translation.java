package com.dictiographer.desktop.model.entry;

import java.io.Serializable;

public class Translation implements Serializable, Comparable<Translation> {
    private String locale;
    private String translation;

    public String getLocale() {
        return locale == null ? null : locale.toUpperCase();
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }


    @Override
    public int compareTo(Translation o) {
        if (o != null && o.getLocale() != null) {
            return locale.compareTo(o.getLocale());
        } else {
            return 1;
        }
    }
}
