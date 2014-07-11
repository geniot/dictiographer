package com.dictiographer.view.comboboxmodels;


import com.dictiographer.utils.Utf8ResourceBundle;
import com.dictiographer.view.MyThreadLocal;

import java.util.Locale;

/**
 * User: Vitaly Sazanovich
 * Date: 12/18/12
 * Time: 9:45 PM
 */
public class PosComboBoxModel extends AbstractComboBoxModel {

    public PosComboBoxModel() {
        super();
    }

    @Override
    protected String[] getKeys() {
        String lang = MyThreadLocal.get().getLang();
        String pos = Utf8ResourceBundle.getBundle("DictiographerResource", new Locale(lang), this.getClass().getClassLoader()).getString("pos");
        return pos.split(",");
    }
}
