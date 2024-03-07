/**
 * @author Benny Sun
 */
import edu.duke.*;

public class Helper
{
    public int getKey(String s)
    {
        int m = maxIndex(countLetters(s));
        int k = m - 4;
        
        return (m < 4) ? k + 26 : k;
    }
    
    public int maxIndex(int[] values)
    {
        int ans = 0, max = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] > max) {
                max = values[i];
                ans = i;
            }
        }
        
        return ans;
    }
    
    public int[] countLetters(String s)
    {
        int[] ans = new int[26];
        for (char ch: s.toLowerCase().toCharArray()) {
            if (Character.isLetter(ch)) ans[ch - 'a']++;
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
}
