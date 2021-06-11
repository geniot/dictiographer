package com.dictiographer.desktop.presenter;

import com.dictiographer.desktop.model.*;
import com.dictiographer.desktop.view.DictionaryDialog;
import com.dictiographer.desktop.view.JTextFieldLimit;
import com.dictiographer.desktop.view.View;
import com.dictiographer.shared.model.IDictionary;
import com.dictiographer.shared.model.ZipDictionary;
import org.apache.commons.io.IOUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class DictionaryHandler extends BaseHandler {
    private DictionaryDialog dialog;

    public DictionaryHandler(Model m, View v, Presenter p) {
        super(m, v, p);
    }

    public void handle() {
        dialog = new DictionaryDialog();
        dialog.setModal(true);
        dialog.setTitle(model.resourceBundle.getString("title"));
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.getRootPane().setDefaultButton(dialog.buttonOK);
        dialog.setContentPane(dialog.$$$getRootComponent$$$());
        //todo location and size from settings
        dialog.setSize(430, 430);
        dialog.setLocationRelativeTo(view);

        dialog.nameTextField.setBorder(new EmptyBorder(4, 4, 4, 4));
        dialog.annotationTextArea.setBorder(BorderFactory.createCompoundBorder(dialog.annotationTextArea.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        dialog.nameTextField.getCaret().setBlinkRate(0);
        dialog.annotationTextArea.getCaret().setBlinkRate(0);

        dialog.setResizable(false);

        DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        initLang(defaultComboBoxModel1);
        initLang(defaultComboBoxModel2);

        dialog.indexLanguageComboBox.setModel(defaultComboBoxModel1);
        dialog.contentsLanguageComboBox.setModel(defaultComboBoxModel2);

        String defaultIndexLanguageCode = model.properties.getProperty(model.properties.getProperty("DEFAULT_INDEX_LANGUAGE"));
        String defaultContentsLanguageCode = model.properties.getProperty(model.properties.getProperty("DEFAULT_CONTENTS_LANGUAGE"));
        dialog.indexLanguageComboBox.setSelectedItem(findElementByCode(dialog.indexLanguageComboBox.getModel(), defaultIndexLanguageCode));
        dialog.contentsLanguageComboBox.setSelectedItem(findElementByCode(dialog.contentsLanguageComboBox.getModel(), defaultContentsLanguageCode));

        dialog.buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        dialog.buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        dialog.nameTextField.setDocument(new JTextFieldLimit(Constants.PROPS_NAME_LIMIT));
        dialog.annotationTextArea.setDocument(new JTextFieldLimit(Constants.PROPS_ANNOTATION_LIMIT));


        // call onCancel() when cross is clicked
        dialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        dialog.contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        dialog.setVisible(true);
    }

    private LanguageElement findElementByCode(ComboBoxModel model, String code) {
        for (int i = 0; i < model.getSize(); i++) {
            LanguageElement languageElement = (LanguageElement) model.getElementAt(i);
            if (languageElement.getCode().equals(code)) {
                return languageElement;
            }
        }
        return null;
    }

    private void initLang(DefaultComboBoxModel comboBoxModel) {
        SortedSet<LanguageElement> langs = model.getLangs();
        for (LanguageElement lang : langs) {
            comboBoxModel.addElement(lang);
        }
    }

    private void onCancel() {
        dialog.dispose();
    }

    private void onOK() {
        model.addDictionary(dialog.getData());
        dialog.dispose();
    }

}
