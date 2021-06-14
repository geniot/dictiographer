package com.dictiographer.desktop.view.entry.dialogs;

import com.dictiographer.desktop.model.Constants;
import com.dictiographer.desktop.model.entry.EntryObjectModel;
import com.dictiographer.desktop.model.entry.Idioom;
import com.dictiographer.desktop.model.entry.PartOfSpeech;
import com.dictiographer.desktop.presenter.Bindable;
import com.dictiographer.desktop.presenter.DesktopUtils;
import com.dictiographer.desktop.view.entry.DnDTabbedPane;
import com.dictiographer.desktop.view.entry.MySwingEngine;
import com.dictiographer.desktop.view.entry.MyThreadLocal;
import com.dictiographer.desktop.view.entry.panels.FormContentPanel;
import com.dictiographer.desktop.view.entry.panels.PosPanel;
import com.dictiographer.shared.model.IDictionary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;

public class EntryDialog extends AbstractDialog {
    public EntryDialogPanel entryDialogPanel;

    public EntryDialog(Window view, Bindable b, EntryObjectModel eom, String mode) {
        super(view, ModalityType.APPLICATION_MODAL, b);
        this.setSize(900, 700);
        setTitle(MyThreadLocal.get().getMessageSource().getString(mode.equals(Constants.ACTIONS.NEW_ACTION.name()) ? "title.new" : "title.edit"));

        entryDialogPanel = new EntryDialogPanel();
        setContentPane(entryDialogPanel.mainPanel);

        setLocationRelativeTo(view);
        if (eom != null) {
            entryDialogPanel.setData(eom);
        }
    }

    public class EntryDialogPanel extends MySwingEngine {
        public Box contentPane;
        public JTextField headword;
        public JTextField syllables;
        public JTextField zie;
        public DnDTabbedPane partOfSpeeches;
        public JComboBox dictionary;

        public Idioom[] idioms;

        public EntryDialogPanel() {
            init("descriptors/EntryDialog.xml");
            partOfSpeeches.addNewTab(null);
        }

        @Override
        public void setData(Object inputObject) {
            try {
                if (inputObject == null) return;

                if (inputObject instanceof Idioom[]) {
                    idioms = ((Idioom[]) inputObject).length == 0 ? null : (Idioom[]) inputObject;
                    return;
                }

                if (inputObject instanceof EntryObjectModel) {
                    EntryObjectModel eom = (EntryObjectModel) inputObject;
                    if (eom.getHeadword() != null) headword.setText(eom.getHeadword());
                    if (eom.getSyllables() != null) syllables.setText(eom.getSyllables());
                    if (eom.getZie() != null) zie.setText(DesktopUtils.links2str(eom.getZie()));
//                stressedSyllable.setSelectedItem(eom.getStressedSyllable());
                    idioms = eom.getIdioms() == null ? new Idioom[]{} : eom.getIdioms();
                    if (eom.getPartOfSpeeches() != null) {
                        for (int i = 0; i < eom.getPartOfSpeeches().length; i++) {
                            Component c = partOfSpeeches.getComponentAt(i);
                            if (c != null && c instanceof FormContentPanel) {
                                FormContentPanel form = (FormContentPanel) c;
                                PosPanel posPanel = (PosPanel) form.getForm();
                                posPanel.setData(eom.getPartOfSpeeches()[i]);
                            } else {
                                partOfSpeeches.addNewTab(eom.getPartOfSpeeches()[i]);
                            }
                        }
                    }
                    partOfSpeeches.setSelectedIndex(0);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public Object getData(Object d) {
            EntryObjectModel data = (EntryObjectModel) d;
            data.setiDictionary((IDictionary) dictionary.getSelectedItem());
            if (!headword.getText().trim().equals("")) data.setHeadword(headword.getText().trim());
            if (!syllables.getText().trim().equals("")) data.setSyllables(syllables.getText().trim());
            if (!zie.getText().trim().equals("")) data.setZie(DesktopUtils.str2links(zie.getText().trim()));
//        data.setStressedSyllable(Integer.parseInt(stressedSyllable.getSelectedItem().toString()));
            if (idioms != null && idioms.length > 0) {
                data.setIdioms(idioms);
            }
            ArrayList<PartOfSpeech> poses = new ArrayList();
            for (int i = 0; i < partOfSpeeches.getTabCount() - 1; i++) {
                Component c = partOfSpeeches.getComponentAt(i);
                if (c != null && c instanceof FormContentPanel) {
                    FormContentPanel form = (FormContentPanel) c;
                    PosPanel posPanel = (PosPanel) form.getForm();
                    if (posPanel.isEmpty()) continue;
                    PartOfSpeech pos = new PartOfSpeech();
                    posPanel.getData(pos);
                    poses.add(pos);
                }
            }
            data.setPartOfSpeeches(poses.size() > 0 ? poses.toArray(new PartOfSpeech[poses.size()]) : null);
            return d;
        }

        @Override
        public boolean isEmpty() {
            if (!headword.getText().trim().equals("")) return false;
            if (!syllables.getText().trim().equals("")) return false;
            if (!zie.getText().trim().equals("")) return false;
            for (int i = 0; i < partOfSpeeches.getTabCount() - 1; i++) {
                Component c = partOfSpeeches.getComponentAt(i);
                if (c != null && c instanceof FormContentPanel) {
                    FormContentPanel form = (FormContentPanel) c;
                    PosPanel posPanel = (PosPanel) form.getForm();
                    if (!posPanel.isEmpty()) {
                        return false;
                    }
                }
            }
            if (idioms != null) return false;
            return true;
        }


        public Action idiomsAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    IdiomenDialog dialog = new IdiomenDialog(EntryDialog.this, EntryDialogPanel.this, idioms);
                    dialog.setVisible(true);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        };

        public Action saveAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                EntryObjectModel eom = new EntryObjectModel();
                getData(eom);
                if (eom.getHeadword() == null) {
                    JOptionPane.showMessageDialog(EntryDialog.this,
                            MyThreadLocal.get().getMessageSource().getString("ERROR_HEADWORD_REQUIRED"));
                } else {
                    parent.setData(eom);
                }
            }
        };

        public Action cancelAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                EntryDialog.this.dispose();
            }
        };
    }


}
