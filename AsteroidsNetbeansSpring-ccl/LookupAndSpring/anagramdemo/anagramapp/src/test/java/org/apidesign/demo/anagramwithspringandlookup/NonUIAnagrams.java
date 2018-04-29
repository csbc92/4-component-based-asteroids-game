/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apidesign.demo.anagramwithspringandlookup;

import junit.framework.Assert;

/** Test only "UI" to verify that library and scrambler are
 * provided from the Lookup.getDefault().
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
public final class NonUIAnagrams implements UI {
    final WordLibrary library;
    final Scrambler scrambler;

    public NonUIAnagrams(WordLibrary l, Scrambler s) {
        this.library = l;
        this.scrambler = s;
    }

    public void display() {
        Assert.assertNotNull("Word Library provided", library);
        Assert.assertNotNull("Scrambler provided", scrambler);
    }
}
