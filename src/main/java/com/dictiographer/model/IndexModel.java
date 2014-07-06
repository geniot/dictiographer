package com.dictiographer.model;

/**
 * Author: Vitaly Sazanovich
 * Email: Vitaly.Sazanovich@gmail.com
 * Date: 7/6/14
 */
public class IndexModel {
    private static IndexModel INSTANCE;

    public static IndexModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IndexModel();
        }
        return INSTANCE;
    }

    public String[] domains = new String[]{};
}
