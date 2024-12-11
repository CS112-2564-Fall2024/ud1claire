package org.example.ud1claire;

public class RC4 extends Cipher{
    private byte[] sbox = new byte[256];
    private int a = 0;
    private int b = 0;

    /**
     * Initialize the cipher to encrypt or decrypt data.
     * @param key The secret key to use when encrypting data.
     * @see <a href="https://en.wikipedia.org/wiki/RC4#Key-scheduling_algorithm_(KSA)">RC4 Key Scheduling Algorithm</a>
     *
     */
    public RC4(byte[] key) throws KeySizeError{
        super(key);
        if(key.length < 1 || key.length > 256) {
            throw new KeySizeError("Key must be between 1 and 256 bytes long");
        }
//        this.key = key;
        keyScheduler();
    }

//    Key scheduler
    private void keyScheduler() {
//        Initialize the S-Box with values between 0 and 255
        for (int i = 0; i < 256; i++) {
            sbox[i] = (byte) i;
        }

        int j = 0;

//        Shuffle the S-box using the key
        for (int i = 0; i < 256; i++) {
//            I mask it with 0xff to ensure that it is unsigned per the cipher.
            j = (j + sbox[i] + getKey()[i % getKey().length]) & 0xff;
//            Swap sbox[i] and sbox[j]
            Cipher.Util.swap(sbox, i, j);
        }
    }

    public byte[] process(byte[] input) {
        byte[] result = new byte[input.length];

        for (int i = 0; i < input.length; i++) {
            a = (a + 1) & 0xff;
            b = (b + sbox[a]) & 0xff;

//            Swap sbox[a] and sbox[b]
            Cipher.Util.swap(sbox, a, b);

            // Generate the keystream byte and XOR it with the input byte
//            Generate the byte to be XORed with the current byte of the stream.
            byte keystream = sbox[(sbox[a] + sbox[b]) & 0xff];
            result[i] = (byte) (input[i] ^ keystream);
        }

        return result;
    }

    public byte[] encrypt(byte[] plaintext) {
        return process(plaintext);
    }

    public byte[] decrypt(byte[] ciphertext) {
        return process(ciphertext);
    }
}