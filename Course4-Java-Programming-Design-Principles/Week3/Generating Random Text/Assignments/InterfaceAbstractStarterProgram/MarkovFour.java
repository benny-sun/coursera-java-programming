/**
 * @author Benny Sun
 */
import java.util.Random;
import java.util.*;

public class MarkovFour extends AbstractMarkovModel
{
    public MarkovFour()
    {
        super(4);
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
}
