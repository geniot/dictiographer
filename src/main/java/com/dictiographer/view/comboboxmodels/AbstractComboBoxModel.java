package com.dictiographer.view.comboboxmodels;


import com.dictiographer.utils.DictiographerUtils;
import com.dictiographer.view.KeyValuePair;
import com.dictiographer.view.MyThreadLocal;
import com.dictiographer.view.ThreadContext;

import javax.swing.*;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * User: Vitaly Sazanovich
 * Date: 12/19/12
 * Time: 2:45 PM
 */
public abstract class AbstractComboBoxModel extends DefaultComboBoxModel {
    public KeyValuePair[] kvps;
    private String lang;

    public AbstractComboBoxModel() {
        ThreadContext context = MyThreadLocal.get();
        lang = context.getLocale().getLanguage();
        kvps = makeModel(getKeys());
        for (KeyValuePair kvp : kvps) {
            addElement(kvp);
        }
    }

    protected abstract String[] getKeys();

    protected KeyValuePair[] makeModel(String[] posKeys) {
        SortedSet<KeyValuePair> s = new TreeSet<KeyValuePair>();
        for (String k : posKeys) {
            KeyValuePair kvp = new KeyValuePair(k, DictiographerUtils.getProperty(lang, k));
            s.add(kvp);
        }
        return s.toArray(new KeyValuePair[s.size()]);
    }

    public KeyValuePair get(String posKey) {
        for (KeyValuePair kvp : kvps) {
            if (kvp.getKey().equals(posKey)) {
                return kvp;
            }
        }
        return kvps[0];
    }
}
