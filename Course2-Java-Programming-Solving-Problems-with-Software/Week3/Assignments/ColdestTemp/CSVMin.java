/**
 * @author Benny Sun
 * @version 0.1.0
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class CSVMin
{
    public CSVRecord coldestHourInFile(CSVParser parser)
    {
        CSVRecord coldestSoFar = null;
        for (CSVRecord row: parser) {
            coldestSoFar = getMinRecord(row, coldestSoFar, "TemperatureF");
        }

        return coldestSoFar;
    }

    public String fileWithColdestTemperature()
    {
        DirectoryResource dr = new DirectoryResource();
        File coldestTempfile = null;
        CSVRecord coldest = null;
        for (File f: dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord dayColdest = coldestHourInFile(fr.getCSVParser());

            if (coldest == null) {
                coldest = dayColdest;
                coldestTempfile = f;
            } else {
                double dayColdestTemp = Double.parseDouble(dayColdest.get("TemperatureF"));
                double coldestTemp = Double.parseDouble(coldest.get("TemperatureF"));
                if (dayColdestTemp < coldestTemp) {
                    coldest = dayColdest;
                    coldestTempfile = f;
                }
            }
        }

        String log = "";
        FileResource fr = new FileResource(coldestTempfile);
        for (CSVRecord row: fr.getCSVParser()) {
            String tempStr = row.get("TemperatureF");
            if (tempStr.equals("-9999")) continue; // skip bogus temperature
            log += (row.get("DateUTC") + ": " + tempStr + "\n");
        }

        return "Coldest day was in file " + coldestTempfile.getName() + "\n"
        + "Coldest temperature on that day was " + coldest.get("TemperatureF") + "\n"
        + "All the Temperatures on the coldest day were:\n" + log;
    }

    public CSVRecord lowestHumidityInFile(CSVParser parser)
    {
        CSVRecord lowest = null;
        for (CSVRecord row: parser) {
            lowest = getMinRecord(row, lowest, "Humidity");
        }

        return lowest;
    }

    public CSVRecord lowestHumidityInManyFiles()
    {
        CSVRecord lowest = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f: dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord row = lowestHumidityInFile(fr.getCSVParser());
            lowest = getMinRecord(row, lowest, "Humidity");
        }

        return lowest;
    }

    private CSVRecord getMinRecord(CSVRecord row, CSVRecord minRow, String column)
    {
        // skip bogus
        if (row.get(column).equals("N/A") || row.get(column).equals("-9999")) return null;

        if (minRow == null) {
            minRow = row;
        } else {
            String strVal = row.get(column);
            double val = Double.parseDouble(strVal);
            double minVal = Double.parseDouble(minRow.get(column));
            if (val < minVal) minRow = row;
        }

        return minRow;
    }

    public Double averageTemperatureInFile(CSVParser parser)
    {
        int count = 0;
        double sum = 0;

        for (CSVRecord row: parser) {
            if (row.get("TemperatureF").equals("-9999")) continue;
            sum += Double.parseDouble(row.get("TemperatureF"));
            count++;
        }

        if (count == 0) return null;

        return sum / count;
    }

    public Double averageTemperatureWithHighHumidityInFile(CSVParser parser, int humidity)
    {   
        int days = 0;
        double tempSum = 0;
        for (CSVRecord row: parser) {
            if (row.get("Humidity").equals("N/A")) continue;
            double rowHum = Double.parseDouble(row.get("Humidity"));
            if (rowHum >= humidity) {
                days++;
                tempSum += Double.parseDouble(row.get("TemperatureF"));
            }
        }

        if (days == 0) return null;

        return tempSum / days;
    }

    public CSVRecord highestHumidityInFile(CSVParser parser)
    {
        CSVRecord highest = null;
        for (CSVRecord row: parser) {
            highest = getMaxRecord(row, highest, "Humidity");
        }

        return highest;
    }

    private CSVRecord getMaxRecord(CSVRecord row, CSVRecord maxRow, String column)
    {
        // skip bogus
        if (row.get(column).equals("N/A") || row.get(column).equals("-9999")) return null;

        if (maxRow == null) {
            maxRow = row;
        } else {
            String strVal = row.get(column);
            double val = Double.parseDouble(strVal);
            double MaxVal = Double.parseDouble(maxRow.get(column));
            if (val > MaxVal) maxRow = row;
        }

        return maxRow;
    }

    public void testColdestHourInFile()
    {
        FileResource fr = new FileResource();
        CSVRecord row = coldestHourInFile(fr.getCSVParser());
        System.out.println("coldest temperature was " + row.get("TemperatureF") +
            " at " + row.get("TimeEST"));
    }

    public void testFileWithColdestTemperature()
    {
        System.out.println(fileWithColdestTemperature());
    }

    public void testLowestHumidityInFile()
    {
        FileResource fr = new FileResource();
        CSVRecord row = lowestHumidityInFile(fr.getCSVParser());
        System.out.println(
            "Lowest Humidity was " + row.get("Humidity") +
            " at DateUTC " + row.get("DateUTC")
        );
    }

    public void testLowestHumidityInManyFiles()
    {
        CSVRecord row = lowestHumidityInManyFiles();
        System.out.println("Lowest Humidity was " + row.get("Humidity") + " at " + row.get("DateUTC"));
    }

    public void testAverageTemperatureInFile()
    {
        FileResource fr = new FileResource();
        double ans = averageTemperatureInFile(fr.getCSVParser());
        System.out.println("Average temperature in file is " + ans);
    }

    public void testAverageTemperatureWithHighHumidityInFile()
    {
        FileResource fr = new FileResource();
        Double ans = averageTemperatureWithHighHumidityInFile(fr.getCSVParser(), 80);
        if (ans == null) {
            System.out.println("No temperatures with that humidity");
        } else {
            System.out.println("Average Temp when high Humidity is " + ans);
        }

    }
}
