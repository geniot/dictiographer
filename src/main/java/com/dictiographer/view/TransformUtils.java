package com.dictiographer.view;

import com.dictiographer.controller.Utf8ResourceBundle;
import com.dictiographer.model.Constants;
import com.sun.org.apache.xalan.internal.xsltc.DOM;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Vitaly Sazanovich
 * Email: vitaly.sazanovich@gmail.com
 * Date: 07/07/14
 */
public class TransformUtils {
    private static final Logger logger = Logger.getLogger(TransformUtils.class.getName());

    public static String getProperty(String lang, String key) {
        try {
            return Utf8ResourceBundle.getBundle("DictiographyResource", new Locale(lang.toLowerCase()), TransformUtils.class.getClassLoader()).getString(key);
        } catch (Exception e) {
            try {
                String defLocale = Constants.PROPS.getProperty(Constants.DEFAULT_LOCALE_PROP_KEY);
                return Utf8ResourceBundle.getBundle("DictiographyResource", new Locale(defLocale), TransformUtils.class.getClassLoader()).getString(key);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Can't find resource for key: " + key);
                return key;
            }
        }
    }


    public static String stressSyllable(String wrapperTag, String className, String vowels, Object s, Object ss) {
        if (s == null || ((DOM) s).getStringValue().equals("")) return null;
        String syllables = ((DOM) s).getStringValue();
        int stressedSyllable = ((DOM) ss).getStringValue().equals("") ? 1 : Integer.parseInt(((DOM) ss).getStringValue());

        String[] vowelsArr = vowels.split(",");
        String[] syllablesArr = syllables.split("·|▪|\\||(?<=[0-9])|(?<=[-])");
        String[] patchedSyllablesArr = syllablesArr.clone();

        if (stressedSyllable <= syllablesArr.length && syllablesArr.length > 1) {
            patchedSyllablesArr[stressedSyllable - 1] = patch(wrapperTag, className, syllablesArr[stressedSyllable - 1], vowelsArr);//syllables start with 1
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < syllablesArr.length; i++) {
            sb.append(patchedSyllablesArr[i]);
            if (i < syllablesArr.length - 1 && !isNumber(syllablesArr[i]) && !syllablesArr[i].endsWith("-")) {
                sb.append("·");
            }
        }
        return sb.toString();
    }

    private static boolean isNumber(String s) {
        if (s.isEmpty()) return false;
        if (s.length() > 1) return false;
        return Character.isDigit(s.charAt(0));
    }

    private static String patch(String wrapperTag, String className, String s, String[] vowels) {
        String stressMeVowel = null;
        for (String v : vowels) {
            if (s.contains(v)) {
                if (stressMeVowel == null) {
                    stressMeVowel = v;
                } else {
                    int stressMeVowelIndex = s.indexOf(stressMeVowel);
                    if (s.indexOf(v) < stressMeVowelIndex) {
                        stressMeVowel = v;
                    }
                }
            }
        }
        if (stressMeVowel != null) {
            s = s.replaceFirst(stressMeVowel, "<" + wrapperTag + " class=\"" + className + "\">" + stressMeVowel + "</" + wrapperTag + ">");
        }
        return s;
    }

    public static String addStress(String input) {
        return input.replaceAll("\\[", "<b>").replaceAll("\\]", "</b>");
    }

    public static String encodeUrl(String in) {
        try {
            return URLEncoder.encode(in, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return in;
        }
    }
}
