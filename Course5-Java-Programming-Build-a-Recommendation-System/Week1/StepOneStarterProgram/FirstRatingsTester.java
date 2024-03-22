/**
 * @author Benny Sun
 */
import java.util.*;
import java.util.stream.*;

public class FirstRatingsTester
{
    public void testLoadMovies()
    {
        FirstRatings sut = new FirstRatings();
        ArrayList<Movie> actual = sut.loadMovies("ratedmovies_short.csv");

        System.out.println("In ratedmovies_short.csv");
        System.out.println("Number of movies is " + actual.size());
        System.out.println(
            "Number of Comedy genre is " +
            actual.stream().filter(m -> m.getGenres().contains("Comedy")).count()
        );
        System.out.println(
            "Number of greater than 150 minutes is " +
            actual.stream().filter(m -> m.getMinutes() > 150).count()
        );

        Map<String, Integer> map = new HashMap<>();
        for (Movie m: actual) {
            String[] directors = m.getDirector().split(", ");
            for (String director: directors) {
                map.put(director, map.getOrDefault(director, 0) + 1);
            }
        }

        int max = Collections.max(map.values());
        System.out.println(
            "The maximum number of movies by any director is " + max + "\n" +
            "and the number of maximum directors is " + map.entrySet().stream().filter(o -> o.getValue() == max).count() + "\n" +
            "and the directors list below:\n" + map.entrySet().stream().filter(o -> o.getValue() == max).map(o -> o.getKey()).collect(Collectors.joining(", "))
        );

        System.out.println("------");

        actual = sut.loadMovies("ratedmoviesfull.csv");
        System.out.println("Number of movies in ratedmoviesfull.csv: " + actual.size());
    }

    public void testLoadRaters()
    {
        FirstRatings sut = new FirstRatings();
        ArrayList<Rater> raters = sut.loadRaters("ratings_short.csv");
        System.out.println("ratings_short.csv");
        System.out.println("Number of raters is " + raters.size());
        raters.forEach(r -> System.out.println(
                    "Rater ID " + r.getID() +
                    ", the number of ratings is " + r.numRatings() +
                    ", movie ratings " + r.getRatings()
                ));

        String raterID = "2";
        Rater found = null;
        for (Rater r: raters) {
            if (r.getID().equals(raterID)) {
                found = r;
                break;
            }
        }
        System.out.println("Rater ID 2 has " + found.numRatings() + " ratings");

        int max = raters.stream()
            .mapToInt(r -> r.numRatings())
            .max()
            .getAsInt();
        List<Rater> maxRaters = raters.stream()
            .filter(r -> r.numRatings() == max)
            .collect(Collectors.toList());
        System.out.println(
            "The maximum number of ratings is " + max +
            ", and the rater IDs: " +
            maxRaters.stream().map(r -> r.getID()).collect(Collectors.joining(" ,"))
        );

        String movieID = "1798709";
        long raterNum = raters.stream()
            .filter(r -> r.getItemsRated().contains(movieID))
            .count();
        System.out.println("Movie ID 1798709 has " + raterNum + " raters");

        List<String> uniqueMovieIDs = raters.stream()
            .map(r -> r.getItemsRated())
            .flatMap(Collection::stream)
            .distinct()
            .collect(Collectors.toList());
        System.out.println("The number of unique movie IDs by all raters is " + uniqueMovieIDs.size());

        System.out.println("------");
        raters = sut.loadRaters("ratings.csv");
        System.out.println("ratings.csv");
        System.out.println("Number of raters is " + raters.size());
    }
}
