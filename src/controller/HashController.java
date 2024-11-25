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
}
