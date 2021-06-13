package com.dictiographer.desktop.view.entry.comboboxmodels;


import com.dictiographer.desktop.presenter.DesktopUtils;
import com.dictiographer.desktop.view.entry.KeyValuePair;
import com.dictiographer.desktop.view.entry.MyThreadLocal;
import com.dictiographer.desktop.view.entry.ThreadContext;

import javax.swing.*;
import java.util.SortedSet;
import java.util.TreeSet;

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
            KeyValuePair kvp = new KeyValuePair(k, DesktopUtils.getProperty(lang, k));
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
