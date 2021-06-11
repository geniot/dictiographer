package com.dictiographer.desktop.view;

import com.dictiographer.desktop.presenter.Presenter;
import com.dictiographer.shared.model.IDictionary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.Map;

public class DictionaryToggleButton extends JToggleButton {
    public Presenter presenter;
    protected IDictionary dictionary;

    public DictionaryToggleButton(Presenter p, IDictionary d) {
        super();
        this.presenter = p;
        this.dictionary = d;

        setFocusPainted(false);
        setBorderPainted(true);
        setSelected(true);
        setFocusable(false);

        Map<String, Serializable> properties = dictionary.getProperties();

        setToolTipText((String) properties.get(IDictionary.DictionaryProperty.NAME.name()));
        setIcon(new ImageIcon((byte[]) properties.get(IDictionary.DictionaryProperty.ICON.name())));

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

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (e.isPopupTrigger()) {
                    doPop(e);
                }
            }

            private void doPop(MouseEvent e) {
                DictionaryPopupMenu menu = new DictionaryPopupMenu(presenter, DictionaryToggleButton.this);
                menu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
    }
}
