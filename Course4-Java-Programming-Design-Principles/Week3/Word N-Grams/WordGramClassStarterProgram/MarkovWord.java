/**
 * @author Benny Sun
 */
import java.util.*;

public class MarkovWord implements IMarkovModel
{
    private String[] myText;
    private Random myRandom;
    private int myOrder;

    public MarkovWord(int order)
    {
        myOrder = order;
        myRandom = new Random();
    }

    public void setRandom(int seed)
    {
        myRandom = new Random(seed);
    }

    public void setTraining(String text)
    {
        myText = text.split("\\s+");
    }

    public String getRandomText(int numWords)
    {
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length - myOrder);  // random word to start with
        WordGram key = new WordGram(myText, index, myOrder);
        sb.append(key);
        sb.append(" ");
        for(int k=0; k < numWords - 1; k++) {
            ArrayList<String> follows = getFollows(key);
            if (follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            key = key.shiftAdd(next);
        }

        return sb.toString().trim();
    }

    public ArrayList<String> getFollows(WordGram kGram)
    {
        ArrayList<String> ans = new ArrayList<>();

        for (int i = 0; i < myText.length - kGram.length(); i++) {
            int idx = indexOf(myText, kGram, i);
            if (idx != -1) {
                ans.add(myText[idx + kGram.length()]);
                i = idx;
            }            
        }

        return ans;
    }

    private int indexOf(String[] words, WordGram target, int start)
    {
        for (int i = start; i < words.length - target.length(); i++) {
            WordGram current = new WordGram(words, i, target.length());
            if (current.equals(target)) return i;
        }

        return -1;
    }
}
