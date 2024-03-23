/**
 * @author Benny Sun
 */
import java.util.*;

public class MovieRunnerAverage
{
    public void printAverageRatings()
    {
        String movieFile = "ratedmovies_short.csv";
        String ratingsfile = "ratings_short.csv";
        SecondRatings sut = new SecondRatings(movieFile, ratingsfile);
        System.out.println(sut.getRaterSize() + " raters, " + sut.getMovieSize() + " movies");

        ArrayList<Rating> avgRatings = sut.getAverageRatings(3);
        avgRatings.forEach(r -> System.out.println(r.getValue() + " " + sut.getTitle(r.getItem())));
    }

    public void getAverageRatingOneMovie()
    {
        String movieFile = "ratedmovies_short.csv";
        String ratingsfile = "ratings_short.csv";
        String movieName= "The Godfather";
        SecondRatings sut = new SecondRatings(movieFile, ratingsfile);
        ArrayList<Rating> avgRatings = sut.getAverageRatings(3);
        String movieID = sut.getID(movieName);
        for (Rating rating: avgRatings) {
            if (rating.getItem().equals(movieID)) {
                System.out.println(movieName + " " + rating.getValue());
            }
        }
    }
}
