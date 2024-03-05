
/**
 * Write a description of class Playground here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Playground
{
    public Playground()
    {
    }

    public void test1()
    {
        String s = "abcdefg";
        System.out.println(s.indexOf("de", 3));
    }

    public void test2()
    {
        String dna = "xxxyyyzzzTxxxyyyzzzTxxyyzzTxxyyzzTxyz";
        System.out.println(mystery(dna));
    }

    public String mystery(String dna) {
        int pos = dna.indexOf("T");
        int count = 0;
        int startPos = 0;
        String newDna = "";
        if (pos == -1) {
            return dna;
        }
        while (count < 3) {
            count += 1;
            newDna = newDna + dna.substring(startPos,pos);
            startPos = pos+1;
            pos = dna.indexOf("T", startPos);
            if (pos == -1) {
                break;
            }
        }
        newDna = newDna + dna.substring(startPos);
        return newDna;
    }
}
