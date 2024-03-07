/**
 * @author Benny Sun
 */
import edu.duke.*;
import java.util.ArrayList;

public class CharactersInPlay
{
    ArrayList<String> characters;
    ArrayList<Integer> freqs;

    public CharactersInPlay()
    {
        characters = new ArrayList<>();
        freqs = new ArrayList<>();
    }

    public void findAllCharacters()
    {
        characters.clear();
        freqs.clear();
        FileResource resource = new FileResource();

        for (String line: resource.lines()) {
            int idx = line.indexOf('.');
            if (idx != -1) {
                String name = line.substring(0, idx);
                update(name);
            }
        }
    }

    public void update(String person)
    {
        int idx = characters.indexOf(person);
        if (idx == -1) { // create
            characters.add(person);
            freqs.add(1);
        } else { // update
            int freq = freqs.get(idx);
            freqs.set(idx, freq + 1);
        }
    }

    public void tester()
    {
        findAllCharacters();
        printTheMostSpeaking();
        charactersWithNumParts(10, 15);
    }

    private void printTheMostSpeaking()
    {
        int max = 0;
        String topName = "";
        for (int i = 0; i < characters.size(); i++) {
            String character = characters.get(i);
            int freq = freqs.get(i);
            if (freq > max) {
                max = freq;
                topName = character;
            }
        }
        System.out.println("The most speaking parts: " + topName + " " + max);
    }

    private void charactersWithNumParts(int num1, int num2)
    {
        System.out.println(String.format("Speaking parts in range %d to %d:", num1, num2));
        for (int i = 0; i < characters.size(); i++) {
            String character = characters.get(i);
            int freq = freqs.get(i);
            if (num1 <= freq && freq <= num2) {
                System.out.println(character + " " + freq);
            }
        }
    }
}
