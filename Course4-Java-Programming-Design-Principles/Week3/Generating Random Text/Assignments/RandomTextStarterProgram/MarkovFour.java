/**
 * @author Benny Sun
 */
import java.util.Random;
import java.util.*;

public class MarkovFour
{
    private String myText;
    private Random myRandom;

    public MarkovFour()
    {
        myRandom = new Random();
    }

    public void setRandom(int seed)
    {
        myRandom = new Random(seed);
    }

    public void setTraining(String s)
    {
        myText = s.trim();
    }

    public String getRandomText(int numChars)
    {
        if (myText == null) {
            return "";
        }
        StringBuilder ans = new StringBuilder();
        int index = myRandom.nextInt(myText.length() - 4);
        String key = myText.substring(index, index + 4);
        ans.append(key);

        for (int k = 0; k < numChars - 4; k++) {
            ArrayList<String> follows = getFollows(key);
            String next = follows.get(myRandom.nextInt(follows.size()));
            ans.append(next);
            key = key.substring(1) + next;
        }

        return ans.toString();
    }

    public ArrayList<String> getFollows(String key)
    {
        ArrayList<String> ans = new ArrayList<>();

        int start = 0;
        int end = myText.length();
        int keyLength = key.length();
        while (start < end) {
            int idx = myText.indexOf(key, start);
            if (idx == -1) break;
            if (idx + keyLength >= end) break;
            char nextChar = myText.charAt(idx + keyLength);
            ans.add(String.valueOf(nextChar));
            start = idx + keyLength;
        }

        return ans;
    }
}
