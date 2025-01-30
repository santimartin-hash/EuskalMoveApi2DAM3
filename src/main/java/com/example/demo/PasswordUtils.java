package com.example.demo;
import org.apache.commons.codec.digest.DigestUtils;

public class PasswordUtils {
    public static String hashPassword(String password) {
        return DigestUtils.sha256Hex(password);
    }
}