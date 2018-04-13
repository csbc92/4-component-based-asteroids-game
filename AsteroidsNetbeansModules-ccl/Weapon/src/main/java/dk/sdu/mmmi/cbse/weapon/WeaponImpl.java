/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.weapon;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IWeapon;
import dk.sdu.mmmi.cbse.commonbullet.Bullet;
import dk.sdu.mmmi.cbse.commonenemy.Enemy;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author ccl
 */

@ServiceProvider(service = IWeapon.class) 
public class WeaponImpl implements IWeapon {

    @Override
    public Entity spawnBullet(Entity shooter, GameData gameData, World world) {
        
        Bullet bullet = null;
        
        // Spawn a new bullet at the shooter position
        if (shooter != null) {
            PositionPart positionPart = shooter.getPart(PositionPart.class);
            float x = positionPart.getX();
            float y = positionPart.getY();
            float radians = positionPart.getRadians();

            // Init a new bullet with same pos and radians as shooter..
            bullet = new Bullet(x, y, radians);
            if (shooter instanceof Enemy) {
                bullet.setColor(0.8f, 0.8f, 0.0f, 0.0f);
            }
            world.addEntity(bullet);
        }
        
        return bullet;
    }    
}
