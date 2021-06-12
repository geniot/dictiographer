package com.dictiographer.desktop.view.dialogs.grammar.by;

import com.dictiographer.desktop.model.entry.Grammar;
import com.dictiographer.desktop.model.entry.grammar.by.GrammarNAZ;
import com.dictiographer.desktop.presenter.Bindable;
import com.dictiographer.desktop.view.MySwingEngine;
import com.dictiographer.desktop.view.MyThreadLocal;
import com.dictiographer.desktop.view.dialogs.AbstractDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;

public class GrammarDialogNAZ extends AbstractDialog {

    private GrammarDialogNAZPanel grammarDialogNAZPanel;

    public GrammarDialogNAZ(Window view, ModalityType applicationModal, Bindable p, Grammar grammar) {
        super(view, applicationModal, p);

        setTitle(MyThreadLocal.get().getMessageSource().getString("title.grammar"));
        grammarDialogNAZPanel = new GrammarDialogNAZPanel();
        setContentPane(grammarDialogNAZPanel.mainPanel);
        pack();
        setLocationRelativeTo(view);

        if (grammar != null && grammar.getGrammarNAZ() != null) {
            grammarDialogNAZPanel.setData(grammar.getGrammarNAZ());
        }
    }

    public class GrammarDialogNAZPanel extends MySwingEngine {
        public JCheckBox genderMaleCheckBox;
        public JCheckBox genderFemaleCheckBox;
        public JCheckBox genderNeutralCheckBox;

        public JCheckBox onlySingularCheckBox;
        public JCheckBox onlyPluralCheckBox;

        public JTextField base;
        public JComboBox endings;

        public JTextField singular;
        public JTextField plural;
        public JTextField singularR;
        public JTextField pluralR;
        public JTextField singularD;
        public JTextField pluralD;
        public JTextField singularV;
        public JTextField pluralV;
        public JTextField singularT;
        public JTextField pluralT;
        public JTextField singularM;
        public JTextField pluralM;

        public GrammarDialogNAZPanel() {
            init("descriptors/" + MyThreadLocal.get().getLocale().getLanguage() + "/GrammarDialogNAZ.xml");
        }

        public Action saveAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                parent.setData(getData(null));
                GrammarDialogNAZ.this.dispose();
            }
        };

        public Action cancelAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                GrammarDialogNAZ.this.dispose();
            }
        };

        public Action propagateAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String[] ends = endings.getSelectedItem().toString().replaceAll("-", "").split(",|;");

                    JTextField[] fieldSingular = new JTextField[]{singular, singularR, singularD, singularV, singularT, singularM};
                    JTextField[] fieldsPlural = new JTextField[]{plural, pluralR, pluralD, pluralV, pluralT, pluralM};

                    boolean onlyPlural = onlySingularCheckBox != null && !onlySingularCheckBox.isSelected();
                    boolean onlySingular = onlyPluralCheckBox != null && !onlyPluralCheckBox.isSelected();

                    for (int i = 0; i < fieldSingular.length; i++) {
                        fieldSingular[i].setText(onlyPlural ? base.getText() + ends[i] : "-");
                    }

                    for (int i = 0; i < fieldsPlural.length; i++) {
                        fieldsPlural[i].setText(onlySingular ? base.getText() + ends[fieldSingular.length + i] : "-");
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
            GrammarNAZ data = gr.getGrammarNAZ();
            if (data == null) data = new GrammarNAZ();
            gr.setGrammarNAZ(data);


            set(singular, data, "singular");
            set(plural, data, "plural");
            set(singularR, data, "singularR");
            set(pluralR, data, "pluralR");
            set(singularD, data, "singularD");
            set(pluralD, data, "pluralD");
            set(singularV, data, "singularV");
            set(pluralV, data, "pluralV");
            set(singularT, data, "singularT");
            set(pluralT, data, "pluralT");
            set(singularM, data, "singularM");
            set(pluralM, data, "pluralM");

            set(onlySingularCheckBox, data, "onlySingular");
            set(onlyPluralCheckBox, data, "onlyPlural");

            StringBuffer sb = new StringBuffer();
            if (genderMaleCheckBox != null && genderMaleCheckBox.isSelected()) sb.append("m,");
            if (genderFemaleCheckBox != null && genderFemaleCheckBox.isSelected()) sb.append("v,");
            if (genderNeutralCheckBox != null && genderNeutralCheckBox.isSelected()) sb.append("o");

            String res = sb.toString();
            if (res.endsWith(",")) res = res.substring(0, res.length() - 1);
            if (res.length() > 0) data.setGenderKey(res);

            return gr;
        }

        @Override
        public void setData(Object d) {
            if (d == null) return;
            GrammarNAZ data = (GrammarNAZ) d;
            if (data == null) return;


            if (data.getGenderKey() != null) {
                List<String> s = Arrays.asList(data.getGenderKey().split(","));
                if (genderMaleCheckBox != null)
                    genderMaleCheckBox.setSelected(data.getGenderKey().equals("m") || s.contains("m"));
                if (genderFemaleCheckBox != null)
                    genderFemaleCheckBox.setSelected(data.getGenderKey().equals("v") || s.contains("v"));
                if (genderNeutralCheckBox != null)
                    genderNeutralCheckBox.setSelected(data.getGenderKey().equals("o") || s.contains("o"));
            }

            set(singular, data.getSingular());
            set(plural, data.getPlural());
            set(singularR, data.getSingularR());
            set(pluralR, data.getPluralR());
            set(singularD, data.getSingularD());
            set(pluralD, data.getPluralD());
            set(singularV, data.getSingularV());
            set(pluralV, data.getPluralV());
            set(singularT, data.getSingularT());
            set(pluralT, data.getPluralT());
            set(singularM, data.getSingularM());
            set(pluralM, data.getPluralM());

            set(onlySingularCheckBox, data.getOnlySingular());
            set(onlyPluralCheckBox, data.getOnlyPlural());

        }
    }


}
