
/**
 * Write a description of class MarkovRunner here.
 * 
 * @author Duke Software
 * @version 1.0
 */

import edu.duke.*; 

public class MarkovRunnerWithInterface {
    
    public void compareMethods()
    {
        FileResource fr = new FileResource("data/hawthorne.txt");
        String input = fr.asString();
        
        long t0 = System.nanoTime();
        
        IMarkovModel markov = new MarkovModel(2);
        runModel(markov, input, 1000, 42);
        
        long t1 = System.nanoTime();
        System.out.println("MarkovModel finished use: " + (t1 - t0) / 1000000 + "ns");
        
        markov = new EfficientMarkovModel(2);
        runModel(markov, input, 1000, 42);
        
        long t2 = System.nanoTime();
        System.out.println("EfficientMarkovModel finished use: " + (t2 - t1) / 1000000 + "ns");
    }
    
    public void testHashMap() {
        EfficientMarkovModel markov = new EfficientMarkovModel(2);
        String input = "yes-this-is-a-thin-pretty-pink-thistle";
        runModel(markov, input, 50, 42);
    }

    public void runModel(IMarkovModel markov, String text, int size, int seed) {
        markov.setRandom(seed);
        markov.setTraining(text);
        System.out.println("running with " + markov);
        for(int k=0; k < 3; k++){
            String st= markov.getRandomText(size);
            printOut(st);
        }
    }

    public void runMarkov() {
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        int size = 200;
        int seed = 42;

        MarkovZero mz = new MarkovZero();
        runModel(mz, st, size, seed);

        MarkovOne mOne = new MarkovOne();
        runModel(mOne, st, size, seed);

        MarkovModel mThree = new MarkovModel(3);
        runModel(mThree, st, size, seed);

        MarkovFour mFour = new MarkovFour();
        runModel(mFour, st, size, seed);

    }

    private void printOut(String s){
        String[] words = s.split("\\s+");
        int psize = 0;
        System.out.println("----------------------------------");
        for(int k=0; k < words.length; k++){
            System.out.print(words[k]+ " ");
            psize += words[k].length() + 1;
            if (psize > 60) {
                System.out.println();
                psize = 0;
            }
        }
        System.out.println("\n----------------------------------");
    }
}
