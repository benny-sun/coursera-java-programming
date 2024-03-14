/**
 * @author Benny Sun
 */
import java.util.*;
import edu.duke.*;

public class QuakeSortInPlace
{
    public int getSmallestMagnitude(ArrayList<QuakeEntry> quakes, int from)
    {
        int minIdx = from;
        for (int i=from+1; i< quakes.size(); i++) {
            if (quakes.get(i).getMagnitude() < quakes.get(minIdx).getMagnitude()) {
                minIdx = i;
            }
        }
        return minIdx;
    }

    public void sortByMagnitude(ArrayList<QuakeEntry> in)
    {
        for (int i=0; i< in.size(); i++) {
            int minIdx = getSmallestMagnitude(in,i);
            QuakeEntry qi = in.get(i);
            QuakeEntry qmin = in.get(minIdx);
            in.set(i,qmin);
            in.set(minIdx,qi);
        }
    }

    public void testSort()
    {
        EarthQuakeParser parser = new EarthQuakeParser(); 
        String source = "testing/earthquakeDataSampleSix2.atom";
        //String source = "testing/nov20quakedatasmall.atom";
        //String source = "testing/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);  

        System.out.println("read data for "+list.size()+" quakes");    
        //sortByMagnitude(list);
        //sortByLargestDepth(list);
        //sortByMagnitudeWithBubbleSort(list);
        //sortByMagnitudeWithBubbleSortWithCheck(list);
        sortByMagnitudeWithCheck(list);
        
        System.out.println("Sorted result:");
        list.forEach(qe -> System.out.println(qe));
    }

    public void createCSV()
    {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "testing/nov20quakedata.atom";
        String source = "testing/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
    }

    public void dumpCSV(ArrayList<QuakeEntry> list)
    {
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }

    }

    private int getLargestDepth(ArrayList<QuakeEntry> quakeData, int start)
    {
        double max = -Double.MAX_VALUE;
        int ans = start;

        for (int i = start; i < quakeData.size(); i++) {
            QuakeEntry qe = quakeData.get(i);
            if (qe.getDepth() > max) {
                max = qe.getDepth();
                ans = i;
            }
        }

        return ans;
    }

    public void sortByLargestDepth(ArrayList<QuakeEntry> in)
    {
        //for (int i = 0; i < 70; i++) {
        for (int i = 0; i < in.size(); i++) {
            int maxIdx = getLargestDepth(in, i);
            QuakeEntry current = in.get(i);
            QuakeEntry max = in.get(maxIdx);
            in.set(i, max);
            in.set(maxIdx, current);
        }
    }
    
    private void onePassBubbleSort(ArrayList<QuakeEntry> quakeData, int numSorted)
    {
        int n = quakeData.size() - numSorted - 1;
        
        for (int i = 0; i < n; i++) {
            QuakeEntry current = quakeData.get(i);
            QuakeEntry next = quakeData.get(i + 1);
            if (current.getMagnitude() > next.getMagnitude()) {
                // swap
                quakeData.set(i, next);
                quakeData.set(i + 1, current);
            }
        }
    }
    
    public void sortByMagnitudeWithBubbleSort(ArrayList<QuakeEntry> in)
    {
        for (int i = 0; i < in.size(); i++) {
            in.forEach(qe -> System.out.println(qe.toString()));
            
            onePassBubbleSort(in, i);
            
            System.out.println("Printing Quakes after pass " + i);
        }
    }
    
    private boolean checkInSortedOrder(ArrayList<QuakeEntry> quakes)
    {
        if (quakes.isEmpty()) return true;
        
        QuakeEntry prev = quakes.get(0);
        
        for (int i = 1; i < quakes.size(); i++) {
            QuakeEntry current = quakes.get(i);
            if (current.getMagnitude() < prev.getMagnitude()) return false;
            prev = current;
        }
        
        return true;
    }
    
    public void sortByMagnitudeWithBubbleSortWithCheck(ArrayList<QuakeEntry> in)
    {
        for (int i = 0; i < in.size(); i++) {
            in.forEach(qe -> System.out.println(qe.toString()));
            
            onePassBubbleSort(in, i);
            
            System.out.println("Printing Quakes after pass " + i);
            
            if (checkInSortedOrder(in)) break;
        }
    }
    
    public void sortByMagnitudeWithCheck(ArrayList<QuakeEntry> in)
    {
        int counter = 0;
        for (int i = 0; i < in.size(); i++) {
            int minIdx = getSmallestMagnitude(in, i);
            QuakeEntry qi = in.get(i);
            QuakeEntry qmin = in.get(minIdx);
            in.set(i,qmin);
            in.set(minIdx,qi);
            
            counter++;
            System.out.println("Printing Quakes after pass " + i);
            if (checkInSortedOrder(in)) break;
        }
        
        System.out.println(counter);
    }
}
