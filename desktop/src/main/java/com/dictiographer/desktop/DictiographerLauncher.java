package com.dictiographer.desktop;

import com.dictiographer.desktop.presenter.Presenter;

public class DictiographerLauncher {
    public static void main(String[] args) {
        Presenter.getInstance().init();
    }
}
