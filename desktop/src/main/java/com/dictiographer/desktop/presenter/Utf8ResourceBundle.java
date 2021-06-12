package com.dictiographer.desktop.presenter;


import java.util.Enumeration;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public abstract class Utf8ResourceBundle {

    /**
     * Gets the unicode friendly resource bundle
     *
     * @param baseName
     * @return Unicode friendly resource bundle
     * @see java.util.ResourceBundle#getBundle(String)
     */
    public static final ResourceBundle getBundle(final String baseName, Locale l, ClassLoader cl) {
        return createUtf8PropertyResourceBundle(
                ResourceBundle.getBundle(baseName, l, cl));
    }

    /**
     * Creates unicode friendly {@link java.util.PropertyResourceBundle} if possible.
     *
     * @param bundle
     * @return Unicode friendly property resource bundle
     */
    private static ResourceBundle createUtf8PropertyResourceBundle(
            final ResourceBundle bundle) {
        if (!(bundle instanceof PropertyResourceBundle)) {
            return bundle;
        }
        return new Utf8PropertyResourceBundle((PropertyResourceBundle) bundle);
    }

    /**
     * Resource Bundle that does the hard work
     */
    private static class Utf8PropertyResourceBundle extends ResourceBundle {

        /**
         * Bundle with unicode data
         */
        private final PropertyResourceBundle bundle;

        /**
         * Initializing constructor
         *
         * @param bundle
         */
        private Utf8PropertyResourceBundle(final PropertyResourceBundle bundle) {
            this.bundle = bundle;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Enumeration getKeys() {
            return bundle.getKeys();
        }

        @Override
        protected Object handleGetObject(final String key) {
            final String value = bundle.getString(key);
            if (value == null)
                return null;
            try {
                return value;//new String(value.getBytes("ISO-8859-1"), "UTF-8");
            } catch (final Exception e) {
                throw new RuntimeException("Encoding not supported", e);
            }
        }
    }
}

