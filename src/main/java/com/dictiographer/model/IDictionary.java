package com.dictiographer.model;

public interface IDictionary {
    public int getSize();

    public void createOrUpdate(String headword, String entry);

    public String read(String headword);

    public void delete(String headword);

}
