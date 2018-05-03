/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.io.IOException;
import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import junit.framework.Test;
import org.netbeans.junit.NbModuleSuite;
import org.netbeans.junit.NbTestCase;
import org.openide.util.Lookup;

public class IntegrationTest extends NbTestCase {

    private static final String ADD_ENEMY_UPDATES_FILE = "/home/ccl/git/komponentbaseret-labs/AsteroidsNetbeansModules-ccl/application/src/test/resources/enemy/updates.xml";
    private static final String REM_ENEMY_UPDATES_FILE = "/home/ccl/git/komponentbaseret-labs/AsteroidsNetbeansModules-ccl/application/src/test/resources/removeenemy/updates.xml";
    private static final String UPDATES_FILE = "/home/ccl/git/komponentbaseret-labs/AsteroidsNetbeansModules-ccl/UpdateCenter/netbeans_site/updates.xml";
    private static final String UPDATES_FILE_BACKUP = "/home/ccl/git/komponentbaseret-labs/AsteroidsNetbeansModules-ccl/application/src/test/resources/update-xml-backup/updates.xml";
    private static final String UPDATES_FILE_FOR_TEST = "/home/ccl/git/komponentbaseret-labs/AsteroidsNetbeansModules-ccl/application/src/test/resources/update-xml-for-test/updates.xml";
    
    
    public IntegrationTest(String name) {
        super(name);
    }
    
    public static Test suite() {
        Test testSuite = NbModuleSuite.createConfiguration(IntegrationTest.class).
                gui(false).
                failOnMessage(Level.WARNING). // works at least in RELEASE71
                failOnException(Level.INFO).
                enableClasspathModules(false).
                clusters(".*").
                suite(); // RELEASE71+, else use NbModuleSuite.create(NbModuleSuite.createConfiguration(...))
        return testSuite;
    }
    
    @Override
    public void setUp() throws Exception {
        System.out.println("Before test - run setUp()");
        // MAKE A COPY OF ORIGIN UPDATE XML FILE
        System.out.println("Copy " + UPDATES_FILE + " to " + UPDATES_FILE_BACKUP);
        copy(get(UPDATES_FILE), get(UPDATES_FILE_BACKUP), REPLACE_EXISTING);
        
        // COPY THE UPDATE XML FILE FOR TESTING INTO THE UPDATE CENTER
        // CONTAINS ONLY THE CORE MODULE
        System.out.println("Copy " + UPDATES_FILE_FOR_TEST + " to " + UPDATES_FILE);
        copy(get(UPDATES_FILE_FOR_TEST), get(UPDATES_FILE), REPLACE_EXISTING);
    }
    
    @Override
    public void tearDown() throws Exception {
        System.out.println("After test - run tearDown()");
        // COPY THE ORIGIN FILE BACK INTO THE UPDATE CENTER
        System.out.println("Copy " + UPDATES_FILE_BACKUP + " to " + UPDATES_FILE);
        copy(get(UPDATES_FILE_BACKUP), get(UPDATES_FILE), REPLACE_EXISTING);
    }
    
    public void testIntegration() throws InterruptedException, IOException {

        // SETUP
        List<IEntityProcessingService> processors = new CopyOnWriteArrayList<>();
        List<IGamePluginService> plugins = new CopyOnWriteArrayList<>();
        
        waitForUpdate(processors, plugins, 10000);

        // PRE ASSERTS
        //Size should be 0 because no modules are installed.
        assertEquals("No plugins", 0, processors.size());
        assertEquals("No processors", 0, plugins.size());
        
        // TEST: Load Enemy via UC
        System.out.println("Copy " + ADD_ENEMY_UPDATES_FILE + " to " + UPDATES_FILE);
        copy(get(ADD_ENEMY_UPDATES_FILE), get(UPDATES_FILE), REPLACE_EXISTING);

        waitForUpdate(processors, plugins, 10000);

        // ASSERTS: Enemy loaded
        assertEquals("One plugins", 1, plugins.size());
        assertEquals("One processors", 1, processors.size());

        // TEST: Unload Enemy via UC
        System.out.println("Copy " + REM_ENEMY_UPDATES_FILE + " to " + UPDATES_FILE);
        copy(get(REM_ENEMY_UPDATES_FILE), get(UPDATES_FILE), REPLACE_EXISTING);

        waitForUpdate(processors, plugins, 10000);

        // ASSERTS: Enemy unloaded
        assertEquals("No plugins", 0, plugins.size());
        assertEquals("No processors", 0, processors.size());
    }
    
    private void waitForUpdate(List<IEntityProcessingService> processors, List<IGamePluginService> plugins, long millis) throws InterruptedException {
        // Needs time for silentUpdater to install all modules
        Thread.sleep(millis);

        processors.clear();
        processors.addAll(Lookup.getDefault().lookupAll(IEntityProcessingService.class));

        plugins.clear();
        plugins.addAll(Lookup.getDefault().lookupAll(IGamePluginService.class));
    }
    
}
