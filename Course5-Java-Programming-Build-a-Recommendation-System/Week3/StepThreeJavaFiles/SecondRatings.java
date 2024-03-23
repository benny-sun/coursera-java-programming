/**
 * @author Benny Sun
 */
import java.util.*;
import java.util.stream.*;

public class SecondRatings
{
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;

    public SecondRatings()
    {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }

    public SecondRatings(String moviefile, String ratingsfile)
    {
        FirstRatings firstRatings = new FirstRatings();
        myMovies = firstRatings.loadMovies(moviefile);
        myRaters = firstRatings.loadRaters(ratingsfile);
    }

    public int getMovieSize()
    {
        return myMovies.size();
    }

    public int getRaterSize()
    {
        return myRaters.size();
    }

    public String getID(String title)
    {
        for (Movie movie: myMovies) {
            if (movie.getTitle().equals(title)) return movie.getID();
        }

        return "NO SUCH TITLE.";
    }

    public String getTitle(String id)
    {
        for (Movie movie: myMovies) {
            if (movie.getID().equals(id)) return movie.getTitle();
        }

        return "ID " + id + " NOT FOUND.";
    }

    public ArrayList<Rating> getAverageRatings(int minimalRaters)
    {
        ArrayList<Rating> ans = new ArrayList<>();

        for (Movie movie: myMovies) {
            int raterNum = getNumberOfRatersBy(movie);
            if (raterNum >= minimalRaters) {
                ans.add(new Rating(
                        movie.getID(),
                        getAverageByID(movie.getID(), minimalRaters)
                    ));
            }
        }

        return ans;
    }

    private int getNumberOfRatersBy(Movie movie)
    {
        return (int) myRaters.stream()
        .filter(r -> r.getItemsRated().contains(movie.getID()))
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
}
