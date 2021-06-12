package com.dictiographer.desktop.model.entry;

import java.io.Serializable;

public class PartOfSpeech implements Serializable {

    private Grammar grammar;
    private String[] afleidingen;
    private String pronunciation;
    private EntryDefinition[] entryDefinitions;



    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String[] getAfleidingen() {
        return afleidingen;
    }

    public void setAfleidingen(String[] afleidingen) {
        this.afleidingen = afleidingen;
    }

    public EntryDefinition[] getEntryDefinitions() {
        return entryDefinitions;
    }

    public void setEntryDefinitions(EntryDefinition[] entryDefinitions) {
        this.entryDefinitions = entryDefinitions;
    }

    public Grammar getGrammar() {
        return grammar;
    }

    public void setGrammar(Grammar grammar) {
        this.grammar = grammar;
    }
}
