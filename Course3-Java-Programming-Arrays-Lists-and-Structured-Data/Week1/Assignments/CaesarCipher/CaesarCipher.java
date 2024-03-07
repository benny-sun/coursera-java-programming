/**
 * @author Benny Sun
 */
import edu.duke.*;

public class CaesarCipher
{
    public String encrypt(String input, int key)
    {
        String alphabetUpper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String alphabetLower = alphabetUpper.toLowerCase();
        String dictionary = alphabetUpper.substring(26 - key) + alphabetUpper.substring(0, 26 - key);
        StringBuilder ans = new StringBuilder(input);

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            int idx = dictionary.indexOf(Character.toUpperCase(ch));

            if (idx == -1) continue;

            char newChar = Character.isUpperCase(ch)
                ? alphabetUpper.charAt(idx)
                : alphabetLower.charAt(idx);
            ans.setCharAt(i, newChar);
        }

        return ans.toString();
    }

    public String encryptApproach2(String input, int key)
    {
        StringBuilder ans = new StringBuilder(input);
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (! Character.isAlphabetic(ch)) continue;

            int ascii = (int) ch;
            int start = (int) 'a';
            int end = (int) 'z';
            if (Character.isUpperCase(ch)) {
                start = (int) 'A';
                end = (int) 'Z';
            }
            int newAscii = (ascii + key) > end
                ? start + (ascii + key) % end - 1
                : ascii + key;

            ans.setCharAt(i, (char) newAscii);
        }

        return ans.toString();
    }
    
    public String encryptTwoKeys(String input, int key1, int key2)
    {
        StringBuilder ans = new StringBuilder(input);
        
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (Character.isAlphabetic(ch)) {
                boolean isOdd = ((i + 1) & 1) == 1;
                String s = isOdd
                    ? encrypt(String.valueOf(ch), key1)
                    : encrypt(String.valueOf(ch), key2);
                ans.setCharAt(i, s.charAt(0));
            }
        }

        return ans.toString();
    }

    public void testEncrypt()
    {
        // Approch 1 (Use dictionary)
        String expected = "CFOPQ IBDFLK XQQXZH BXPQ CIXKH!";
        String actual = encrypt("FIRST LEGION ATTACK EAST FLANK!", 23);
        String result = expected.equals(actual) ? "Passed" : "Failure";
        System.out.println(result + " testEncrypt()");

        expected = "Cfopq Ibdflk";
        actual = encrypt("First Legion", 23);
        result = expected.equals(actual) ? "Passed" : "Failure";
        System.out.println(result + " testEncrypt()");

        expected = "Wzijk Cvxzfe";
        actual = encrypt("First Legion", 17);
        result = expected.equals(actual) ? "Passed" : "Failure";
        System.out.println(result + " testEncrypt()");

        // Approch 2 (ASCII code manipulatoin)
        expected = "CFOPQ IBDFLK XQQXZH BXPQ CIXKH!";
        actual = encryptApproach2("FIRST LEGION ATTACK EAST FLANK!", 23);
        result = expected.equals(actual) ? "Passed" : "Failure";
        System.out.println(result + " encryptApproach2()");

        expected = "Cfopq Ibdflk";
        actual = encryptApproach2("First Legion", 23);
        result = expected.equals(actual) ? "Passed" : "Failure";
        System.out.println(result + " encryptApproach2()");

        expected = "Wzijk Cvxzfe";
        actual = encryptApproach2("First Legion", 17);
        result = expected.equals(actual) ? "Passed" : "Failure";
        System.out.println(result + " encryptApproach2()");

    }

    public void testCaesar()
    {
        FileResource fr = new FileResource();
        String message = fr.asString();
        int key = 23;
        String encrypted = encrypt(message, key);
        System.out.println("key is " + key + "\n" + encrypted);
        
        int key1 = 23, key2 = 2;
        encrypted = encryptTwoKeys(message, key1, key2);
        String format = "key1 is %d, key2 is %d\nencrypted is %s";
        System.out.println(String.format(format, key1, key2, encrypted));
    }
    
    public void testEncryptTwoKeys()
    {
        String expected = "Czojq Ivdzle";
        String actual = encryptTwoKeys("First Legion", 23, 17);
        String result = expected.equals(actual) ? "Passed" : "Failure";
        System.out.println(result + " testEncryptTwoKeys()");
    }
    
    public void test()
    {
        testEncrypt();
        testEncryptTwoKeys();
        
    }
}
