/**
 * @author Benny Sun
 */
import java.util.*;

public class TitleAndDepthComparator implements Comparator<QuakeEntry>
{
    public int compare(QuakeEntry q1, QuakeEntry q2)
    {
        int diff = q1.getInfo().compareTo(q2.getInfo());

        if (diff == 0) {
            diff = Double.compare(q1.getDepth(), q2.getDepth());
        }
        
        return diff;
    }
}
