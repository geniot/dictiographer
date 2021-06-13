package com.dictiographer.desktop.view.entry.dialogs.grammar.nl;

import com.dictiographer.desktop.model.entry.Grammar;
import com.dictiographer.desktop.presenter.Bindable;
import com.dictiographer.desktop.view.entry.MySwingEngine;
import com.dictiographer.desktop.view.entry.MyThreadLocal;
import com.dictiographer.desktop.view.entry.dialogs.AbstractDialog;

import javax.swing.*;
import java.awt.*;

public class GrammarDialogZNW extends AbstractDialog {

    private GrammarDialogZNWPanel grammarDialogZNWPanel;

    public GrammarDialogZNW(Window view, ModalityType applicationModal, Bindable p, Grammar grammar) {
        super(view, applicationModal, p);
        setTitle(MyThreadLocal.get().getMessageSource().getString("title.grammar"));
        grammarDialogZNWPanel = new GrammarDialogZNWPanel();
        setContentPane(grammarDialogZNWPanel.mainPanel);

        if (grammar != null && grammar.getGrammarZNW() != null) {
            grammarDialogZNWPanel.setData(grammar.getGrammarZNW());
        }
    }

    public class GrammarDialogZNWPanel extends MySwingEngine {
        public JCheckBox mCheckBox;
        public JCheckBox mvCheckBox;
        public JCheckBox vCheckBox;
        public JCheckBox oCheckBox;

        public JTextField plural;

        public JCheckBox alleenEnkel;
        public JCheckBox alleenMeer;


        @Override
        public void setData(Object d) {
//        if (d == null) return;
//        Grammar data = (Grammar) d;
//        if (data.getGenderKey() != null) {
//            List<String> s = Arrays.asList(data.getGenderKey().split(","));
//            if (mCheckBox != null) mCheckBox.setSelected(data.getGenderKey().equals("m") || s.contains("m"));
//            if (mvCheckBox != null) mvCheckBox.setSelected(data.getGenderKey().equals("mv") || s.contains("mv"));
//            if (vCheckBox != null) vCheckBox.setSelected(data.getGenderKey().equals("v") || s.contains("v"));
//            if (oCheckBox != null) oCheckBox.setSelected(data.getGenderKey().equals("o") || s.contains("o"));
//        }
//
//        if (data.getAlleenEnkel() != null && alleenEnkel != null)
//            alleenEnkel.setSelected(data.getAlleenEnkel().booleanValue());
//        if (data.getAlleenMeer() != null && alleenMeer != null)
//            alleenMeer.setSelected(data.getAlleenMeer().booleanValue());
        }

        @Override
        public Object getData(Object d) {
//        Grammar data = (Grammar) d;
//        if (data == null) data = new Grammar();
//
//        StringBuffer sb = new StringBuffer();
//        if (isChildOf(mCheckBox, getCurrentCard(cardPanel))) {
//            if (mCheckBox != null && mCheckBox.isSelected()) sb.append("m,");
//            if (mvCheckBox != null && mvCheckBox.isSelected()) sb.append("mv,");
//            if (vCheckBox != null && vCheckBox.isSelected()) sb.append("v,");
//            if (oCheckBox != null && oCheckBox.isSelected()) sb.append("o");
//        }
//
//
//        if (alleenEnkel != null && alleenEnkel.isSelected()) data.setAlleenEnkel(true);
//        if (alleenMeer != null && alleenMeer.isSelected()) data.setAlleenMeer(true);
//
//        String res = sb.toString();
//        if (res.endsWith(",")) res = res.substring(0, res.length() - 1);
//        if (res.length() > 0) data.setGenderKey(res);
//
//        return data;
            return null;
        }
    }

}
