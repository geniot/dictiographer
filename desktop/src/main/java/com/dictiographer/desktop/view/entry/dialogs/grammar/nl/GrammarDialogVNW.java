package com.dictiographer.desktop.view.entry.dialogs.grammar.nl;

import com.dictiographer.desktop.model.entry.Grammar;
import com.dictiographer.desktop.presenter.Bindable;
import com.dictiographer.desktop.view.entry.MySwingEngine;
import com.dictiographer.desktop.view.entry.MyThreadLocal;
import com.dictiographer.desktop.view.entry.dialogs.AbstractDialog;

import javax.swing.*;
import java.awt.*;

public class GrammarDialogVNW extends AbstractDialog {
    private GrammarDialogVNWPanel grammarDialogVNWPanel;

    public GrammarDialogVNW(Window view, ModalityType applicationModal, Bindable p, Grammar grammar) {
        super(view, applicationModal, p);
        setTitle(MyThreadLocal.get().getMessageSource().getString("title.grammar"));
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

