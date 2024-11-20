public class RC4 extends Cipher {
    private byte[] S = new byte[256];

    public RC4(String plaintext, String key) throws NonASCIIException {
        super(plaintext, key);

        // Key scheduling algorithm
        keyScheduler();
    }

    public byte[] getS() {
        return this.S;
    }

    public void keyScheduler() {
        for(int i = 0; i < 256; i++) {
//            Initialize array 0 through 256
            S[i] = (byte) i;
        }

        int j = 0;
        for(int h = 0; h < 256; h++) {
//            Everything is masked just to make sure it is all unsigned.
            j = (j + (getS()[h] & 0xFF) + (getKey()[h % getKey().length] & 0xFF)) & 0xFF;
//            Swap current iteration with j in the scheduled key.
            Util.swap(getS(), h, j);
        }
    }

    protected byte[] encrypt() {
        int a = 0,  b = 0;
        int keystream;
        byte[] text = getPlaintext();
        byte[] ciphertext = new byte[text.length];
        byte[] S = getS();

        for(int i = 0; i < text.length; i++) {
//            Add 1 to a and make it unsigned by masking it.
            a = (a + 1) & 0xFF;
            b = (b + (S[a] & 0xFF)) & 0xFF;
            Util.swap(S, a, b);
//            Get the keystream used to XOR the plaintext
            keystream = (S[(S[a] + S[b]) & 0xFF]) & 0xFF;
//            XOR (exclusive or) on plaintext with keystream to make ciphertext of current byte.
            ciphertext[i] = (byte) ((text[i] & 0xFF) ^ keystream);
        }

        return ciphertext;
    }

//    Encryption and decryption are the same data transformations 
    protected byte[] decrypt() {
        return this.encrypt();
    }
}
