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
public class GrammarDialogVNW extends AbstractDialog {
    private GrammarDialogVNWPanel grammarDialogVNWPanel;

    public GrammarDialogVNW(Window view, ModalityType applicationModal, Bindable p, Grammar grammar) {
        super(view, applicationModal, p);
        setTitle(MyThreadLocal.get().getMessageSource().getMessage("title.grammar", null, MyThreadLocal.get().getLocale()));
        grammarDialogVNWPanel = new GrammarDialogVNWPanel();
        setContentPane(grammarDialogVNWPanel.mainPanel);

        if (grammar != null && grammar.getGrammarBNW() != null) {
            grammarDialogVNWPanel.setData(grammar.getGrammarBNW());
        }
    }

    public class GrammarDialogVNWPanel extends MySwingEngine {
        public JComboBox vnwKey;


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

    }
}

