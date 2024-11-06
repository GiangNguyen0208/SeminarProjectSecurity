package controller;

import basicModel.CaesarCipher;
import basicModel.SubstitutionCipher;
import java.util.Base64;

public class BasicController {

    private CaesarCipher caesarCipher;
    private SubstitutionCipher substitutionCipher;
    private String key;

    public BasicController() {
        this.key = "QWERTYUIOPASDFGHJKLZXCVBNM";
        caesarCipher = new CaesarCipher(3);
        substitutionCipher = new SubstitutionCipher(key);
    }

    public String encryptCaesar(String text) {
        return caesarCipher.encrypt(text);
    }

    public String decryptCaesar(String text) {
        return caesarCipher.decrypt(text);
    }

    public String encryptSubstitution(String text) {
        return substitutionCipher.encrypt(text);
    }

    public String decryptSubstitution(String text) {
        return substitutionCipher.decrypt(text);
    }

    public String encryptBase64(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }

    public String decryptBase64(String text) {
        return new String(Base64.getDecoder().decode(text));
    }

    public void setCaesarShift(int shift) {
        caesarCipher = new CaesarCipher(shift);
    }

    public void setSubstitutionKey(String key) {
        substitutionCipher = new SubstitutionCipher(key);
    }
}
