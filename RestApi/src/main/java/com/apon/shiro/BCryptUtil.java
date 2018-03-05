package com.apon.shiro;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptUtil {

    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(12));
    }

    public static boolean checkPassword(String plainTextPassword, String storedHash) {
        if (storedHash == null || !storedHash.startsWith("$2a$")) {
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
        }

        return BCrypt.checkpw(plainTextPassword, storedHash);
    }
}
