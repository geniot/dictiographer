package com.dictiographer.desktop.presenter;

import com.dictiographer.desktop.model.Model;
import com.dictiographer.desktop.view.View;

public class EntryHandler extends BaseHandler {
    public EntryHandler(Model m, View v, Presenter p) {
        super(m, v, p);
    }

    public void handle() {
        try {

//            ThreadContext context = new ThreadContext();
//            Locale loc = new Locale(domain);
//            context.setLocale(loc);
//            context.setLocalizer(new MyLocalizer(loc));
//            context.setMessageSource(messageSource);
//            MyThreadLocal.set(context);
//
//            Bindable b = new BindableAdapter() {
//                @Override
//                public void setData(Object data) {
//                    try {
//                        EntryObjectModel eom = (EntryObjectModel) data;
//
//                    } catch (Exception ex) {
//                        throw new RuntimeException(ex);
//                    }
//                }
//            };
//
//            EntryDialog entryDialog = new EntryDialog(view, b, null, Constants.NEW_ACTION);
//            entryDialog.setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
