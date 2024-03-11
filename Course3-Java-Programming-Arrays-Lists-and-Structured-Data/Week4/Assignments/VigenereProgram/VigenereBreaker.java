/**
 * @author Benny Sun
 */
import java.util.*;
import edu.duke.*;

public class VigenereBreaker
{
    public String sliceString(String message, int whichSlice, int totalSlices)
    {
        String ans = "";
        for (int i = whichSlice; i < message.length(); i += totalSlices) {
            ans += message.charAt(i);
        }
        
        return ans;
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon)
    {
        int[] keys = new int[klength];
        CaesarCracker cc = new CaesarCracker(mostCommon);
        
        for (int i = 0; i < klength; i++) {
            String s = sliceString(encrypted, i, klength);
            keys[i] = cc.getKey(s);
        }
        
        return keys;
    }

    public void breakVigenere(String filename)
    {
        String[] languages = {"Danish", "Dutch", "English", "French", "German", "Italian", "Portuguese", "Spanish"};
        FileResource fr = new FileResource(filename);
        String actual = breakForAllLangs(fr.asString(), languages);
        System.out.println(actual);
    }
    
    public HashSet<String> readDictionary(FileResource fr)
    {
        HashSet<String> ans = new HashSet<>();
        for (String line: fr.lines()) {
            ans.add(line.toLowerCase());
        }
        
        return ans;
    }
    
    public String breakForLanguage(String encrypted, HashSet<String> dictionary)
    {
        char ch = mostCommonCharIn(dictionary);
        for (int i = 1; i < encrypted.length(); i++) {
            int[] keys = tryKeyLength(encrypted, i, ch);
            VigenereCipher vc = new VigenereCipher(keys);
            String decrypted = vc.decrypt(encrypted);
            
            String[] words = decrypted.split("\\W+");
            int threshold = words.length / 2;
            
            if (countWords(decrypted, dictionary) > threshold) {
                System.out.println("keys " + Arrays.toString(keys));
                System.out.println("keys length " + keys.length);
                System.out.println("count words " + countWords(decrypted, dictionary));
                System.out.println("the threshold " + threshold);
                return decrypted;
            }
        }
        
        return "";
    }
    
    public int countWords(String message, HashSet<String> dictionary)
    {
        int ans = 0;
        for (String word: message.split("\\W+")) {
            if (dictionary.contains(word.toLowerCase())) ans++;
        }
        
        return ans;
    }
    
    public char mostCommonCharIn(HashSet<String> dictionary)
    {
        HashMap<Character, Integer> table = new HashMap<>();
        
        // calculate all number of each characters
        for (String word: dictionary) {
            for (char ch: word.toLowerCase().toCharArray()) {
                if (Character.isLetter(ch)) {
                    int plusOne = table.getOrDefault(ch, 0) + 1;
                    table.put(ch, plusOne);
                }
            }
        }
        
        // find the maximum
        char ans = 0;
        int max = 0;
        for (char ch: table.keySet()) {
            if (table.get(ch) > max) {
                max = table.get(ch);
                ans = ch;
            }
        }
        
        return ans;
    }
    
    public String breakForAllLangs(String encrypted, String[] languages)
    {
        for (String lang: languages) {
            System.out.println(lang);
            FileResource fr = new FileResource("dictionaries/" + lang);
            HashSet<String> dictionary = readDictionary(fr);
            String decrypted = breakForLanguage(encrypted, dictionary);
            if (! decrypted.isEmpty()) {
                return decrypted;
            }
        }

        return "";
    }
}
