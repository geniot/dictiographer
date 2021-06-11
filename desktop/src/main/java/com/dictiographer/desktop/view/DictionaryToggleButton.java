package com.dictiographer.desktop.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DictionaryToggleButton extends JToggleButton {
    public DictionaryToggleButton(Icon icon) {
        super(icon);
        setFocusPainted(false);
        setBorderPainted(false);
        setFocusable(false);

        setOpaque(true);
        setContentAreaFilled(false);

        Dimension dm = new Dimension(23, 30);
        setMaximumSize(dm);
        setMinimumSize(dm);
        setPreferredSize(dm);
        setText("");
        setHorizontalAlignment(SwingConstants.CENTER);

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
                boolean selected = abstractButton.getModel().isSelected();
                setBorderPainted(selected);
            }
        });
    }
}
