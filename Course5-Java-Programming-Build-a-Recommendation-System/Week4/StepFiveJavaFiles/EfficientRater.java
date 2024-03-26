/**
 * @author Benny Sun
 */

import java.util.*;

public class EfficientRater implements Rater
{
    private String myID;
    private HashMap<String, Rating> myRatings;

    public EfficientRater(String id)
    {
        myID = id;
        myRatings = new HashMap<>();
    }

    public void addRating(String item, double rating)
    {
        myRatings.put(item, new Rating(item, rating));
    }

    public boolean hasRating(String item)
    {
        return myRatings.containsKey(item);
    }

    public String getID()
    {
        return myID;
    }

    public double getRating(String item)
    {
        Rating ans = myRatings.get(item);
        
        return ans != null ? ans.getValue() : -1;
    }

    public int numRatings()
    {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated()
    {   
        return new ArrayList<>(myRatings.keySet());
    }
}
