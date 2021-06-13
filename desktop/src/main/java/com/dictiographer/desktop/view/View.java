package com.dictiographer.desktop.view;

import com.dictiographer.desktop.presenter.Presenter;
import org.xhtmlrenderer.simple.xhtml.XhtmlNamespaceHandler;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

    public Presenter presenter;
    public JPanel cards;

    public QuickHelpPanel2 quickHelpPanel;
    public MainPanel mainPanel;
    public JPanel emptyPanel;

    public CardLayout cardLayout;

    private XhtmlNamespaceHandler nsh = new XhtmlNamespaceHandler();

    public String getContentsLanguage() {
        ExtendedCardLayout extendedCardLayout = (ExtendedCardLayout) mainPanel.cardsPanel.getLayout();
        JTabbedPane contentsTabbedPane = (JTabbedPane) extendedCardLayout.getPanelByName(getIndexLanguage());
        return contentsTabbedPane.getTitleAt(contentsTabbedPane.getSelectedIndex());
    }

    public String getIndexLanguage() {
        return mainPanel.indexTabbedPane.getTitleAt(mainPanel.indexTabbedPane.getSelectedIndex());
    }

    public String getSelectedHeadword() {
        IndexPanel indexPanel = (IndexPanel) mainPanel.indexTabbedPane.getComponentAt(mainPanel.indexTabbedPane.getSelectedIndex());
        return (String) indexPanel.indexList.getSelectedValue();
    }

    public void setContent(String entry) {
        ExtendedCardLayout extendedCardLayout = (ExtendedCardLayout) mainPanel.cardsPanel.getLayout();
        JTabbedPane contentsTabbedPane = (JTabbedPane) extendedCardLayout.getPanelByName(getIndexLanguage());
        ContentsPanel contentsPanel = (ContentsPanel) contentsTabbedPane.getComponentAt(contentsTabbedPane.getSelectedIndex());
        contentsPanel.entryPanel.setDocumentFromString(entry, null, nsh);
    }

    public enum MainViews {
        MAIN, QUICK_HELP, EMPTY
    }

    public View(Presenter p) {
        this.presenter = p;
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        quickHelpPanel = new QuickHelpPanel2();
        mainPanel = new MainPanel(p);
        emptyPanel = new JPanel();

        cards.add(mainPanel.$$$getRootComponent$$$(), MainViews.MAIN.name());
        cards.add(quickHelpPanel.$$$getRootComponent$$$(), MainViews.QUICK_HELP.name());
        cards.add(emptyPanel, MainViews.EMPTY.name());

        cardLayout.show(cards, MainViews.EMPTY.name());

        getContentPane().add(cards, BorderLayout.CENTER);
    }
}
