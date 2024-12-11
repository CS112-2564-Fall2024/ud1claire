package org.example.ud1claire;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

abstract class Cipher {
    private byte[] key;

    public Cipher(byte[] key) {
        setKey(key);
    }

    protected abstract byte[] encrypt(byte[] plaintext) throws Exception;
    protected abstract byte[] decrypt(byte[] ciphertext) throws Exception;


    public void setKey(byte[] key) {
        this.key = key;
    }


    public byte[] getKey() {
        return this.key;
    }


    public static class Util {
        public static SecretKey generateKey(int length) throws NoSuchAlgorithmException {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(length);
            return keyGenerator.generateKey();
        }

        public static void swap(byte[] arr, int a, int b) {
            byte buff = arr[a];
            arr[a] = arr[b];
            arr[b] = buff;
        }

        /**
         * Converts an array of bytes to a hexadecimal string.
         * @param s Byte array
         * @return Human-readable hexadecimal string.
         */
        public static String bToS(byte[] s) {
            StringBuilder hexString = new StringBuilder();

            for (byte b : s) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        }

        // I did not write this method, I found it on StackOverflow at https://stackoverflow.com/a/140861/24756368.
//        The alternate method of HexFormat.of().parseHex(hex) does not work well enough.
        public static byte[] hToB(String hex) {
            int len = hex.length();
            byte[] data = new byte[len / 2];

            for (int i = 0; i < len; i += 2) {
                    data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                            + Character.digit(hex.charAt(i + 1), 16));
            }
            return data;
        }

        /**
         * Bitwise circular shift of a number, i, by n places.
         */
        public static int leftshift(int i, int n) {
            return (i << n) | (i >>> (32-n));
        }
    }
}
