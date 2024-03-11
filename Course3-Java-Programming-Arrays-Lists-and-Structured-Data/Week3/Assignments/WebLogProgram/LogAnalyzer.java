/**
 * @author Benny Sun
 */
import java.text.*;
import java.util.*;
import java.util.stream.*;
import edu.duke.*;

public class LogAnalyzer
{
    private ArrayList<LogEntry> records;

    public LogAnalyzer()
    {
        records = new ArrayList<>();
    }

    public void readFile(String filename)
    {
        FileResource fr = new FileResource(filename);
        for (String line: fr.lines()) {
            records.add(WebLogParser.parseEntry(line));
        }
    }

    public void printAll()
    {
        for (LogEntry le : records) {
            System.out.println(le);
        }
    }

    public void printAllHigherThanNum(int num)
    {
        for (LogEntry record: records) {
            if (record.getStatusCode() > num) {
                System.out.println(record.toString());
            }
        }
    }

    public int countUniqueIPs()
    {   
        return uniqueIpLogEntries(records).size();
    }

    public ArrayList<LogEntry> uniqueIPVisitsOnDay(String someday)
    {
        ArrayList<LogEntry> logs = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd", Locale.US);

        for (LogEntry record: records) {
            if (formatter.format(record.getAccessTime()).equals(someday)) {
                logs.add(record);
            }
        }

        return uniqueIpLogEntries(logs);
    }

    public int countUniqueIPsInRange(int low, int high)
    {
        ArrayList<LogEntry> logs = new ArrayList<>();
        for (LogEntry record: records) {
            if (low <= record.getStatusCode() && record.getStatusCode() <= high) {
                logs.add(record);
            }
        }

        return uniqueIpLogEntries(logs).size();
    }

    private ArrayList<LogEntry> uniqueIpLogEntries(ArrayList<LogEntry> logs)
    {
        ArrayList<LogEntry> ans = new ArrayList<>();
        ArrayList<String> uniqueIps = new ArrayList<>();
        for (LogEntry log: logs) {
            String ip = log.getIpAddress();
            if (! uniqueIps.contains(ip)) {
                ans.add(log);
                uniqueIps.add(ip);
            }
        }

        return ans;
    }

    public HashMap<String, Integer> countVisitsPerIP()
    {
        ArrayList<String> ipList = records.stream()
            .map(LogEntry::getIpAddress)
            .collect(Collectors.toCollection(ArrayList::new));

        return countVisitsPerIP(ipList);
    }

    private HashMap<String, Integer> countVisitsPerIP(ArrayList<String> ipList)
    {
        HashMap<String, Integer> ans = new HashMap<>();
        for (String ip: ipList) {
            ans.put(ip, ans.getOrDefault(ip, 0) + 1);
        }

        return ans;
    }

    public int mostNumberVisitsByIP(HashMap<String, Integer> map)
    {
        return Collections.max(map.values());
    }

    public ArrayList<String> iPsMostVisits(HashMap<String, Integer> map)
    {
        int max = mostNumberVisitsByIP(map);
        ArrayList<String> ans = new ArrayList<>();
        for (String ip: map.keySet()) {
            if (map.get(ip) == max) ans.add(ip);
        }

        return ans;
    }

    public HashMap<String, ArrayList<String>> iPsForDays()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd", Locale.US);
        HashMap<String, ArrayList<String>> ans = new HashMap<>();

        for (LogEntry record: records) {
            String strDate = formatter.format(record.getAccessTime());
            ArrayList<String> ipList = ans.getOrDefault(strDate, new ArrayList<>());
            ipList.add(record.getIpAddress());
            ans.put(strDate, ipList);
        }

        return ans;
    }

    public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> map)
    {
        int max = 0;
        String ans = "";
        for (String strDate: map.keySet()) {
            int ipCount = map.get(strDate).size();
            if (ipCount > max) {
                max = ipCount;
                ans = strDate;
            }
        }

        return ans;
    }

    public ArrayList<String> iPsWithMostVisitsOnDay(
    HashMap<String, ArrayList<String>> map,
    String strDate) {
        if (map == null
        || map.size() == 0
        || map.get(strDate) == null
        ) return new ArrayList<>();

        HashMap<String, Integer> ipCountMap = countVisitsPerIP(map.get(strDate));

        return iPsMostVisits(ipCountMap);
    }
}
