/**
 * @author Benny Sun
 */
import edu.duke.*;

public class TestCaesarCipherTwo
{
    Helper helper;
    
    public TestCaesarCipherTwo()
    {
        this.helper = new Helper();
    }
    
    public void simpleTests()
    {
        String expected = "First Legion eeeeeeeeeeeeeeeees";

        CaesarCipherTwo cct = new CaesarCipherTwo(17, 3);
        String encrypted= cct.encrypt(expected);
        System.out.println("encrypted: " + encrypted);
        
        String decrypted = breakCaesarCipher(encrypted);
        System.out.println("decrypted: " + decrypted);
        
        String result = expected.equals(decrypted) ? "Passed" : "Failure";
        System.out.println(result + " simpleTests()");
    }
    
    public String breakCaesarCipher(String input)
    {
        int key1 = this.helper.getKey(this.helper.halfOfString(input, 0));
        int key2 = this.helper.getKey(this.helper.halfOfString(input, 1));
        CaesarCipherTwo cct = new CaesarCipherTwo(key1, key2);
        
        System.out.println(String.format("key1:%d, key2:%d", key1, key2));

        return cct.decrypt(input);
    }
}
