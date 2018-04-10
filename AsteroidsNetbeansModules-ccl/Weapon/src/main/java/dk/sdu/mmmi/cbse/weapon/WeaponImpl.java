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
            //boolean shoot = gameData.getKeys().isDown(SPACE);
            PositionPart positionPart = shooter.getPart(PositionPart.class);
            float x = positionPart.getX();
            float y = positionPart.getY();
            float radians = positionPart.getRadians();

            // Init a new bullet with same pos and radians as shooter..
            bullet = new Bullet(x, y, radians);
            world.addEntity(bullet);
        }
        
        return bullet;
    }    
}
