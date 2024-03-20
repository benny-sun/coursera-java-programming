/**
 * @author Benny Sun
 */
import java.util.*;
import java.util.stream.*;

public class EfficientMarkovWord implements IMarkovModel
{
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    private HashMap<WordGram, ArrayList<String>> myMap;

    public EfficientMarkovWord(int order)
    {
        myOrder = order;
        myRandom = new Random();
        myMap = new HashMap<>();
    }

    public void setRandom(int seed)
    {
        myRandom = new Random(seed);
    }

    public void setTraining(String text)
    {
        myText = text.split("\\s+");
        buildMap();
        printHashMapInfo();
    }

    private void buildMap()
    {
        for (int i = 0; i <= myText.length - myOrder; i++) {
            WordGram wg = new WordGram(myText, i, myOrder);

            // create empty list if not exists
            if (! myMap.containsKey(wg)) {
                myMap.put(wg, new ArrayList<>());
            }

            // stop for loop if in the end
            if (i + myOrder == myText.length) break;

            // insert the follow-word into the correspond list
            ArrayList<String> list = myMap.get(wg);
            list.add(myText[i + myOrder]);
        }
    }

    private void printHashMapInfo()
    {
        if (myMap.size() < 20) {
            System.out.println(myMap);
        }

        System.out.println("Map size: " + myMap.size());

        int largestSize = myMap.values()
            .stream()
            .mapToInt(follows -> follows.size())
            .max()
            .getAsInt();
        System.out.println("Largest size: " + largestSize);

        System.out.println("Largest keys:");
        myMap.entrySet()
        .stream()
        .filter(entry -> entry.getValue().size() == largestSize)
        .forEach(System.out::println);
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
        return myMap.get(kGram);
    }
}
