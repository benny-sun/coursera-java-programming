/**
 * @author Benny Sun
 */
import java.util.*;
import java.util.stream.*;

public class EfficientMarkovModel extends AbstractMarkovModel
{
    private HashMap<String, ArrayList<String>> map;

    public EfficientMarkovModel(int n)
    {
        super(n);
        map = new HashMap<>();
    }

    @Override
    public void setTraining(String s)
    {
        super.setTraining(s);
        buildMap();
        printHashMapInfo();
    }

    private void buildMap()
    {
        for (int i = 0; i <= myText.length() - n; i++) {
            int tail = i + n;
            String key = myText.substring(i, tail);

            if (! map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            
            if (tail == myText.length()) break;

            ArrayList<String> list = map.get(key);
            String next = myText.substring(tail, tail + 1);
            list.add(next);
        }
    }

    private void printHashMapInfo()
    {
        System.out.println("Map size: " + map.size());

        int largestSize = map.values()
            .stream()
            .mapToInt(follows -> follows.size())
            .max()
            .getAsInt();
        System.out.println("Largest size: " + largestSize);

        List<String> keys = map.entrySet()
            .stream()
            .filter(entry -> entry.getValue().size() == largestSize)
            .map(entry -> entry.getKey())
            .collect(Collectors.toList());
        System.out.println("Largest keys: " + keys);
    }

    public String getRandomText(int numChars)
    {
        if (myText == null) {
            return "";
        }
        StringBuilder ans = new StringBuilder();
        int index = myRandom.nextInt(myText.length() - n);
        String key = myText.substring(index, index + n);
        ans.append(key);

        for (int k = 0; k < numChars - n; k++) {
            ArrayList<String> follows = getFollows(key);
            if (follows.size() == 0) break;
            String next = follows.get(myRandom.nextInt(follows.size()));
            ans.append(next);
            key = key.substring(1) + next;
        }

        return ans.toString();
    }

    public ArrayList<String> getFollows(String key)
    {
        return map.get(key);
    }

    @Override
    public String toString()
    {
        return "EfficientMarkovModel of order " + n;
    }
}
