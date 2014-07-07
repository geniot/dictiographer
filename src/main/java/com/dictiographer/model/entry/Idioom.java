package com.dictiographer.model.entry;

import java.io.Serializable;

/**
 * User: Vitaly Sazanovich
 * Date: 12/25/12
 * Time: 1:25 PM
 */
public class Idioom implements Serializable {
    private String idioom;
    private String meta;

    private String explanation;
    private Translation[] translations;

    private IdiomExplanation[] idiomExplanations;

    public IdiomExplanation[] getIdiomExplanations() {
        return idiomExplanations;
    }

    public void setIdiomExplanations(IdiomExplanation[] idiomExplanations) {
        this.idiomExplanations = idiomExplanations;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getIdioom() {
        return idioom;
    }

    public void setIdioom(String idioom) {
        this.idioom = idioom;
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
