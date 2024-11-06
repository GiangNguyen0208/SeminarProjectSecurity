package basicModel;

import java.nio.charset.StandardCharsets;

public class SubstitutionCipher {

    private String key;

    public SubstitutionCipher(String key) {
        this.key = key;
    }

    public String encrypt(String text) {
        StringBuilder encryptCode = new StringBuilder();
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        for (byte b : bytes) {
            char ch = (char) b;
            char encryptChar;
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                int index = (ch - base);
                encryptChar = key.charAt(index % key.length());
            } else {
                encryptChar = ch;
            }
            encryptCode.append(encryptChar);
        }
        return encryptCode.toString();
    }

    public String decrypt(String textEncrypt) {
        StringBuilder decryptCode = new StringBuilder();
        for (char ch : textEncrypt.toCharArray()) {
            char decryptChar;
            if (Character.isLetter(ch)) {
                int index = key.indexOf(ch);
                if (index == -1) {
                    decryptChar = ch;
                } else {
                    char base = Character.isUpperCase(ch) ? 'A' : 'a';
                    decryptChar = (char) (base + index);
                }
            } else {
                decryptChar = ch;
            }
            decryptCode.append(decryptChar);
        }
        return decryptCode.toString();
    }
}
