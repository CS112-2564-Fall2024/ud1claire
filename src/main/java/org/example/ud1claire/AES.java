package org.example.ud1claire;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Base64;


public class AES extends org.example.ud1claire.Cipher{

    public AES(byte[] plaintext) {
        super(plaintext);
    }

    @Override
    protected byte[] encrypt(byte[] plaintext) throws Exception {
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES/ECB/PKCS5Padding ");
        byte[] k = Util.generateKey(128).getEncoded();
        SecretKey key = new SecretKeySpec(k,0, k.length,"AES");
        System.out.println(key.getEncoded().length);

        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE,key);
        byte[] ciphertext = cipher.doFinal(plaintext);

        System.out.println(key.getEncoded().length);

        this.setKey(key.getEncoded());

        return ciphertext;
    }


    @Override
    protected byte[] decrypt(byte[] ciphertext) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(getKey(),0, getKey().length,"AES" ));
        byte[] decrypted = cipher.doFinal(ciphertext);
        return decrypted;
    }
}
