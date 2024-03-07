/**
 * @author Benny Sun
 */
import edu.duke.*;

public class CaesarCipherTwo
{
    private String alphabets;
    private String shiftedAlphabet1;
    private String shiftedAlphabet2;
    private int key1, key2;

    public CaesarCipherTwo(int key1, int key2)
    {
        this.alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        this.shiftedAlphabet1 = alphabets.substring(26 - key1) + alphabets.substring(0, 26 - key1);
        this.shiftedAlphabet2 = alphabets.substring(26 - key2) + alphabets.substring(0, 26 - key2);
        this.key1 = key1;
        this.key2 = key2;
    }

    public String encrypt(String input)
    {
        StringBuilder ans = new StringBuilder(input);
        String alphabetUpper = this.alphabets.toUpperCase();
        String alphabetLower = this.alphabets.toLowerCase();
        
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            
            boolean isOdd = ((i + 1) & 1) == 1;
            int idx = isOdd
                ? shiftedAlphabet1.indexOf(Character.toUpperCase(ch))
                : shiftedAlphabet2.indexOf(Character.toUpperCase(ch));

            if (idx == -1) continue;

            char newChar = Character.isUpperCase(ch)
                ? alphabetUpper.charAt(idx)
                : alphabetLower.charAt(idx);
                
            ans.setCharAt(i, newChar);
        }

        return ans.toString();
    }

    public String decrypt(String input)
    {
        CaesarCipherTwo cct = new CaesarCipherTwo(26 - this.key1, 26 - this.key2);
        
        return cct.encrypt(input);
    }
}
