/**
 * @author Benny Sun
 */
import java.text.*;
import java.util.*;
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
            int statusCode = record.getStatusCode();
            if (statusCode > num) {
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
}
