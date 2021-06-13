package com.dictiographer.desktop.view.entry.comboboxmodels;

public class WerkwoordSoortComboBoxModel extends AbstractComboBoxModel {

    public WerkwoordSoortComboBoxModel() {
        super();
    }

    @Override
    protected String[] getKeys() {
        return new String[]{"ov", "intr", "refl", "ditr"};
    }
}
