/**
 * @author Benny Sun
 */
import java.util.Random;
import java.util.*;

public class MarkovOne extends AbstractMarkovModel
{
    public MarkovOne()
    {
        super(1);
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
        int index = myRandom.nextInt(myText.length() - 1);
        String key = myText.substring(index, index + 1);
        ans.append(key);

        for (int k = 0; k < numChars - 1; k++) {
            ArrayList<String> follows = getFollows(key);
            String next = follows.get(myRandom.nextInt(follows.size()));
            ans.append(next);
            key = next;
        }

        return ans.toString();
    }
}
