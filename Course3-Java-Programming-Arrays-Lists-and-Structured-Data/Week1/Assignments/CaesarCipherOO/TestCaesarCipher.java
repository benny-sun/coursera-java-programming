/**
 * @author Benny Sun
 */
import edu.duke.*;

public class TestCaesarCipher
{
    Helper helper;
    
    public TestCaesarCipher()
    {
        this.helper = new Helper();
    }
    
    public void simpleTests()
    {
        String expected = "First Legion eeeeeeeeeeeeeeeees";

        CaesarCipher cc = new CaesarCipher(18);
        String encrypted= cc.encrypt(expected);
        System.out.println("encrypted: " + encrypted);
        
        String decrypted = breakCaesarCipher(encrypted);
        System.out.println("decrypted: " + decrypted);
        
        String result = expected.equals(decrypted) ? "Passed" : "Failure";
        System.out.println(result + " simpleTests()");
    }
    
    private String breakCaesarCipher(String input)
    {
        int key = this.helper.getKey(input);
        CaesarCipher cc = new CaesarCipher(key);
        
        return cc.decrypt(input);
    }
}
