package com.dictiographer.desktop.view.entry;

import com.dictiographer.desktop.presenter.Utf8ResourceBundle;
import org.swixml.Localizer;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MyLocalizer extends Localizer {
    private static String SEPARATOR = ",";
    private Locale locale = Locale.getDefault();
    private String bundleName;
    private ResourceBundle bundle;
    private ClassLoader cl = MyLocalizer.class.getClassLoader();


    public MyLocalizer(Locale locale) {
        setLocale(locale);
    }

    /**
     * Sets the regular expression used to split a key, that could not be found in the resource bundle.
     *
     * @param regExp <code>String</code>
     * @see String#split
     * @see #getString
     */
    public static void setSeparator(String regExp) {
        MyLocalizer.SEPARATOR = regExp;
    }

    /**
     * Returns the localized String baseed on the given key.
     * If the key cannot be found, the key is returned insstead.
     *
     * @param key <code>String</code>
     * @return <code>String</code> - localized String , or key , if no lacalization is found.
     */
    public String getString(final String key) {
        if (!isUsable()) return key;
        String s = "";
        try {
            s = bundle.getString(key);
        } catch (MissingResourceException e) {
            String[] keys = key.split(MyLocalizer.SEPARATOR);
            if (2 <= keys.length) {
                for (int i = 0; i < keys.length; i++) {
                    s += (i == 0) ? getString(keys[i]) : "," + getString(keys[i]);
                }
            } else s = key;
        } catch (Exception e) {
            s = key;   // key not found, return key
        }
        return s;
    }


    public String getString(final String key, String lang) {
        if (!lang.equals(this.locale.getLanguage())) {
            setLocale(new Locale(lang));
        }
        return getString(key);
    }

    /**
     * Sets this Localizer's locale.
     *
     * @param locale <code>Locale</code>
     */
    public void setLocale(Locale locale) {
        if (locale == null) {
            this.locale = null;
            this.bundle = null;
            this.bundleName = null;
        } else if (this.locale != locale) {
            this.locale = locale;
            setResourceBundle(bundleName);
        }
    }

    /**
     * Sets this Localizer's ResourceBundle.
     *
     * @param bundleName <code>String</code>ResourceBundle file / class name
     * @throws java.util.MissingResourceException
     *          - if no resource bundle for the specified base name can be found
     */
    public void setResourceBundle(String bundleName) throws MissingResourceException {
        this.bundleName = bundleName;
        if (locale == null) {
            locale = Locale.getDefault();
        }
        if (bundleName != null) {
            try {
                bundle = Utf8ResourceBundle.getBundle(bundleName, locale, cl);
            } catch (MissingResourceException mre) {
                bundle = Utf8ResourceBundle.getBundle(bundleName, new Locale("en"), cl);
            }
        } else {
            bundle = null;
        }
    }

    /**
     * Informs about the usablility of this Localizer.
     *
     * @return <code>boolean</code> - true if Localizer is setup with Locale and ResourceBundle.
     */
    public boolean isUsable() {
        return (locale != null && bundle != null);
    }

    /**
     * @return <code>ClassLoader</code> returns the classloader attribute, which has probably been set by the SwingEngine
     */
    public ClassLoader getClassLoader() {
        return cl;
    }

    /**
     * Sets the ClassLoader attribute.
     * The Localizer does not use the provided classloader directly but return it when asked for.
     *
     * @param cl <code>ClassLoader</code> - custom classloader
     */
    public void setClassLoader(ClassLoader cl) {
        this.cl = cl;
    }
}
