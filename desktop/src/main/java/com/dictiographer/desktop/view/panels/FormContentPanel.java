package com.dictiographer.desktop.view.panels;


import javax.swing.*;
import java.awt.*;

public class FormContentPanel extends JPanel {
    private Object form;

    public FormContentPanel(JPanel content, Object parentForm) {
        this.form = parentForm;
        this.setLayout(new BorderLayout());
        this.add(content, BorderLayout.CENTER);
    }

    public Object getForm() {
        return form;
    }

    public void setForm(Object form) {
        this.form = form;
    }

}


