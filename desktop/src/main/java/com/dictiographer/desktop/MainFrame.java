package com.dictiographer.desktop;

import com.dictiographer.desktop.event.Event;
import com.dictiographer.desktop.event.*;
import com.dictiographer.shared.model.IDictionary;
import com.dictiographer.shared.model.ZipDictionary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;

import static com.dictiographer.desktop.Constants.DEFAULT_LAF;

public class MainFrame extends JFrame implements Subscriber {
    private JPanel cards;
    private QuickHelpPanel quickHelpPanel;
    private CardLayout cardLayout;
    private MainKeyEventDispatcher mainKeyEventDispatcher;
    private MainPanel mainPanel;

    protected Properties properties;
    protected ResourceBundle resourceBundle;

    private enum MainViews {
        MAIN,
        QUICK_HELP
    }

    public MainFrame() {
        EventService.getInstance().subscribe(DictionaryEvent.class, null, this);
        EventService.getInstance().subscribe(DictionaryAllDeletedEvent.class, null, this);

        try {
            Files.createDirectories(Paths.get(Constants.DICT_DIR_NAME));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        mainKeyEventDispatcher = new MainKeyEventDispatcher(this);

        resourceBundle = ResourceBundle.getBundle("DictiographerResource");
        setTitle(resourceBundle.getString("title"));

        properties = new Properties();
        //classpath properties
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(Constants.PROPS_FILE_NAME));
        } catch (Exception ex) {
            throw new RuntimeException("Couldn't load properties file from the classpath.");
        }

        int defaultWidth = Integer.parseInt(properties.getProperty(Constants.PropKeys.PROP_WIDTH.name()));
        int defaultHeight = Integer.parseInt(properties.getProperty(Constants.PropKeys.PROP_HEIGHT.name()));
        int defaultX = Integer.parseInt(properties.getProperty(Constants.PropKeys.PROP_POS_X.name()));
        int defaultY = Integer.parseInt(properties.getProperty(Constants.PropKeys.PROP_POS_Y.name()));

        //user properties override some classpath properties
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(getUserPropertiesFileName());
            properties.load(fis);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        //setting size and location from properties
        try {
            int width = properties.containsKey(Constants.PropKeys.PROP_WIDTH.name()) ? Integer.parseInt(properties.getProperty(Constants.PropKeys.PROP_WIDTH.name())) : defaultWidth;
            int height = properties.containsKey(Constants.PropKeys.PROP_HEIGHT.name()) ? Integer.parseInt(properties.getProperty(Constants.PropKeys.PROP_HEIGHT.name())) : defaultHeight;
            setPreferredSize(new Dimension(width, height));
        } catch (Exception ex) {
            setPreferredSize(new Dimension(defaultWidth, defaultHeight));
        }

        try {
            int posX = properties.containsKey(Constants.PropKeys.PROP_POS_X.name()) ? Integer.parseInt(properties.getProperty(Constants.PropKeys.PROP_POS_X.name())) : defaultX;
            int posY = properties.containsKey(Constants.PropKeys.PROP_POS_Y.name()) ? Integer.parseInt(properties.getProperty(Constants.PropKeys.PROP_POS_Y.name())) : defaultY;
            setLocation(posX, posY);
        } catch (Exception ex) {
            setLocation(defaultX, defaultY);
        }

        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("images/favicon-16x16.png")).getImage());
        //todo read laf from properties
        setLAF(DEFAULT_LAF);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(mainKeyEventDispatcher);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                properties.setProperty(Constants.PropKeys.PROP_WIDTH.name(), String.valueOf(getWidth()));
                properties.setProperty(Constants.PropKeys.PROP_HEIGHT.name(), String.valueOf(getHeight()));
                properties.setProperty(Constants.PropKeys.PROP_POS_X.name(), String.valueOf((int) getLocation().getX()));
                properties.setProperty(Constants.PropKeys.PROP_POS_Y.name(), String.valueOf((int) getLocation().getY()));

                try {
                    Files.deleteIfExists(Paths.get(getUserPropertiesFileName()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    FileOutputStream fos = new FileOutputStream(getUserPropertiesFileName());
                    properties.store(fos, "");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                e.getWindow().dispose();
                System.exit(0);
            }
        });

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        quickHelpPanel = new QuickHelpPanel();
        mainPanel = new MainPanel(this);

        cards.add(mainPanel, MainViews.MAIN.name());
        cards.add(quickHelpPanel, MainViews.QUICK_HELP.name());

        cardLayout.show(cards, MainViews.QUICK_HELP.name());

        getContentPane().add(cards, BorderLayout.CENTER);

        pack();

        try {
            String[] dictionaryFileNames = new File(Constants.DICT_DIR_NAME).list();
            for (String dictionaryFileName : dictionaryFileNames) {
                URI uri = URI.create("jar:" + new File(Constants.DICT_DIR_NAME + File.separator + dictionaryFileName).toURI());
                IDictionary dictionary = new ZipDictionary(uri);
                mainPanel.addDictionary(dictionary);
            }
            if (dictionaryFileNames.length > 0) {
                cardLayout.show(cards, MainViews.MAIN.name());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //for quick debugging of dialogs
//        new DictionaryDialog(this).setVisible(true);
    }

    private String getUserPropertiesFileName() {
        return System.getProperty("user.home") + File.separator + Constants.PROPS_FILE_NAME;
    }

    private void setLAF(String newLAF) {
        try {
            if (newLAF == null) {
                newLAF = DEFAULT_LAF;
            }
            UIManager.setLookAndFeel(newLAF);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SortedSet<LanguageElement> getLangs() {
        SortedSet<LanguageElement> s = new TreeSet<>();
        for (String propertyName : properties.stringPropertyNames()) {
            if (propertyName.startsWith("LANGUAGE_")) {
                String languageCode = properties.getProperty(propertyName);
                String displayText = resourceBundle.getString("language." + languageCode);
                LanguageElement languageElement = new LanguageElement(displayText, languageCode);
                s.add(languageElement);
            }
        }
        return s;
    }

    @Override
    public void inform(Event event) {
        if (event instanceof DictionaryEvent) {
            DictionaryEvent dictionaryEvent = (DictionaryEvent) event;
            if (dictionaryEvent.getEventType().equals(Constants.EventType.DICTIONARY_ADD)) {
                cardLayout.show(cards, MainViews.MAIN.name());
            }
        } else if (event instanceof DictionaryAllDeletedEvent) {
            cardLayout.show(cards, MainViews.QUICK_HELP.name());
        }
    }

    public SortedSet<DictionaryToggleButton> getCurrentShelf(boolean onlyActive) {
        return mainPanel.getCurrentShelf(onlyActive);
    }

    public String getCurrentIndexLanguage() {
        return mainPanel.getCurrentIndexLanguage();
    }

    public String getCurrentContentsLanguage() {
        return mainPanel.getCurrentContentsLanguage();
    }

}
