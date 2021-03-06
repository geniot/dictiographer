package com.dictiographer.view;

/**
 * User: Vitaly Sazanovich
 * Date: 12/10/12
 * Time: 1:55 AM
 */
public class KeyValuePair implements Comparable {
    private String key;
    private String value;

    public KeyValuePair(String k, String v) {
        this.key = k;
        this.value = v;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int compareTo(Object o) {
        return value.compareTo(((KeyValuePair)o).getValue());
    }
}
