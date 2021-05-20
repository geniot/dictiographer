package com.dictiographer.model;

import com.dictiographer.collections.IndexedTreeMap;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class ZipDictionary implements IDictionary {
    private FileSystem zipFileSystem;
    private IndexedTreeMap<String, Integer> headword2id = new IndexedTreeMap<>();
    private MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

    public ZipDictionary(File zipFile) throws Exception {
        Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        zipFileSystem = FileSystems.newFileSystem(zipFile.toPath(), env);
    }

    @Override
    public int getSize() {
        return headword2id.size();
    }

    @Override
    public void createOrUpdate(String headword, String entry) {
        //get path from headword


        //put entry to path

        //update index

        //update full-text index

        //update headword2path
    }

    @Override
    public String read(String headword) {
        return null;
    }


    @Override
    public void delete(String headword) {

    }
}
