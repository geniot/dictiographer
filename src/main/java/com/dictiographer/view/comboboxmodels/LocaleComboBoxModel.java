package com.dictiographer.view.comboboxmodels;

import javax.swing.*;

/**
 * User: Vitaly Sazanovich
 * Date: 12/19/12
 * Time: 2:53 PM
 */
public class LocaleComboBoxModel extends DefaultComboBoxModel {

    public LocaleComboBoxModel() {
        super(new String[]{"BY", "EN", "NL", "FR", "DE", "RU", "ES"});
    }
}
