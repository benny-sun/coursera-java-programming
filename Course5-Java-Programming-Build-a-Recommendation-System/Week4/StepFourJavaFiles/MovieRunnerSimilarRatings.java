/**
 * @author Benny Sun
 */
import java.util.*;

public class MovieRunnerSimilarRatings
{
    public void printAverageRatings()
    {
        MovieDatabase.initialize("ratedmovies_short.csv");
        RaterDatabase.initialize("ratings_short.csv");
        FourthRatings sut = new FourthRatings();
        ArrayList<Rating> avgRatings = sut.getAverageRatings(1);
        Collections.sort(avgRatings);
        
        System.out.println("read data for " + RaterDatabase.size() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + avgRatings.size() + " movies");
        avgRatings.forEach(r -> System.out.println(r.getValue() + " " + MovieDatabase.getTitle(r.getItem())));
    }
    
    public void printAverageRatingsByYearAfterAndGenre()
    {
        MovieDatabase.initialize("ratedmovies_short.csv");
        RaterDatabase.initialize("ratings_short.csv");
        FourthRatings sut = new FourthRatings();
        AllFilters filter = new AllFilters();
        filter.addFilter(new YearAfterFilter(1980));
        filter.addFilter(new GenreFilter("Romance"));
        
        ArrayList<Rating> avgRatings = sut.getAverageRatingsByFilter(1, filter);
        Collections.sort(avgRatings);
        
        System.out.println("read data for " + RaterDatabase.size() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + avgRatings.size() + " movies");
        avgRatings.forEach(r -> System.out.println(
                    r.getValue() + " " +
                    MovieDatabase.getYear(r.getItem()) + " " +
                    MovieDatabase.getTitle(r.getItem()) + "\n  " +
                    MovieDatabase.getGenres(r.getItem())
                ));
    }
    
    public void printSimilarRatings()
    {
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        FourthRatings sut = new FourthRatings();
        String raterID = "65";
        int top = 20;
        int minimalRaters = 5;
        ArrayList<Rating> avgRatings = sut.getSimilarRatings(raterID, top, minimalRaters);
        
        System.out.println("read data for " + RaterDatabase.size() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + avgRatings.size() + " movies");
        avgRatings.forEach(r -> System.out.println(MovieDatabase.getTitle(r.getItem())));
    }
    
    public void printSimilarRatingsByGenre()
    {
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        FourthRatings sut = new FourthRatings();
        String raterID = "65";
        int top = 20;
        int minimalRaters = 5;
        Filter filter = new GenreFilter("Action");
        ArrayList<Rating> avgRatings = sut.getSimilarRatingsByFilter(raterID, top, minimalRaters, filter);
        
        System.out.println("read data for " + RaterDatabase.size() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + avgRatings.size() + " movies");
        avgRatings.forEach(r -> System.out.println(MovieDatabase.getTitle(r.getItem())));
    }
    
    public void printSimilarRatingsByDirector()
    {
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        FourthRatings sut = new FourthRatings();
        String raterID = "1034";
        int top = 10;
        int minimalRaters = 3;
        Filter filter = new DirectorsFilter("Clint Eastwood,Sydney Pollack,David Cronenberg,Oliver Stone");
        ArrayList<Rating> avgRatings = sut.getSimilarRatingsByFilter(raterID, top, minimalRaters, filter);
        
        System.out.println("read data for " + RaterDatabase.size() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + avgRatings.size() + " movies");
        avgRatings.forEach(r -> System.out.println(MovieDatabase.getTitle(r.getItem())));
    }
    
    public void printSimilarRatingsByGenreAndMinutes()
    {
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        FourthRatings sut = new FourthRatings();
        String raterID = "65";
        int top = 10;
        int minimalRaters = 5;
        AllFilters filter = new AllFilters();
        filter.addFilter(new GenreFilter("Adventure"));
        filter.addFilter(new MinutesFilter(100, 200));
        ArrayList<Rating> avgRatings = sut.getSimilarRatingsByFilter(raterID, top, minimalRaters, filter);
        
        System.out.println("read data for " + RaterDatabase.size() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + avgRatings.size() + " movies");
        avgRatings.forEach(r -> System.out.println(MovieDatabase.getTitle(r.getItem())));
    }
    
    public void printSimilarRatingsByYearAfterAndMinutes()
    {
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        FourthRatings sut = new FourthRatings();
        String raterID = "65";
        int top = 10;
        int minimalRaters = 5;
        AllFilters filter = new AllFilters();
        filter.addFilter(new YearAfterFilter(2000));
        filter.addFilter(new MinutesFilter(80, 100));
        ArrayList<Rating> avgRatings = sut.getSimilarRatingsByFilter(raterID, top, minimalRaters, filter);
        
        System.out.println("read data for " + RaterDatabase.size() + " raters");
        System.out.println("read data for " + MovieDatabase.size() + " movies");
        System.out.println("found " + avgRatings.size() + " movies");
        avgRatings.forEach(r -> System.out.println(MovieDatabase.getTitle(r.getItem())));
    }
}
