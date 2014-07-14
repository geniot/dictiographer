package com.dictiographer.view;

import java.util.Locale;

/**
 * User: Vitaly Sazanovich
 * Date: 1/13/13
 * Time: 9:22 PM
 */
public class ThreadContext {
    private Locale locale;
    private MyLocalizer localizer;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public MyLocalizer getLocalizer() {
        return localizer;
    }

    public void setLocalizer(MyLocalizer localizer) {
        this.localizer = localizer;
    }
}
