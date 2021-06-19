package com.dictiographer.desktop;


import com.dictiographer.desktop.event.Event;
import com.dictiographer.desktop.event.*;
import com.dictiographer.shared.model.IDictionary;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.SortedSet;
import java.util.TreeSet;

public class MainPanel extends JPanel implements ChangeListener, Subscriber {
    private JPanel containerPanel;
    private JTabbedPane indexTabbedPane;
    private JSplitPane mainSplitPane;
    private JPanel cardsPanel;
    private ExtendedCardLayout extendedCardLayout;
    private MainFrame mainFrame;

    public MainPanel(MainFrame frame) {
        this.mainFrame = frame;
        setLayout(new BorderLayout());
        add(containerPanel, BorderLayout.CENTER);


        indexTabbedPane.addChangeListener(this);
        mainSplitPane.addPropertyChangeListener(changeEvent -> {
            String propertyName = changeEvent.getPropertyName();
            if (propertyName.equals(JSplitPane.DIVIDER_LOCATION_PROPERTY)) {
                int newValue = (int) changeEvent.getNewValue();
                mainFrame.properties.setProperty(Constants.PropKeys.MAIN_DIVIDER_LOCATION.name(), String.valueOf(newValue));
            }
        });

        try {
            int dividerLocation = Integer.parseInt(mainFrame.properties.getProperty(Constants.PropKeys.MAIN_DIVIDER_LOCATION.name()));
            mainSplitPane.setDividerLocation(dividerLocation);
        } catch (Exception ex) {
            mainSplitPane.setDividerLocation(100);
        }

        extendedCardLayout = new ExtendedCardLayout();
        cardsPanel.setLayout(extendedCardLayout);

        EventService.getInstance().subscribe(DictionaryEvent.class, null, this);
        EventService.getInstance().subscribe(DictionaryDeletedEvent.class, null, this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource().equals(indexTabbedPane)) {
            if (indexTabbedPane.getSelectedIndex() >= 0) {//it can be negative when no selection (all tabs removed)
                String selectedLanguage = indexTabbedPane.getTitleAt(indexTabbedPane.getSelectedIndex());
                extendedCardLayout.show(cardsPanel, selectedLanguage);
            }
        }
    }

    @Override
    public void inform(Event event) {
        if (event instanceof DictionaryEvent) {
            DictionaryEvent dictionaryEvent = (DictionaryEvent) event;
            //initializing necessary panels when a dictionary is added
            if (dictionaryEvent.getEventType().equals(Constants.EventType.DICTIONARY_ADD)) {
                IDictionary dictionary = dictionaryEvent.getDictionary();
                addDictionary(dictionary);
            }
        } else if (event instanceof DictionaryDeletedEvent) {
            DictionaryDeletedEvent dictionaryDeletedEvent = (DictionaryDeletedEvent) event;
            JTabbedPane contentsTabbedPane = (JTabbedPane) extendedCardLayout.getPanelByName(dictionaryDeletedEvent.getIndexLanguage());
            ContentsPanel contentsPanel = (ContentsPanel) DesktopUtils.findTabByName(dictionaryDeletedEvent.getContentsLanguage(), contentsTabbedPane);
            contentsPanel.deleteDictionary(dictionaryDeletedEvent.getDictionaryToggleButton());

            IndexPanel indexPanel = (IndexPanel) DesktopUtils.findTabByName(dictionaryDeletedEvent.getIndexLanguage(), indexTabbedPane);

            if (contentsPanel.isEmpty()) {
                contentsTabbedPane.remove(contentsPanel);
            }
            if (contentsTabbedPane.getTabRunCount() == 0) {
                indexTabbedPane.remove(indexPanel);
                cardsPanel.remove(contentsTabbedPane);
            }
            if (cardsPanel.getComponentCount() == 0) {
                indexTabbedPane.removeAll();
                EventService.getInstance().publish(new DictionaryAllDeletedEvent());
            }
        }
    }

    public void addDictionary(IDictionary dictionary) {
        String indexLanguage = dictionary.getIndexLanguage();
        String contentsLanguage = dictionary.getContentsLanguage();

        IndexPanel indexPanel = (IndexPanel) DesktopUtils.findTabByName(indexLanguage, indexTabbedPane);
        if (indexPanel == null) {
            indexPanel = new IndexPanel(indexLanguage, mainFrame);
            indexTabbedPane.addTab(indexLanguage, indexPanel);
        }

        Component contentsCard = extendedCardLayout.getPanelByName(indexLanguage);
        ContentsPanel contentsPanel;
        JTabbedPane contentsTabbedPane;
        if (contentsCard == null) {
            contentsTabbedPane = new JTabbedPane();
            contentsTabbedPane.setFocusable(false);
            contentsTabbedPane.setRequestFocusEnabled(false);
            contentsTabbedPane.setTabPlacement(JTabbedPane.RIGHT);
            contentsPanel = new ContentsPanel(mainFrame, indexLanguage, contentsLanguage);
            contentsTabbedPane.add(contentsPanel, contentsLanguage);
            cardsPanel.add(contentsTabbedPane, indexLanguage);
        } else {
            contentsTabbedPane = (JTabbedPane) contentsCard;
            contentsPanel = (ContentsPanel) DesktopUtils.findTabByName(contentsLanguage, contentsTabbedPane);
            if (contentsPanel == null) {
                contentsPanel = new ContentsPanel(mainFrame, indexLanguage, contentsLanguage);
                contentsTabbedPane.add(contentsPanel, contentsLanguage);

            }
        }
        contentsPanel.addDictionary(dictionary);
        indexTabbedPane.setSelectedComponent(indexPanel);
        contentsTabbedPane.setSelectedComponent(contentsPanel);
        indexPanel.updateIndex();
    }

    public SortedSet<DictionaryToggleButton> getCurrentShelf(boolean onlyActive) {
        if (indexTabbedPane.getTabRunCount() == 0) {
            return new TreeSet<>();
        }
        String indexLanguage = indexTabbedPane.getTitleAt(indexTabbedPane.getSelectedIndex());
        JTabbedPane contentsTabbedPane = (JTabbedPane) extendedCardLayout.getPanelByName(indexLanguage);
        String contentsLanguage = contentsTabbedPane.getTitleAt(contentsTabbedPane.getSelectedIndex());
        ContentsPanel contentsPanel = (ContentsPanel) DesktopUtils.findTabByName(contentsLanguage, contentsTabbedPane);
        return contentsPanel.getShelf(onlyActive);
    }

    public String getCurrentIndexLanguage() {
        return indexTabbedPane.getTitleAt(indexTabbedPane.getSelectedIndex());
    }

    public String getCurrentContentsLanguage() {
        String indexLanguage = indexTabbedPane.getTitleAt(indexTabbedPane.getSelectedIndex());
        JTabbedPane contentsTabbedPane = (JTabbedPane) extendedCardLayout.getPanelByName(indexLanguage);
        return contentsTabbedPane.getTitleAt(contentsTabbedPane.getSelectedIndex());
    }

}
