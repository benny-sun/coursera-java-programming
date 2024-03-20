/**
 * @author Benny Sun
 */
import java.util.Random;
import java.util.*;

public class MarkovModel extends AbstractMarkovModel
{
    public MarkovModel(int n)
    {
        super(n);
    }

    public String getRandomText(int numChars)
    {
        if (myText == null) {
            return "";
        }
        StringBuilder ans = new StringBuilder();
        int index = myRandom.nextInt(myText.length() - n);
        String key = myText.substring(index, index + n);
        ans.append(key);

        for (int k = 0; k < numChars - n; k++) {
            ArrayList<String> follows = getFollows(key);
            String next = follows.get(myRandom.nextInt(follows.size()));
            ans.append(next);
            key = key.substring(1) + next;
        }

        return ans.toString();
    }
}
