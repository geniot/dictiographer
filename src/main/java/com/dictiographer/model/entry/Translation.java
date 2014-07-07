package com.dictiographer.model.entry;

import java.io.Serializable;

/**
 * User: Vitaly Sazanovich
 * Date: 29/11/12
 * Time: 10:39
 * Email: Vitaly.Sazanovich@gmail.com
 */
public class Translation implements Serializable {
    private String locale;
    private String translation;

    public String getLocale() {
        return locale==null?null:locale.toUpperCase();
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
