/**
 * @author Benny Sun
 */
import edu.duke.*;
import java.util.*;

public class GladLibMap
{
    HashMap<String, List<String>> myMap;
    private Random myRandom;
    private List<String> seenCategories;
    private List<String> seenWords;

    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "testing/data";

    public GladLibMap()
    {
        myMap = new HashMap<>();
        seenCategories = new ArrayList<>();
        seenWords = new ArrayList<>();
        initializeFromSource(dataSourceDirectory);
        myRandom = new Random();
    }

    public GladLibMap(String source)
    {
        myMap = new HashMap<>();
        seenCategories = new ArrayList<>();
        seenWords = new ArrayList<>();
        initializeFromSource(source);
        myRandom = new Random();
    }

    private void initializeFromSource (String source)
    {
        String[] categories = {
                "adjective", "noun", "color", "country",
                "name", "animal", "timeframe", "verb", "fruit"
            };

        for (String category: categories) {
            List<String> list = readIt(String.format("%s/%s.txt", source, category));
            myMap.put(category, list);
        }
    }

    private List<String> readIt(String source)
    {
        List<String> list = new ArrayList<>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        } else {
            FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }

        return list;
    }

    private String getSubstitute(String label)
    {
        if (seenCategories.indexOf(label) == -1) {
            seenCategories.add(label);
        }

        if (label.equals("number")) {
            return "" + (myRandom.nextInt(50) + 5);
        } else {
            List<String> list = myMap.get(label);
            return randomFrom(list);
        }        
    }

    private String randomFrom(List<String> source)
    {
        int index = myRandom.nextInt(source.size());

        return source.get(index);
    }

    public void makeStory()
    {
        seenWords.clear();
        seenCategories.clear();
        
        String story = fromTemplate("testing/datalong/madtemplate2.txt");
        printOut(story, 60);
        System.out.println("\n");

        int totalWords = totalWordsInMap();
        System.out.println("Total words is " + totalWords);
        
        int totalWords2 = totalWordsConsidered();
        System.out.println("Total words considered is " + totalWords2);
    }

    private String fromTemplate(String source)
    {
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        } else {
            FileResource resource = new FileResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        return story;
    }

    private String processWord(String w)
    {
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);
        if (first == -1 || last == -1){
            return w;
        }
        String prefix = w.substring(0,first);
        String suffix = w.substring(last + 1);
        String sub = getSubstitute(w.substring(first + 1,last));
        
        // each word only appear once
        while (seenWords.indexOf(sub) != -1) {
            sub = getSubstitute(w.substring(first+1,last));
        }
        
        seenWords.add(sub);

        return prefix + sub + suffix;
    }

    private void printOut(String s, int lineWidth)
    {
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w + " ");
            charsWritten += w.length() + 1;
        }
    }

    private int totalWordsInMap()
    {
        return myMap
            .values()
            .stream()
            .mapToInt(list -> list.size())
            .sum();
    }

    private int totalWordsConsidered()
    {
        return seenCategories
            .stream()
            .mapToInt(s -> myMap.get(s).size())
            .sum();
    }
}
