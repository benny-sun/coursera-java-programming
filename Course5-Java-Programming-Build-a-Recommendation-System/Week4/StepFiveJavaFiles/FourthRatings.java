/**
 * @author Benny Sun
 */
import java.util.*;

public class FourthRatings
{
    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters)
    {
        return getSimilarRatingsByFilter(
            id,
            numSimilarRaters,
            minimalRaters,
            new TrueFilter()
        );
    }

    public ArrayList<Rating> getSimilarRatingsByFilter(
    String id,
    int numSimilarRaters,
    int minimalRaters,
    Filter filterCriteria) {
        ArrayList<Rating> ans = new ArrayList<>();
        ArrayList<Rating> similarRatings = getSimilarities(id);
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        int top = Math.min(similarRatings.size(), numSimilarRaters);

        for (String movieID: movies) {
            // calculating weighted ratings
            ArrayList<Rating> weightRatings = new ArrayList<>();
            for (int i = 0; i < top; i++) {
                Rating rating = similarRatings.get(i);
                String raterID = rating.getItem();
                Rater rater = RaterDatabase.getRater(raterID);
                if (rater.hasRating(movieID)) {
                    double weight = rating.getValue();
                    double movieRating = rater.getRating(movieID);
                    weightRatings.add(new Rating(raterID, weight * movieRating));
                }
            }

            // calculating weighted average ratings
            if (weightRatings.size() >= minimalRaters) {
                double weightAverageRating = weightRatings.stream()
                    .mapToDouble(w -> w.getValue())
                    .average()
                    .getAsDouble();
                ans.add(new Rating(movieID, weightAverageRating));
            }
        }

        Collections.sort(ans, Collections.reverseOrder());

        return ans;
    }

    private ArrayList<Rating> getSimilarities(String id)
    {
        // init
        ArrayList<Rating> ans = new ArrayList<>();
        Rater me = RaterDatabase.getRater(id);
        ArrayList<Rater> raters = RaterDatabase.getRaters();

        // not to use itself
        raters.removeIf(r -> r.getID().equals(id));

        // generate dot product ratings
        for (Rater rater: raters) {
            int score = dotProduct(me, rater);
            if (score > 0) {
                ans.add(new Rating(rater.getID(), score));
            }
        }

        Collections.sort(ans, Collections.reverseOrder());

        return ans;
    }

    private int dotProduct(Rater me, Rater r)
    {
        int ans = 0;
        for (String movieID: me.getItemsRated()) {
            if (r.hasRating(movieID)) {
                double meRating = me.getRating(movieID);
                double rRating = r.getRating(movieID);
                ans += (meRating - 5) * (rRating - 5);
            }
        }

        return ans;
    }
}
