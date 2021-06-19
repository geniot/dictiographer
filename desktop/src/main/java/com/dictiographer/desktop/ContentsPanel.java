package com.dictiographer.desktop;

import com.dictiographer.desktop.event.Event;
import com.dictiographer.desktop.event.*;
import com.dictiographer.desktop.model.DictionaryEntry;
import com.dictiographer.shared.model.IDictionary;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.xhtmlrenderer.simple.FSScrollPane;
import org.xhtmlrenderer.simple.XHTMLPanel;
import org.xhtmlrenderer.simple.extend.XhtmlNamespaceHandler;
import org.xhtmlrenderer.swing.BasicPanel;
import org.xhtmlrenderer.swing.FSMouseListener;
import org.xhtmlrenderer.swing.LinkListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class ContentsPanel extends JPanel implements Subscriber {
    private JTextArea textArea1;
    private JPanel dictionariesPanel;
    private JPanel mainPanel;
    private FSScrollPane entryScrollPane;
    private XHTMLPanel entryPanel;
    private JSplitPane contentsSplitPane;
    private MainFrame mainFrame;
    private String indexLanguage;
    private String contentsLanguage;

    private XhtmlNamespaceHandler nsh = new XhtmlNamespaceHandler();

    public ContentsPanel(MainFrame mf, String il, String cl) {
        this.mainFrame = mf;
        this.indexLanguage = il;
        this.contentsLanguage = cl;

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        dictionariesPanel.setLayout(new WrapLayout());

        entryPanel.getSharedContext().getTextRenderer().setSmoothingThreshold(0);
        List l = entryPanel.getMouseTrackingListeners();
        for (Object o : l) {
            if (o instanceof LinkListener) {
                entryPanel.removeMouseTrackingListener((FSMouseListener) o);
            }
        }

        entryPanel.addMouseTrackingListener(new LinkListener() {
            @Override
            public void linkClicked(BasicPanel panel, String uri) {
                System.out.println(uri);
            }
        });

        contentsSplitPane.addPropertyChangeListener(changeEvent -> {
            String propertyName = changeEvent.getPropertyName();
            if (propertyName.equals(JSplitPane.DIVIDER_LOCATION_PROPERTY)) {
                int newValue = (int) changeEvent.getNewValue();
                mainFrame.properties.setProperty(Constants.PropKeys.CONTENTS_DIVIDER_LOCATION.name(), String.valueOf(newValue));
                EventService.getInstance().publish(new DividerEvent(newValue, ContentsPanel.this));
            }
        });

        try {
            int dividerLocation = Integer.parseInt(mainFrame.properties.getProperty(Constants.PropKeys.CONTENTS_DIVIDER_LOCATION.name()));
            contentsSplitPane.setDividerLocation(dividerLocation);
        } catch (Exception ex) {
            contentsSplitPane.setDividerLocation(100);
        }

        EventService.getInstance().subscribe(IndexEvent.class, null, this);
        EventService.getInstance().subscribe(DividerEvent.class, null, this);
    }

    public void addDictionary(IDictionary dictionary) {
        dictionariesPanel.add(new DictionaryToggleButton(dictionary));
        dictionariesPanel.revalidate();
        dictionariesPanel.repaint();
    }

    public void deleteDictionary(DictionaryToggleButton dictionaryToggleButton) {
        for (int i = 0; i < dictionariesPanel.getComponentCount(); i++) {
            Component c = dictionariesPanel.getComponent(i);
            if (c.equals(dictionaryToggleButton)) {
                dictionariesPanel.remove(c);
                dictionariesPanel.revalidate();
                dictionariesPanel.repaint();
                return;
            }
        }
    }

    public boolean isEmpty() {
        return dictionariesPanel.getComponentCount() == 0;
    }

    public SortedSet<DictionaryToggleButton> getShelf(boolean active) {
        SortedSet<DictionaryToggleButton> shelf = new TreeSet<>();
        for (int i = 0; i < dictionariesPanel.getComponentCount(); i++) {
            DictionaryToggleButton dictionaryToggleButton = (DictionaryToggleButton) dictionariesPanel.getComponent(i);
            if (active) {
                if (dictionaryToggleButton.isSelected()) {
                    shelf.add(dictionaryToggleButton);
                }
            } else {
                shelf.add(dictionaryToggleButton);
            }
        }
        return shelf;
    }

    @Override
    public void inform(Event event) {
        if (event instanceof IndexEvent) {
            IndexEvent indexEvent = (IndexEvent) event;
            if (this.indexLanguage.equals(mainFrame.getCurrentIndexLanguage()) &&
                    this.contentsLanguage.equals(mainFrame.getCurrentContentsLanguage())) {
                SortedSet<DictionaryToggleButton> activeDictionaries = getShelf(true);
                for (DictionaryToggleButton dictionaryToggleButton : activeDictionaries) {
                    IDictionary dictionary = dictionaryToggleButton.getDictionary();
                    try {
                        String selectedHeadword = indexEvent.getHeadword();
                        if (selectedHeadword == null) {
                            entryPanel.setDocumentFromString("<html></html>", null, nsh);
                        } else {
                            String article = dictionary.read(selectedHeadword);
                            DictionaryEntry dictionaryEntry = new ObjectMapper().readValue(article, DictionaryEntry.class);
                            String html = "<html><body><p>" + dictionaryEntry.getDefinitions() + "</p></body></html>";
                            entryPanel.setDocumentFromString(html, null, nsh);
                        }
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

                    SwingUtilities.invokeLater(() -> {
                        JViewport jv = entryScrollPane.getViewport();
                        jv.setViewPosition(new Point(0, 0));
                    });
                }
            }
        } else if (event instanceof DividerEvent) {
            DividerEvent dividerEvent = (DividerEvent) event;
            if (!dividerEvent.getSource().equals(this)) {
                contentsSplitPane.setDividerLocation(dividerEvent.getNewValue());
            }
        }
    }

}
