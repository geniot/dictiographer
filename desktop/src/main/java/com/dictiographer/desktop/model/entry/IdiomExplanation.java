package com.dictiographer.desktop.model.entry;

import java.io.Serializable;

public class IdiomExplanation implements Serializable {
    private String meta;
    private String explanation;
    private Translation[] translations;

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Translation[] getTranslations() {
        return translations;
    }

    public void setTranslations(Translation[] translations) {
        this.translations = translations;
    }
}
