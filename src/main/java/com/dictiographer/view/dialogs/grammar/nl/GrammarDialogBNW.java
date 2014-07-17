package com.dictiographer.view.dialogs.grammar.nl;

import com.dictiographer.view.Bindable;
import com.dictiographer.view.MySwingEngine;
import com.dictiographer.view.MyThreadLocal;
import com.dictiographer.view.dialogs.AbstractDialog;
import entry.Grammar;

import javax.swing.*;
import java.awt.*;

/**
 * Author: Vitaly Sazanovich
 * Email: vitaly.sazanovich@gmail.com
 * Date: 01/07/14
 */
public class GrammarDialogBNW extends AbstractDialog {

    private GrammarDialogBNWPanel grammarDialogBNWPanel;

    public GrammarDialogBNW(Window view, ModalityType applicationModal, Bindable p, Grammar grammar) {
        super(view, applicationModal, p);
        setTitle(MyThreadLocal.get().getMessageSource().getMessage("title.grammar", null, MyThreadLocal.get().getLocale()));
        grammarDialogBNWPanel = new GrammarDialogBNWPanel();
        setContentPane(grammarDialogBNWPanel.mainPanel);

        if (grammar != null && grammar.getGrammarBNW() != null) {
            grammarDialogBNWPanel.setData(grammar.getGrammarBNW());
        }
    }

    public class GrammarDialogBNWPanel extends MySwingEngine {
        public JTextField comparative;
        public JTextField superlative;
        public JCheckBox alleenAttributief;
        public JCheckBox alleenPredikatief;

        public GrammarDialogBNWPanel() {
            init("descriptors/GrammarDialogBNW.xml");
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
    }


}
