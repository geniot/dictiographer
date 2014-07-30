package com.dictiographer.utils;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Author: Vitaly Sazanovich
 * Email: Vitaly.Sazanovich@gmail.com
 * Date: 7/31/14
 */
public class ReloadableResourceBundleMessageSourceProxy extends ReloadableResourceBundleMessageSource {
    private Map<String, Locale> localesCache = new HashMap<String, Locale>();

    public String getMessage(String key, String lang) {
        Locale locale = localesCache.get(lang);
        if (locale == null) {
            locale = new Locale(lang);
            localesCache.put(lang, locale);
        }
        return getMessage(key, null, locale);
    }
}
