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
public class GrammarDialogWEW extends AbstractDialog {

    private GrammarDialogWEWPanel grammarDialogWEWPanel;

    public GrammarDialogWEW(Window view, ModalityType applicationModal, Bindable p, Grammar grammar) {
        super(view, applicationModal, p);
        setTitle(MyThreadLocal.get().getMessageSource().getMessage("title.grammar", null, MyThreadLocal.get().getLocale()));
        grammarDialogWEWPanel = new GrammarDialogWEWPanel();
        setContentPane(grammarDialogWEWPanel.mainPanel);

        if (grammar != null && grammar.getGrammarWEW() != null) {
            grammarDialogWEWPanel.setData(grammar.getGrammarWEW());
        }
    }

    public class GrammarDialogWEWPanel extends MySwingEngine {
        public JComboBox verbType;
        public JComboBox helpVerb;
        public JTextField verleden;
        public JTextField voltooid;
        public JCheckBox ookAbsoluut;


        @Override
        public void setData(Object d) {
            if (d == null) return;
            Grammar data = (Grammar) d;

//        if (data.getVerbType() != null && verbType != null)
//            verbType.setSelectedItem(((WerkwoordSoortComboBoxModel) verbType.getModel()).get(data.getVerbType()));
//        if (data.getVerleden() != null && verleden != null) verleden.setText(data.getVerleden());
//        if (data.getHelpVerb() != null && helpVerb != null)
//            helpVerb.setSelectedItem(((HelpWerkwoordComboBoxModel) helpVerb.getModel()).get(data.getHelpVerb()));
//        if (data.getVoltooid() != null && voltooid != null) voltooid.setText(data.getVoltooid());
//        if (data.getOokAbsoluut() != null && ookAbsoluut != null)
//            ookAbsoluut.setSelected(data.getOokAbsoluut().booleanValue());

        }


        @Override
        public Object getData(Object d) {
            Grammar data = (Grammar) d;
            if (data == null) data = new Grammar();

//        if (verbType != null) data.setVerbType(((KeyValuePair) verbType.getSelectedItem()).getKey());
//        if (verleden != null && !verleden.getText().trim().equals("")) data.setVerleden(verleden.getText().trim());
//        if (helpVerb != null) data.setHelpVerb(((KeyValuePair) helpVerb.getSelectedItem()).getKey());
//        if (voltooid != null && !voltooid.getText().trim().equals("")) data.setVoltooid(voltooid.getText().trim());
//        if (ookAbsoluut != null && ookAbsoluut.isSelected()) data.setOokAbsoluut(true);


            return data;
        }
    }


}
