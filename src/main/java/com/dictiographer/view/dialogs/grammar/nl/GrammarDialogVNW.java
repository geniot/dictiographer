package com.dictiographer.view.dialogs.grammar.nl;

import entry.Grammar;
import com.dictiographer.view.Bindable;
import com.dictiographer.view.KeyValuePair;
import com.dictiographer.view.comboboxmodels.VoornaamwoordSoortComboBoxModel;
import com.dictiographer.view.dialogs.grammar.GrammarDialog;

import javax.swing.*;

/**
 * Author: Vitaly Sazanovich
 * Email: vitaly.sazanovich@gmail.com
 * Date: 01/07/14
 */
public class GrammarDialogVNW extends GrammarDialog {

    public JComboBox vnwKey;

    public GrammarDialogVNW(Bindable p, Grammar grammar, String cn) {
        super(null,ModalityType.APPLICATION_MODAL);
//        super(p, grammar, cn);
    }

    @Override
    public void setData(Object d) {
        if (d == null) return;
        Grammar data = (Grammar) d;


//        if (data.getVnwKey() != null && vnwKey != null)
//            vnwKey.setSelectedItem(((VoornaamwoordSoortComboBoxModel) vnwKey.getModel()).get(data.getVnwKey()));
    }


    @Override
    public Object getData(Object d) {
        Grammar data = (Grammar) d;
        if (data == null) data = new Grammar();

//        if (vnwKey != null) data.setVnwKey(((KeyValuePair) vnwKey.getSelectedItem()).getKey());

        return data;
    }

    @Override
    public JPanel getMainPanel() {
        return null;
    }
}

