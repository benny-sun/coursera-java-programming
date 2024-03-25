/**
 * @author Benny Sun
 */
import java.util.*;
import java.util.stream.*;

public class ThirdRatings
{
    private ArrayList<Rater> myRaters;

    public ThirdRatings()
    {
        // default constructor
        this("ratings.csv");
    }

    public ThirdRatings(String ratingsfile)
    {
        FirstRatings firstRatings = new FirstRatings();
        myRaters = firstRatings.loadRaters(ratingsfile);
    }

    public int getRaterSize()
    {
        return myRaters.size();
    }

    public ArrayList<Rating> getAverageRatings(int minimalRaters)
    {
        ArrayList<Rating> ans = new ArrayList<>();
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());

        for (String movieID: movies) {
            int raterNum = getNumberOfRatersBy(movieID);
            if (raterNum >= minimalRaters) {
                ans.add(new Rating(
                        movieID,
                        getAverageByID(movieID, minimalRaters)
                    ));
            }
        }

        return ans;
    }

    private int getNumberOfRatersBy(String movieID)
    {
        return (int) myRaters.stream()
        .filter(r -> r.getItemsRated().contains(movieID))
        .count();
    }

    private double getAverageByID(String id, int minimalRaters)
    {
        List<Rater> filteredRaters = myRaters.stream()
            .filter(r -> r.getItemsRated().contains(id))
            .collect(Collectors.toList());

        return filteredRaters.size() >= minimalRaters
        ? filteredRaters.stream()
            .mapToDouble(r -> r.getRating(id))
            .average().orElse(Double.NaN)
        : 0.0;
    }

    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria)
    {
        ArrayList<Rating> ans = new ArrayList<>();
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        
        for (String movieID: movies) {
            int raterNum = getNumberOfRatersBy(movieID);
            if (raterNum >= minimalRaters) {
                ans.add(new Rating(
                        movieID,
                        getAverageByID(movieID, minimalRaters)
                    ));
            }
        }

        return ans;
    }
}
