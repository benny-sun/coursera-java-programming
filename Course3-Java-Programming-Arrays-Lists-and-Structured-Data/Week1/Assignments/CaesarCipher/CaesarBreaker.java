/**
 * @author Benny Sun
 */
import edu.duke.*;
import java.util.*;

public class CaesarBreaker
{
    public String decryptTwoKeys(String encrypted)
    {
        int key1 = getKey(halfOfString(encrypted, 0));
        int key2 = getKey(halfOfString(encrypted, 1));
        System.out.println(String.format("key1:%d, key2:%d", key1, key2));
        
        CaesarCipher cc = new CaesarCipher();
        return cc.encryptTwoKeys(encrypted, 26 - key1, 26 - key2);
    }
    
    public String decrypt(String encrypted)
    {
        CaesarCipher cc = new CaesarCipher();
        int key = getKey(encrypted);
        
        return cc.encrypt(encrypted, 26 - key);
    }
    
    public int[] countLetters(String s)
    {
        int[] ans = new int[26];
        for (char ch: s.toLowerCase().toCharArray()) {
            if (! Character.isLetter(ch)) continue;
            ans[ch - 'a']++;
        }
        
        return ans;
    }
    
    public int maxIndex(int[] values)
    {
        int ans = 0;
        int max = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] > max) {
                max = values[i];
                ans = i;
            }
        }
        
        return ans;
    }
    
    public String halfOfString(String message, int start)
    {
        String ans = "";
        for (int i = start; i < message.length(); i += 2) {
            ans += message.charAt(i);
        }
        
        return ans;
    }
    
    public int getKey(String s)
    {
        int m = maxIndex(countLetters(s));
        int k = m - 4;
        
        return (m < 4) ? k + 26 : k;
    }
    
    public void testDecryptTwoKeys()
    {
        FileResource resource = new FileResource("testing/wordsLotsOfEs.txt");
        String expected = resource.asString();
        
        FileResource resourceEncrypted = new FileResource("testing/wordsLotsOfEsEncrypted.txt");
        String actual = decryptTwoKeys(resourceEncrypted.asString());

        String result = expected.equals(actual) ? "Passed" : "Failure";
        System.out.println(result + " testDecryptTwoKeys()");
    }
    
    public void testHalfOfString()
    {
        String expected = "Qk gs";
        String actual = halfOfString("Qbkm Zgis", 0);
        String result = expected.equals(actual) ? "Passed" : "Failure";
        System.out.println(result + " testHalfOfString()");
        
        expected = "bmZi";
        actual = halfOfString("Qbkm Zgis", 1);
        result = expected.equals(actual) ? "Passed" : "Failure";
        System.out.println(result + " testHalfOfString()");
    }
    
    public void testDecrypt()
    {
        FileResource resource = new FileResource("testing/wordsLotsOfEs.txt");
        String expected = resource.asString();
        
        CaesarCipher cc = new CaesarCipher();
        String actual = decrypt(cc.encrypt(expected, 17));
        
        String result = expected.equals(actual) ? "Passed" : "Failure";
        System.out.println(result + " testDecrypt()");
    }
    
    public void test()
    {
        testDecrypt();
        testHalfOfString();
        testDecryptTwoKeys();
    }
}
