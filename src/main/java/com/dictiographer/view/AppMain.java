package com.dictiographer.view;

import javax.swing.*;

/**
 * Author: Vitaly Sazanovich
 * Email: Vitaly.Sazanovich@gmail.com
 * Date: 7/5/14
 */
public class AppMain extends JFrame {

    public AppMain() {

        setTitle("Simple example");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AppMain ex = new AppMain();
                ex.setVisible(true);
            }
        });
    }
}
