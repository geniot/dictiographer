package com.dictiographer.view.comboboxmodels;

/**
 * Author: Vitaly Sazanovich
 * Email: Vitaly.Sazanovich@gmail.com
 * Date: 6/27/14
 */
public class EndingsComboBoxModel extends AbstractComboBoxModel {

    public EndingsComboBoxModel() {
        super();
    }

    @Override
    protected String[] getKeys() {
        return new String[]{"konchatki1","konchatki2","konchatki3", "konchatki4","konchatki5"};
    }
}
