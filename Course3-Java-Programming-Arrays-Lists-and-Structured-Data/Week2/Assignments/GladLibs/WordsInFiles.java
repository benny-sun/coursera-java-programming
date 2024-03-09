/**
 * @author Benny Sun
 */
import edu.duke.*;
import java.util.*;
import java.io.*;

public class WordsInFiles
{
    private HashMap<String, List<String>> map;

    public WordsInFiles()
    {
        map = new HashMap<>();
    }

    public void buildWordFileMap()
    {
        DirectoryResource dr = new DirectoryResource();
        for (File f: dr.selectedFiles()) {
            addWordsFromFile(f);
        }
    }

    private void addWordsFromFile(File f)
    {
        try {
            Scanner scanner = new Scanner(f);
            while (scanner.hasNext()) {
                String word = scanner.next();
                String filename = f.getName();
                if (map.containsKey(word)) {
                    List<String> list = map.get(word);
                    int idx = list.indexOf(filename);
                    if (idx == -1) list.add(filename);
                } else {
                    List<String> newList = new ArrayList<>();
                    newList.add(filename);
                    map.put(word, newList);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public int maxNumber()
    {
        int ans = 0;
        for (String word: map.keySet()) {
            List<String> list = map.get(word);
            int n = list.size();
            if (n > ans) ans = n;
        }

        return ans;
    }

    public List<String> wordsInNumFiles(int number)
    {
        List<String> ans = new ArrayList<>();
        for (String word: map.keySet()) {
            List<String> list = map.get(word);
            int n = list.size();
            if (number == list.size()) ans.add(word);
        }

        return ans;
    }

    public void printFilesIn(String word)
    {
        if (! map.containsKey(word)) return;

        map.get(word).forEach((filename) -> System.out.println(filename));
    }

    public void tester()
    {
        buildWordFileMap();
        
        int n = 3;
        System.out.println("Words with " + n + " is " + wordsInNumFiles(3));
        
        String s = "cats";
        System.out.println(s + " in files:");
        printFilesIn("cats");
        
        System.out.println("The max number is " + maxNumber());
    }
}
