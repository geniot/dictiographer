package com.dictiographer.desktop;

import com.dictiographer.desktop.event.Event;
import com.dictiographer.desktop.event.*;
import com.dictiographer.shared.collections.IndexedTreeSet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.SortedSet;

public class IndexPanel extends JPanel implements ListSelectionListener, Subscriber {
    private JTextField searchTextField;
    private JList indexList;
    private JPanel contentPanel;
    private MainFrame mainFrame;
    private final String indexLanguage;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new IndexPanel(null, null), BorderLayout.CENTER);
        frame.setVisible(true);
    }


    public IndexPanel(String il, MainFrame mf) {
        this.indexLanguage = il;
        this.mainFrame = mf;
        this.setLayout(new BorderLayout());
        this.add(contentPanel, BorderLayout.CENTER);

        searchTextField.getCaret().setBlinkRate(0);
        searchTextField.setBorder(new EmptyBorder(0, 4, 0, 0));

        EmptyBorder border = new EmptyBorder(4, 4, 4, 4);
        indexList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                DefaultListCellRenderer renderer = (DefaultListCellRenderer) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                renderer.setBorder(border);
                return renderer;
            }
        });

        indexList.addListSelectionListener(this);

        indexList.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                maybeShowPopup(e);
            }

            public void mouseReleased(MouseEvent e) {
                maybeShowPopup(e);
            }

            private void maybeShowPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    EntryPopupMenu popup = new EntryPopupMenu(mainFrame);
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        EventService.getInstance().subscribe(EntryEvent.class, null, this);
        EventService.getInstance().subscribe(DictionaryEvent.class, null, this);
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }
        String selectedHeadword = (String) indexList.getSelectedValue();
        EventService.getInstance().publish(new IndexEvent(selectedHeadword, Constants.EventType.INDEX_HEADWORD_SELECTED));
    }

    @Override
    public void inform(Event event) {
        //we only update current index as entries are added to the current index
        if (event instanceof EntryEvent ||
                event instanceof DictionaryEvent && ((DictionaryEvent) event).getEventType().equals(Constants.EventType.DICTIONARY_SELECTED)) {
            if (mainFrame.getCurrentIndexLanguage().equals(indexLanguage)) {
                updateIndex();
            }
        }
    }

    protected void updateIndex() {
        SortedSet<DictionaryToggleButton> dictionaries = mainFrame.getCurrentShelf(true);
        IndexedTreeSet<String> index = new IndexedTreeSet<>();
        for (DictionaryToggleButton dictionaryToggleButton : dictionaries) {
            if (dictionaryToggleButton.isSelected()) {
                index.addAll(dictionaryToggleButton.getDictionary().getIndex());
            }
        }
        indexList.setListData(index.toArray(new String[index.size()]));
    }

}
