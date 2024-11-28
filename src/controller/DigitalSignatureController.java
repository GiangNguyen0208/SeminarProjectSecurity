package controller;

import java.security.*;
import java.util.Base64;

public class DigitalSignatureController {

    private KeyPairGenerator keyPairGen;
    private KeyPair keyPair;
    private Signature signature;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    public DigitalSignatureController(String alg, String keySize) throws Exception {
        this.keyPair = generateKeyPair(alg, keySize);
        this.publicKey = keyPair.getPublic();
        this.privateKey = keyPair.getPrivate();
    }

    public KeyPair generateKeyPair(String alg, String keySize) throws Exception {
        try {
            String algorithm = getAlgorithms(alg);
            SecureRandom secureRandom = new SecureRandom();
            this.keyPairGen = KeyPairGenerator.getInstance(algorithm);
            this.keyPairGen.initialize(Integer.parseInt(keySize), secureRandom);
            this.keyPair = keyPairGen.generateKeyPair();
            this.publicKey = keyPair.getPublic();
            this.privateKey = keyPair.getPrivate();

            return this.keyPair;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String getPublicKey() {
        return Base64.getEncoder().encodeToString(this.publicKey.getEncoded());
    }

    public String getPrivateKey() {
        return Base64.getEncoder().encodeToString(this.privateKey.getEncoded());
    }

    public String signText(String alg, String plainText) throws Exception {
        try {
            String algorithm = getAlgorithms(alg);
            this.signature = Signature.getInstance(algorithm);
            this.signature.initSign(this.privateKey);
            this.signature.update(plainText.getBytes());
            byte[] signedData = signature.sign();
            return Base64.getEncoder().encodeToString(signedData);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    public boolean verifySigned(String alg, String verifyPlainText, String plainText) throws Exception {
        try {
            String algorithm = getAlgorithms(alg);
            byte[] encryptedText = Base64.getDecoder().decode(signText(algorithm, plainText));
            this.signature = Signature.getInstance(algorithm);
            signature.initVerify(this.publicKey);
            signature.update(verifyPlainText.getBytes());
            boolean isVerified = signature.verify(encryptedText);
            return isVerified;
        } catch (Exception e) {
            throw new Exception("Verification failed: " + e.getMessage());
        }
    }

    public String getAlgorithms(String alg) {
        String result;
        if (alg.equalsIgnoreCase("SHA256withRSA") || alg.equalsIgnoreCase("SHA512withRSA")) {
            result = "RSA";
        }
        if (alg.equalsIgnoreCase("SHA256withECDSA") || alg.equalsIgnoreCase("SHA512withECDSA")) {
            result = "ECDSA";
        } else {
            result = "DSA";
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        String alg = "DSA";
        DigitalSignatureController controller = new DigitalSignatureController(alg, "1024");
        String intput = "Giang";
        String inputVerify = "Giang";
        System.err.println("Hash: " + controller.signText(alg, intput));
        System.out.println("Verify: " + controller.verifySigned("DSA", inputVerify, intput));
    }
}
