import java.security.SecureRandom;
import java.security.SecureRandomParameters;

public class Util {
    public static byte[] generateKey(int length) {
        byte[] key = new byte[length];
        SecureRandomParameters parameters;
        new SecureRandom().nextBytes(key);
        return key;
    }

    public static boolean isStringAscii(String string) throws NonASCIIException {
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

    public static String getHex(byte[] s) {
        String hexString = "";
        for(byte b: s) {
            hexString += String.format("%02x", b);
        }

        return hexString;
    }
}
