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
import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;

public class EntryHandler extends BaseHandler {
    EntryDialog entryDialog;
    Template freemarkerTemplate;

    public EntryHandler(Model m, View v, Presenter p) {
        super(m, v, p);
        try {
            Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            configuration.setClassForTemplateLoading(EntryHandler.class.getClass(), "/");
            freemarkerTemplate = configuration.getTemplate("templates/main.ftl");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
            entryDialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    super.windowClosed(e);
                    model.properties.setProperty(Constants.PropKeys.PROP_ENTRY_POS_X.name(), String.valueOf(entryDialog.getLocation().x));
                    model.properties.setProperty(Constants.PropKeys.PROP_ENTRY_POS_Y.name(), String.valueOf(entryDialog.getLocation().y));
                    model.properties.setProperty(Constants.PropKeys.PROP_ENTRY_HEIGHT.name(), String.valueOf((int)entryDialog.getSize().getHeight()));
                    model.properties.setProperty(Constants.PropKeys.PROP_ENTRY_WIDTH.name(), String.valueOf((int)entryDialog.getSize().getWidth()));
                }
            });
            try {
                if (model.properties.containsKey(Constants.PropKeys.PROP_ENTRY_POS_X.name()) &&
                        model.properties.containsKey(Constants.PropKeys.PROP_ENTRY_POS_Y.name())) {
                    int posX = Integer.parseInt(model.properties.getProperty(Constants.PropKeys.PROP_ENTRY_POS_X.name()));
                    int posY = Integer.parseInt(model.properties.getProperty(Constants.PropKeys.PROP_ENTRY_POS_Y.name()));
                    entryDialog.setLocation(posX, posY);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            try {
                if (model.properties.containsKey(Constants.PropKeys.PROP_ENTRY_HEIGHT.name()) &&
                        model.properties.containsKey(Constants.PropKeys.PROP_ENTRY_WIDTH.name())) {
                    int height = Integer.parseInt(model.properties.getProperty(Constants.PropKeys.PROP_ENTRY_HEIGHT.name()));
                    int width = Integer.parseInt(model.properties.getProperty(Constants.PropKeys.PROP_ENTRY_WIDTH.name()));
                    entryDialog.setSize(width,height);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

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
        String entry = convert(eom, "nl", model.resourceBundle);
        view.setContent(entry);
    }

    public String convert(EntryObjectModel eom, String lang, ResourceBundle messageSource) {
        try {
            Map<String, Object> root = new HashMap<>();
            root.put("entry", eom);
            root.put("lang", lang);
            root.put("props", messageSource);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Writer out = new OutputStreamWriter(baos, "UTF-8");
            freemarkerTemplate.process(root, out);
            return new String(baos.toByteArray(), "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }
}
