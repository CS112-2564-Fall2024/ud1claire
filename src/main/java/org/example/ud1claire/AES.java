package org.example.ud1claire;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/*
* This one does not work either. I'm not entirely sure what went wrong but the errors I was getting primarily revolved
* around the key size being wrong during decryption. Somehow the key size was between 28-32 bytes, but even when it was
* exactly 32 bytes, it didn't work. It should have been exactly 16 bytes because the key size I tried to use was 128
* bits (16 bytes). It works perfectly fine for encryption. I also had some trouble figuring out how to deal with
* passing the key to the UI since it's randomly generated opposed to the RC4 key being user made.
*/

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
