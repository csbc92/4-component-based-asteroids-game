/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.apidesign.demo.anagramwithspringandlookup;

import java.awt.GraphicsEnvironment;
import org.apidesign.spring.SpringAndLookup;
import org.junit.Test;
import org.openide.util.Lookup;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
public class MainTest {

    public MainTest() {
    }

    @Test public void makeSureTheWiringsArePossibleAndMainSucceeds() throws Exception {
        if (GraphicsEnvironment.isHeadless()) {
            // can't test as the main creates a JFrame
            return;
        }
        Main.main(null);
    }

    @Test public void makeSureTheWiringsArePossibleArtificially() throws Exception {
        ApplicationContext servicesContext = SpringAndLookup.create(
            Lookup.getDefault(), "java.extensions"
        );
        ClassPathXmlApplicationContext mergedContext;
        mergedContext = new ClassPathXmlApplicationContext(
            new String[] { "Test.xml" },
            Main.class,
            servicesContext
        );
        UI ui = (UI)mergedContext.getBean("ui", UI.class);
        ui.display();
    }
}