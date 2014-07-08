package com.dictiographer.view.comboboxmodels;

/**
 * User: Vitaly Sazanovich
 * Date: 1/6/13
 * Time: 12:44 PM
 */
public class VoornaamwoordSoortComboBoxModel extends AbstractComboBoxModel {

    public VoornaamwoordSoortComboBoxModel() {
        super();
    }

    @Override
    protected String[] getKeys() {
        return new String[]{"aan", "bet", "bez", "onb","pers","wed","vra"};
    }
}

