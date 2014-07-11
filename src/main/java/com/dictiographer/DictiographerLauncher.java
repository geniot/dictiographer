package com.dictiographer;

/**
 * Author: Vitaly Sazanovich
 * Email: vitaly.sazanovich@gmail.com
 * Date: 11/07/14
 */

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DictiographerLauncher {
    public static void main(String[] args) {
        String[] contextPaths = new String[]{"context/app-context.xml"};
        new ClassPathXmlApplicationContext(contextPaths);
    }
}
