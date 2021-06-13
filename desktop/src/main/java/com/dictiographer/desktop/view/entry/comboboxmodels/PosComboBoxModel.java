package com.dictiographer.desktop.view.entry.comboboxmodels;


import com.dictiographer.desktop.presenter.Utf8ResourceBundle;
import com.dictiographer.desktop.view.entry.MyThreadLocal;

import java.util.Locale;

public class PosComboBoxModel extends AbstractComboBoxModel {

    public PosComboBoxModel() {
        super();
    }

    @Override
    protected String[] getKeys() {
        String lang = MyThreadLocal.get().getLocale().getLanguage();
        String pos = Utf8ResourceBundle.getBundle("DictiographerResource", new Locale(lang), this.getClass().getClassLoader()).getString("pos");
        return pos.split(",");
    }
}
