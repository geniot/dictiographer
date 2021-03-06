package com.dictiographer.view.dialogs;

import com.dictiographer.model.Constants;
import com.dictiographer.utils.DictiographerUtils;
import com.dictiographer.view.Bindable;
import com.dictiographer.view.DnDTabbedPane;
import com.dictiographer.view.MySwingEngine;
import com.dictiographer.view.MyThreadLocal;
import com.dictiographer.view.panels.FormContentPanel;
import com.dictiographer.view.panels.PosPanel;
import entry.EntryObjectModel;
import entry.Idioom;
import entry.PartOfSpeech;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * User: Vitaly Sazanovich
 * Date: 12/18/12
 * Time: 6:39 PM
 */
public class EntryDialog extends AbstractDialog {
    protected EntryDialogPanel entryDialogPanel;

    public EntryDialog(Window view, Bindable b, EntryObjectModel eom, String mode) {
        super(view, ModalityType.APPLICATION_MODAL, b);
        this.setSize(900, 700);
        setTitle(MyThreadLocal.get().getMessageSource().getMessage(mode.equals(Constants.NEW_ACTION) ? "title.new" : "title.edit", null, MyThreadLocal.get().getLocale()));

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
                    if (eom.getZie() != null) zie.setText(DictiographerUtils.links2str(eom.getZie()));
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
            if (!headword.getText().trim().equals("")) data.setHeadword(headword.getText().trim());
            if (!syllables.getText().trim().equals("")) data.setSyllables(syllables.getText().trim());
            if (!zie.getText().trim().equals("")) data.setZie(DictiographerUtils.str2links(zie.getText().trim()));
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
                            MyThreadLocal.get().getMessageSource().getMessage("ERROR_HEADWORD_REQUIRED", null, MyThreadLocal.get().getLocale()));
                } else {
                    parent.setData(eom);
                    EntryDialog.this.dispose();
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
