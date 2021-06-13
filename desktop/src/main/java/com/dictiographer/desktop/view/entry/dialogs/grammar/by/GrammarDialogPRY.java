package com.dictiographer.desktop.view.entry.dialogs.grammar.by;

import com.dictiographer.desktop.model.entry.Grammar;
import com.dictiographer.desktop.model.entry.grammar.by.GrammarPRY;
import com.dictiographer.desktop.presenter.Bindable;
import com.dictiographer.desktop.view.entry.MySwingEngine;
import com.dictiographer.desktop.view.entry.MyThreadLocal;
import com.dictiographer.desktop.view.entry.dialogs.AbstractDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;

public class GrammarDialogPRY extends AbstractDialog {

    private GrammarDialogPRYPanel grammarDialogPRYPanel;

    public GrammarDialogPRY(Window view, ModalityType applicationModal, Bindable p, Grammar grammar) {
        super(view, applicationModal, p);

        setTitle(MyThreadLocal.get().getMessageSource().getString("title.grammar"));
        grammarDialogPRYPanel = new GrammarDialogPRYPanel();
        setContentPane(grammarDialogPRYPanel.mainPanel);
        pack();
        setLocationRelativeTo(view);

        if (grammar != null && grammar.getGrammarPRY() != null) {
            grammarDialogPRYPanel.setData(grammar.getGrammarPRY());
        }
    }


    public class GrammarDialogPRYPanel extends MySwingEngine {
        public JTextField singularMN;
        public JTextField singularZN;
        public JTextField singularNN;

        public JTextField singularMR;
        public JTextField singularZR;
        public JTextField singularNR;

        public JTextField singularMD;
        public JTextField singularZD;
        public JTextField singularND;

        public JTextField singularMV;
        public JTextField singularZV;
        public JTextField singularNV;

        public JTextField singularMT;
        public JTextField singularZT;
        public JTextField singularNT;

        public JTextField singularMM;
        public JTextField singularZM;
        public JTextField singularNM;


        public JTextField pluralMN;
        public JTextField pluralZN;
        public JTextField pluralNN;

        public JTextField pluralMR;
        public JTextField pluralZR;
        public JTextField pluralNR;

        public JTextField pluralMD;
        public JTextField pluralZD;
        public JTextField pluralND;

        public JTextField pluralMV;
        public JTextField pluralZV;
        public JTextField pluralNV;

        public JTextField pluralMT;
        public JTextField pluralZT;
        public JTextField pluralNT;

        public JTextField pluralMM;
        public JTextField pluralZM;
        public JTextField pluralNM;

        public JTextField base;
        public JComboBox endings;


        public GrammarDialogPRYPanel() {
            init("descriptors/" + MyThreadLocal.get().getLocale().getLanguage() + "/GrammarDialogPRY.xml");
        }

        public Action saveAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                parent.setData(getData(null));
                GrammarDialogPRY.this.dispose();
            }
        };

        public Action cancelAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                GrammarDialogPRY.this.dispose();
            }
        };

        public Action propagateAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String[] ends = endings.getSelectedItem().toString().replaceAll("-", "").split(",|;");

                    JTextField[] fields1 = new JTextField[]{
                            singularMN, singularZN, singularNN,
                            singularMR, singularZR, singularNR,
                            singularMD, singularZD, singularND,
                            singularMV, singularZV, singularNV,
                            singularMT, singularZT, singularNT,
                            singularMM, singularZM, singularNM,

                            pluralMN, pluralZN, pluralNN,
                            pluralMR, pluralZR, pluralNR,
                            pluralMD, pluralZD, pluralND,
                            pluralMV, pluralZV, pluralNV,
                            pluralMT, pluralZT, pluralNT,
                            pluralMM, pluralZM, pluralNM,

                    };


                    for (int i = 0; i < fields1.length; i++) {
                        String ending = ends.length > i ? ends[i] : "";
                        fields1[i].setText(base.getText() + ending);
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
            GrammarPRY data = gr.getGrammarPRY();
            if (data == null) data = new GrammarPRY();
            gr.setGrammarPRY(data);

            Field[] fields = this.getClass().getDeclaredFields();
            for (Field f : fields) {
                try {
                    if (f.getType().equals(JTextField.class)) {
                        JTextField textField = (JTextField) f.get(this);
                        set(textField, data, f.getName());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            return gr;
        }

        @Override
        public void setData(Object d) {
            if (d == null) return;
            GrammarPRY data = (GrammarPRY) d;
            if (data == null) return;


            Field[] fields = this.getClass().getDeclaredFields();
            for (Field f : fields) {
                try {
                    if (f.getType().equals(JTextField.class)) {
                        String fn = f.getName();
                        JTextField textField = (JTextField) f.get(this);
                        Field fi = null;
                        try {
                            fi = data.getClass().getDeclaredField(fn);
                        } catch (NoSuchFieldException nsf) {
                            continue;
                        }
                        fi.setAccessible(true);
                        String val = (String) fi.get(data);
                        set(textField, val);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }


    }

}
