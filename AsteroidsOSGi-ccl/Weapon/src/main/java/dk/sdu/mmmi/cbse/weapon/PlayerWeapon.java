/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.weapon;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import static dk.sdu.mmmi.cbse.common.data.GameKeys.SPACE;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.playersystem.Player;
import java.util.List;

/**
 *
 * @author ccl
 */
public class PlayerWeapon implements IEntityProcessingService {
    
    public PlayerWeapon() {
        System.out.println("Player weapon EntityProcessingService");
    }

    @Override
    public void process(GameData gameData, World world) {
        
        List<Entity> players = world.getEntities(Player.class);
        Entity player = null;
        if (players.size() > 0) {
            player = players.get(0);
        }
        // Spawn a new bullet at the player position
        if (player != null) {
            boolean shoot = gameData.getKeys().isDown(SPACE);

            if (shoot) {
                PositionPart positionPart = player.getPart(PositionPart.class);
                float x = positionPart.getX();
                float y = positionPart.getY();
                float radians = positionPart.getRadians();
                
                // Init a new bullet with same pos and radians as player..
                Bullet bullet = new Bullet(x, y, radians); 
                world.addEntity(bullet);
            }
        }
        
        // Process every bullet in the game
        for (Entity bullet : world.getEntities(Bullet.class)) {
                PositionPart bulletPosPart = bullet.getPart(PositionPart.class);
                MovingPart bulletMovingPart = bullet.getPart(MovingPart.class);
                LifePart bulletLifePart = bullet.getPart(LifePart.class);
                bulletLifePart.reduceExpiration(gameData.getDelta());
                
                if (bulletLifePart.getExpiration() < 0) {
                    world.removeEntity(bullet);
                }
                
                bulletMovingPart.setUp(true);
                bulletPosPart.process(gameData, bullet);
                bulletMovingPart.process(gameData, bullet);
                updateShape(bullet);
            }
    }
    
    private void updateShape(Entity entity) {
        float[] shapeX = entity.getShapeX();
        float[] shapeY = entity.getShapeY();
        
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
        float pi = 3.1415f;
        int size = 4;
        
        shapeX[0] = (float)(x + Math.cos(radians + pi/4) * size);
        shapeY[0] = (float)(y + Math.sin(radians + pi/4) * size);
        
        shapeX[1] = (float)(x + Math.cos(radians + pi/4 + pi/2) * size);
        shapeY[1] = (float)(y + Math.sin(radians + pi/4 + pi/2) * size);
        
        shapeX[2] = (float)(x + Math.cos(radians + pi/4 + pi) * size);
        shapeY[2] = (float)(y + Math.sin(radians + pi/4 + pi) * size);
        
        shapeX[3] = (float)(x + Math.cos(radians + pi/4 - pi/2) * size);
        shapeY[3] = (float)(y + Math.sin(radians + pi/4 - pi/2) * size);
        
        entity.setShapeX(shapeX);
        entity.setShapeY(shapeY);
    }
    
}
