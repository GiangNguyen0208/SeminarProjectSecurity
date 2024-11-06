package basicModel;

import java.nio.charset.StandardCharsets;

public class CaesarCipher {

    private int shift;

    public CaesarCipher(int shift) {
        this.shift = shift;
    }

    public String encrypt(String text) {
        StringBuilder encrypted = new StringBuilder();
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        for (byte b : bytes) {
            char ch = (char) b;
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                char encryptChar = (char) ((ch - base + this.shift) % 26 + base);
                encrypted.append(encryptChar);
            } else if (Character.isDigit(ch)) {
                char encryptChar = (char) ((ch - '0' - this.shift + 10) % 10 + '0');
                encrypted.append(encryptChar);
            } else {
                encrypted.append(ch);
            }
        }
        return encrypted.toString();
    }

    public String decrypt(String text) {
        StringBuilder decrypted = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                char shifted = (char) ((ch - base - this.shift + 26) % 26 + base);
                decrypted.append(shifted);
            } else if (Character.isDigit(ch)) {
                char decryptChar = (char) ((ch - '0' - this.shift + 10) % 10 + '0');
                decrypted.append(decryptChar);
            } else {
                decrypted.append(ch);
            }
        }
        return decrypted.toString();
    }
}
