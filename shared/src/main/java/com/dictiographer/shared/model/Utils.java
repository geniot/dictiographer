package com.dictiographer.shared.model;

import com.dictiographer.shared.model.lucene.SerializableRAMDirectory;
import com.dictiographer.shared.model.lucene.SerializableRAMFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class Utils {
    private static final Logger logger = Logger.getLogger(Utils.class.getName());

    public static byte[] serialize(Object yourObject) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(yourObject);
            byte[] yourBytes = bos.toByteArray();
            return yourBytes;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                bos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return new byte[]{};
    }

    public static Object deserialize(byte[] yourBytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(yourBytes);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            Object o = in.readObject();
            return o;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public static SerializableRAMDirectory deserializeIndex(byte[] bbs) {
        try {
            SerializableRAMDirectory directory = new SerializableRAMDirectory();
            ByteArrayInputStream bais = new ByteArrayInputStream(bbs);
            ObjectInput input = new ObjectInputStream(bais);
            Integer fileMapSize = (Integer) input.readObject();
            directory.fileMap = new ConcurrentHashMap<>();
            for (int i = 0; i < fileMapSize; i++) {
                String key = (String) input.readObject();
                SerializableRAMFile file = new SerializableRAMFile();
                file.directory = directory;
                file.buffers = (ArrayList<byte[]>) input.readObject();
                file.sizeInBytes = (long) input.readObject();
                file.length = (long) input.readObject();
                directory.fileMap.put(key, file);
            }
            directory.sizeInBytes = (AtomicLong) input.readObject();
            return directory;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static byte[] serializeIndex(SerializableRAMDirectory directory) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(baos);
            out.writeObject(directory.fileMap.size());
            for (Map.Entry<String, SerializableRAMFile> entry : directory.fileMap.entrySet()) {
                out.writeObject(entry.getKey());
                out.writeObject(entry.getValue().buffers);
                out.writeObject(entry.getValue().sizeInBytes);
                out.writeObject(entry.getValue().length);
            }
            out.writeObject(directory.sizeInBytes);
            return baos.toByteArray();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
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

