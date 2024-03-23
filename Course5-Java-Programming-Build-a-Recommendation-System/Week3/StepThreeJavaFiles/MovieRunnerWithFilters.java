/**
 * @author Benny Sun
 */
import java.util.*;

public class MovieRunnerWithFilters
{
    public void printAverageRatings()
    {
        MovieDatabase.initialize("ratedmovies_short.csv");
        String ratingsfile = "data/ratings_short.csv";
        ThirdRatings sut = new ThirdRatings(ratingsfile);
        ArrayList<Rating> avgRatings = sut.getAverageRatings(1);
        Collections.sort(avgRatings);
        
        System.out.println("read data for " + sut.getRaterSize() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + avgRatings.size() + " movies");
        avgRatings.forEach(r -> System.out.println(r.getValue() + " " + MovieDatabase.getTitle(r.getItem())));
    }

    public void printAverageRatingsByYear()
    {
        MovieDatabase.initialize("ratedmovies_short.csv");
        String ratingsfile = "data/ratings_short.csv";
        ThirdRatings sut = new ThirdRatings(ratingsfile);
        Filter filter = new YearAfterFilter(2000);
        ArrayList<Rating> avgRatings = sut.getAverageRatingsByFilter(1, filter);
        Collections.sort(avgRatings);
        
        System.out.println("read data for " + sut.getRaterSize() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + avgRatings.size() + " movies");
        avgRatings.forEach(r -> System.out.println(
                    r.getValue() + " " +
                    MovieDatabase.getYear(r.getItem()) + " " +
                    MovieDatabase.getTitle(r.getItem())
                ));
    }

    public void printAverageRatingsByGenre()
    {
        MovieDatabase.initialize("ratedmovies_short.csv");
        String ratingsfile = "data/ratings_short.csv";
        ThirdRatings sut = new ThirdRatings(ratingsfile);
        Filter filter = new GenreFilter("Crime");
        ArrayList<Rating> avgRatings = sut.getAverageRatingsByFilter(1, filter);
        Collections.sort(avgRatings);
        
        System.out.println("read data for " + sut.getRaterSize() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + avgRatings.size() + " movies");
        avgRatings.forEach(r -> System.out.println(
                    r.getValue() + " " +
                    MovieDatabase.getTitle(r.getItem()) + "\n  " +
                    MovieDatabase.getGenres(r.getItem())
                ));
    }
    
    public void printAverageRatingsByMinutes()
    {
        MovieDatabase.initialize("ratedmovies_short.csv");
        String ratingsfile = "data/ratings_short.csv";
        ThirdRatings sut = new ThirdRatings(ratingsfile);
        Filter filter = new MinutesFilter(110, 170);
        ArrayList<Rating> avgRatings = sut.getAverageRatingsByFilter(1, filter);
        Collections.sort(avgRatings);
        
        System.out.println("read data for " + sut.getRaterSize() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + avgRatings.size() + " movies");
        avgRatings.forEach(r -> System.out.println(
                    r.getValue() + " " +
                    "Time: " + MovieDatabase.getMinutes(r.getItem()) + " " +
                    MovieDatabase.getTitle(r.getItem())
                ));
    }
    
    public void printAverageRatingsByDirectors()
    {
        MovieDatabase.initialize("ratedmovies_short.csv");
        String ratingsfile = "data/ratings_short.csv";
        ThirdRatings sut = new ThirdRatings(ratingsfile);
        Filter filter = new DirectorsFilter("Charles Chaplin,Michael Mann,Spike Jonze");
        ArrayList<Rating> avgRatings = sut.getAverageRatingsByFilter(1, filter);
        Collections.sort(avgRatings);
        
        System.out.println("read data for " + sut.getRaterSize() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + avgRatings.size() + " movies");
        avgRatings.forEach(r -> System.out.println(
                    r.getValue() + " " +
                    MovieDatabase.getTitle(r.getItem()) + "\n  " +
                    MovieDatabase.getDirector(r.getItem())
                ));
    }
    
    public void printAverageRatingsByYearAfterAndGenre()
    {
        MovieDatabase.initialize("ratedmovies_short.csv");
        String ratingsfile = "data/ratings_short.csv";
        ThirdRatings sut = new ThirdRatings(ratingsfile);
        AllFilters filter = new AllFilters();
        filter.addFilter(new YearAfterFilter(1980));
        filter.addFilter(new GenreFilter("Romance"));
        
        ArrayList<Rating> avgRatings = sut.getAverageRatingsByFilter(1, filter);
        Collections.sort(avgRatings);
        
        System.out.println("read data for " + sut.getRaterSize() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + avgRatings.size() + " movies");
        avgRatings.forEach(r -> System.out.println(
                    r.getValue() + " " +
                    MovieDatabase.getYear(r.getItem()) + " " +
                    MovieDatabase.getTitle(r.getItem()) + "\n  " +
                    MovieDatabase.getGenres(r.getItem())
                ));
    }
    
    public void printAverageRatingsByDirectorsAndMinutes()
    {
        MovieDatabase.initialize("ratedmovies_short.csv");
        String ratingsfile = "data/ratings_short.csv";
        ThirdRatings sut = new ThirdRatings(ratingsfile);
        AllFilters filter = new AllFilters();
        filter.addFilter(new MinutesFilter(30, 170));
        filter.addFilter(new DirectorsFilter("Spike Jonze,Michael Mann,Charles Chaplin,Francis Ford Coppola"));
        
        ArrayList<Rating> avgRatings = sut.getAverageRatingsByFilter(1, filter);
        Collections.sort(avgRatings);
        
        System.out.println("read data for " + sut.getRaterSize() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + avgRatings.size() + " movies");
        avgRatings.forEach(r -> System.out.println(
                    r.getValue() + " " +
                    "Time: " + MovieDatabase.getMinutes(r.getItem()) + " " +
                    MovieDatabase.getTitle(r.getItem()) + "\n  " +
                    MovieDatabase.getDirector(r.getItem())
                ));
    }
}
