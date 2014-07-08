package com.dictiographer.view;

import com.dictiographer.model.Constants;

/**
 * User: Vitaly Sazanovich
 * Date: 1/13/13
 * Time: 9:22 PM
 */
public class ThreadContext {
    private String lang = Constants.PROPS.getProperty(Constants.DEFAULT_LOCALE_PROP_KEY);

    public String getLang() {
        return lang.toLowerCase();
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
