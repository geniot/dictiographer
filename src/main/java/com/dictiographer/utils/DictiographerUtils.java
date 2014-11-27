package com.dictiographer.utils;

import entry.ByteArrayPersistenceDelegate;
import entry.EntryLink;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;
import java.awt.image.BufferedImage;
import java.beans.PersistenceDelegate;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Vitaly Sazanovich
 * Email: Vitaly.Sazanovich@gmail.com
 * Date: 7/7/14
 */
public class DictiographerUtils {
    private static final Logger logger = Logger.getLogger(DictiographerUtils.class.getName());

    public static Object xml2entry(String content) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(content.getBytes("UTF-8"));
            XMLDecoder decoder = new XMLDecoder(bais);
            Object eom = decoder.readObject();
            decoder.close();
            bais.close();
            return eom;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return new Object();
        }
    }

    public static String entry2xml(Object eom) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            XMLEncoder encoder = new XMLEncoder(baos) {
                public PersistenceDelegate getPersistenceDelegate(Class<?> type) {
                    if (type == byte[].class)
                        return new ByteArrayPersistenceDelegate();
                    else
                        return super.getPersistenceDelegate(type);
                }
            };
            encoder.writeObject(eom);
            encoder.close();
            baos.close();
            return new String(baos.toByteArray(), "UTF-8");
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return "";
        }
    }

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    public static BufferedImage bytesToBufferedImage(byte[] bbs) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(bbs);
            MemoryCacheImageInputStream mem = new MemoryCacheImageInputStream(in);
            return ImageIO.read(mem);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static byte[] bufferedImageToBytes(String fileExt, BufferedImage bi) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, fileExt, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public static String getProperty(String lang, String key) {
        try {
            return Utf8ResourceBundle.getBundle("DictiographerResource", new Locale(lang.toLowerCase()), DictiographerUtils.class.getClassLoader()).getString(key);
        } catch (Exception e) {
            try {
                return Utf8ResourceBundle.getBundle("DictiographerResource", new Locale("en"), DictiographerUtils.class.getClassLoader()).getString(key);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Can't find resource for key: " + key);
                return key;
            }
        }
    }

    public static String links2str(EntryLink[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i].getText());
            if (i < arr.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static EntryLink[] str2links(String str) {
        String[] ss = str.split(",");
        List<EntryLink> els = new ArrayList<EntryLink>();
        for (String s : ss) {
            EntryLink el = new EntryLink();
            el.setText(s.trim());
            els.add(el);
        }
        return els.toArray(new EntryLink[els.size()]);
    }

    public static String arr2str(String[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }


}
