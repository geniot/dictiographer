package com.dictiographer.desktop.model;

import javax.swing.*;
import java.awt.*;

public class Constants {
    public static final String PROPS_FILE_NAME = "dictiographer.properties";

    public static final String DICT_DIR_NAME = "data";
    public static String MEDIA_CURRENT_DIRECTORY;

    public static final int PROPS_NAME_LIMIT = 200;
    public static final int PROPS_ANNOTATION_LIMIT = 10000;

    public static final String DEFAULT_LAF = "com.jtattoo.plaf.smart.SmartLookAndFeel";
    public static String LAF_PREFIX = "com.jtattoo.plaf.";
    public static String[] LAFS = new String[]{"Acryl", "Aero", "Aluminium", "Bernstein", "Fast", "Graphite", "HiFi", "Luna", "McWin", "Mint", "Noire", "Smart", "Texture"};

    public enum PropKeys {
        PROP_WIDTH,
        PROP_HEIGHT,
        PROP_POS_X,
        PROP_POS_Y,
        PROP_ENTRY_POS_X,
        PROP_ENTRY_POS_Y,
        PROP_ENTRY_WIDTH,
        PROP_ENTRY_HEIGHT,
        PROP_DIVIDER_LOCATION
    }

    public enum ACTIONS{
        NEW_ACTION,UPDATE_ACTION
    }

    public static void setLAF(Component frame, String newLAF) {
        try {
            if (newLAF == null) {
                newLAF = DEFAULT_LAF;
            }
            UIManager.setLookAndFeel(newLAF);
            if (frame != null) {
                SwingUtilities.updateComponentTreeUI(frame);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
