package dk.sdu.mmmi.cbse.enemy2;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        context.registerService(IGamePluginService.class, new EnemyPlugin(), null);
        context.registerService(IEntityProcessingService.class, new EnemyControlSystem(), null);
    }

    public void stop(BundleContext context) throws Exception {
    }

}
