/**
 * @author Benny Sun
 */
import java.util.*;

public class LargestQuakes
{
    public void findLargestQuakes()
    {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for " + list.size());
        
        ArrayList<QuakeEntry> data = getLargest(list, 5);
        data.forEach(qe -> System.out.println(qe.toString()));
    }
    
    public ArrayList<QuakeEntry> getLargest(ArrayList<QuakeEntry> quakeData, int howMany)
    {
        Comparator<QuakeEntry> comparator = (qe1, qe2) -> (qe2.getMagnitude() - qe1.getMagnitude()) < 0 ? 1 : -1;
        Queue<QuakeEntry> heap = new PriorityQueue<>(comparator);
        
        for (QuakeEntry qe: quakeData) {
            heap.add(qe);
            if (heap.size() > howMany) heap.poll();
        }
        
        ArrayList<QuakeEntry> ans = new ArrayList<>(heap);
        Collections.sort(ans, comparator.reversed());
        
        return ans;
    }
}
