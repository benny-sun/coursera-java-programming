/**
 * @author Benny Sun
 */
import edu.duke.*;

public class CaesarCipher
{
    private int key;
    private String alphabets;
    private String shiftedAlphabet;

    public CaesarCipher(int key)
    {
        this.key = key;
        this.alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        this.shiftedAlphabet = alphabets.substring(26 - key) + alphabets.substring(0, 26 - key);
    }

    public String encrypt(String input)
    {
        StringBuilder ans = new StringBuilder(input);
        String alphabetUpper = this.alphabets.toUpperCase();
        String alphabetLower = this.alphabets.toLowerCase();

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            int idx = shiftedAlphabet.indexOf(Character.toUpperCase(ch));

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
        CaesarCipher cc = new CaesarCipher(26 - this.key);

        return cc.encrypt(input);
    }
}
