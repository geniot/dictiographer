package com.dictiographer.controller;

import com.dictiographer.model.Constants;
import com.dictiographer.model.IndexModel;
import com.dictiographer.view.Dictiographer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.swing.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Author: Vitaly Sazanovich
 * Email: Vitaly.Sazanovich@gmail.com
 * Date: 7/6/14
 */
public class ViewController {
    private static ViewController INSTANCE;

    public static ViewController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ViewController();
        }
        return INSTANCE;
    }

    private ViewController() {
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

            StringBuilder sb = new StringBuilder();
            sb.append("<!DOCTYPE html>");
            sb.append("<html>");
            sb.append("<head>");
            sb.append("<style>");
            sb.append(readCSS());
            sb.append("</style>");
            sb.append("</head>");
            sb.append("<body>");
            sb.append(transform(domain, s));
            sb.append("</body>");
            sb.append("</html>");

            return sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private String readCSS() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("transform/style.css");
        byte[] bytes = IOUtils.toByteArray(is);
        return new String(bytes, "UTF-8");
    }

    private String transform(String locale, String s) throws Exception {
        Source xmlInput = new StreamSource(new ByteArrayInputStream(s.getBytes("UTF-8")));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ClassLoader cl = this.getClass().getClassLoader();
        String systemID = "transform/transform.xsl";
        InputStream in = cl.getResourceAsStream(systemID);
        URL url = cl.getResource(systemID);
        Source source = new StreamSource(in);
        source.setSystemId(url.toExternalForm());
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setURIResolver(new ClasspathResourceURIResolver());
        Transformer transformer = transformerFactory.newTransformer(source);
        transformer.setParameter("lang", locale);
        Result xmlOutput = new StreamResult(baos);
        transformer.transform(xmlInput, xmlOutput);
        return new String(baos.toByteArray(), "UTF-8");
    }

    class ClasspathResourceURIResolver implements URIResolver {
        @Override
        public Source resolve(String href, String base) throws TransformerException {
            return new StreamSource(getClass().getClassLoader().getResourceAsStream("transform/" + href));
        }
    }
}
