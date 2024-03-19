/**
 * @author Benny Sun
 */
import java.util.*;
import edu.duke.*;

public class Tester
{
    public void testGetFollows()
    {
        List<String> expected = new ArrayList<>(Arrays.asList("h", "e", " ", "h", "e", "."));
        String input = "this is a test yes this is a test.";
        MarkovOne sut = new MarkovOne();
        sut.setTraining(input);
        ArrayList<String> actual = sut.getFollows("t");
        String result = expected.equals(actual) ? "Passed" : "Failure";
        System.out.println(result + " testGetFollows()");
        
        expected = Arrays.asList("s", "s", "s");
        input = "this is a test yes this is a test.";
        sut.setTraining(input);
        actual = sut.getFollows("e");
        result = expected.equals(actual) ? "Passed" : "Failure";
        System.out.println(result + " testGetFollows()");
        
        expected = Arrays.asList("t", " ", "t");
        input = "this is a test yes this is a test.";
        sut.setTraining(input);
        actual = sut.getFollows("es");
        result = expected.equals(actual) ? "Passed" : "Failure";
        System.out.println(result + " testGetFollows()");
    }
    
    public void testGetFollowsWithFile()
    {
        int expected = 11548;
        
        FileResource fr = new FileResource("data/confucius.txt");
        String input = fr.asString();
        
        MarkovOne sut = new MarkovOne();
        sut.setTraining(input);
        
        ArrayList<String> actual = sut.getFollows("t");
        String result = expected == actual.size() ? "Passed" : "Failure";
        System.out.println(result + " testGetFollowsWithFile()");
    }
}
