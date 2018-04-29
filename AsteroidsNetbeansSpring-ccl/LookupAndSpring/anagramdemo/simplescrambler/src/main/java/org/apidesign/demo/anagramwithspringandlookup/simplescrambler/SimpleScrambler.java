package org.apidesign.demo.anagramwithspringandlookup.simplescrambler;

import java.util.Random;
import org.apidesign.demo.anagramwithspringandlookup.Scrambler;
import org.openide.util.lookup.ServiceProvider;

// BEGIN: anagramdemo.springlookup.scrambler
@ServiceProvider(service=Scrambler.class)
public class SimpleScrambler implements Scrambler {
    private static final Random random = new Random();

    
    public SimpleScrambler() {
        System.out.println(this.getClass().getCanonicalName());
    }
    
    public String scramble(String word) {
// FINISH: anagramdemo.springlookup.scrambler
        for (;;) {
            int index1 = random.nextInt(word.length());
            int index2 = random.nextInt(word.length());

            if (index1 == index2) {
                continue;
            }

            char char1 = word.charAt(index1);
            char char2 = word.charAt(index2);
            
            StringBuilder sb = new StringBuilder(word);
            sb.setCharAt(index1, char2);
            sb.setCharAt(index2, char1);
            return sb.toString();
        }
    }

}
