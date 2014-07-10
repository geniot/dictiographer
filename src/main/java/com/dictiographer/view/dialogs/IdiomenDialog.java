package com.dictiographer.view.dialogs;

import com.dictiographer.model.Constants;
import com.dictiographer.view.Bindable;
import com.dictiographer.view.panels.IdiomenDialogPanel;
import entry.Idioom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * User: Vitaly Sazanovich
 * Date: 12/18/12
 * Time: 6:39 PM
 */
public class IdiomenDialog extends JDialog {

    public Bindable parent;
    private IdiomenDialogPanel idiomenDialogPanel;


    public IdiomenDialog(Window view, Bindable b, Idioom[] idiooms) {
        super(view, ModalityType.APPLICATION_MODAL);
        this.parent = b;
        setTitle(Constants.LOCALIZER.getString("title.idioms"));
        idiomenDialogPanel = new IdiomenDialogPanel();
        setContentPane(idiomenDialogPanel.mainPanel);

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        idiomenDialogPanel.mainPanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.setSize(900, 700);
        this.setLocationRelativeTo(view);


        if (idiooms != null) {
            idiomenDialogPanel.setData(idiooms);
        }


    }

    protected void onCancel() {
        dispose();
    }

    public Action saveAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            parent.setData(idiomenDialogPanel.getData(null));
        }
    };

}
