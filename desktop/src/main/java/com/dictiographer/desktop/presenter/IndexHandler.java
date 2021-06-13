package com.dictiographer.desktop.presenter;

import com.dictiographer.desktop.model.Model;
import com.dictiographer.desktop.view.IndexPanel;
import com.dictiographer.desktop.view.View;
import com.dictiographer.shared.collections.IndexedTreeSet;
import com.dictiographer.shared.model.IDictionary;

import java.util.SortedSet;

public class IndexHandler extends BaseHandler {
    public IndexHandler(Model m, View v, Presenter p) {
        super(m, v, p);
    }


    public void handle() {
        SortedSet<IDictionary> sortedSet = model.getDictionaries().getShelfDictionaries(view.getIndexLanguage(), view.getContentsLanguage());
        IndexedTreeSet<String> index = new IndexedTreeSet<>();
        for (IDictionary dictionary : sortedSet) {
            index.addAll(dictionary.getIndex());
        }
        IndexPanel indexPanel = (IndexPanel) view.mainPanel.indexTabbedPane.getComponentAt(view.mainPanel.indexTabbedPane.getSelectedIndex());
        indexPanel.indexList.setListData(index.toArray(new String[index.size()]));
    }
}
