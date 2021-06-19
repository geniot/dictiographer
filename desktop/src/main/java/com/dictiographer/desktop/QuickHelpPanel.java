package com.dictiographer.desktop;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

public class QuickHelpPanel extends JPanel {
    private JPanel contentPanel;

    public QuickHelpPanel() {
        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
    }

}
