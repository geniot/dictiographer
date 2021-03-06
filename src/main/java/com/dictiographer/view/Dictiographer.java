package com.dictiographer.view;

import com.dictiographer.controller.ViewController;
import com.dictiographer.model.Constants;
import org.xhtmlrenderer.simple.FSScrollPane;
import org.xhtmlrenderer.simple.XHTMLPanel;
import org.xhtmlrenderer.simple.xhtml.XhtmlNamespaceHandler;
import org.xhtmlrenderer.swing.BasicPanel;
import org.xhtmlrenderer.swing.FSMouseListener;
import org.xhtmlrenderer.swing.LinkListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.SortedSet;

public class Dictiographer extends JFrame {

    private JPanel contentPane;
    private JTextField textField1;
    private JComboBox domainComboBox;
    private JList list1;
    private JSplitPane mainSplitPane;
    private JScrollPane entryScrollPane;
    private JButton addEntryButton;
    private JButton editEntryButton;
    private JButton removeEntryButton;
    private JButton refreshButton;
    private XHTMLPanel entryPanel;


    private XhtmlNamespaceHandler nsh = new XhtmlNamespaceHandler();
    private ViewController viewController;
    private Properties props;

    public Dictiographer(ViewController vc, Properties ppc) {
        this.viewController = vc;
        this.props = ppc;

        $$$setupUI$$$();
        setContentPane(contentPane);

        setTitle("Dictiographer " + ppc.getProperty(Constants.APP_VERSION_PROP_KEY));

        int w = (int) Double.parseDouble(props.getProperty(Constants.WIDTH_PROP_KEY));
        int h = (int) Double.parseDouble(props.getProperty(Constants.HEIGHT_PROP_KEY));
        int x = (int) Double.parseDouble(props.getProperty(Constants.X_POS_PROP_KEY));
        int y = (int) Double.parseDouble(props.getProperty(Constants.Y_POS_PROP_KEY));
        int div = Integer.parseInt(props.getProperty(Constants.DIVIDER_PROP_KEY));

        setSize(w, h);
        setLocation(x, y);
        mainSplitPane.setDividerLocation(div);

        entryPanel.getSharedContext().getTextRenderer().setSmoothingThreshold(0);

        updateView();


        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                viewController.onClosing(Dictiographer.this);
            }
        });

        domainComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateView();
            }
        });

        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (list1.getSelectedValue() != null) {
                        selectEntry(list1.getSelectedValue().toString());
                    } else {
                        selectEntry(null);
                    }
                    updateButtonStates();
                }

            }
        });

        List l = entryPanel.getMouseTrackingListeners();
        for (Object o : l) {
            if (o instanceof LinkListener) {
                entryPanel.removeMouseTrackingListener((FSMouseListener) o);
            }
        }

        entryPanel.addMouseTrackingListener(new LinkListener() {
            @Override
            public void linkClicked(BasicPanel panel, String uri) {
                SortedSet<String> index = viewController.getIndex(domainComboBox.getSelectedItem().toString());
                if (index.contains(uri)) {
                    list1.setSelectedValue(uri, true);
                }
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                textField1.requestFocus();
            }
        });

        addEntryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (domainComboBox.getSelectedItem() != null) {
                    viewController.onEntryAdd(domainComboBox.getSelectedItem().toString());
                }
            }
        });
        editEntryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list1.getSelectedValue() != null) {
                    viewController.onEntryEdit(domainComboBox.getSelectedItem().toString(), list1.getSelectedValue().toString());
                }
            }
        });
        removeEntryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (list1.getSelectedValue() != null) {
                    viewController.onEntryRemove(domainComboBox.getSelectedItem().toString(), list1.getSelectedValue().toString());
                }
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateView();
            }
        });
    }

    private void updateButtonStates() {
        addEntryButton.setEnabled(domainComboBox.getSelectedItem() != null);
        editEntryButton.setEnabled(list1.getSelectedValue() != null);
        removeEntryButton.setEnabled(list1.getSelectedValue() != null);
    }

    private void selectEntry(String selectedEntry) {
        if (selectedEntry == null) {
            entryPanel.setDocumentFromString("<html></html>", null, nsh);
        } else {
            props.setProperty(Constants.SELECTED_WORD_PROP_KEY, selectedEntry);
            String text = viewController.getEntry(domainComboBox.getSelectedItem().toString(), selectedEntry);
            if (!text.equals("")) {
                entryPanel.setDocumentFromString(text, null, nsh);
            }
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JViewport jv = entryScrollPane.getViewport();
                jv.setViewPosition(new Point(0, 0));
            }
        });
        textField1.requestFocus();
    }


    public void updateView() {
//        if (domainComboBox.getSelectedItem() == null) {
//            return;
//        }
        if (domainComboBox.getSelectedItem() == null) {
            String[] dns = viewController.getDomains();
            domainComboBox.setModel(new DefaultComboBoxModel(dns));
            if (dns.length > 0) {
                domainComboBox.setEnabled(true);
            }

            String selDomain = props.getProperty(Constants.SELECTED_DOMAIN_PROP_KEY);
            if (!selDomain.equals("")) {
                domainComboBox.setSelectedItem(selDomain);
            }
        }

        SortedSet<String> s = viewController.getIndex(domainComboBox.getSelectedItem().toString());
        String[] hws = domainComboBox.getSelectedItem() == null ? new String[]{} : s.toArray(new String[s.size()]);
        list1.setListData(hws);

        String selWord = props.getProperty(Constants.SELECTED_WORD_PROP_KEY);
        if (selWord != null && !selWord.equals("") && Arrays.asList(hws).contains(selWord)) {
            list1.setSelectedValue(selWord, true);
            selectEntry(list1.getSelectedValue().toString());
        } else {
            entryPanel.setDocumentFromString("<html></html>", null, nsh);
        }


        updateButtonStates();

        textField1.requestFocus();
    }

    public int getDividerLocation() {
        return mainSplitPane.getDividerLocation();
    }


    public String getSelectedDomain() {
        Object selItem = domainComboBox.getSelectedItem();
        return selItem == null ? "" : selItem.toString();
    }

    public String getSelectedWord() {
        Object selItem = list1.getSelectedValue();
        return selItem == null ? "" : selItem.toString();
    }


    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        contentPane.add(panel1, BorderLayout.CENTER);
        mainSplitPane = new JSplitPane();
        mainSplitPane.setDividerLocation(70);
        panel1.add(mainSplitPane, BorderLayout.CENTER);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        mainSplitPane.setRightComponent(panel2);
        final JToolBar toolBar1 = new JToolBar();
        toolBar1.setFloatable(false);
        panel2.add(toolBar1, BorderLayout.NORTH);
        final JToggleButton toggleButton1 = new JToggleButton();
        toggleButton1.setIcon(new ImageIcon(getClass().getResource("/images/translation.png")));
        toolBar1.add(toggleButton1);
        final JToggleButton toggleButton2 = new JToggleButton();
        toggleButton2.setIcon(new ImageIcon(getClass().getResource("/images/example.png")));
        toolBar1.add(toggleButton2);
        final JToggleButton toggleButton3 = new JToggleButton();
        toggleButton3.setIcon(new ImageIcon(getClass().getResource("/images/link.png")));
        toolBar1.add(toggleButton3);
        final JToggleButton toggleButton4 = new JToggleButton();
        toggleButton4.setIcon(new ImageIcon(getClass().getResource("/images/idiom.png")));
        toolBar1.add(toggleButton4);
        final JToggleButton toggleButton5 = new JToggleButton();
        toggleButton5.setIcon(new ImageIcon(getClass().getResource("/images/image.png")));
        toolBar1.add(toggleButton5);
        final JToggleButton toggleButton6 = new JToggleButton();
        toggleButton6.setIcon(new ImageIcon(getClass().getResource("/images/grammar.png")));
        toolBar1.add(toggleButton6);
        entryScrollPane = new FSScrollPane();
        entryScrollPane.setHorizontalScrollBarPolicy(30);
        entryScrollPane.setVerticalScrollBarPolicy(20);
        panel2.add(entryScrollPane, BorderLayout.CENTER);
        entryPanel = new XHTMLPanel();
        entryScrollPane.setViewportView(entryPanel);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        mainSplitPane.setLeftComponent(panel3);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new BorderLayout(5, 0));
        panel3.add(panel4, BorderLayout.NORTH);
        panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(9, 3, 9, 3), null));
        textField1 = new JTextField();
        panel4.add(textField1, BorderLayout.CENTER);
        domainComboBox = new JComboBox();
        domainComboBox.setEnabled(false);
        panel4.add(domainComboBox, BorderLayout.WEST);
        final JScrollPane scrollPane1 = new JScrollPane();
        panel3.add(scrollPane1, BorderLayout.CENTER);
        list1 = new JList();
        list1.setSelectionMode(0);
        scrollPane1.setViewportView(list1);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        panel1.add(panel5, BorderLayout.SOUTH);
        final JToolBar toolBar2 = new JToolBar();
        toolBar2.setBorderPainted(false);
        toolBar2.setFloatable(false);
        panel5.add(toolBar2, BorderLayout.SOUTH);
        addEntryButton = new JButton();
        addEntryButton.setIcon(new ImageIcon(getClass().getResource("/images/application_form_add.png")));
        addEntryButton.setText("");
        toolBar2.add(addEntryButton);
        editEntryButton = new JButton();
        editEntryButton.setIcon(new ImageIcon(getClass().getResource("/images/application_form_edit.png")));
        editEntryButton.setText("");
        toolBar2.add(editEntryButton);
        removeEntryButton = new JButton();
        removeEntryButton.setIcon(new ImageIcon(getClass().getResource("/images/application_form_delete.png")));
        removeEntryButton.setText("");
        toolBar2.add(removeEntryButton);
        refreshButton = new JButton();
        refreshButton.setIcon(new ImageIcon(getClass().getResource("/images/arrow_refresh.png")));
        refreshButton.setText("");
        toolBar2.add(refreshButton);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
