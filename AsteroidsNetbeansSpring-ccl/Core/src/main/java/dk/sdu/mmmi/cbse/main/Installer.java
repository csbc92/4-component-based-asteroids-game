/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.main;

import org.apidesign.spring.SpringAndLookup;
import org.openide.modules.ModuleInstall;
import org.openide.util.Lookup;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        ApplicationContext servicesContext = SpringAndLookup.create(Lookup.getDefault(), "java.extensions");
        ApplicationContext mergedContext = new ClassPathXmlApplicationContext(new String[] { "application-context.xml" }, Game.class, servicesContext);
    }

}
