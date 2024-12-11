package org.example.ud1claire;

import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HexFormat;

abstract class Cipher {
    protected byte[] plaintext;
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
            keyGenerator.init(length); // Use 256 bits for strong encryption (ensure JCE policy is installed for Java 8)
            return keyGenerator.generateKey();
        }

        public static boolean isStringAscii(String string){
            // Check if the plaintext string contains only printable ASCII letters so that it can use the byte type.

            for (char c : string.toCharArray()) {
                if (c > 127 || c < 32) {
                    return false;
                }
            }

            return true;
        }

        public static void swap(int[] arr, int a, int b) {
            int buff = arr[a];
            arr[a] = arr[b];
            arr[b] = buff;
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
            String hexString = "";
            for(byte b: s) {
                hexString += Integer.toHexString(0xff&b);
            }

            return hexString;
        }

        public static byte[] hToB(String hex) {
            int len = hex.length();
            byte[] data = new byte[len / 2];
            for (int i = 0; i < len; i += 2) {
                data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                        + Character.digit(hex.charAt(i+1), 16));
            }
            return data;
//            return HexFormat.of().parseHex(hex);
        }

        /**
         * Bitwise circular shift of a number, i, by n places.
         * @param i
         * @param n
         * @return
         */
        public static int leftshift(int i, int n) {
            return (i << n) | (i >>> (32-n));
        }
    }
}
