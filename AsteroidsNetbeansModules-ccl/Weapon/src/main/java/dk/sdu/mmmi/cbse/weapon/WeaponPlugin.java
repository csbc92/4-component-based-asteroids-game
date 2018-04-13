/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.weapon;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commonbullet.Bullet;
import java.util.List;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author ccl
 */

@ServiceProvider(service = IGamePluginService.class)
public class WeaponPlugin implements IGamePluginService{
   
    @Override
    public void start(GameData gameData, World world) {
    }

    @Override
    public void stop(GameData gameData, World world) {
        List<Entity> bullets = world.getEntities(Bullet.class);
        
        for (Entity bullet : bullets) {
            world.removeEntity(bullet);
        }
    }
    
}
