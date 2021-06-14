package com.dictiographer.desktop.presenter;

import com.dictiographer.desktop.model.entry.ByteArrayPersistenceDelegate;
import com.dictiographer.desktop.model.entry.EntryLink;
import com.dictiographer.desktop.model.entry.EntryObjectModel;
import com.dictiographer.shared.model.Utils;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.beans.PersistenceDelegate;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DesktopUtils {
    private static final Logger logger = Logger.getLogger(DesktopUtils.class.getName());

    public static String links2str(EntryLink[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i].getText());
            if (i < arr.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static EntryLink[] str2links(String str) {
        String[] ss = str.split(",");
        List<EntryLink> els = new ArrayList<EntryLink>();
        for (String s : ss) {
            EntryLink el = new EntryLink();
            el.setText(s.trim());
            els.add(el);
        }
        return els.toArray(new EntryLink[els.size()]);
    }

    public static Object xml2entry(String content) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(content.getBytes("UTF-8"));
            XMLDecoder decoder = new XMLDecoder(bais);
            Object eom = decoder.readObject();
            decoder.close();
            bais.close();
            return eom;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return new Object();
        }
    }

    public static String entry2xml(Object eom) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            XMLEncoder encoder = new XMLEncoder(baos) {
                public PersistenceDelegate getPersistenceDelegate(Class<?> type) {
                    if (type == byte[].class)
                        return new ByteArrayPersistenceDelegate();
                    else
                        return super.getPersistenceDelegate(type);
                }
            };
            encoder.writeObject(eom);
            encoder.close();
            baos.close();
            return new String(baos.toByteArray(), "UTF-8");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return "";
        }
    }

    public static String getProperty(String lang, String key) {
        try {
            return Utf8ResourceBundle.getBundle("DictiographerResource", new Locale(lang.toLowerCase()), Utils.class.getClassLoader()).getString(key);
        } catch (Exception e) {
            try {
                return Utf8ResourceBundle.getBundle("DictiographerResource", new Locale("en"), Utils.class.getClassLoader()).getString(key);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Can't find resource for key: " + key);
                return key;
            }
        }
    }
}
