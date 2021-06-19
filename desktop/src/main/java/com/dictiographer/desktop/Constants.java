package com.dictiographer.desktop;

public class Constants {
    public static final String PROPS_FILE_NAME = "dictiographer.properties";
    public static final String DICT_DIR_NAME = "data";
    public static final String DEFAULT_LAF = "com.jtattoo.plaf.smart.SmartLookAndFeel";
    public static String LAF_PREFIX = "com.jtattoo.plaf.";
    public static String[] LAFS = new String[]{"Acryl", "Aero", "Aluminium", "Bernstein", "Fast", "Graphite", "HiFi", "Luna", "McWin", "Mint", "Noire", "Smart", "Texture"};

    public enum PropKeys {
        PROP_WIDTH,
        PROP_HEIGHT,
        PROP_POS_X,
        PROP_POS_Y,
        DEFAULT_INDEX_LANGUAGE,
        DEFAULT_CONTENTS_LANGUAGE,
        MAIN_DIVIDER_LOCATION,
        CONTENTS_DIVIDER_LOCATION
    }

    public enum EventType {
        DICTIONARY_ADD,
        DICTIONARY_EDIT,
        DICTIONARY_SELECTED,
        INDEX_HEADWORD_SELECTED,
        ENTRY_ADD
    }
}
