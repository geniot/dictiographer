package com.dictiographer.desktop.view.entry.panels;

import com.dictiographer.desktop.model.entry.*;
import com.dictiographer.desktop.presenter.DesktopUtils;
import com.dictiographer.desktop.view.entry.KeyValuePair;
import com.dictiographer.desktop.view.entry.MySwingEngine;
import com.dictiographer.desktop.view.entry.dialogs.ExamplesDialog;
import com.dictiographer.desktop.view.entry.dialogs.IdiomenDialog;
import com.dictiographer.desktop.view.entry.dialogs.ImageDialog;
import com.dictiographer.desktop.view.entry.dialogs.TranslationDialog;
import com.dictiographer.desktop.view.entry.dialogs.grammar.GrammarDialogLauncher;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DefPanel extends MySwingEngine {

    public JTextArea definition;
    public JTextField meta;
    public JTextField hyperoniems;
    public JTextField hyponiems;
    public JTextField synonyms;
    public JTextField antoniems;
    public JTextField zie;
    public JComboBox posComboBox;

    public Translation[] translations;
    public Example[] examples;
    public Idioom[] idioms;
    public Grammar grammar;
    public EntryImage[] images;

    public DefPanel() {
        init("descriptors/DefPanel.xml");
    }

    @Override
    public void setData(Object d) {
        if (d == null) return;

        if (d instanceof Translation[]) {
            translations = ((Translation[]) d).length == 0 ? null : (Translation[]) d;
            return;
        }

        if (d instanceof Example[]) {
            examples = ((Example[]) d).length == 0 ? null : (Example[]) d;
            return;
        }

        if (d instanceof Idioom[]) {
            idioms = ((Idioom[]) d).length == 0 ? null : (Idioom[]) d;
            return;
        }

        if (d instanceof Grammar) {
            grammar = (Grammar) d;
            return;
        }

        if (d instanceof EntryImage[]) {
            images = ((EntryImage[]) d).length == 0 ? null : (EntryImage[]) d;
            return;
        }

        if (d instanceof EntryDefinition) {
            EntryDefinition ed = (EntryDefinition) d;
            translations = ed.getTranslations();
            images = ed.getImages();
            examples = ed.getExamples();
            idioms = ed.getIdioms();
            grammar = ed.getGrammar();
            if (ed.getDefinition() != null) definition.setText(ed.getDefinition());
            if (ed.getMeta() != null) meta.setText(ed.getMeta());
            if (ed.getHyperoniems() != null) hyperoniems.setText(DesktopUtils.links2str(ed.getHyperoniems()));
            if (ed.getHyponiems() != null) hyponiems.setText(DesktopUtils.links2str(ed.getHyponiems()));
            if (ed.getSynonyms() != null) synonyms.setText(DesktopUtils.links2str(ed.getSynonyms()));
            if (ed.getAntoniems() != null) antoniems.setText(DesktopUtils.links2str(ed.getAntoniems()));
            if (ed.getZie() != null) zie.setText(DesktopUtils.links2str(ed.getZie()));
        }
    }

    @Override
    public Object getData(Object d) {
        EntryDefinition ed = (EntryDefinition) d;
        if (translations != null) ed.setTranslations(translations);
        if (images != null) ed.setImages(images);
        if (examples != null) ed.setExamples(examples);
        if (idioms != null) ed.setIdioms(idioms);
        if (grammar != null) ed.setGrammar(grammar);

        if (!definition.getText().equals("")) ed.setDefinition(definition.getText().trim());
        if (!meta.getText().equals("")) ed.setMeta(meta.getText().trim());
        if (!hyperoniems.getText().equals(""))
            ed.setHyperoniems(DesktopUtils.str2links(hyperoniems.getText().trim()));
        if (!hyponiems.getText().equals("")) ed.setHyponiems(DesktopUtils.str2links(hyponiems.getText().trim()));
        if (!synonyms.getText().equals("")) ed.setSynonyms(DesktopUtils.str2links(synonyms.getText().trim()));
        if (!antoniems.getText().equals("")) ed.setAntoniems(DesktopUtils.str2links(antoniems.getText().trim()));
        if (!zie.getText().equals("")) ed.setZie(DesktopUtils.str2links(zie.getText().trim()));
        return ed;
    }

    @Override
    public boolean isEmpty() {
        if (examples != null) return false;
        if (images != null) return false;
        if (idioms != null) return false;
        if (translations != null) return false;
        if (grammar != null) return false;
        if (!definition.getText().trim().equals("")) return false;
        if (!meta.getText().trim().equals("")) return false;
        if (!hyperoniems.getText().trim().equals("")) return false;
        if (!hyponiems.getText().trim().equals("")) return false;
        if (!synonyms.getText().trim().equals("")) return false;
        if (!antoniems.getText().trim().equals("")) return false;
        if (!zie.getText().trim().equals("")) return false;
        return true;
    }


    public Action cancelAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            if (container instanceof JDialog) {
                ((JDialog) container).dispose();
            }
        }
    };

    public Action translationAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            try {
                TranslationDialog td = new TranslationDialog(getClosestWindow(container), DefPanel.this, translations);
                td.setVisible(true);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    };
    public Action imageAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            try {
                ImageDialog id = new ImageDialog(getClosestWindow(container), DefPanel.this, images);
                id.setVisible(true);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    };
    public Action examplesAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            try {
                ExamplesDialog ed = new ExamplesDialog(getClosestWindow(container), DefPanel.this, examples);
                ed.setVisible(true);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    };
    public Action idiomsAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            try {
                IdiomenDialog id = new IdiomenDialog(getClosestWindow(container), DefPanel.this, idioms);
                id.setVisible(true);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    };


    public Action grammarAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            try {
                new GrammarDialogLauncher(
                        getClosestWindow(getMainPanel()),
                        DefPanel.this, grammar,
                        ((KeyValuePair) getPosComboBox().getSelectedItem()).getKey());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    };


}
