package com.dictiographer.view.dialogs;

import com.dictiographer.controller.DictiographerUtils;
import com.dictiographer.model.Constants;
import com.dictiographer.view.Bindable;
import com.dictiographer.view.MyThreadLocal;
import com.dictiographer.view.panels.EntryDialogPanel;
import entry.EntryObjectModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * User: Vitaly Sazanovich
 * Date: 12/18/12
 * Time: 6:39 PM
 */
public class EntryDialog extends JDialog {
    public Bindable parent;
    public String mode = Constants.NEW_ACTION;
    private EntryDialogPanel entryDialogPanel;



    public EntryDialog(Window view, Bindable b, EntryObjectModel eom, String mode) {
        super(view, ModalityType.APPLICATION_MODAL);
        this.parent = b;
        this.mode = mode;
        setTitle(Constants.LOCALIZER.getString(mode.equals(Constants.NEW_ACTION) ? "title.new" : "title.edit"));

        entryDialogPanel = new EntryDialogPanel();
        setContentPane(entryDialogPanel.mainPanel);

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        entryDialogPanel.mainPanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.setSize(900, 700);
        this.setLocationRelativeTo(view);


        if (eom != null) {
            entryDialogPanel.setData(eom);
        }


    }

    protected void onCancel() {
        dispose();
    }

    public Action saveAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            EntryObjectModel eom = new EntryObjectModel();
            entryDialogPanel.getData(eom);
            if (eom.getHeadword() == null) {
                String lang = MyThreadLocal.get().getLang();
                JOptionPane.showMessageDialog(EntryDialog.this, DictiographerUtils.getProperty(lang, "ERROR_HEADWORD_REQUIRED"));
            } else {
                parent.setData(eom);
            }
        }
    };


    public static void main(String[] args) {
        EntryDialog ed = new EntryDialog(null, null, null, Constants.NEW_ACTION);
        ed.setVisible(true);
    }

}
