import java.util.ArrayList;

public class Tester {
    public ArrayList<Boolean> testAll() throws NonASCIIException {
        Cipher[] objs = {new RC4("test", "key"), new DES("test", "key")};
        ArrayList<Boolean> result = new ArrayList<>();
        for(Cipher obj: objs) {
            result.add(obj.decrypt().equals( "test"));
            obj.decrypt();
        }

        return result;
    }
}
