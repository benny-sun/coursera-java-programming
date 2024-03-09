/**
 * @author Benny Sun
 */
import edu.duke.*;
import java.util.*;

public class DnaCodon
{
    private HashMap<String, Integer> codons;

    public DnaCodon()
    {
        codons = new HashMap<>();
    }

    public void buildCodonMap(int start, String dna)
    {
        codons.clear();
        dna = dna.trim();
        
        int n = dna.length();
        for (int i = start; i < n; i += 3) {
            if (i + 3 >= n) break;
            String codon = dna.substring(i, i + 3);
            int freq = codons.getOrDefault(codon, 0);
            codons.put(codon, freq + 1);
        }
    }

    public String getMostCommonCodon()
    {
        String ans = "";
        int max = 0;
        for (String codon: codons.keySet()) {
            int freq = codons.get(codon);
            if (freq > max) {
                max = freq;
                ans = codon;
            }
        }

        return ans;
    }

    private void printCodonCounts(int start, int end)
    {
        codons.forEach((k, v) -> {
                    if (start <= v && v <= end) {
                        System.out.println("key:" + k + ", value" + v);
                    }
            });
    }

    public void tester()
    {
        FileResource fr = new FileResource();
        String dna = fr.asString();

        for (int i = 0; i < 3; i++) {
            buildCodonMap(i, dna);
            System.out.println("Unique codons: " + codons.size());
        }
    }
}
