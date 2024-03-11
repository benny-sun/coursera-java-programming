
/**
 * @author Duke Software Team
 */

import java.util.*;

public class Tester
{
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }

    public void testLogAnalyzer() {
        LogAnalyzer sut = new LogAnalyzer();
        sut.readFile("testing/short-test_log");
        sut.printAll();
    }

    public void testUniqueIP()
    {
        LogAnalyzer sut = new LogAnalyzer();
        sut.readFile("testing/short-test_log");
        int actual = sut.countUniqueIPs();
        System.out.println("Count unique ip: " + actual);
    }

    public void testPrintAllHigherThanNum()
    {
        LogAnalyzer sut = new LogAnalyzer();
        sut.readFile("testing/short-test_log");
        int num = 400;
        sut.printAllHigherThanNum(num);
    }

    public void testUniqueIPVisitsOnDay()
    {
        LogAnalyzer sut = new LogAnalyzer();
        sut.readFile("testing/short-test_log");
        String date = "Sep 14";
        ArrayList<LogEntry> actual = sut.uniqueIPVisitsOnDay(date);
        actual.forEach((LogEntry log) -> System.out.println(log.toString()));

        date = "Sep 30";
        actual = sut.uniqueIPVisitsOnDay(date);
        actual.forEach((LogEntry log) -> System.out.println(log.toString()));
    }
    
    public void testCountUniqueIPsInRange()
    {
        LogAnalyzer sut = new LogAnalyzer();
        sut.readFile("testing/short-test_log");
        int actual = sut.countUniqueIPsInRange(200, 299);
        System.out.println("200 to 299: " + actual);
        
        actual = sut.countUniqueIPsInRange(300, 399);
        System.out.println("300 to 399: " + actual);
    }
    
    public void testCountVisitsPerIP()
    {
        LogAnalyzer sut = new LogAnalyzer();
        sut.readFile("testing/weblog3-short_log");
        HashMap<String, Integer> actual = sut.countVisitsPerIP();
        System.out.println(actual);
        
    }
    
    public void testMostNumberVisitsByIP()
    {
        LogAnalyzer sut = new LogAnalyzer();
        sut.readFile("testing/weblog3-short_log");
        int actual = sut.mostNumberVisitsByIP(sut.countVisitsPerIP());
        System.out.println("Most number visits by IP is " + actual);
    }
    
    public void testIpsMostVisits()
    {
        LogAnalyzer sut = new LogAnalyzer();
        sut.readFile("testing/weblog3-short_log");
        ArrayList<String> actual = sut.iPsMostVisits(sut.countVisitsPerIP());
        System.out.println("IPs most visits " + actual);
    }
    
    public void testIpsForDays()
    {
        LogAnalyzer sut = new LogAnalyzer();
        sut.readFile("testing/weblog3-short_log");
        HashMap<String, ArrayList<String>> actual = sut.iPsForDays();
        System.out.println("IPs for days " + actual);
    }
    
    public void testDayWithMostIPVisits()
    {
        LogAnalyzer sut = new LogAnalyzer();
        sut.readFile("testing/weblog3-short_log");
        String actual = sut.dayWithMostIPVisits(sut.iPsForDays());
        System.out.println("Day with most IP visits is " + actual);
    }
    
    public void testDayIpsWithMostVisitsOnDay()
    {
        LogAnalyzer sut = new LogAnalyzer();
        sut.readFile("testing/weblog3-short_log");
        String strDate = "Sep 30";
        ArrayList<String> actual = sut.iPsWithMostVisitsOnDay(sut.iPsForDays(), strDate);
        System.out.println("IPs with most visits on day " + strDate + " " + actual);
    }
}
