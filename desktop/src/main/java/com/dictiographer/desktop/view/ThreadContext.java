package com.dictiographer.desktop.view;


import java.util.Locale;
import java.util.ResourceBundle;

public class ThreadContext {
    private Locale locale;
    private MyLocalizer localizer;
    private ResourceBundle messageSource;

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

    public ResourceBundle getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(ResourceBundle messageSource) {
        this.messageSource = messageSource;
    }
}
