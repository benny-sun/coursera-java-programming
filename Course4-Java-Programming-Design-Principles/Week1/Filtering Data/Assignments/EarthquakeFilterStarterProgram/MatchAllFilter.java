/**
 * @author Benny Sun
 */
import java.util.*;
import java.util.stream.*;

public class MatchAllFilter implements Filter
{
    private ArrayList<Filter> filters;
    
    public MatchAllFilter()
    {
        filters = new ArrayList<>();
    }
    
    public void addFilter(Filter filter)
    {
        filters.add(filter);
    }
    
    public boolean satisfies(QuakeEntry qe)
    {
        for (Filter filter: filters) {
            if (! filter.satisfies(qe)) return false;
        }
        
        return true;
    }
    
    public String getName()
    {
        return filters.stream()
            .map((Filter f)-> f.getName())
            .collect(Collectors.joining(" "));
    }
}
