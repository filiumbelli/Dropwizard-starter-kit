package com.sofisticat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Hasher {
    private static final SecureRandom random = new SecureRandom();
    private static final byte[] salt = new byte[16];
    private static final Logger logger = LoggerFactory.getLogger(Hasher.class);

    public static MessageDigest getMessageDigestSHA512() {
        try {
            random.nextBytes(salt);
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            return md;
        } catch (NoSuchAlgorithmException ex) {
            logger.error("No such an algorithm" + ex.getMessage());
        }
        return null;
    }

}
