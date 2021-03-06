package com.dictiographer.controller;

import com.dictiographer.utils.DictiographerUtils;
import entry.EntryObjectModel;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URLEncoder;

/**
 * Author: Vitaly Sazanovich
 * Email: Vitaly.Sazanovich@gmail.com
 * Date: 7/7/14
 */
public class DictionaryUtilsTest extends TestCase {
    public void testXml2Entry() throws Exception {
        File file = new File("data" + File.separator + "by" + File.separator + URLEncoder.encode("панядзелак", "UTF-8"));
        String s = FileUtils.readFileToString(file, "UTF-8");
        EntryObjectModel eom = (EntryObjectModel) DictiographerUtils.xml2entry(s);


    }
}
