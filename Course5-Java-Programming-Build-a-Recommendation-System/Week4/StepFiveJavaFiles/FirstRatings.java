/**
 * @author Benny Sun
 */
import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

public class FirstRatings
{
    public ArrayList<Movie> loadMovies(String filename)
    {
        ArrayList<Movie> ans = new ArrayList<>();
        FileResource fr = new FileResource(filename);
        for (CSVRecord record: fr.getCSVParser()) {
            Movie movie = new Movie(
            record.get("id"),
            record.get("title"),
            record.get("year"),
            record.get("genre"),
            record.get("director"),
            record.get("country"),
            record.get("poster"),
            Integer.parseInt(record.get("minutes"))
            );
            ans.add(movie);
        }

        return ans;
    }
    
    public ArrayList<Rater> loadRaters(String filename)
    {
        Map<String, Rater> map = new HashMap<>();
        FileResource fr = new FileResource(filename);
        for (CSVRecord record: fr.getCSVParser()) {
            String raterID = record.get("rater_id");
            
            if (! map.containsKey(raterID)) {
                map.put(raterID, new EfficientRater(raterID));
            }
            
            Rater rater = map.get(raterID);
            rater.addRating(
                record.get("movie_id"),
                Double.parseDouble(record.get("rating"))
            );
        }
        
        return new ArrayList<>(map.values());
    }
}
