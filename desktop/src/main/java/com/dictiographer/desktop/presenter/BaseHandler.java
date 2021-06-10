package com.dictiographer.desktop.presenter;

import com.dictiographer.desktop.view.View;
import com.dictiographer.desktop.model.Constants;
import com.dictiographer.desktop.model.Model;

import java.io.File;

public class BaseHandler {
    protected Model model;
    protected View view;

    protected Presenter presenter;

    public BaseHandler(Model m, View v, Presenter p) {
        this.model = m;
        this.view = v;
        this.presenter = p;
    }

    protected String getUserPropertiesFileName(){
        return System.getProperty("user.home") + File.separator + Constants.PROPS_FILE_NAME;
    }
}
