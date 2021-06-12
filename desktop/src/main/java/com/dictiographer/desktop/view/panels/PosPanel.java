package com.dictiographer.desktop.view.panels;

import com.dictiographer.desktop.model.entry.EntryDefinition;
import com.dictiographer.desktop.model.entry.Grammar;
import com.dictiographer.desktop.model.entry.PartOfSpeech;
import com.dictiographer.desktop.view.DnDTabbedPane;
import com.dictiographer.desktop.view.KeyValuePair;
import com.dictiographer.desktop.view.MySwingEngine;
import com.dictiographer.desktop.view.comboboxmodels.PosComboBoxModel;
import com.dictiographer.desktop.view.dialogs.grammar.GrammarDialogLauncher;
import com.dictiographer.shared.model.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class PosPanel extends MySwingEngine {

    public JTextField afleidingen;
    public JTextField pronunciation;
    public DnDTabbedPane entryDefinitions;
    public JComboBox posComboBox;

    public Grammar grammar;

    public PosPanel() {
        init("descriptors/PosPanel.xml");
        entryDefinitions.addNewTab(null);
    }

    @Override
    public void setData(Object d) {
        if (d == null) return;

        if (d instanceof Grammar) {
            grammar = ((Grammar) d).isEmpty() ? null : (Grammar) d;
            return;
        }

        if (d instanceof PartOfSpeech) {
            PartOfSpeech pos = (PartOfSpeech) d;
            if (pos.getAfleidingen() != null) afleidingen.setText(Utils.arr2str(pos.getAfleidingen()));
            if (pos.getPronunciation() != null) pronunciation.setText(pos.getPronunciation());
            grammar = pos.getGrammar();
            if (pos.getGrammar() != null) {
                PosComboBoxModel model = (PosComboBoxModel) posComboBox.getModel();
                posComboBox.setSelectedItem(model.get(pos.getGrammar().getPosKey()));
            }
            //definitions
            if (pos.getEntryDefinitions() != null) {
                for (int i = 0; i < pos.getEntryDefinitions().length; i++) {
                    Component c = entryDefinitions.getComponentAt(i);
                    if (c != null && c instanceof FormContentPanel) {
                        FormContentPanel form = (FormContentPanel) c;
                        DefPanel defForm = (DefPanel) form.getForm();
                        defForm.setData(pos.getEntryDefinitions()[i]);
                    } else {
                        entryDefinitions.addNewTab(pos.getEntryDefinitions()[i]);
                    }
                }
            }

            entryDefinitions.setSelectedIndex(0);
        }
    }

    @Override
    public Object getData(Object d) {
        PartOfSpeech pos = (PartOfSpeech) d;

        if (!afleidingen.getText().trim().equals("")) pos.setAfleidingen(afleidingen.getText().trim().split(","));
        if (!pronunciation.getText().trim().equals("")) pos.setPronunciation(pronunciation.getText().trim());

        if (grammar == null) grammar = new Grammar();
        grammar.setPosKey(((KeyValuePair) posComboBox.getSelectedItem()).getKey());
        pos.setGrammar(grammar);

        ArrayList<EntryDefinition> eds = new ArrayList();
        for (int i = 0; i < entryDefinitions.getTabCount() - 1; i++) {
            Component c = entryDefinitions.getComponentAt(i);
            if (c != null && c instanceof FormContentPanel) {
                FormContentPanel form = (FormContentPanel) c;
                DefPanel defForm = (DefPanel) form.getForm();
                if (defForm.isEmpty()) continue;
                EntryDefinition ed = new EntryDefinition();
                defForm.getData(ed);
                eds.add(ed);
            }
        }
        pos.setEntryDefinitions(eds.isEmpty() ? null : eds.toArray(new EntryDefinition[eds.size()]));
        return d;
    }


    @Override
    public boolean isEmpty() {
        if (grammar != null) return false;
        if (!afleidingen.getText().trim().equals("")) return false;
        if (!pronunciation.getText().trim().equals("")) return false;
        for (int i = 0; i < entryDefinitions.getTabCount() - 1; i++) {
            Component c = entryDefinitions.getComponentAt(i);
            if (c != null && c instanceof FormContentPanel) {
                FormContentPanel form = (FormContentPanel) c;
                DefPanel defPanel = (DefPanel) form.getForm();
                if (!defPanel.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Action grammarAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            try {
                new GrammarDialogLauncher(
                        getClosestWindow(getMainPanel()),
                        PosPanel.this, grammar,
                        ((KeyValuePair) getPosComboBox().getSelectedItem()).getKey());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    };
}
