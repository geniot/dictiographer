package com.dictiographer.view;

import com.dictiographer.view.dialogs.*;
import com.dictiographer.view.dialogs.grammar.GrammarDialogLauncher;
import com.dictiographer.view.panels.FormContentPanel;
import com.dictiographer.view.panels.SingleTranslationPanel;
import entry.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.util.Locale;

/**
 * User: Vitaly Sazanovich
 * Date: 12/18/12
 * Time: 11:18 PM
 */
public abstract class AbstractContainerRenderer extends MySwingEngine implements Bindable {
    public Bindable parent;
    public JPanel mainPanel;
    public Container container;
    public Box contentPanel;
    protected String lang;

    public Example[] examples;
    public EntryImage[] images;
    public Translation[] translations;
    public Idioom[] idioms;
    public Grammar grammar;
    public SemanticCategory category;
    public JComboBox posComboBox;

    public void init(String fileName) {
        try {
            getTaglib().registerTag("dndtabbedpane", DnDTabbedPane.class);
            getTaglib().registerTag("layeredpane", JLayeredPane.class);

            lang = MyThreadLocal.get().getLang();
            setLocale(new Locale(lang));
            getLocalizer().setLocale(new Locale(lang));

            container = render(fileName);

            if (container instanceof JDialog) {
                ((JDialog) container).setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                ((JDialog) container).addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        onCancel();
                    }
                });
                mainPanel.registerKeyboardAction(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        onCancel();
                    }
                }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void addSingleTranslation(Translation tr) {
        SingleTranslationPanel stp = new SingleTranslationPanel(contentPanel);
        if (tr != null) {
            stp.setData(tr);
        }
        FormContentPanel formContentPanel = new FormContentPanel(stp.getMainPanel(), stp);
        contentPanel.add(formContentPanel, contentPanel.getComponentCount());
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public Action saveAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            try {
                parent.setData(getData(null));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            ((JDialog) container).dispose();
        }
    };

    public Action addTranslationAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            addSingleTranslation(null);
        }
    };

    public Action categoryAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            try {
                new CategoryDialog(AbstractContainerRenderer.this, category);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    };

    public Action grammarAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            try {
                new GrammarDialogLauncher(AbstractContainerRenderer.this, grammar, ((KeyValuePair) getPosComboBox().getSelectedItem()).getKey());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    };


    private JComboBox getPosComboBox() throws Exception {
        if (posComboBox != null) {
            return posComboBox;
        }
        Container c = container;
        while (c != null) {
            try {
                c = c.getParent();
                if (c == null) break;
                if (c instanceof FormContentPanel) {
                    AbstractContainerRenderer tmp = (AbstractContainerRenderer) ((FormContentPanel) c).getForm();
                    JComboBox f = (JComboBox) getInheritedPrivateFieldValue(tmp, tmp.getClass(), "posComboBox");
                    if (f != null) {
                        return f;
                    }
                } else {
                    Field f = c.getClass().getDeclaredField("posComboBox");
                    if (f.get(c) != null) {
                        return (JComboBox) f.get(c);
                    }
                }
            } catch (NoSuchFieldException e) {
            }
        }
        throw new Exception("Couldn't find posComboBox in the hierarchy");
    }

    private Object getInheritedPrivateFieldValue(Object thisObj, Class<?> type, String fieldName) throws IllegalAccessException {
        Class<?> i = type;
        while (i != null && i != Object.class) {
            for (Field field : i.getDeclaredFields()) {
                if (!field.isSynthetic() && field.getName().equals(fieldName)) {
                    field.setAccessible(true);
                    return field.get(thisObj);
                }
            }
            i = i.getSuperclass();
        }

        return null;
    }

    public Action cancelAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            if (container instanceof JDialog) {
                onCancel();
            }
        }
    };

    public Action translationAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            try {
                new TranslationDialog(AbstractContainerRenderer.this, translations);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    };
    public Action imageAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            try {
                new ImageDialog(AbstractContainerRenderer.this, images);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    };
    public Action examplesAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            try {
                new ExamplesDialog(AbstractContainerRenderer.this, examples);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    };
    public Action idiomsAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            try {
                new IdiomenDialog(AbstractContainerRenderer.this, idioms);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    };

    protected void onCancel() {
        if (container instanceof JDialog) {
            ((JDialog) container).dispose();
        }
        Container parent = mainPanel.getParent();
        while (parent != null) {
            parent = parent.getParent();
            if (parent != null  && parent instanceof Window) {
                ((Window) parent).dispose();
                return;
            }
        }
    }

    @Override
    public void setData(Object d) {
        if (d instanceof Translation[]) {
            translations = ((Translation[]) d).length == 0 ? null : (Translation[]) d;
            return;
        }
        if (d instanceof EntryImage[]) {
            images = ((EntryImage[]) d).length == 0 ? null : (EntryImage[]) d;
            return;
        }
        if (d instanceof Example[]) {
            examples = ((Example[]) d).length == 0 ? null : (Example[]) d;
            return;
        }
        if (d instanceof Idioom[]) {
            idioms = ((Idioom[]) d).length == 0 ? null : (Idioom[]) d;
            return;
        }
        if (d instanceof Grammar) {
            grammar = ((Grammar) d).isEmpty() ? null : (Grammar) d;
            return;
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getCurrentCard(JPanel cards) {
        JPanel card = null;
        for (Component comp : cards.getComponents()) {
            if (comp.isVisible() == true) {
                card = (JPanel) comp;
            }
        }
        return card;
    }

    public boolean isChildOf(JComponent comp, JComponent container) {
        Container parent = comp.getParent();
        while (parent != null) {
            if (parent == container) {
                return true;
            }
            parent = parent.getParent();
        }
        return false;
    }

}
