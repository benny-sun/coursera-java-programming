/**
 * @author Benny Sun
 */
import edu.duke.*;
import java.util.*;

public class VigenereBreakerTester
{   
    public void testBreakVigenere()
    {
        System.out.println("Start testBreakVigenere()");
        
        VigenereBreaker sut = new VigenereBreaker();
        sut.breakVigenere("testing/athens_keyflute.txt");
        
        System.out.println("Finished testBreakVigenere()");
    }
    
    public void testTryKeyLength()
    {
        System.out.println("Start testTryKeyLength()");
        
        FileResource fr = new FileResource("testing/athens_keyflute.txt");
        VigenereBreaker sut = new VigenereBreaker();
        int[] expected = {5, 11, 20, 19, 4};
        int[] actual = sut.tryKeyLength(fr.asString(), "fluet".length(), 'e');
        System.out.println(Arrays.toString(actual));
        System.out.println(Arrays.equals(expected, actual) ? "Passed" : "Failure");
        
        System.out.println("Finished testTryKeyLength()");
    }
    
    public void testSliceString()
    {
        System.out.println("Start testSliceString()");
        
        VigenereBreaker sut = new VigenereBreaker();
        String expected = "adgjm";
        String actual = sut.sliceString("abcdefghijklm", 0, 3);
        printStringEquals(expected, actual);
        
        expected = "behk";
        actual = sut.sliceString("abcdefghijklm", 1, 3);
        printStringEquals(expected, actual);
        
        expected = "cfil";
        actual = sut.sliceString("abcdefghijklm", 2, 3);
        printStringEquals(expected, actual);
        
        expected = "aeim";
        actual = sut.sliceString("abcdefghijklm", 0, 4);
        printStringEquals(expected, actual);
        
        expected = "bfj";
        actual = sut.sliceString("abcdefghijklm", 1, 4);
        printStringEquals(expected, actual);
        
        expected = "cgk";
        actual = sut.sliceString("abcdefghijklm", 2, 4);
        printStringEquals(expected, actual);
        
        expected = "dhl";
        actual = sut.sliceString("abcdefghijklm", 3, 4);
        printStringEquals(expected, actual);
        
        expected = "afk";
        actual = sut.sliceString("abcdefghijklm", 0, 5);
        printStringEquals(expected, actual);
        
        expected = "bgl";
        actual = sut.sliceString("abcdefghijklm", 1, 5);
        printStringEquals(expected, actual);
        
        expected = "chm";
        actual = sut.sliceString("abcdefghijklm", 2, 5);
        printStringEquals(expected, actual);
        
        expected = "di";
        actual = sut.sliceString("abcdefghijklm", 3, 5);
        printStringEquals(expected, actual);
        
        expected = "ej";
        actual = sut.sliceString("abcdefghijklm", 4, 5);
        printStringEquals(expected, actual);
        
        System.out.println("Finished testSliceString()");
    }

    private void printStringEquals(String expected, String actual)
    {
         String result = actual.equals(expected) ? "Passed" : "Failure";
         System.out.println(result);
    }
}
