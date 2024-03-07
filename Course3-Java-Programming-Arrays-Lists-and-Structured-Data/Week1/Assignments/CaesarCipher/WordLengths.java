/**
 * @author Benny Sun
 */
import edu.duke.*;
import java.util.*;

public class WordLengths
{
    public void countWordLengths(FileResource resource, int[] counts)
    {
        for(String word: resource.words()){
            char lastChar = word.charAt(word.length() - 1);
            int len = Character.isLetter(lastChar)
                ? word.length()
                : word.length() - 1;
            counts[len]++;
        }
    }
    
    public int indexOfMax(int[] values)
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
    
    public void testCountWordLengths()
    {
        FileResource resource = new FileResource("testing/smallHamlet.txt");
        int counts[] = new int[31];
        countWordLengths(resource, counts);

        int expected = 3;
        int actual = indexOfMax(counts);
        String result = actual == expected ? "Passed" : "Failure";
        System.out.println(result + " testEncrypt()");
    }
}
