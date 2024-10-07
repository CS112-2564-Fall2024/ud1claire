abstract class Cipher {
    protected byte[] plaintext;
    private byte[] key;

    public Cipher(String plaintext, String key) {
        setPlaintext(plaintext);
        this.key = key.getBytes();
    }

    protected abstract byte[] encrypt();
    protected abstract byte[] decrypt();

    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext.getBytes();
    }

    public byte[] getPlaintext() {
        return this.plaintext;
    }

    public byte[] getKey() {
        return this.key;
    }
}
