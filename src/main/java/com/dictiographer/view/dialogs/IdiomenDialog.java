package com.dictiographer.view.dialogs;

import com.dictiographer.view.Bindable;
import com.dictiographer.view.DnDTabbedPane;
import com.dictiographer.view.MySwingEngine;
import com.dictiographer.view.MyThreadLocal;
import com.dictiographer.view.panels.FormContentPanel;
import com.dictiographer.view.panels.IdioomPanel;
import entry.Idioom;
import org.springframework.context.MessageSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * User: Vitaly Sazanovich
 * Date: 12/18/12
 * Time: 6:39 PM
 */
public class IdiomenDialog extends JDialog {

    public Bindable parent;
    private IdiomenDialogPanel idiomenDialogPanel;
    private MessageSource messageSource;

    public IdiomenDialog(Window view, Bindable b, Idioom[] idiooms, MessageSource ms) {
        super(view, ModalityType.APPLICATION_MODAL);
        this.parent = b;
        this.messageSource = ms;
        setTitle(messageSource.getMessage("title.idioms", null, MyThreadLocal.get().getLocale()));
        idiomenDialogPanel = new IdiomenDialogPanel();
        setContentPane(idiomenDialogPanel.mainPanel);

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        idiomenDialogPanel.mainPanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.setSize(900, 700);
        this.setLocationRelativeTo(view);


        if (idiooms != null) {
            idiomenDialogPanel.setData(idiooms);
        }


    }

    protected void onCancel() {
        dispose();
    }


    public class IdiomenDialogPanel extends MySwingEngine implements Bindable {
        public JPanel mainPanel;
        public Container container;
        public DnDTabbedPane idDndTabbedPane;

        public IdiomenDialogPanel() {

            try {
                getTaglib().registerTag("dndtabbedpane", DnDTabbedPane.class);
                getTaglib().registerTag("layeredpane", JLayeredPane.class);

                container = render("descriptors/IdiomenDialog.xml");

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

        @Override
        public void setData(Object d) {
            Idioom[] data = (Idioom[]) d;
            for (int i = 0; i < data.length; i++) {
                Component c = idDndTabbedPane.getComponentAt(i);
                if (c != null && c instanceof FormContentPanel) {
                    FormContentPanel form = (FormContentPanel) c;
                    IdioomPanel idioomPanel = (IdioomPanel) form.getForm();
                    idioomPanel.setData(data[i]);
                } else {
                    idDndTabbedPane.addNewTab(data[i]);
                }
            }
            idDndTabbedPane.setSelectedIndex(0);
        }

        @Override
        public Object getData(Object data) {
            ArrayList<Idioom> poses = new ArrayList();
            for (int i = 0; i < idDndTabbedPane.getTabCount() - 1; i++) {
                Component c = idDndTabbedPane.getComponentAt(i);
                if (c != null && c instanceof FormContentPanel) {
                    FormContentPanel form = (FormContentPanel) c;
                    IdioomPanel idioomPanel = (IdioomPanel) form.getForm();
                    if (idioomPanel.isEmpty()) continue;
                    Idioom idiom = new Idioom();
                    idioomPanel.getData(idiom);
                    poses.add(idiom);
                }
            }
            Idioom[] ids = poses.toArray(new Idioom[poses.size()]);
            return ids;
        }

        @Override
        public boolean isEmpty() {
            for (int i = 0; i < idDndTabbedPane.getTabCount() - 1; i++) {
                Component c = idDndTabbedPane.getComponentAt(i);
                if (c != null && c instanceof FormContentPanel) {
                    FormContentPanel form = (FormContentPanel) c;
                    IdioomPanel idioomPanel = (IdioomPanel) form.getForm();
                    if (!idioomPanel.isEmpty()) return false;
                }
            }
            return true;
        }

        public Action saveAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                parent.setData(idiomenDialogPanel.getData(null));
                IdiomenDialog.this.dispose();
            }
        };

        public Action cancelAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                IdiomenDialog.this.dispose();
            }
        };

        @Override
        public JPanel getMainPanel() {
            return mainPanel;
        }
    }


}
