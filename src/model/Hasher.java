package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class used to hash the password to protect it
 * and provide a certain security level
 */
public class Hasher {
    /**
     * Takes a String and returs its SHA-256 hash
     * @param input The text is to be hashed
     * @return The hashed value as a hexadecimal String
     * @throws RuntimeException if SHA-256 is not supported
     * @author Lilas Beirakdar
     */

    public static String hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
