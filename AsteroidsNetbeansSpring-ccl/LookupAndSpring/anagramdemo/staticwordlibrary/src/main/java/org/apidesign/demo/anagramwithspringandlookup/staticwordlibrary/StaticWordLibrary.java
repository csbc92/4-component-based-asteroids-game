
package org.apidesign.demo.anagramwithspringandlookup.staticwordlibrary;

import org.apidesign.demo.anagramwithspringandlookup.WordLibrary;
import org.openide.util.lookup.ServiceProvider;

// BEGIN: anagramdemo.springlookup.wordlibrary
@ServiceProvider(service=WordLibrary.class)
public class StaticWordLibrary implements WordLibrary {
    
    public StaticWordLibrary() {
        System.out.println(this.getClass().getCanonicalName());
    }
// FINISH: anagramdemo.springlookup.wordlibrary
    private static String wordList[] = {
        "abstraction",
        "ambiguous",
        "arithmetic",
        "backslash",
        "bitmap",
        "circumstance",
        "combination",
        "consequently",
        "consortium",
        "decrementing",
        "dependency",
        "disambiguate",
        "dynamic",
        "encapsulation",
        "equivalent",
        "expression",
        "facilitate",
        "fragment",
        "hexadecimal",
        "implementation",
        "indistinguishable",
        "inheritance",
        "internet",
        "java",
        "localization",
        "microprocessor",
        "navigation",
        "optimization",
        "parameter",
        "patrick",
        "pickle",
        "polymorphic",
        "rigorously",
        "simultaneously",
        "specification",
        "structure",
        "lexical",
        "likewise",
        "management",
        "manipulate",
        "mathematics",
        "hotjava",
        "vertex",
        "unsigned",
        "traditional"};

    public String[] getWords() {
        return wordList;
    }
}
