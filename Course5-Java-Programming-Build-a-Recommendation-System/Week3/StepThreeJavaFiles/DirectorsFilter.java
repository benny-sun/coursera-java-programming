/**
 * @author Benny Sun
 */
import java.util.ArrayList;

public class DirectorsFilter implements Filter
{
    private String directors;
    
    public DirectorsFilter(String directors)
    {
        this.directors = directors;
    }
    
    @Override
    public boolean satisfies(String id)
    {
        String[] movieDirectors = MovieDatabase.getDirector(id).split(",");
        
        for (String director: movieDirectors) {
            if (directors.contains(director.trim())) return true;
        }
        
        return false;
    }
}
