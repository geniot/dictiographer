package com.dictiographer.controller;

import com.dictiographer.model.Constants;
import com.dictiographer.model.IndexModel;
import entry.EntryObjectModel;
import com.dictiographer.view.Dictiographer;
import freemarker.template.*;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * Author: Vitaly Sazanovich
 * Email: Vitaly.Sazanovich@gmail.com
 * Date: 7/6/14
 */
public class ViewController {
    private static ViewController INSTANCE;
    private Configuration cfg;

    public static ViewController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ViewController();
        }
        return INSTANCE;
    }

    private ViewController() {

        cfg = new Configuration();

        cfg.setClassForTemplateLoading(this.getClass(), "/");

        cfg.setObjectWrapper(new DefaultObjectWrapper());
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));


        try {
            Constants.PROPS.load(Launcher.class.getClassLoader().getResourceAsStream("app.properties"));
            FileInputStream fis = new FileInputStream(getUserPropsFilePath());
            Constants.PROPS.loadFromXML(fis);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            File f = new File(Constants.PROPS.getProperty(Constants.DATA_FOLDER_PROP_KEY));
            if (!f.exists()) {
                f.mkdirs();
            }
            String[] domains = f.list();
            Arrays.sort(domains);
            IndexModel.getInstance().setDomains(domains);

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Dictiographer view = new Dictiographer();
                view.setVisible(true);
            }
        });
    }


    public void onClosing(Dictiographer view) {
        Constants.PROPS.setProperty(Constants.X_POS_PROP_KEY, String.valueOf(view.getLocation().getX()));
        Constants.PROPS.setProperty(Constants.Y_POS_PROP_KEY, String.valueOf(view.getLocation().getY()));
        Constants.PROPS.setProperty(Constants.WIDTH_PROP_KEY, String.valueOf(view.getSize().getWidth()));
        Constants.PROPS.setProperty(Constants.HEIGHT_PROP_KEY, String.valueOf(view.getSize().getHeight()));
        Constants.PROPS.setProperty(Constants.DIVIDER_PROP_KEY, String.valueOf(view.getDividerLocation()));
        Constants.PROPS.setProperty(Constants.SELECTED_DOMAIN_PROP_KEY, String.valueOf(view.getSelectedDomain()));
        Constants.PROPS.setProperty(Constants.SELECTED_WORD_PROP_KEY, String.valueOf(view.getSelectedWord()));

        try {
            FileOutputStream fos = new FileOutputStream(getUserPropsFilePath());
            Constants.PROPS.storeToXML(fos, "Dictiographer user properties", "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        view.dispose();
    }

    private String getUserPropsFilePath() {
        return System.getProperty("user.home") + File.separator + "dictiographer.properties";
    }

    public String[] getIndex(String domain) {
        try {
            File f = new File(Constants.PROPS.getProperty(Constants.DATA_FOLDER_PROP_KEY) + File.separator + domain);
            Set<String> s = new TreeSet<String>();
            String[] fns = f.list();
            for (String fn : fns) {
                s.add(URLDecoder.decode(fn, "UTF-8"));
            }
            return s.toArray(new String[s.size()]);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new String[]{};
        }
    }

    public String getEntry(String domain, String hw) {
        try {
            File file = new File(Constants.PROPS.getProperty(Constants.DATA_FOLDER_PROP_KEY) + File.separator + domain + File.separator + URLEncoder.encode(hw, "UTF-8"));
            String s = FileUtils.readFileToString(file, "UTF-8");
            EntryObjectModel eom = (EntryObjectModel) DictiographerUtils.xml2entry(s);
            return convert(eom);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public String convert(EntryObjectModel eom) throws Exception {
        Map root = new HashMap();
        root.put("entry", eom);
        Template temp = cfg.getTemplate("templates/main.ftl");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Writer out = new OutputStreamWriter(baos);
        temp.process(root, out);
        return new String(baos.toByteArray(), "UTF-8");
    }

}
