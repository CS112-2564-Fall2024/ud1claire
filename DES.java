public class DES extends Cipher{
    public DES(String plaintext, String key) throws NonASCIIException {
        super(plaintext, key);
    }

    @Override
    protected byte[] encrypt() {
        return new byte[0];
    }

    @Override
    protected byte[] decrypt() {
        return new byte[0];
    }
}
