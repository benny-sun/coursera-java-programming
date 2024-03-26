/**
 * @author Benny Sun
 */
import java.util.*;

public class RecommendationRunner implements Recommender
{
    private StringBuilder htmlTable;

    public RecommendationRunner()
    {
        htmlTable = new StringBuilder();
    }

    public ArrayList<String> getItemsToRate()
    {
        MovieDatabase.initialize("ratedmoviesfull.csv");
        ArrayList<String> movieIDs = MovieDatabase.filterBy(new TrueFilter());
        Collections.shuffle(movieIDs);

        return new ArrayList<>(movieIDs.subList(0, 10));
    }

    public void printRecommendationsFor(String webRaterID)
    {
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");

        ArrayList<Rating> ratings = new FourthRatings().getSimilarRatings(webRaterID, 10, 3);
        List<Rating> recommands = ratings.subList(0, Math.min(ratings.size(), 10));
        if (recommands.size() > 0) {
            System.out.print(
                htmlHead() +
                htmlMovieList(recommands) +
                css()
            );
            return;
        }

        System.out.print("Sorry, there has no recommanded moveis for you.");
    }

    private String htmlHead()
    {
        return tag("h1", "Movie Recommandation");
    }

    private String htmlMovieList(List<Rating> ratings)
    {
        int rank = 1;
        String rows = "";
        for (Rating rating: ratings) {
            String id = rating.getItem();
            String row = "";
            row += tag("div", "movie-poster", img(MovieDatabase.getPoster(id), MovieDatabase.getTitle(id)));
            row += tag("div", "movie-content",
                tag("h3", "title",
                    tag("span", "rank", String.valueOf(rank++) + ".") +
                    MovieDatabase.getTitle(id) +
                    tag("span", "year", String.valueOf(MovieDatabase.getYear(id)))
                ) +
                tag("div", "metadata",
                    tag("span", MovieDatabase.getCountry(id)) +
                    tag("span", "v-divider", "|") +
                    tag("span", humanReadableTime(MovieDatabase.getMinutes(id))) +
                    tag("span", "v-divider", "|") +
                    tag("span", MovieDatabase.getGenres(id))
                ) +
                tag("div", "metadata",
                    tag("span", "Director: " + MovieDatabase.getDirector(id))
                ) +
                tag("div", "rate",
                    Math.floor(getAverageRatingBy(id) * 10) / 10 +
                    tag("span", "weight", "(Weight Avg:" + (int) rating.getValue() + ")")
                )
            );
            rows += tag("div", "list-item", row);
        }

        return tag("div", "list", rows);
    }

    private double getAverageRatingBy(String movieID)
    {
        ArrayList<Rater> raters = RaterDatabase.getRaters();

        return raters
            .stream()
            .filter(r -> r.hasRating(movieID))
            .mapToDouble(r -> r.getRating(movieID))
            .average()
            .getAsDouble();
    }

    private String humanReadableTime(int minutes)
    {
        int hour = minutes / 60;
        int min = minutes % 60;

        return hour + "h " + min + "m";
    }

    private String tag(String tag, String content)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("<" + tag + ">");
        builder.append(content);
        builder.append("</" + tag + ">");

        return builder.toString();
    }

    private String tag(String tag, String style, String content)
    {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("<%s class=\"%s\">", tag, style));
        builder.append(content);
        builder.append("</" + tag + ">");

        return builder.toString();
    }

    private String img(String src, String alt)
    {
        String format = "<img src=\"%s\" alt=\"%s\">";

        return String.format(format, src, alt);
    }

    private String css()
    {
        String styles = "body{font-family:Helvetica}h1{text-align:center}.list{display:block;width:30%;margin-left:auto;margin-right:auto}.list-item{display:flex;flex-direction:row;margin-bottom:6px;width:100%;background-color:#ebf2ff}.movie-poster{flex:1;max-width:20%;margin-right:20px}.movie-poster img{width:100%;height:auto}.movie-content{flex:2;max-width:80%}.movie-content div{margin-top:5px;margin-bottom:5px}.movie-content .title{color:#434e61;margin-bottom:0;text-align:left}.movie-content span{font-size:10pt;color:#7b8087;margin-right:6px}.movie-content span.weight,.movie-content span.year{margin-left:6px}.movie-content .title .rank{font-size:12pt;color:#3c4c66}.movie-content .title .year{font-size:10pt;color:#7a889e}.movie-content span.v-divider{font-size:70%;font-weight:700;color:#b1b5ba}.movie-content .rate{color:#17345c;margin-top:15px;margin-bottom:15px}.movie-content .rate:before{content:url('data:image/svg+xml,<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 576 512\"><!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.--><path fill=\"rgb(242,222,2)\" d=\"M316.9 18C311.6 7 300.4 0 288.1 0s-23.4 7-28.8 18L195 150.3 51.4 171.5c-12 1.8-22 10.2-25.7 21.7s-.7 24.2 7.9 32.7L137.8 329 113.2 474.7c-2 12 3 24.2 12.9 31.3s23 8 33.8 2.3l128.3-68.5 128.3 68.5c10.8 5.7 23.9 4.9 33.8-2.3s14.9-19.3 12.9-31.3L438.5 329 542.7 225.9c8.6-8.5 11.7-21.2 7.9-32.7s-13.7-19.9-25.7-21.7L381.2 150.3 316.9 18z\"/></svg>');display:inline-block;width:14px;height:14px;padding-right:2px}";

        return tag("style", styles);
    }
}
