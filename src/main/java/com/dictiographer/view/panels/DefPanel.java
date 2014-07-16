package com.dictiographer.view.panels;

import com.dictiographer.utils.DictiographerUtils;
import com.dictiographer.view.Bindable;
import com.dictiographer.view.KeyValuePair;
import com.dictiographer.view.MySwingEngine;
import com.dictiographer.view.dialogs.ExamplesDialog;
import com.dictiographer.view.dialogs.IdiomenDialog;
import com.dictiographer.view.dialogs.grammar.GrammarDialogLauncher;
import entry.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;

/**
 * User: Vitaly Sazanovich
 * Date: 12/18/12
 * Time: 11:09 PM
 */
public class DefPanel extends MySwingEngine implements Bindable {
    public JTextArea definition;
    public JTextField meta;
    public JTextField hyperoniems;
    public JTextField hyponiems;
    public JTextField synonyms;
    public JTextField antoniems;
    public JTextField zie;

    public Container container;
    public JPanel mainPanel;

    public Translation[] translations;
    public Example[] examples;
    public Idioom[] idioms;
    public Grammar grammar;
    public EntryImage[] images;

    public JComboBox posComboBox;


    public DefPanel() {
        super("descriptors/DefPanel.xml");
    }

    @Override
    public void setData(Object d) {
        if (d == null) return;
//        super.setData(d);

        if (d instanceof EntryDefinition) {
            EntryDefinition ed = (EntryDefinition) d;
            translations = ed.getTranslations();
            images = ed.getImages();
            examples = ed.getExamples();
            idioms = ed.getIdioms();
            grammar = ed.getGrammar();
            if (ed.getDefinition() != null) definition.setText(ed.getDefinition());
            if (ed.getMeta() != null) meta.setText(ed.getMeta());
            if (ed.getHyperoniems() != null) hyperoniems.setText(DictiographerUtils.links2str(ed.getHyperoniems()));
            if (ed.getHyponiems() != null) hyponiems.setText(DictiographerUtils.links2str(ed.getHyponiems()));
            if (ed.getSynonyms() != null) synonyms.setText(DictiographerUtils.links2str(ed.getSynonyms()));
            if (ed.getAntoniems() != null) antoniems.setText(DictiographerUtils.links2str(ed.getAntoniems()));
            if (ed.getZie() != null) zie.setText(DictiographerUtils.links2str(ed.getZie()));
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
            ed.setHyperoniems(DictiographerUtils.str2links(hyperoniems.getText().trim()));
        if (!hyponiems.getText().equals("")) ed.setHyponiems(DictiographerUtils.str2links(hyponiems.getText().trim()));
        if (!synonyms.getText().equals("")) ed.setSynonyms(DictiographerUtils.str2links(synonyms.getText().trim()));
        if (!antoniems.getText().equals("")) ed.setAntoniems(DictiographerUtils.str2links(antoniems.getText().trim()));
        if (!zie.getText().equals("")) ed.setZie(DictiographerUtils.str2links(zie.getText().trim()));
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

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
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
//                new TranslationDialog(DefPanel.this, translations);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    };
    public Action imageAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            try {
//                new ImageDialog(DefPanel.this, images);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    };
    public Action examplesAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            try {
                new ExamplesDialog(getClosestWindow(container), DefPanel.this, examples);
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
                new GrammarDialogLauncher(DefPanel.this, grammar, ((KeyValuePair) getPosComboBox().getSelectedItem()).getKey());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    };


    private JComboBox getPosComboBox() throws Exception {
        if (posComboBox != null) {
            return posComboBox;
        }
        Container c = container;
        while (c != null) {
            try {
                c = c.getParent();
                if (c == null) break;
                if (c instanceof FormContentPanel) {
                    DefPanel tmp = (DefPanel) ((FormContentPanel) c).getForm();
                    JComboBox f = (JComboBox) getInheritedPrivateFieldValue(tmp, tmp.getClass(), "posComboBox");
                    if (f != null) {
                        return f;
                    }
                } else {
                    Field f = c.getClass().getDeclaredField("posComboBox");
                    if (f.get(c) != null) {
                        return (JComboBox) f.get(c);
                    }
                }
            } catch (NoSuchFieldException e) {
            }
        }
        throw new Exception("Couldn't find posComboBox in the hierarchy");
    }

    private Object getInheritedPrivateFieldValue(Object thisObj, Class<?> type, String fieldName) throws IllegalAccessException {
        Class<?> i = type;
        while (i != null && i != Object.class) {
            for (Field field : i.getDeclaredFields()) {
                if (!field.isSynthetic() && field.getName().equals(fieldName)) {
                    field.setAccessible(true);
                    return field.get(thisObj);
                }
            }
            i = i.getSuperclass();
        }

        return null;
    }
}
