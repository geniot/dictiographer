package com.dictiographer.desktop.view.comboboxmodels;

public class WerkwoordSoortComboBoxModel extends AbstractComboBoxModel {

    public WerkwoordSoortComboBoxModel() {
        super();
    }

    @Override
    protected String[] getKeys() {
        return new String[]{"ov", "intr", "refl", "ditr"};
    }
}
