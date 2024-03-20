/**
 * @author Benny Sun
 */
import java.util.*;

public class WordGram
{
    private String[] myWords;
    private int myHash;

    public WordGram(String[] source, int start, int size)
    {
        myWords = new String[size];
        System.arraycopy(source, start, myWords, 0, size);
        myHash = toString().hashCode();
    }
    
    public int hashCode()
    {
        return myHash;
    }

    public String wordAt(int index)
    {
        if (index < 0 || index >= myWords.length) {
            throw new IndexOutOfBoundsException("bad index in wordAt "+index);
        }
        return myWords[index];
    }

    public int length()
    {
        return myWords.length;
    }

    public String toString()
    {
        String ret = "";
        
        for (String word: myWords) {
            ret += word + " ";
        }

        return ret.trim();
    }

    public boolean equals(Object o)
    {
        WordGram other = (WordGram) o;
        
        if (this.length() != other.length()) return false;
        
        for (int i = 0; i < this.length(); i++) {
            if (! this.wordAt(i).equals(other.wordAt(i))) return false;
        }
        
        return true;

    }

    public WordGram shiftAdd(String word)
    {
        int n = myWords.length;
        String[] shifedWords = new String[n];
        shifedWords[n - 1] = word;
        for (int i = n - 2; i >= 0; i--) {
            shifedWords[i] = myWords[i + 1];
        }
        
        return new WordGram(shifedWords, 0, myWords.length);
    }

}