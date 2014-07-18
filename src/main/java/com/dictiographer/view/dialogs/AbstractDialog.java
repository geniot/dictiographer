package com.dictiographer.view.dialogs;

import com.dictiographer.view.Bindable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Author: Vitaly Sazanovich
 * Email: vitaly.sazanovich@gmail.com
 * Date: 15/07/14
 */
public abstract class AbstractDialog extends JDialog {
    /**
     * When the dialog is closed with save, parent will received data
     */
    protected Bindable parent;

    public AbstractDialog(Window view, ModalityType applicationModal, Bindable p) {
        super(view, applicationModal);
        this.parent = p;

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
    }

    protected void onCancel() {
        dispose();
    }
}
