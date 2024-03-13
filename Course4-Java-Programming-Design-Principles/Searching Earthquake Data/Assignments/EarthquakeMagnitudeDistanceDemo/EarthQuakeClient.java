
import java.util.*;
import java.util.stream.*;
import edu.duke.*;

public class EarthQuakeClient {

    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData, double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        //TODO
        for (QuakeEntry qe : quakeData) {
            if (qe.getMagnitude() > magMin) {
                answer.add(qe);
            }
        }
        return answer;              
    }

    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData, double distMax, Location from) {      
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        for (QuakeEntry qe : quakeData) {
            if (qe.getLocation().distanceTo(from) < distMax) {
                answer.add(qe);
            }
        }
        return answer;
    }

    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }

    }

    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for " + list.size() + " quakes");
        /*
        for (QuakeEntry qe : list) {
        if (qe.getMagnitude() > 5.0) {
        System.out.println(qe);
        }
        }
         */
        ArrayList<QuakeEntry> listBig = filterByMagnitude(list, 5.0);
        for (QuakeEntry qe : listBig) {
            System.out.println(qe); 
        }
        System.out.println("Found " + listBig.size() + " quakes that match that criteria");
    }

    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
    }

    public void closeToMe() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "data/nov20quakedata.atom";
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("# quakes read: " + list.size());

        //Durham, NC
        //Location city = new Location(35.988, -78.907);
        //Bridgeport, CA
        Location city = new Location(38.17, -118.82);
        ArrayList<QuakeEntry> close = filterByDistanceFrom(list, 1000*1000, city);
        for (int k=0; k< close.size(); k++) {
            QuakeEntry entry = close.get(k);
            double distanceInMeters = city.distanceTo(entry.getLocation());
            System.out.println(distanceInMeters/1000 + " " + entry.getInfo());
        }

        System.out.println("Found " + close.size() + " quakes that match that criteria");
    }

    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData, double minDepth, double maxDepth) {
        return quakeData.stream()
        .filter(qe -> minDepth < qe.getDepth() && qe.getDepth() < maxDepth)
        .collect(Collectors.toCollection(ArrayList::new));
    }

    public void quakesOfDepth() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for " + list.size() + " quakes");

        ArrayList<QuakeEntry> depths = filterByDepth(list, -10000.0, -5000.0);
        for (QuakeEntry entry: depths) {
            System.out.println(entry.toString());
        }

        System.out.println("Found " + depths.size() + " quakes that match that criteria");
    }

    public ArrayList<QuakeEntry> filterByPhrase(ArrayList<QuakeEntry> quakeData, String where, String phrase) {
        ArrayList<QuakeEntry> ans = new ArrayList<>();
        for (QuakeEntry qe: quakeData) {
            String title = qe.getInfo();
            if (where.equals("start")) {
                if (title.startsWith(phrase)) ans.add(qe);
            } else if (where.equals("end")) {
                if (title.endsWith(phrase)) ans.add(qe);
            } else {
                if (title.contains(phrase)) ans.add(qe);
            }
        }

        return ans;
    }

    public void quakesByPhrase() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for " + list.size() + " quakes");

        String where = "start";
        String pharse = "Explosion";
        ArrayList<QuakeEntry> data = filterByPhrase(list, where, pharse);
        data.forEach(qe -> System.out.println(qe.toString()));

        System.out.println("Found " + data.size() + " quakes that match " + pharse + " at " + where);
    }
}
