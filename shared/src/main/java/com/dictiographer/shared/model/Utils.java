package com.dictiographer.shared.model;

import com.dictiographer.shared.model.lucene.SerializableRAMDirectory;
import com.dictiographer.shared.model.lucene.SerializableRAMFile;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Utils {

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
                // ignore close exception
            }
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
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
                // ignore close exception
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
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
}

