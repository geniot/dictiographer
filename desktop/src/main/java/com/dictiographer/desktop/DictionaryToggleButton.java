package com.dictiographer.desktop;

import com.dictiographer.desktop.event.DictionaryEvent;
import com.dictiographer.desktop.event.EventService;
import com.dictiographer.shared.model.IDictionary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.Map;

public class DictionaryToggleButton extends JToggleButton implements Comparable<DictionaryToggleButton> {

    private IDictionary dictionary;

    public DictionaryToggleButton(IDictionary d) {
        super();
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
                EventService.getInstance().publish(new DictionaryEvent(dictionary, Constants.EventType.DICTIONARY_SELECTED));
            }
        });

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                maybeShowPopup(e);
            }

            public void mouseReleased(MouseEvent e) {
                maybeShowPopup(e);
            }

            private void maybeShowPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    DictionaryPopupMenu popup = new DictionaryPopupMenu(DictionaryToggleButton.this);
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    public IDictionary getDictionary() {
        return dictionary;
    }

    @Override
    public int compareTo(DictionaryToggleButton o) {
        return this.dictionary.compareTo(o.dictionary);
    }
}
