package com.dictiographer.desktop;

import com.dictiographer.desktop.event.DictionaryEvent;
import com.dictiographer.desktop.event.EventService;
import io.github.geniot.dictiographer.model.IDictionary;
import io.github.geniot.dictiographer.model.ZipDictionary;
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
import java.util.*;

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

    private MainFrame mainFrame;
    private IDictionary dictionary;

    public DictionaryDialog(MainFrame mf, IDictionary d) {
        this.mainFrame = mf;
        this.dictionary = d;
        init();
        nameTextField.setText((String) d.getProperties().get(IDictionary.DictionaryProperty.NAME.name()));
        annotationTextArea.setText((String) d.getProperties().get(IDictionary.DictionaryProperty.ANNOTATION.name()));
        indexLanguageComboBox.setSelectedItem(findElementByCode(indexLanguageComboBox.getModel(), d.getIndexLanguage()));
        contentsLanguageComboBox.setSelectedItem(findElementByCode(contentsLanguageComboBox.getModel(), d.getContentsLanguage()));
        indexLanguageComboBox.setEnabled(false);
        contentsLanguageComboBox.setEnabled(false);

    }

    public DictionaryDialog(MainFrame f) {
        this.mainFrame = f;
        init();
    }

    private void init() {
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPane, BorderLayout.CENTER);
        setModal(true);
        setTitle(mainFrame.resourceBundle.getString("dictionary.add.title"));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getRootPane().setDefaultButton(buttonOK);
        //todo location and size from settings
        setSize(430, 430);
        setLocationRelativeTo(mainFrame);

        indexLanguageComboBox.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                lbl.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
                return lbl;
            }
        });
        contentsLanguageComboBox.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                lbl.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
                return lbl;
            }
        });

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

        String defaultIndexLanguageCode = mainFrame.properties.getProperty(mainFrame.properties.getProperty(Constants.PropKeys.DEFAULT_INDEX_LANGUAGE.name()));
        String defaultContentsLanguageCode = mainFrame.properties.getProperty(mainFrame.properties.getProperty(Constants.PropKeys.DEFAULT_CONTENTS_LANGUAGE.name()));
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
            if (languageElement.getCode().equalsIgnoreCase(code)) {
                return languageElement;
            }
        }
        return null;
    }

    private void initLang(DefaultComboBoxModel comboBoxModel) {
        SortedSet<LanguageElement> langs = mainFrame.getLangs();
        for (LanguageElement lang : langs) {
            comboBoxModel.addElement(lang);
        }
    }

    private void onCancel() {
        dispose();
    }

    private void onOK() {
        Map<String, Serializable> properties = getData();
        if (dictionary == null) {
            String dictionaryFileName =
                    Constants.DICT_DIR_NAME + File.separator +
                            properties.get(IDictionary.DictionaryProperty.INDEX_LANGUAGE.name()) + "_" +
                            properties.get(IDictionary.DictionaryProperty.CONTENTS_LANGUAGE.name()) + "_" +
                            System.currentTimeMillis() + ".zip";
            dictionary = new ZipDictionary(URI.create("jar:" + new File(dictionaryFileName).toURI()));
            dictionary.setProperties(properties);
            EventService.getInstance().publish(new DictionaryEvent(dictionary, Constants.EventType.DICTIONARY_ADD));
        } else {
            dictionary.setProperties(properties);
            EventService.getInstance().publish(new DictionaryEvent(dictionary, Constants.EventType.DICTIONARY_EDIT));
        }

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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        this.$$$loadButtonText$$$(buttonOK, this.$$$getMessageFromBundle$$$("DictiographerResource", "button.ok"));
        panel2.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        this.$$$loadButtonText$$$(buttonCancel, this.$$$getMessageFromBundle$$$("DictiographerResource", "button.cancel"));
        panel2.add(buttonCancel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        panel3.add(panel4, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), this.$$$getMessageFromBundle$$$("DictiographerResource", "dictionary.name"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        panel4.add(panel5, BorderLayout.SOUTH);
        panel5.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new BorderLayout(0, 0));
        panel5.add(panel6, BorderLayout.SOUTH);
        panel6.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        nameTextField = new JTextField();
        Font nameTextFieldFont = this.$$$getFont$$$(null, -1, -1, nameTextField.getFont());
        if (nameTextFieldFont != null) nameTextField.setFont(nameTextFieldFont);
        panel6.add(nameTextField, BorderLayout.CENTER);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new BorderLayout(0, 0));
        panel3.add(panel7, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel7.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), this.$$$getMessageFromBundle$$$("DictiographerResource", "dictionary.index.language"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new BorderLayout(0, 0));
        panel7.add(panel8, BorderLayout.CENTER);
        panel8.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        indexLanguageComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        indexLanguageComboBox.setModel(defaultComboBoxModel1);
        panel8.add(indexLanguageComboBox, BorderLayout.CENTER);
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new BorderLayout(0, 0));
        panel3.add(panel9, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel9.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), this.$$$getMessageFromBundle$$$("DictiographerResource", "dictionary.contents.language"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new BorderLayout(0, 0));
        panel9.add(panel10, BorderLayout.CENTER);
        panel10.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        contentsLanguageComboBox = new JComboBox();
        panel10.add(contentsLanguageComboBox, BorderLayout.CENTER);
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new BorderLayout(0, 0));
        panel3.add(panel11, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel11.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), this.$$$getMessageFromBundle$$$("DictiographerResource", "dictionary.icon"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel12 = new JPanel();
        panel12.setLayout(new BorderLayout(0, 0));
        panel11.add(panel12, BorderLayout.CENTER);
        final JPanel panel13 = new JPanel();
        panel13.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel12.add(panel13, BorderLayout.CENTER);
        iconButton = new JButton();
        iconButton.setFocusPainted(false);
        iconButton.setFocusable(false);
        iconButton.setIcon(new ImageIcon(getClass().getResource("/images/user.png")));
        iconButton.setMaximumSize(new Dimension(14, 21));
        iconButton.setMinimumSize(new Dimension(14, 21));
        iconButton.setPreferredSize(new Dimension(14, 21));
        iconButton.setText("");
        panel13.add(iconButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(23, 30), new Dimension(23, 30), new Dimension(23, 30), 0, false));
        final JPanel panel14 = new JPanel();
        panel14.setLayout(new BorderLayout(0, 0));
        panel3.add(panel14, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panel14.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), this.$$$getMessageFromBundle$$$("DictiographerResource", "dictionary.annotation"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel15 = new JPanel();
        panel15.setLayout(new BorderLayout(0, 0));
        panel14.add(panel15, BorderLayout.CENTER);
        panel15.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel15.add(scrollPane1, BorderLayout.CENTER);
        annotationTextArea = new JTextArea();
        annotationTextArea.setEditable(true);
        annotationTextArea.setEnabled(true);
        scrollPane1.setViewportView(annotationTextArea);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    private static Method $$$cachedGetBundleMethod$$$ = null;

    private String $$$getMessageFromBundle$$$(String path, String key) {
        ResourceBundle bundle;
        try {
            Class<?> thisClass = this.getClass();
            if ($$$cachedGetBundleMethod$$$ == null) {
                Class<?> dynamicBundleClass = thisClass.getClassLoader().loadClass("com.intellij.DynamicBundle");
                $$$cachedGetBundleMethod$$$ = dynamicBundleClass.getMethod("getBundle", String.class, Class.class);
            }
            bundle = (ResourceBundle) $$$cachedGetBundleMethod$$$.invoke(null, path, thisClass);
        } catch (Exception e) {
            bundle = ResourceBundle.getBundle(path);
        }
        return bundle.getString(key);
    }

    /**
     * @noinspection ALL
     */
    private void $$$loadButtonText$$$(AbstractButton component, String text) {
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '&') {
                i++;
                if (i == text.length()) break;
                if (!haveMnemonic && text.charAt(i) != '&') {
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if (haveMnemonic) {
            component.setMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
