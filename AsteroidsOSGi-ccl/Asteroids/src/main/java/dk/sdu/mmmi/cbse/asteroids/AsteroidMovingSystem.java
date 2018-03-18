/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

/**
 *
 * @author ccl
 */
public class AsteroidMovingSystem implements IEntityProcessingService {

    public AsteroidMovingSystem() {
        System.out.println("Asteroid EntityProcessingService ctor..");
    }
    
    @Override
    public void process(GameData gameData, World world) {
        
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            // Update shape
            MovingPart movingPart = asteroid.getPart(MovingPart.class);
            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            
            // Initial "push" for moving the asteroid
            if (((Asteroid)asteroid).getIsMoving() == false) {
                movingPart.setUp(true);
                ((Asteroid)asteroid).setIsMoving(true);
            }
            else {
                movingPart.setUp(false);
            }
            
            movingPart.process(gameData, asteroid);
            positionPart.process(gameData, asteroid);
            
            updateShape((Asteroid)asteroid);
        }
    }
    
    private void updateShape(Asteroid asteroid) {
        
        PositionPart positionPart = asteroid.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
        radians += 0.04f;
        positionPart.setRadians(radians);
       
        AsteroidShape shape = new AsteroidShape(asteroid.getSize(), x, y, radians);
        
        asteroid.setShapeX(shape.getShapeX());
        asteroid.setShapeY(shape.getShapeY());
    }  
}
