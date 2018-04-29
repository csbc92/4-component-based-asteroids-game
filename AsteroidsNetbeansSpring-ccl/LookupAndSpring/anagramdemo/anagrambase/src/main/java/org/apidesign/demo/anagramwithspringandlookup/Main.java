package org.apidesign.demo.anagramwithspringandlookup;

import java.util.Map;
import org.apidesign.spring.SpringAndLookup;
import org.openide.util.Lookup;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class Main {
    private Main() { }

    // BEGIN: anagramdemo.springlookup.main
    public static void main(String[] args) throws Exception {
        ApplicationContext servicesContext = SpringAndLookup.create(
            Lookup.getDefault(), "java.extensions"
        );
        ClassPathXmlApplicationContext mergedContext;
        mergedContext = new ClassPathXmlApplicationContext(
            new String[] { "Main.xml" },
            Main.class,
            servicesContext
        );
        
        Map map = mergedContext.getBeansOfType(UI.class);
        System.out.println(map.isEmpty());
        
        UI ui = (UI)mergedContext.getBean("ui", UI.class);
        
        
        
        ui.display();
        
        
    }
    // END: anagramdemo.springlookup.main
}
