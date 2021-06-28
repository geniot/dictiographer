package com.dictiographer.desktop;

import com.dictiographer.desktop.event.Event;
import com.dictiographer.desktop.event.*;
import io.github.geniot.indexedtreemap.IndexedTreeSet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.SortedSet;

public class IndexPanel extends JPanel implements ListSelectionListener, Subscriber {
    private JTextField searchTextField;
    private JList indexList;
    private JPanel contentPanel;
    private MainFrame mainFrame;
    private final String indexLanguage;
    private int listCellRendererHeight = 27;
    private String lastSelectedHeadword = "welcome";

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

        indexList.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int count = (int) (indexList.getSize().getHeight() / listCellRendererHeight);
                if (count == indexList.getModel().getSize()) {
                    return;
                } else {
                    updateIndex();
                }
            }
        });

        indexList.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int notches = e.getWheelRotation();
                if (notches < 0 && indexList.getSelectedIndex() > 0) {
                    indexList.setSelectedIndex(indexList.getSelectedIndex() - 1);
                } else if (notches > 0 && indexList.getSelectedIndex() < indexList.getModel().getSize() - 1) {
                    indexList.setSelectedIndex(indexList.getSelectedIndex() + 1);
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
        if (selectedHeadword != null) {
            lastSelectedHeadword = selectedHeadword;
        }
        EventService.getInstance().publish(new IndexEvent(selectedHeadword, Constants.EventType.INDEX_HEADWORD_SELECTED));
        SwingUtilities.invokeLater(() -> searchTextField.requestFocus());
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
        int count = (int) (indexList.getSize().getHeight() / listCellRendererHeight);
        if (count == 0) {
            indexList.setListData(new String[]{});
            return;
        }
        int selectedIndex = indexList.getSelectedIndex();
        String selectedHeadword = (String) indexList.getSelectedValue();
        if (selectedHeadword == null) {
            selectedHeadword = lastSelectedHeadword;
        }
        if (selectedIndex < 0) {
            selectedIndex = indexList.getModel().getSize();
        }
        SortedSet<DictionaryToggleButton> dictionaries = mainFrame.getCurrentShelf(true);
        IndexedTreeSet<String> fullIndex = new IndexedTreeSet<>();
        for (DictionaryToggleButton dictionaryToggleButton : dictionaries) {
            if (dictionaryToggleButton.isSelected()) {
                fullIndex.addAll(dictionaryToggleButton.getDictionary().getIndex());
            }
        }

        IndexedTreeSet<String> indexView = new IndexedTreeSet<>();
        if (fullIndex.size() <= count) {
            indexView.addAll(fullIndex);
        } else {
            int index = fullIndex.entryIndex(selectedHeadword);
            while (indexView.size() < count && selectedIndex >= 0 && index >= 0) {
                indexView.add(fullIndex.exact(index--));
                --selectedIndex;
            }
            index = fullIndex.entryIndex(selectedHeadword);
            while (indexView.size() < count && index < fullIndex.size()) {
                indexView.add(fullIndex.exact(index++));
            }
        }


        indexList.setListData(indexView.toArray(new String[indexView.size()]));
        //selecting a headword
        if (indexView.size() > 0) {
            if (indexView.contains(selectedHeadword)) {
                indexList.setSelectedValue(selectedHeadword, false);
            } else {
                if (selectedIndex >= 0 && selectedIndex < indexView.size()) {
                    indexList.setSelectedIndex(selectedIndex);
                } else {
                    indexList.setSelectedIndex(fullIndex.size() - 1);
                }
            }
        }
        SwingUtilities.invokeLater(() -> searchTextField.requestFocus());

    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(0, 0));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        contentPanel.add(panel1, BorderLayout.CENTER);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        panel1.add(panel2, BorderLayout.NORTH);
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5, 5, 7, 5), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        panel2.add(panel3, BorderLayout.CENTER);
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        searchTextField = new JTextField();
        Font searchTextFieldFont = this.$$$getFont$$$(null, -1, 14, searchTextField.getFont());
        if (searchTextFieldFont != null) searchTextField.setFont(searchTextFieldFont);
        searchTextField.setMargin(new Insets(2, 6, 2, 6));
        searchTextField.setMaximumSize(new Dimension(2147483647, 28));
        searchTextField.setMinimumSize(new Dimension(49, 28));
        searchTextField.setPreferredSize(new Dimension(49, 28));
        panel3.add(searchTextField, BorderLayout.CENTER);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(0, 0));
        panel1.add(panel4, BorderLayout.CENTER);
        panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        panel4.add(panel5, BorderLayout.CENTER);
        panel5.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        indexList = new JList();
        indexList.setFixedCellHeight(-1);
        indexList.setFixedCellWidth(-1);
        Font indexListFont = this.$$$getFont$$$(null, -1, 14, indexList.getFont());
        if (indexListFont != null) indexList.setFont(indexListFont);
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        indexList.setModel(defaultListModel1);
        panel5.add(indexList, BorderLayout.CENTER);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPanel;
    }
}
