package controller;

import java.io.*;
import java.math.BigInteger;
import java.security.*;

public class HashController {

    MessageDigest md;
    BigInteger bigInteger;
    byte[] buff, digest;
    File file;
    InputStream input;
    DigestInputStream digestInputStream;

    public HashController(String alg) throws Exception {
        this.md = MessageDigest.getInstance(alg);
    }

    public String hashPlainText(String input) throws Exception {
        try {
            this.buff = input.getBytes();
            this.digest = this.md.digest(this.buff);
            this.bigInteger = new BigInteger(1, digest);
            // Return Values.
            return this.bigInteger.toString(16);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String hashFile(String src) throws Exception {
        try {
            this.file = new File(src);
            this.input = new BufferedInputStream(new FileInputStream(this.file));
            this.digestInputStream = new DigestInputStream(this.input, this.md);
            byte[] buff = new byte[1024];
            int data;
            if ((data = this.digestInputStream.read(buff)) != -1) {
                data = this.digestInputStream.read(buff);
            }
            this.bigInteger = new BigInteger(1, this.digestInputStream.getMessageDigest().digest());
            return this.bigInteger.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    public boolean verifyPlainText(String input, String inputVerify) throws Exception {
        try {
            String encryptPlainText = hashPlainText(input);
            String verifyText = hashPlainText(inputVerify);
            if (verifyText.equals(encryptPlainText)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public boolean verifyFile(String src, String srcVerify) throws Exception {
        try {
            String encryptFile = hashFile(src);
            String verifyEncryptFile = hashFile(srcVerify);
            if (verifyEncryptFile.equals(encryptFile)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        HashController hash = new HashController("MD5");
        String plainText = "Giang Truong Nguyen";
        String src = "C:\\Users\\COHOTECH.VN\\Documents\\decodeFile.txt";
        String encodeText = hash.hashPlainText(plainText);
        String encodeFile = hash.hashFile(src);
        System.err.println("Encode Plain Text " + plainText + " is: " + encodeText);
        System.err.println("Encode Plain Text <" + src + "> is: " + encodeFile);
    }
}
