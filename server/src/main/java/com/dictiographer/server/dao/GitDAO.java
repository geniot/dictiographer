package com.dictiographer.server.dao;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;

public class GitDAO {
    static String URL = "https://github.com/geniot/dictionaries.git";
    static String APP_NAME = ".dictiographer";
    static String USERNAME = "geniot";
    static String PASSWORD = "";

    public static void main(String... args) {
        try {
            String cloneTo = System.getProperty("user.home") + File.separator + APP_NAME;
            CredentialsProvider cp = new UsernamePasswordCredentialsProvider(USERNAME, PASSWORD);
            Git.cloneRepository().setURI(URL).setDirectory(new File(cloneTo)).setCredentialsProvider(cp).call();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
