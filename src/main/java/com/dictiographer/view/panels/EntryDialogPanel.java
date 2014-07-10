package com.dictiographer.view.panels;

import com.dictiographer.controller.DictiographerUtils;
import com.dictiographer.model.Constants;
import com.dictiographer.view.AbstractContainerRenderer;
import com.dictiographer.view.Bindable;
import com.dictiographer.view.DnDTabbedPane;
import entry.EntryObjectModel;
import entry.Idioom;
import entry.PartOfSpeech;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Author: Vitaly Sazanovich
 * Email: vitaly.sazanovich@gmail.com
 * Date: 10/07/14
 */
public class EntryDialogPanel extends AbstractContainerRenderer {
    public JTextField headword;
    public JTextField syllables;
    public JTextField zie;
    public DnDTabbedPane partOfSpeeches;

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
//        data.setStressedSyllable(Integer.parseInt(stressedSyllable.getSelectedItem().toString()));
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
}
