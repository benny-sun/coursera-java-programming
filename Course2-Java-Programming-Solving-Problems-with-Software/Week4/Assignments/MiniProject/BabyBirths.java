/**
 * @author Benny Sun
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.util.*;
import java.util.stream.IntStream;

public class BabyBirths {

    private String filePathFormat = "data/yob%d.csv";

    public void printNames () {
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <= 100) {
                System.out.println("Name " + rec.get(0) +
                    " Gender " + rec.get(1) +
                    " Num Born " + rec.get(2));
            }
        }
    }

    public void totalBirths (FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
            }
            else {
                totalGirls += numBorn;
            }
        }
        System.out.println("total births = " + totalBirths);
        System.out.println("female girls = " + totalGirls);
        System.out.println("male boys = " + totalBoys);

        int girlsNames = numberOfGender(fr, "F");
        int boysNames = numberOfGender(fr, "M");
        int totalNames = girlsNames + boysNames;
        System.out.println("total names = " + totalNames);
        System.out.println("girls names = " + girlsNames);
        System.out.println("boys names = " + boysNames);
    }

    public int getRank(int year, String name, String gender) {
        int rank = 0;
        FileResource fr = new FileResource(getFilePath(year));
        gender = gender.toUpperCase();
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (! gender.equals(rec.get(1))) continue;
            rank++;
            if (name.equals(rec.get(0))) return rank;
        }
        
        return -1;
    }

    public String getName(int year, int rank, String gender) {
        try {
            FileResource fr = new FileResource(getFilePath(year));
            int cur = 0;
            for (CSVRecord rec : fr.getCSVParser(false)) {
                if (! gender.equals(rec.get(1))) continue;
                cur++;
                if (cur == rank) return rec.get(0);
            }
        } catch (Exception e) {}

        return "NO NAME";
    }

    public String whatIsNameInYear(String name, int year, int newYear, String gender) {
        int rank = getRank(year, name, gender);
        String newName = getName(newYear, rank, gender);
        String pron = gender.equals("M") ? "he" : "she";

        return name + " born in " + year + " would be " + newName + " if " + pron + " was born in " + newYear + ".";
    }

    public int yearOfHighestRank(String name, String gender, int[] years) {
        Integer ans = null;
        int highestRank = Integer.MAX_VALUE;
        for (int year: years) {
            int rank = getRank(year, name, gender);
            if (rank == -1) continue;
            if (ans == null) {
                ans = year;
                highestRank = rank;
            } else {
                if (rank < highestRank) {
                    ans = year;
                    highestRank = rank;
                }
            }
        }

        return ans == null ? -1 : ans;
    }

    public double getAverageRank(String name, String gender, int[] years) {
        int sum = 0;
        for (int year: years) {
            int rank = getRank(year, name, gender);
            if (rank == -1) continue;
            sum += rank;
        }

        return sum == 0 ? -1.0 : (double) sum / years.length;
    }

    public int getTotalBirthsRankedHigher(int year, String name, String gender) {
        int rank = getRank(year, name, gender);
        int cur = 1, sum = 0;
        FileResource fr = new FileResource(getFilePath(year));
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (! gender.equals(rec.get(1))) continue;
            if (cur == rank) return sum;
            int births = Integer.parseInt(rec.get(2));
            sum += births;
            cur++;
        }

        return sum;
    }

    private int numberOfName(FileResource fr, String name, String gender) {
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (name.equals(rec.get(0)) && gender.equals(rec.get(1)))
                return Integer.parseInt(rec.get(2));
        }

        return -1;
    }

    private int numberOfGender(FileResource fr, String gender) {
        int boys = 0;
        int girls = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals("M"))
                boys++;
            else
                girls++;
        }

        return gender.equals("M") ? boys : girls;
    }

    private String getFilePath(int year) {
        return String.format(filePathFormat, year);
    }

    private void setFilefilePathFormat(String format) {
        filePathFormat = format;
    }

    public void testGetFilePath() {
        setFilefilePathFormat("data/yob%d.csv");
        String expected = "data/yob2012.csv";
        String actual = getFilePath(2012);
        String result = actual.equals(expected) ? "Passed" : "Failure";
        System.out.println(
            "testGetFilePath: " + result +
            ", Expected: " + expected +
            ", Actual: " + actual
        );

        setFilefilePathFormat("data/yob%dshort.csv");
        expected = "data/yob2013short.csv";
        actual = getFilePath(2013);
        result = actual.equals(expected) ? "Passed" : "Failure";
        System.out.println(
            "testGetFilePath: " + result +
            ", Expected: " + expected +
            ", Actual: " + actual
        );

    }

    public void testTotalBirths () {
        //FileResource fr = new FileResource();
        FileResource fr = new FileResource(getFilePath(2012));
        totalBirths(fr);
    }

    public void testGetRank() {
        //int ans = getRank(1971, "Frank", "M");
        //System.out.println("quiz: " + ans);
        
        int expected = 2;
        int actual = getRank(2012, "Mason", "M");
        String result = actual == expected ? "Passed" : "Failure";
        System.out.println(
            "testGetRank: " + result +
            ", Expected: " + expected +
            ", Actual: " + actual
        );

        expected = -1;
        actual = getRank(2012, "BennyFromTaiwan", "F");
        result = actual == expected ? "Passed" : "Failure";
        System.out.println(
            "testGetRank: " + result +
            ", Expected: " + expected +
            ", Actual: " + actual
        );
    }

    public void testGetName() {
        //String ans = getName(1982, 450, "M");
        //System.out.println("quiz: " + ans);
        
        String expected = "Mason";
        String actual = getName(2012, 2, "M");
        String result = actual.equals(expected) ? "Passed" : "Failure";
        System.out.println(
            "testGetName: " + result +
            ", Expected: " + expected +
            ", Actual: " + actual
        );

        expected = "NO NAME";
        actual = getName(2012, 99999, "F");
        result = actual.equals(expected) ? "Passed" : "Failure";
        System.out.println(
            "testGetName: " + result +
            ", Expected: " + expected +
            ", Actual: " + actual
        );
    }

    public void testWhatIsNameInYear() {
        //String ans = whatIsNameInYear("Susan", 1972, 2014, "F");
        //System.out.println("quiz: " + ans);
        
        String expected = "Isabella born in 2012 would be Sophia if she was born in 2014.";
        String actual = whatIsNameInYear("Isabella", 2012, 2014, "F");
        String result = actual.equals(expected) ? "Passed" : "Failure";
        System.out.println(
            "testWhatIsNameInYear: " + result +
            "\nExpected: " + expected +
            "\nActual: " + actual
        );
    }

    public void testYearOfHighestRank() {
        //int[] years = IntStream.rangeClosed(1880, 2014).toArray();
        //int ans = yearOfHighestRank("Mich", "M", years);
        //System.out.println("quiz: " + ans);
        
        int expected = 2012;
        int actual = yearOfHighestRank("Mason", "M", new int[]{2012, 2013, 2014});
        String result = actual == expected ? "Passed" : "Failure";
        System.out.println(
            "testYearOfHighestRank: " + result +
            ", Expected: " + expected +
            ", Actual: " + actual
        );

        expected = -1;
        actual = yearOfHighestRank("BennyFromTaiwan", "M", new int[]{2012, 2013, 2014});
        result = actual == expected ? "Passed" : "Failure";
        System.out.println(
            "testYearOfHighestRank: " + result +
            ", Expected: " + expected +
            ", Actual: " + actual
        );
    }

    public void testGetAverageRank() {
        //int[] years = IntStream.rangeClosed(1880, 2014).toArray();
        //double ans = getAverageRank("Robert", "M", years);
        //System.out.println("quiz: " + ans);
        
        double expected = 3.0;
        double actual = getAverageRank("Mason", "M", new int[]{2012, 2013, 2014});
        actual = Math.floor(actual * 100.0) / 100.0;
        String result = actual == expected ? "Passed" : "Failure";
        System.out.println(
            "testGetAverageRank: " + result +
            ", Expected: " + expected +
            ", Actual: " + actual
        );

        expected = 2.66;
        actual = getAverageRank("Jacob", "M", new int[]{2012, 2013, 2014});
        actual = Math.floor(actual * 100.0) / 100.0;
        result = actual == expected ? "Passed" : "Failure";
        System.out.println(
            "testGetAverageRank: " + result +
            ", Expected: " + expected +
            ", Actual: " + actual
        );
    }

    public void testGetTotalBirthsRankedHigher() {
        //int ans = getTotalBirthsRankedHigher(1990, "Drew", "M");
        //System.out.println("quiz: " + ans);

        int expected = 15;
        int actual = getTotalBirthsRankedHigher(2012, "Ethan", "M");
        String result = actual == expected ? "Passed" : "Failure";
        System.out.println(
            "testGetTotalBirthsRankedHigher: " + result +
            ", Expected: " + expected +
            ", Actual: " + actual
        );

        expected = 0;
        actual = getTotalBirthsRankedHigher(2012, "Jacob", "M");
        result = actual == expected ? "Passed" : "Failure";
        System.out.println(
            "testGetTotalBirthsRankedHigher: " + result +
            ", Expected: " + expected +
            ", Actual: " + actual
        );
    }

    public void test() {
        setFilefilePathFormat("testing/yob%dshort.csv");
        testTotalBirths();
        testGetRank();
        testGetName();
        testWhatIsNameInYear();
        testYearOfHighestRank();
        testGetAverageRank();
        testGetTotalBirthsRankedHigher();
    }
}
