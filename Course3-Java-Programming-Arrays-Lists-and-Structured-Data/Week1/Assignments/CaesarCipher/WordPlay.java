/**
 * @author Benny Sun
 */
import edu.duke.*;

public class WordPlay
{
    public String emphasize(String phrase, char ch)
    {
        ch = Character.toLowerCase(ch);
        StringBuilder ans = new StringBuilder(phrase);
        for (int i = 0; i < phrase.length(); i++) {
            char cur = phrase.charAt(i);
            if (Character.toLowerCase(cur) == ch) {
                boolean isOdd = ((i + 1) & 1) == 1;
                ans.setCharAt(i, isOdd ? '*' : '+');
            }
        }

        return ans.toString();
    }

    public String replaceVowels(String phrase, char ch)
    {
        StringBuilder ans = new StringBuilder(phrase);
        for (int i = 0; i < phrase.length(); i++) {
            if (isVowel(phrase.charAt(i))) ans.setCharAt(i, ch);
        }

        return ans.toString();
    }

    public boolean isVowel(char ch)
    {   
        return "aeiou".indexOf(Character.toLowerCase(ch)) != -1;
    }

    public void testEmphasize()
    {
        String expected = "dn* ctg+*+ctg+";
        String actual = emphasize("dna ctgaaactga", 'a');
        String result = expected.equals(actual) ? "Passed" : "Failure";
        System.out.println(result + " testEmphasize()");

        expected = "M+ry Bell+ +br*c*d*br+";
        actual = emphasize("Mary Bella Abracadabra", 'a');
        result = expected.equals(actual) ? "Passed" : "Failure";
        System.out.println(result + " testEmphasize()");
    }

    public void testReplaceVowels()
    {
        String expected = "H*ll* W*rld";
        String actual = replaceVowels("Hello World", '*');
        String result = expected.equals(actual) ? "Passed" : "Failure";
        System.out.println(result + " testReplaceVowels()");
    }

    public void testIsVowel()
    {
        boolean expected = false;
        boolean actual = isVowel('F');
        String result = expected == actual ? "Passed" : "Failure";
        System.out.println(result + " testIsVowel()");

        expected = true;
        actual = isVowel('a');
        result = expected == actual ? "Passed" : "Failure";
        System.out.println(result + " testIsVowel()");
    }
    
    public void test()
    {
        testIsVowel();
        testReplaceVowels();
        testEmphasize();
    }
}
