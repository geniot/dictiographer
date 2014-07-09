package com.dictiographer.view.dialogs;

import com.dictiographer.model.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Author: Vitaly Sazanovich
 * Email: vitaly.sazanovich@gmail.com
 * Date: 09/07/14
 */
public class WrapperDialog extends JDialog {
    public WrapperDialog(Window view, JPanel mainPanel, String mode) {
        super(view);
        setTitle(Constants.LOCALIZER.getString(mode.equals(Constants.NEW_ACTION) ? "title.new" : "title.edit"));
        setContentPane(mainPanel);

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        mainPanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    protected void onCancel() {
        dispose();
    }
}
