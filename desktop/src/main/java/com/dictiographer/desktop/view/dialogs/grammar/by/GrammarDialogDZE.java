package com.dictiographer.desktop.view.dialogs.grammar.by;

import com.dictiographer.desktop.model.entry.Grammar;
import com.dictiographer.desktop.model.entry.grammar.by.GrammarDZE;
import com.dictiographer.desktop.presenter.Bindable;
import com.dictiographer.desktop.view.MySwingEngine;
import com.dictiographer.desktop.view.MyThreadLocal;
import com.dictiographer.desktop.view.dialogs.AbstractDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GrammarDialogDZE extends AbstractDialog {


    private GrammarDialogDZEPanel grammarDialogDZEPanel;

    public GrammarDialogDZE(Window view, ModalityType applicationModal, Bindable p, Grammar grammar) {
        super(view, applicationModal, p);
        setTitle(MyThreadLocal.get().getMessageSource().getString("title.grammar"));
        grammarDialogDZEPanel = new GrammarDialogDZEPanel();
        setContentPane(grammarDialogDZEPanel.mainPanel);
        pack();
        setLocationRelativeTo(view);

        if (grammar != null && grammar.getGrammarDZE() != null) {
            grammarDialogDZEPanel.setData(grammar.getGrammarDZE());
        }
    }


    public class GrammarDialogDZEPanel extends MySwingEngine {

        public JTextField base1;
        public JTextField base2;
        public JComboBox endings;

        public JCheckBox bfCheckBox;
        public JCheckBox ztCheckBox;
        public JCheckBox nztCheckBox;

        public JTextField presentSing1;
        public JTextField presentPl1;
        public JTextField presentSing2;
        public JTextField presentPl2;
        public JTextField presentSing3;
        public JTextField presentPl3;
        public JTextField pastSing1;
        public JTextField pastPl1;
        public JTextField pastSing2;
        public JTextField pastPl2;
        public JTextField pastSing3;
        public JTextField pastPl3;
        public JTextField imperSing;
        public JTextField imperPl;
        public JTextField finiteForm;
        public JTextField infiniteForm;
        public JTextField participle;

        public GrammarDialogDZEPanel() {
            init("descriptors/" + MyThreadLocal.get().getLocale().getLanguage() + "/GrammarDialogDZE.xml");
        }

        public Action saveAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                parent.setData(getData(null));
                GrammarDialogDZE.this.dispose();
            }
        };

        public Action cancelAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                GrammarDialogDZE.this.dispose();
            }
        };

        public Action propagateAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String[] ends = endings.getSelectedItem().toString().replaceAll("-", "").split(",|;");

                    JTextField[] fields1 = new JTextField[]{
                            presentSing1, presentPl1,
                            presentSing2, presentPl2,
                            presentSing3, presentPl3,
                    };

                    JTextField[] fields2 = new JTextField[]{
                            pastSing1, pastPl1,
                            pastSing2, pastPl2,
                            pastSing3, pastPl3,
                    };

                    for (int i = 0; i < fields1.length; i++) {
                        String ending = ends.length > i ? ends[i] : "";
                        fields1[i].setText(base1.getText() + ending);
                    }
                    for (int i = 0; i < fields2.length; i++) {
                        String ending = ends.length > i ? ends[fields1.length + i] : "";
                        fields2[i].setText(base2.getText() + ending);
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        };


        @Override
        public Object getData(Object d) {
            Grammar gr = (Grammar) d;
            if (gr == null) gr = new Grammar();
            GrammarDZE data = gr.getGrammarDZE();
            if (data == null) data = new GrammarDZE();
            gr.setGrammarDZE(data);

            set(bfCheckBox, data, "nonPersonalForm");
            set(ztCheckBox, data, "finite");
            set(nztCheckBox, data, "infinite");

            set(presentSing1, data, "presentSing1");
            set(presentPl1, data, "presentPl1");
            set(presentSing2, data, "presentSing2");
            set(presentPl2, data, "presentPl2");
            set(presentSing3, data, "presentSing3");
            set(presentPl3, data, "presentPl3");

            set(pastSing1, data, "pastSing1");
            set(pastPl1, data, "pastPl1");
            set(pastSing2, data, "pastSing2");
            set(pastPl2, data, "pastPl2");
            set(pastSing3, data, "pastSing3");
            set(pastPl3, data, "pastPl3");

            set(imperSing, data, "imperSing");
            set(imperPl, data, "imperPl");
            set(pastSing2, data, "pastSing2");
            set(finiteForm, data, "finiteForm");
            set(infiniteForm, data, "infiniteForm");
            set(participle, data, "participle");

            return gr;
        }

        @Override
        public void setData(Object d) {
            if (d == null) return;
            GrammarDZE data = (GrammarDZE) d;
            if (data == null) return;

            set(bfCheckBox, data.getNonPersonalForm());
            set(ztCheckBox, data.getFinite());
            set(nztCheckBox, data.getInfinite());

            set(presentSing1, data.getPresentSing1());
            set(presentPl1, data.getPresentPl1());
            set(presentSing2, data.getPresentSing2());
            set(presentPl2, data.getPresentPl2());
            set(presentSing3, data.getPresentSing3());
            set(presentPl3, data.getPresentPl3());

            set(pastSing1, data.getPastSing1());
            set(pastPl1, data.getPastPl1());
            set(pastSing2, data.getPastSing2());
            set(pastPl2, data.getPastPl2());
            set(pastSing3, data.getPastSing3());
            set(pastPl3, data.getPastPl3());

            set(imperSing, data.getImperSing());
            set(imperPl, data.getImperPl());
            set(finiteForm, data.getFiniteForm());
            set(infiniteForm, data.getInfiniteForm());
            set(participle, data.getParticiple());

        }
    }


}
