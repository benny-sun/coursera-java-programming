
import edu.duke.*;

public class MarkovRunner {
    public void testHashMap() {
        String st = "this is a test yes this is really a test";
        EfficientMarkovWord markovWord = new EfficientMarkovWord(2);
        runModel(markovWord, st, 50, 42);
        
        st = "this is a test yes this is really a test yes a test this is wow";
        markovWord = new EfficientMarkovWord(2);
        runModel(markovWord, st, 50, 42);
    }
    
    public void runModel(IMarkovModel markov, String text, int size){ 
        markov.setTraining(text); 
        System.out.println("running with " + markov); 
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 

    public void runModel(IMarkovModel markov, String text, int size, int seed){ 
        markov.setTraining(text); 
        markov.setRandom(seed);
        System.out.println("running with " + markov); 
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 

    public void runMarkov() { 
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        //MarkovWordOne markovWord = new MarkovWordOne(); 
        //runModel(markovWord, st, 200);
        
        //MarkovWord markovWord = new MarkovWord(2);
        //runModel(markovWord, st, 100, 42);
        
        EfficientMarkovWord effEarkovWord = new EfficientMarkovWord(2);
        runModel(effEarkovWord, st, 100, 42);
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
    }}
