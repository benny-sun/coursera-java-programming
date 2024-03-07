import edu.duke.*;
import java.io.File;

public class AllGenes
{
    public AllGenes() {}

    public StorageResource allGenes(String dna) {
        StorageResource res = new StorageResource();
        int startIndex = 0;
        while (true) {
            String currentGene = findGene(dna, startIndex);
            if (currentGene.isEmpty()) {
                break;
            }
            res.add(currentGene);
            startIndex = dna.indexOf(currentGene, startIndex) + currentGene.length();
        }

        return res;
    }

    public int longerThanLength(StorageResource genes, int n) {
        int count = 0;
        for (String gene: genes.data()) {
            if (gene.length() > 60) count++;
        }

        return count;
    }

    public int longestGeneLength(StorageResource genes) {
        int max = 0;
        for (String gene: genes.data()) {
            max = Math.max(gene.length(), max);
        }

        return max;
    }

    public int greaterThanCgRatio(StorageResource genes, double n) {
        int count = 0;
        for (String gene: genes.data()) {
            if (cgRatio(gene) > n) count++;
        }

        return count;
    }

    public int countCodon(String dna, String codon) {
        int count = 0;
        int dLen = dna.length();
        int cLen = codon.length();
        int i = dna.indexOf(codon);
        while (i != -1) {
            count++;
            if (i + cLen >= dLen) return count;
            i = dna.indexOf(codon, i + cLen);
        }

        return count;
    }

    public String findGene(String dna, int where) {
        int startIndex = dna.indexOf("ATG", where);
        if (startIndex == -1) return "";

        int taaIndex = findStopCodon(dna, startIndex, "TAA");
        int tagIndex = findStopCodon(dna, startIndex, "TAG");
        int tgaIndex = findStopCodon(dna, startIndex, "TGA");
        int minIndex = 0;
        if (taaIndex == -1 || (tgaIndex != -1 && tgaIndex < taaIndex)) {
            minIndex = tgaIndex;
        } else {
            minIndex = taaIndex;
        }

        if (minIndex == -1 || (tagIndex != -1 && tagIndex < minIndex)) {
            minIndex = tagIndex;
        }

        if (minIndex == -1) return "";

        if (minIndex + 3 >= dna.length()) return "";

        return dna.substring(startIndex, minIndex + 3);
    }

    public int findStopCodon(String dnaStr, int startIndex, String stopCodon) {
        int currIndex = dnaStr.indexOf(stopCodon, startIndex + 3);
        while (currIndex != -1) {
            int diff = currIndex - startIndex;
            if (diff % 3 == 0) {
                return currIndex;
            } else {
                currIndex = dnaStr.indexOf(stopCodon, currIndex + 1);
            }
        }

        return dnaStr.length();
    }

    public float cgRatio(String dna) {
        String dnaLow = dna.toLowerCase();
        int cgCount = 0;
        for (int i = 0; i < dnaLow.length(); i++) {
            char c = dnaLow.charAt(i);
            if (c == 'c' || c == 'g') cgCount++;
        }

        return ( (float) cgCount ) / dna.length();

    }

    public void testLongestGeneLength() {
        StorageResource genes = new StorageResource();
        genes.add("XXXYYYZZZ"); // 9
        genes.add("XXXYYYZZZXXYYZZXYZ"); // 18
        genes.add("XXXYYYZZZXYZ"); // 12
        int expected = 18;

        String result = "failure";
        if (longestGeneLength(genes) == expected) {
            result = "passed";
        }

        System.out.println("testLongestGeneLength: " + result);
    }

    public void testCountCodon() {
        String dna = "xxxyyyzzzCTGxxyyzzCTGxyzCTGCTGTGCTTCCTGTG";
        String codon = "CTG";
        int expected = 5;

        String result = "failure";
        if (countCodon(dna, codon) == expected) {
            result = "passed";
        }

        System.out.println("testCountCodon: " + result);
    }

    public void testFindStop() {
        String dna = "xxxyyyzzzTAAxxxyyyzzzTAAxx";

        int dex = findStopCodon(dna, 0, "TAA");
        if (dex != 9) System.out.println("testFindStop error on 9");

        dex = findStopCodon(dna, 9, "TAA");
        if (dex != 21) System.out.println("testFindStop error on 21");

        dex = findStopCodon(dna, 1, "TAA");
        if (dex != 26) System.out.println("testFindStop error on 26");

        dex = findStopCodon(dna, 0, "TAG");
        if (dex != 26) System.out.println("testFindStop error on 26 TAG");

        System.out.println("testFindStop passed");

    }

    public void testAllGenes() {
        // case 1
        String dna = "ATGATCATAAGAAGATAATAGAGGGCCATGTAA";
        String[] expecteds = new String[]{"ATGATCATAAGAAGATAA"};
        StorageResource genes = allGenes(dna);
        for (String expected: expecteds) {
            if (! genes.contains(expected)) {
                System.out.println("testAllGenes failure:");
                System.out.println("DNA:" + dna);
                System.out.println("expected gene not exists: " + expected);
                return;
            }
        }
        if (genes.size() != expecteds.length) {
            System.out.println("Count of genes not equal to: " + expecteds.length);
            return;
        }

        // case 2
        dna = "ATGATCTAATTTATGCTGCAACGGTGAAGA";
        expecteds = new String[]{"ATGATCTAA", "ATGCTGCAACGGTGA"};
        genes = allGenes(dna);
        for (String expected: expecteds) {
            if (! genes.contains(expected)) {
                System.out.println("testAllGenes failure:");
                System.out.println("DNA:" + dna);
                System.out.println("expected gene not exists: " + expected);
                return;
            }
        }
        if (genes.size() != expecteds.length) {
            System.out.println("Count of genes not equal to: " + expecteds.length);
            return;
        }

        // case 3
        dna = "";
        genes = allGenes(dna);
        if (genes.size() != 0) {
            System.out.println("testAllGenes failure:");
            System.out.println("DNA:" + dna);
            System.out.println("Count of genes not equal to: 0");
            return;
        }

        System.out.println("testAllGenes passed");
    }

    public void test() {
        testLongestGeneLength();
        testCountCodon();
        testFindStop();
        testAllGenes();
    }
}
