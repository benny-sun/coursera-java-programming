/**
 * @author Benny Sun
 */
import edu.duke.*;
import java.util.ArrayList;

public class WordFrequencies
{
    private ArrayList<String> myWords;
    private ArrayList<Integer> myFreqs;
    
    public WordFrequencies()
    {
        myWords = new ArrayList<String>();
        myFreqs = new ArrayList<Integer>();
    }
    
    public void findUnique()
    {
        myWords.clear();
        myFreqs.clear();
        FileResource resource = new FileResource();

        for (String word: resource.words()) {
            String w = word.toLowerCase();
            int idx = myWords.indexOf(w);
            if (idx == -1) { // create
                myWords.add(w);
                myFreqs.add(1);
            } else { // update
                int freq = myFreqs.get(idx);
                myFreqs.set(idx, freq + 1);
            }
        }
    }
    
    public void tester()
    {
        findUnique();
        int n = myWords.size();
        System.out.println("Number of unique words: " + n);
        int max = 0;
        String topWord = "";
        for (int i = 0; i < n; i++) {
            String word = myWords.get(i);
            int freq = myFreqs.get(i);
            if (freq > max) {
                max = freq;
                topWord = word;
            }
            //System.out.println(freq + " " + word);
        }
        System.out.println("The word that occurs most often and its count are: " + topWord + " " + max);
    }
}
