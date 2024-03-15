/**
 * @author Benny Sun
 */
import java.util.*;

public class TitleLastAndMagnitudeComparator implements Comparator<QuakeEntry>
{
    private MagnitudeComparator magComparator;
    
    public TitleLastAndMagnitudeComparator()
    {
        magComparator = new MagnitudeComparator();
    }
    
    public int compare(QuakeEntry q1, QuakeEntry q2)
    {
        String[] words1 = q1.getInfo().split("\\W+");
        String[] words2 = q2.getInfo().split("\\W+");
        
        String lastWord1 = words1.length == 0
            ? "" : words1[words1.length - 1];
        String lastWord2 = words2.length == 0
            ? "" : words2[words2.length - 1];
        
        int diff = lastWord1.compareTo(lastWord2);
        
        if (diff == 0) {
            diff = magComparator.compare(q1, q2);
        }
        
        return diff;
    }
}
