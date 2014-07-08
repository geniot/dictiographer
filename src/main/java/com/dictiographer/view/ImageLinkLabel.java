package com.dictiographer.view;

import javax.swing.*;

/**
 * User: Vitaly Sazanovich
 * Date: 12/28/12
 * Time: 7:50 PM
 */
public class ImageLinkLabel extends JLabel {
    private String linkText;

    public String getLinkText() {
        return linkText;
    }

    public void setLinkText(String linkText) {
        this.linkText = linkText;
    }

    @Override
    public String toString() {
        return (getText() == null ? "" : getText()) + " " + linkText;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof ImageLinkLabel)) return false;
        return ((ImageLinkLabel) o).getText().equals(getText());
    }
}
