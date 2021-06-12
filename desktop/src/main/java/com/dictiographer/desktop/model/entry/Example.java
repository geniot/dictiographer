package com.dictiographer.desktop.model.entry;

import java.io.Serializable;

public class Example implements Serializable {
    private String source;
    private String example;
    private String explanation;
    private String meta;
    private byte[] audio;
    private String ext;
    private long checksum;

    private Translation[] translations;
    public EntryImage[] images;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public EntryImage[] getImages() {
        return images;
    }

    public void setImages(EntryImage[] images) {
        this.images = images;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public long getChecksum() {
        return checksum;
    }

    public void setChecksum(long checksum) {
        this.checksum = checksum;
    }

    public byte[] getAudio() {
        return audio;
    }

    public void setAudio(byte[] audio) {
        this.audio = audio;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public Translation[] getTranslations() {
        return translations;
    }

    public void setTranslations(Translation[] translations) {
        this.translations = translations;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
