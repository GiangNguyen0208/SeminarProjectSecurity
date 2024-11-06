package utils;

import java.security.SecureRandom;
import java.util.Base64;

public class KeyGenerator {

    public static String generateNewKey() {
        // Generate a random key using SecureRandom
        byte[] key = new byte[16]; // Example for AES (128-bit key)
        new SecureRandom().nextBytes(key);
        return Base64.getEncoder().encodeToString(key); // Encode to Base64 for easy display
    }
}
