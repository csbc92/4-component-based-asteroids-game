/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.osgienemy;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 *
 * @author ccl
 */
public class EnemyActivator implements BundleActivator {
    
    public void start(BundleContext context) throws Exception {
        System.out.println("EnemyActivator.start() called..");
        context.registerService(EnemyPlugin.class, new EnemyPlugin(), null);
        context.registerService(EnemyControlSystem.class, new EnemyControlSystem(), null);
        
    }
    
    public void stop(BundleContext context) throws Exception {
        System.out.println("EnemyActivator.stop() called..");
    }
    
}
