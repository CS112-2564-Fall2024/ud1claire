public class Main {
    public static void main(String[] args) throws NonASCIIException {
        Tester tester = new Tester();
        tester.testAll();

        RC4 cipher = new RC4("Attack at dawn", "Secret");
        System.out.println(Util.getHex(cipher.encrypt()));
    }
}
