package controller;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import javax.crypto.Cipher;

public class AsymmetricController {

    private PublicKey publicKey;
    private PrivateKey privateKey;

    public AsymmetricController(String alg, String sizeKey) throws Exception {
        KeyPair keyPair = generateKeyPair(alg, sizeKey);
        this.publicKey = keyPair.getPublic();
        this.privateKey = keyPair.getPrivate();
    }

    public KeyPair generateKeyPair(String alg, String sizeKey) throws Exception {
        try {
            SecureRandom secureRandom = new SecureRandom();
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(alg);
            keyPairGenerator.initialize(Integer.parseInt(sizeKey), secureRandom);

            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String getPublicKey() {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public String getPrivateKey() {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    public String encrypt(String alg, String mode, String padding, String input) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(alg + "/" + mode + "/" + padding);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedText = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(encryptedText);
        } catch (Exception e) {
            throw new Exception("Encryption error: " + e.getMessage());
        }
    }

    public String decrypt(String alg, String mode, String padding, String input) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(alg + "/" + mode + "/" + padding);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedText = cipher.doFinal(Base64.getDecoder().decode(input));
            return new String(decryptedText, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new Exception("Decryption error: " + e.getMessage());
        }
    }
}
