package com.dictiographer.desktop;

import com.dictiographer.desktop.event.EntryEvent;
import com.dictiographer.desktop.event.EventService;
import com.dictiographer.desktop.model.DictionaryEntry;
import com.dictiographer.shared.model.IDictionary;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.SortedSet;

public class EntryDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField headwordTextField;
    private JTextArea definitionsTextArea;
    private JComboBox dictionaryComboBox;
    private MainFrame mainFrame;

    public EntryDialog(MainFrame mf) {
        this.mainFrame = mf;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        headwordTextField.setBorder(new EmptyBorder(4, 4, 4, 4));
        definitionsTextArea.setBorder(new EmptyBorder(4, 4, 4, 4));
        setSize(430, 430);
        setLocationRelativeTo(mainFrame);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);


        headwordTextField.getCaret().setBlinkRate(0);
        definitionsTextArea.getCaret().setBlinkRate(0);

        dictionaryComboBox.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                lbl.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
                return lbl;
            }
        });

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

        // call onCancel() when cross is clicked
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

        if (mainFrame != null) {// can be null when running from main
            setTitle(mainFrame.resourceBundle.getString("entry.add.title"));
            SortedSet<DictionaryToggleButton> dictionaries = mainFrame.getCurrentShelf(false);
            DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
            for (DictionaryToggleButton dictionaryToggleButton : dictionaries) {
                defaultComboBoxModel.addElement(dictionaryToggleButton.getDictionary());
            }
            dictionaryComboBox.setModel(defaultComboBoxModel);
        }

        SwingUtilities.invokeLater(() -> headwordTextField.requestFocus());
    }

    private void onOK() {
        DictionaryEntry entry = getData();
        if (StringUtils.isEmpty(entry.getHeadword())) {
            JOptionPane.showMessageDialog(EntryDialog.this, mainFrame.resourceBundle.getString("entry.headword.required"));
            SwingUtilities.invokeLater(() -> headwordTextField.requestFocus());
            return;
        }
        IDictionary dictionary = (IDictionary) dictionaryComboBox.getSelectedItem();
        try {
            String jsonArticle = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(entry);
            dictionary.createOrUpdate(entry.getHeadword(), jsonArticle);
            EventService.getInstance().publish(new EntryEvent(entry, dictionary, Constants.EventType.ENTRY_ADD));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        dispose();
    }

    private DictionaryEntry getData() {
        DictionaryEntry dictionaryEntry = new DictionaryEntry();
        dictionaryEntry.setHeadword(headwordTextField.getText().trim());
        dictionaryEntry.setDefinitions(definitionsTextArea.getText().trim());
        return dictionaryEntry;
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        EntryDialog dialog = new EntryDialog(null);
        dialog.setVisible(true);
        System.exit(0);
    }

}
