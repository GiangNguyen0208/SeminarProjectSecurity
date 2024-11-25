package controller;

import java.io.*;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SymmetricController {

    private SecretKey secretKey;
    private byte[] iv;

    public SymmetricController(String alg, String keySize, String base64Key) throws Exception {
        if (base64Key != null && !base64Key.isEmpty()) {
            byte[] decodedKey = Base64.getDecoder().decode(base64Key);
            this.secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, alg);
        } else {
            generateKey(alg, keySize);
        }
    }

    public String generateKey(String alg, String keySize) throws Exception {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(alg);
            keyGen.init(Integer.parseInt(keySize));
            this.secretKey = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(this.secretKey.getEncoded());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String encrypt(String alg, String mode, String padding, String input) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(alg + "/" + mode + "/" + padding);
            if ("CBC".equals(mode)) {
                this.iv = new byte[cipher.getBlockSize()];
                new java.security.SecureRandom().nextBytes(iv);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            }
            byte[] encrypted = cipher.doFinal(input.getBytes("UTF-8"));

            byte[] encryptedWithIv = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, encryptedWithIv, 0, iv.length);
            System.arraycopy(encrypted, 0, encryptedWithIv, iv.length, encrypted.length);

            return Base64.getEncoder().encodeToString(encryptedWithIv);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    public String decrypt(String alg, String mode, String padding, String input) throws Exception {
        try {
            input = input.trim();
            byte[] encryptedWithIv = Base64.getDecoder().decode(input);

            if (encryptedWithIv.length < 16) { // AES block size is 16 bytes
                throw new Exception("Invalid encrypted data: not enough bytes to extract IV.");
            }

            this.iv = new byte[16]; // AES block size is 16 bytes
            System.arraycopy(encryptedWithIv, 0, iv, 0, iv.length);
            byte[] encrypted = new byte[encryptedWithIv.length - iv.length];
            System.arraycopy(encryptedWithIv, iv.length, encrypted, 0, encrypted.length);

            Cipher cipher = Cipher.getInstance(alg + "/" + mode + "/" + padding);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
            byte[] decrypted = cipher.doFinal(encrypted);

            return new String(decrypted);
        } catch (IllegalArgumentException e) {
            throw new Exception("Invalid Base64 input: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    public void encryptFile(String alg, String mode, String padding, File inputFile, File outputFile) throws Exception {
        try (FileInputStream fis = new FileInputStream(inputFile); FileOutputStream fos = new FileOutputStream(outputFile)) {
            Cipher cipher = Cipher.getInstance(alg + "/" + mode + "/" + padding);
            if ("CBC".equals(mode) || "CFB".equals(mode) || "OFB".equals(mode)) {
                this.iv = new byte[cipher.getBlockSize()];
                new java.security.SecureRandom().nextBytes(iv);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            }

            byte[] inputBytes = new byte[(int) inputFile.length()];
            fis.read(inputBytes);
            byte[] outputBytes = cipher.doFinal(inputBytes);
            fos.write(outputBytes);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void decryptFile(String alg, String mode, String padding, File inputFile, File outputFile) throws Exception {
        try (FileInputStream fis = new FileInputStream(inputFile); FileOutputStream fos = new FileOutputStream(outputFile)) {
            byte[] encryptedWithIv = new byte[(int) inputFile.length()];
            fis.read(encryptedWithIv);

            if (encryptedWithIv.length < 16) { // Kiểm tra độ dài dữ liệu
                throw new Exception("Invalid encrypted data: not enough bytes to extract IV.");
            }

            this.iv = new byte[16]; // Kích thước khối AES là 16 byte
            System.arraycopy(encryptedWithIv, 0, iv, 0, iv.length);
            byte[] encrypted = new byte[encryptedWithIv.length - iv.length];
            System.arraycopy(encryptedWithIv, iv.length, encrypted, 0, encrypted.length);

            Cipher cipher = Cipher.getInstance(alg + "/" + mode + "/" + padding);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv)); // Khởi tạo cipher với IV
            byte[] decrypted = cipher.doFinal(encrypted);
            fos.write(decrypted);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
