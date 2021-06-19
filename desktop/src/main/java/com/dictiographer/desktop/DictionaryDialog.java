package com.dictiographer.desktop;

import com.dictiographer.desktop.event.DictionaryEvent;
import com.dictiographer.desktop.event.EventService;
import com.dictiographer.shared.model.IDictionary;
import com.dictiographer.shared.model.ZipDictionary;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.apache.commons.io.IOUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.SortedSet;

public class DictionaryDialog extends JDialog {
    private static final int PROPS_NAME_LIMIT = 200;
    private static final int PROPS_ANNOTATION_LIMIT = 10000;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameTextField;
    private JComboBox indexLanguageComboBox;
    private JComboBox contentsLanguageComboBox;
    private JTextArea annotationTextArea;
    private JButton iconButton;

    private MainFrame frame;

    public DictionaryDialog(MainFrame f) {
        this.frame = f;
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPane, BorderLayout.CENTER);
        setModal(true);
        setTitle(frame.resourceBundle.getString("dictionary.add.title"));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getRootPane().setDefaultButton(buttonOK);
        //todo location and size from settings
        setSize(430, 430);
        setLocationRelativeTo(frame);

        nameTextField.setBorder(new EmptyBorder(4, 4, 4, 4));
        annotationTextArea.setBorder(BorderFactory.createCompoundBorder(annotationTextArea.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        nameTextField.getCaret().setBlinkRate(0);
        annotationTextArea.getCaret().setBlinkRate(0);

        setResizable(false);

        DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        initLang(defaultComboBoxModel1);
        initLang(defaultComboBoxModel2);

        indexLanguageComboBox.setModel(defaultComboBoxModel1);
        contentsLanguageComboBox.setModel(defaultComboBoxModel2);

        String defaultIndexLanguageCode = frame.properties.getProperty(frame.properties.getProperty(Constants.PropKeys.DEFAULT_INDEX_LANGUAGE.name()));
        String defaultContentsLanguageCode = frame.properties.getProperty(frame.properties.getProperty(Constants.PropKeys.DEFAULT_CONTENTS_LANGUAGE.name()));
        indexLanguageComboBox.setSelectedItem(findElementByCode(indexLanguageComboBox.getModel(), defaultIndexLanguageCode));
        contentsLanguageComboBox.setSelectedItem(findElementByCode(contentsLanguageComboBox.getModel(), defaultContentsLanguageCode));

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        nameTextField.setDocument(new JTextFieldLimit(PROPS_NAME_LIMIT));
        annotationTextArea.setDocument(new JTextFieldLimit(PROPS_ANNOTATION_LIMIT));


        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

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
        SortedSet<LanguageElement> langs = frame.getLangs();
        for (LanguageElement lang : langs) {
            comboBoxModel.addElement(lang);
        }
    }

    private void onCancel() {
        dispose();
    }

    private void onOK() {
        Map<String, Serializable> properties = getData();
        String dictionaryFileName =
                Constants.DICT_DIR_NAME + File.separator +
                        properties.get(IDictionary.DictionaryProperty.INDEX_LANGUAGE.name()) + "_" +
                        properties.get(IDictionary.DictionaryProperty.CONTENTS_LANGUAGE.name()) + "_" +
                        System.currentTimeMillis() + ".zip";
        IDictionary dictionary = new ZipDictionary(URI.create("jar:" + new File(dictionaryFileName).toURI()));
        dictionary.setProperties(properties);
        EventService.getInstance().publish(new DictionaryEvent(dictionary, Constants.EventType.DICTIONARY_ADD));
        dispose();
    }

    public Map<String, Serializable> getData() {
        Map<String, Serializable> properties = new HashMap<>();
        properties.put(IDictionary.DictionaryProperty.NAME.name(), nameTextField.getText().trim());
        properties.put(IDictionary.DictionaryProperty.ANNOTATION.name(), annotationTextArea.getText().trim());
        String indexLanguage = ((LanguageElement) indexLanguageComboBox.getSelectedItem()).getCode();
        String contentsLanguage = ((LanguageElement) contentsLanguageComboBox.getSelectedItem()).getCode();
        properties.put(IDictionary.DictionaryProperty.INDEX_LANGUAGE.name(), indexLanguage);
        properties.put(IDictionary.DictionaryProperty.CONTENTS_LANGUAGE.name(), contentsLanguage);
        properties.put(IDictionary.DictionaryProperty.ICON.name(), getIconBytes());
        return properties;
    }


    private byte[] getIconBytes() {
        try {
            return IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("images/user.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }

}
