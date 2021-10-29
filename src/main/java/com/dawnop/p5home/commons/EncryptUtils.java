package com.dawnop.p5home.commons;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;

public class EncryptUtils {
    private EncryptUtils() {
    }

    private static final StandardPBEStringEncryptor encryptor;

    private static final String PASSWORD = "dawn";

    static {
        encryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPassword(PASSWORD);
        encryptor.setConfig(config);
    }

    /**
     * 加密
     */
    public static String encrypt(String text) {
        return encryptor.encrypt(text);
    }

    /**
     * 解密
     */
    public static String decrypt(String ciphertext) {
        return encryptor.decrypt(ciphertext);
    }

}
