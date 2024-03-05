/**
 * @author Benny Sun
 */
import edu.duke.*;
import org.apache.commons.csv.*;

public class WhichCountriesExport {
    public String countryInfo(CSVParser parser, String country) {
        for (CSVRecord record: parser) {
            String colCountry = record.get("Country");
            if (colCountry.equals(country)) {
                return record.get("Country") + ": "
                + record.get("Exports") + ": "
                + record.get("Value (dollars)");
            }
        }

        return "";
    }

    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2) {
        for (CSVRecord record: parser) {
            String export = record.get("Exports");
            if (export.contains(exportItem1) && export.contains(exportItem2)) {
                String country = record.get("Country");
                System.out.println(country);
            }
        }
    }

    public int numberOfExporters(CSVParser parser, String exportItem) {
        int count = 0;
        for (CSVRecord record: parser) {
            String export = record.get("Exports");
            if (export.contains(exportItem)) count++;
        }

        return count;
    }

    public void bigExporters(CSVParser parser, String dollars) {
        for (CSVRecord record: parser) {
            String value = record.get("Value (dollars)");
            if (value.length() >= dollars.length()) {
                System.out.println(record.get("Country") + " " + value);
            }
        }
    }

    public void tester1() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        String result = countryInfo(parser, "Nauru");
        System.out.println(result);
    }

    public void tester2() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        // listExportersTwoProducts(parser, "gold", "diamonds");
        listExportersTwoProducts(parser, "cotton", "flowers");
    }

    public void tester3() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        // String keyword = "sugar";
        String keyword = "cocoa";
        System.out.println("Number of countries: " + numberOfExporters(parser, keyword));
    }
    
    public void tester4() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        String keyword = "$1,000,000,000,000";
        bigExporters(parser, keyword);
    }
}
