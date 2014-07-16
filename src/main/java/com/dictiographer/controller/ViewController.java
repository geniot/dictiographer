package com.dictiographer.controller;

import com.dictiographer.model.Constants;
import com.dictiographer.utils.DictiographerUtils;
import com.dictiographer.utils.FreeMarkerConfigurer;
import com.dictiographer.view.*;
import com.dictiographer.view.dialogs.EntryDialog;
import entry.EntryObjectModel;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.springframework.context.MessageSource;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Vitaly Sazanovich
 * Email: Vitaly.Sazanovich@gmail.com
 * Date: 7/6/14
 */
public class ViewController implements Bindable {
    private static final Logger logger = Logger.getLogger(ViewController.class.getName());

    private FreeMarkerConfigurer freemarkerConfig;
    private Dictiographer view;
    private MessageSource messageSource;
    private Properties dictiographerProperties;


    public void init() {
        try {
            FileInputStream fis = new FileInputStream(getUserPropsFilePath());
            dictiographerProperties.loadFromXML(fis);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                view = new Dictiographer(ViewController.this, dictiographerProperties);
                view.setVisible(true);
            }
        });
    }


    public void onClosing(Dictiographer view) {
        dictiographerProperties.setProperty(Constants.X_POS_PROP_KEY, String.valueOf(view.getLocation().getX()));
        dictiographerProperties.setProperty(Constants.Y_POS_PROP_KEY, String.valueOf(view.getLocation().getY()));
        dictiographerProperties.setProperty(Constants.WIDTH_PROP_KEY, String.valueOf(view.getSize().getWidth()));
        dictiographerProperties.setProperty(Constants.HEIGHT_PROP_KEY, String.valueOf(view.getSize().getHeight()));
        dictiographerProperties.setProperty(Constants.DIVIDER_PROP_KEY, String.valueOf(view.getDividerLocation()));
        dictiographerProperties.setProperty(Constants.SELECTED_DOMAIN_PROP_KEY, String.valueOf(view.getSelectedDomain()));
        dictiographerProperties.setProperty(Constants.SELECTED_WORD_PROP_KEY, String.valueOf(view.getSelectedWord()));

        try {
            FileOutputStream fos = new FileOutputStream(getUserPropsFilePath());
            dictiographerProperties.storeToXML(fos, "Dictiographer user properties", "UTF-8");
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
            File f = new File(dictiographerProperties.getProperty(Constants.DATA_FOLDER_PROP_KEY) + File.separator + domain);
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
            File file = new File(dictiographerProperties.getProperty(Constants.DATA_FOLDER_PROP_KEY) + File.separator + domain + File.separator + URLEncoder.encode(hw, "UTF-8"));
            String s = FileUtils.readFileToString(file, "UTF-8");
            EntryObjectModel eom = (EntryObjectModel) DictiographerUtils.xml2entry(s);
            return convert(eom);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public String convert(EntryObjectModel eom) throws Exception {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("entry", eom);
        Template temp = freemarkerConfig.getConfiguration().getTemplate("main.ftl");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Writer out = new OutputStreamWriter(baos);
        temp.process(root, out);
        return new String(baos.toByteArray(), "UTF-8");
    }

    public void onEntryEdit(String domain, String hw) {
        try {
            File file = new File(dictiographerProperties.getProperty(Constants.DATA_FOLDER_PROP_KEY) + File.separator + domain + File.separator + URLEncoder.encode(hw, "UTF-8"));
            String s = FileUtils.readFileToString(file, "UTF-8");
            EntryObjectModel eom = (EntryObjectModel) DictiographerUtils.xml2entry(s);

            ThreadContext context = new ThreadContext();
            Locale loc = new Locale(domain);
            context.setLocale(loc);
            context.setLocalizer(new MyLocalizer(loc));
            context.setMessageSource(messageSource);
            MyThreadLocal.set(context);

            EntryDialog entryDialog = new EntryDialog(view, this, eom, Constants.UPDATE_ACTION);
            entryDialog.setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void setData(Object data) {
        System.out.println(data);
    }

    @Override
    public Object getData(Object data) {
        throw new NotImplementedException();
    }

    @Override
    public boolean isEmpty() {
        throw new NotImplementedException();
    }

    @Override
    public JPanel getMainPanel() {
        throw new NotImplementedException();
    }

    public String[] getDomains() {
        try {
            File f = new File(dictiographerProperties.getProperty(Constants.DATA_FOLDER_PROP_KEY));
            if (!f.exists()) {
                boolean res = f.mkdirs();
                if (!res) {
                    logger.log(Level.WARNING, "Couldn't create folder in " + f.getAbsolutePath());
                }
            }
            String[] domains = f.list();
            Arrays.sort(domains);
            return domains;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new String[]{};
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public Properties getDictiographerProperties() {
        return dictiographerProperties;
    }

    public void setDictiographerProperties(Properties dictiographerProperties) {
        this.dictiographerProperties = dictiographerProperties;
    }

    public FreeMarkerConfigurer getFreemarkerConfig() {
        return freemarkerConfig;
    }

    public void setFreemarkerConfig(FreeMarkerConfigurer freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
    }
}
