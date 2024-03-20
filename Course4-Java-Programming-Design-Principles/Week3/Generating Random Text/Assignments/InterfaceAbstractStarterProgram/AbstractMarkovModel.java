/**
 * @author Benny Sun
 */
import java.util.*;

public abstract class AbstractMarkovModel implements IMarkovModel
{
    protected int n;
    protected String myText;
    protected Random myRandom;

    public AbstractMarkovModel(int n)
    {
        this.n = n;
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

    abstract public String getRandomText(int numChars);

    protected ArrayList<String> getFollows(String key)
    {
        ArrayList<String> ans = new ArrayList<>();
        int keyLength = key.length();
        for (int i = 0; i < myText.length() - n; i++) {
            int tail = i + n;
            if (myText.substring(i, tail).equals(key)) {
                ans.add(myText.substring(tail, tail + 1));
            }
        }
        
        return ans;
    }

    public String toString()
    {
        return "MarkovModel of order " + n;
    }
}
