package org.example.ud1claire;

import java.security.SecureRandom;

abstract class Cipher {
    protected byte[] plaintext;
    private byte[] key;

    public Cipher(byte[] plaintext, byte[] key) {
        setPlaintext(plaintext);
        setKey(key);
    }

    protected abstract byte[] encrypt();
    protected abstract byte[] decrypt();

    public void setPlaintext(byte[] plaintext) {
        this.plaintext = plaintext;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public byte[] getPlaintext() {
        return this.plaintext;
    }

    public byte[] getKey() {
        return this.key;
    }

    public String encryptS() {return Util.bToS(encrypt());}
    public String decryptS() {return Util.bToS(decrypt());}

    public static class Util {
        public static byte[] generateKey(int length) {
            byte[] key = new byte[length];
//        SecureRandomParameters parameters;
            new SecureRandom().nextBytes(key);
            return key;
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

        public static byte[] hToS(String hex) {
            byte[] result = new byte[hex.length()];
            char[] arr = hex.toCharArray();

            for(int i = 0; i < hex.length(); i++) {
                result[i] = (byte) arr[i];
            }

            return result;
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
