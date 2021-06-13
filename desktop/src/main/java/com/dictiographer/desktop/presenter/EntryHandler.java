package com.dictiographer.desktop.presenter;

import com.dictiographer.desktop.model.Constants;
import com.dictiographer.desktop.model.Model;
import com.dictiographer.desktop.model.entry.EntryObjectModel;
import com.dictiographer.desktop.view.View;
import com.dictiographer.desktop.view.entry.MyLocalizer;
import com.dictiographer.desktop.view.entry.MyThreadLocal;
import com.dictiographer.desktop.view.entry.ThreadContext;
import com.dictiographer.desktop.view.entry.dialogs.EntryDialog;
import com.dictiographer.shared.model.IDictionary;

import javax.swing.*;
import java.util.Locale;
import java.util.SortedSet;

public class EntryHandler extends BaseHandler {
    EntryDialog entryDialog;

    public EntryHandler(Model m, View v, Presenter p) {
        super(m, v, p);
    }

    public void handle() {
        try {

            ThreadContext context = new ThreadContext();
            Locale loc = new Locale("nl");
            context.setLocale(loc);
            context.setLocalizer(new MyLocalizer(loc));
            context.setMessageSource(model.resourceBundle);
            MyThreadLocal.set(context);

            Bindable b = new BindableAdapter() {
                @Override
                public void setData(Object data) {
                    try {
                        EntryObjectModel eom = (EntryObjectModel) data;
                        String headword = eom.getHeadword();
                        String article = DesktopUtils.entry2xml(eom);
                        IDictionary dictionary = eom.getiDictionary();

                        model.addEntry(dictionary, headword, article);
                        entryDialog.dispose();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            };

            entryDialog = new EntryDialog(view, b, null, Constants.ACTIONS.NEW_ACTION.name());

            DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
            SortedSet<IDictionary> dictionarySortedSet = model.getDictionaries().getShelfDictionaries(view.getIndexLanguage(), view.getContentsLanguage());
            for (IDictionary dictionary : dictionarySortedSet) {
                defaultComboBoxModel1.addElement(dictionary);
            }
            entryDialog.entryDialogPanel.dictionary.setModel(defaultComboBoxModel1);
            entryDialog.setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void showEntry(String selectedHeadword) {
        SortedSet<IDictionary> shelf = model.getDictionaries().getShelfDictionaries(view.getIndexLanguage(), view.getContentsLanguage());
        String article = shelf.iterator().next().read(selectedHeadword);
        EntryObjectModel eom = (EntryObjectModel) DesktopUtils.xml2entry(article);
        String entry = DesktopUtils.convert(eom, "nl", model.resourceBundle);
        view.setContent(entry);
    }
}
