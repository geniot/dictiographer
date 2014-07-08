package com.dictiographer.view.dialogs;

import com.dictiographer.controller.DictiographerUtils;
import com.dictiographer.model.Constants;
import com.dictiographer.view.AbstractContainerRenderer;
import com.dictiographer.view.Bindable;
import com.dictiographer.view.DnDTabbedPane;
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
public class EntryDialog extends AbstractContainerRenderer {
    public Bindable parent;
    public String mode = Constants.NEW_ACTION;

    public JTextField headword;
    public JTextField syllables;
    public JTextField zie;
    public JComboBox stressedSyllable;
    public DnDTabbedPane partOfSpeeches;


    public EntryDialog(Bindable b, EntryObjectModel eom, String mode) {
        parent = b;
        this.mode = mode;
        init("com/dictiography/client/ui/descriptors/EntryDialog.xml");
        ((JDialog) container).setTitle(getLocalizer().getString(mode.equals(Constants.NEW_ACTION) ? "title.new" : "title.edit"));
        partOfSpeeches.addNewTab(null);
        if (eom != null) {
            setData(eom);
        }

    }

    public Action saveAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            EntryObjectModel eom = new EntryObjectModel();
            getData(eom);
            if (eom.getHeadword() == null) {
                JOptionPane.showMessageDialog(container, DictiographerUtils.getProperty(lang, "ERROR_HEADWORD_REQUIRED"));
            } else {
                parent.setData(eom);
            }
        }
    };

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
                stressedSyllable.setSelectedItem(eom.getStressedSyllable());
                idioms = eom.getIdioms();
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
        data.setStressedSyllable(Integer.parseInt(stressedSyllable.getSelectedItem().toString()));
        data.setIdioms(idioms);
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


    public static void main(String[] args) {
        EntryDialog ed = new EntryDialog(null, null, Constants.NEW_ACTION);
        ed.container.setVisible(true);
    }

}
