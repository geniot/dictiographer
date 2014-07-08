package com.dictiographer.view.comboboxmodels;

/**
 * User: Vitaly Sazanovich
 * Date: 12/19/12
 * Time: 2:56 PM
 */
public class WerkwoordSoortComboBoxModel extends AbstractComboBoxModel {

    public WerkwoordSoortComboBoxModel() {
        super();
    }

    @Override
    protected String[] getKeys() {
        return new String[]{"ov", "intr", "refl", "ditr"};
    }
}
