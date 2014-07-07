package com.dictiographer.model.entry;

import java.io.Serializable;

/**
 * User: Vitaly Sazanovich
 * Date: 20/11/12
 * Time: 17:02
 * Email: Vitaly.Sazanovich@gmail.com
 */
public class PartOfSpeech implements Serializable {
    private Grammar grammar;
    private EntryDefinition[] entryDefinitions;
    private String[] afleidingen;
    private String pronunciation;


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
