package com.dictiographer.desktop.model.entry;

import org.apache.commons.codec.binary.Base64;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;


public class ByteArrayPersistenceDelegate extends PersistenceDelegate {
    protected Expression instantiate(Object oldInstance, Encoder out) {
        byte[] e = (byte[]) oldInstance;
        return new Expression(e, ByteArrayPersistenceDelegate.class, "decode",
                new Object[]{ByteArrayPersistenceDelegate.encode(e)});
    }

// We want methods from this class to appear in the data - the indirection means we're
    // not tied to a particular Base64 implementation and are protected from interface changes.

    public static byte[] decode(String encoded) {
        return Base64.decodeBase64(encoded);
    }

    public static String encode(byte[] data) {
        return Base64.encodeBase64URLSafeString(data);
    }

}

