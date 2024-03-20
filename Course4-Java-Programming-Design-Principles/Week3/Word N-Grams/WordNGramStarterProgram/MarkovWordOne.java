
/**
 * Write a description of class MarkovWordOne here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class MarkovWordOne implements IMarkovModel
{
    private String[] myText;
    private Random myRandom;

    public MarkovWordOne()
    {
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
        int index = myRandom.nextInt(myText.length-1);  // random word to start with
        String key = myText[index];
        sb.append(key);
        sb.append(" ");
        for(int k=0; k < numWords-1; k++){
            ArrayList<String> follows = getFollows(key);
            //System.out.println("key:" + key + ", follows:" + follows);
            if (follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            key = next;
        }

        return sb.toString().trim();
    }

    private ArrayList<String> getFollows(String key)
    {
        ArrayList<String> follows = new ArrayList<String>();

        for (int i = 0; i < myText.length; i++) {
            int idx = indexOf(myText, key, i);
            if (idx == -1 || idx + 1 == myText.length) break;
            follows.add(myText[idx + 1]);
            i = idx;
        }

        return follows;
    }

    private int indexOf(String[] words, String target, int start)
    {
        for (int i = start; i < words.length; i++) {
            if (words[i].equals(target)) return i;
        }

        return -1;
    }

    public void testIndexOf()
    {
        String s = "this is just a test yes this is a simple test";
        String[] words = s.split("\\s+");

        int actual = indexOf(words, "this", 0);
        System.out.println(actual);

        actual = indexOf(words, "this", 3);
        System.out.println(actual);

        actual = indexOf(words, "frog", 0);
        System.out.println(actual);

        actual = indexOf(words, "frog", 5);
        System.out.println(actual);

        actual = indexOf(words, "simple", 2);
        System.out.println(actual);

        actual = indexOf(words, "test", 5);
        System.out.println(actual);
    }
}
