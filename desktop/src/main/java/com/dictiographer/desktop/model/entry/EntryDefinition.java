package com.dictiographer.desktop.model.entry;

import java.io.Serializable;
import java.util.Arrays;

public class EntryDefinition implements Serializable {
    private String definition;
    private String meta;
    private Grammar grammar;
    private Example[] examples;
    private EntryImage[] images;
    private Translation[] translations;
    private EntryLink[] synonyms;
    private EntryLink[] antoniems;
    private EntryLink[] hyperoniems;
    private EntryLink[] hyponiems;
    private EntryLink[] zie;
    private Idioom[] idioms;

    public Grammar getGrammar() {
        return grammar;
    }

    public void setGrammar(Grammar grammar) {
        this.grammar = grammar;
    }

    public Idioom[] getIdioms() {
        return idioms;
    }

    public void setIdioms(Idioom[] idioms) {
        this.idioms = idioms;
    }

    public EntryLink[] getZie() {
        return zie;
    }

    public void setZie(EntryLink[] zie) {
        this.zie = zie;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public EntryImage[] getImages() {
        return images;
    }

    public void setImages(EntryImage[] images) {
        this.images = images;
    }

    public EntryLink[] getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(EntryLink[] synonyms) {
        this.synonyms = synonyms;
    }

    public EntryLink[] getAntoniems() {
        return antoniems;
    }

    public void setAntoniems(EntryLink[] antoniems) {
        this.antoniems = antoniems;
    }

    public EntryLink[] getHyperoniems() {
        return hyperoniems;
    }

    public void setHyperoniems(EntryLink[] hyperoniems) {
        this.hyperoniems = hyperoniems;
    }

    public EntryLink[] getHyponiems() {
        return hyponiems;
    }

    public void setHyponiems(EntryLink[] hyponiems) {
        this.hyponiems = hyponiems;
    }

    public Translation[] getTranslations() {
        return translations;
    }

    public void setTranslations(Translation[] trns) {
        if (trns != null) {
            Arrays.sort(trns);
        }
        this.translations = trns;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public Example[] getExamples() {
        return examples;
    }

    public void setExamples(Example[] examples) {
        this.examples = examples;
    }
}
