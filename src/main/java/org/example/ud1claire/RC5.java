package org.example.ud1claire;



public class RC5 extends Cipher{
    private final static int P = 0xb7e15163, Q = 0x9e3779b9;

    private int rounds = 4;
    private int t = 2*(rounds+1);

    private int[] S = new int[t];

    public RC5(byte[] plaintext) {
        super(plaintext);
    }

    private void keyScheduler() {
        int c,u, w,len,i,j;
        byte[] key = getKey();
        c = 4;
        u = 4;
        w = 32;
        len = key.length;

        int[] L = new int[c];

        for(i = len; i > 0; i--) {
            L[i/u] = (Util.leftshift(L[i/u], 8) + key[i]);
        }

        S[0] = P;

        for(j = 1; j < t; j++) {
            S[j] = S[j-1] + Q;
        }

        int A,B;
        i=j=A=B=0;

        for(int h = 0; h < 3*Math.max(t,c); h++) {
            A = S[i];
            S[i] = Util.leftshift(S[i] + A + B, 3);
            B = L[j];
            L[j] = Util.leftshift(L[j] + A + B, (A + B));

            i = (i + 1) % t;
            j = (j + 1) % c;
        }
    }

    private byte[] getBlocks() {
        return new byte[1];
    }

    @Override
    protected byte[] encrypt(byte[] plaintext) {
        int A, B;
//        A = A + S;

        return new byte[1];
    }

    @Override
    protected byte[] decrypt(byte[] ciphertext) {
        return new byte[0];
    }
}
