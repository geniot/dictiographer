package com.dictiographer.view.dialogs.grammar.nl;

import entry.Grammar;
import com.dictiographer.view.Bindable;
import com.dictiographer.view.dialogs.grammar.GrammarDialog;

import javax.swing.*;

/**
 * Author: Vitaly Sazanovich
 * Email: vitaly.sazanovich@gmail.com
 * Date: 01/07/14
 */
public class GrammarDialogBNW extends GrammarDialog {
    public JTextField comparative;
    public JTextField superlative;
    public JCheckBox alleenAttributief;
    public JCheckBox alleenPredikatief;

    public GrammarDialogBNW(Bindable p, Grammar grammar, String cn) {
        super(null,ModalityType.APPLICATION_MODAL);
//        super(p, grammar, cn);
    }

    @Override
    public void setData(Object d) {
        if (d == null) return;
        Grammar data = (Grammar) d;


//        if (data.getComparative() != null && comparative != null) comparative.setText(data.getComparative());
//        if (data.getSuperlative() != null && superlative != null) superlative.setText(data.getSuperlative());
//        if (data.getAlleenAttributief() != null && alleenAttributief != null)
//            alleenAttributief.setSelected(data.getAlleenAttributief().booleanValue());
//        if (data.getAlleenPredikatief() != null && alleenPredikatief != null)
//            alleenPredikatief.setSelected(data.getAlleenPredikatief().booleanValue());
    }

    @Override
    public Object getData(Object d) {
        Grammar data = (Grammar) d;
        if (data == null) data = new Grammar();

//        if (comparative != null && !comparative.getText().trim().equals(""))
//            data.setComparative(comparative.getText().trim());
//        if (superlative != null && !superlative.getText().trim().equals(""))
//            data.setSuperlative(superlative.getText().trim());
//        if (alleenAttributief != null && alleenAttributief.isSelected()) data.setAlleenAttributief(true);
//        if (alleenPredikatief != null && alleenPredikatief.isSelected()) data.setAlleenPredikatief(true);


        return data;
    }

    @Override
    public JPanel getMainPanel() {
        return null;
    }
}
