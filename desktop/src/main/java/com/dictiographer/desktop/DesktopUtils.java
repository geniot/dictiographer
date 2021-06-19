package com.dictiographer.desktop;

import javax.swing.*;
import java.awt.*;

public class DesktopUtils {
    public static Component findTabByName(String title, JTabbedPane tab) {
        int tabCount = tab.getTabCount();
        for (int i = 0; i < tabCount; i++) {
            String tabTitle = tab.getTitleAt(i);
            if (tabTitle.equals(title)) {
                return tab.getComponentAt(i);
            }
        }
        return null;
    }
}
