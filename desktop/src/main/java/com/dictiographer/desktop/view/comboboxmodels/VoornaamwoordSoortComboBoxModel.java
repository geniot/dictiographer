package com.dictiographer.desktop.view.comboboxmodels;

public class VoornaamwoordSoortComboBoxModel extends AbstractComboBoxModel {

    public VoornaamwoordSoortComboBoxModel() {
        super();
    }

    @Override
    protected String[] getKeys() {
        return new String[]{"aan", "bet", "bez", "onb","pers","wed","vra"};
    }
}
