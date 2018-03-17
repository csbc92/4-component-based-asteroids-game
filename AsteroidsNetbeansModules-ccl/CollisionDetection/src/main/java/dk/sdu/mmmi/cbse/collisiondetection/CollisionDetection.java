/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.collisiondetection;

import dk.sdu.mmmi.cbse.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Point;
import dk.sdu.mmmi.cbse.common.data.Vector2D;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.enemy.Enemy;
import dk.sdu.mmmi.cbse.playersystem.Player;
import dk.sdu.mmmi.cbse.weapon.Bullet;
import java.util.List;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author ccl
 */
@ServiceProvider(service = IPostEntityProcessingService.class)
public class CollisionDetection implements IPostEntityProcessingService {

    
    // TODO Try and implement SAT (Separating Axis Theorem) or GJK (Gilbert-Johnson-Keerthi) collision detection
    // Needs to be refactored: http://www.dyn4j.org/2010/01/sat/#sat-curve
    @Override
    public void process(GameData gameData, World world) {
        
        playerCollision(gameData, world);
        enemyCollision(gameData, world);
        bulletCollision(gameData, world);
    }
    
    private void bulletCollision(GameData gameData, World world) {
        
        List<Entity> bullets = world.getEntities(Bullet.class);
        for (Entity bullet: bullets) {
            for (Entity asteroid : world.getEntities(Asteroid.class)) {
                boolean collision = testNormalsCollision(bullet, asteroid) && testNormalsCollision(asteroid, bullet);
                if (collision) {
                    world.removeEntity(bullet);
                    destroyAsteroid((Asteroid)asteroid, world);
                    return;
                }
            }
        }
    }
    
    private void enemyCollision(GameData gameData, World world) {
        // Player and asteroids collision
        List<Entity> enemies = world.getEntities(Enemy.class);
        for (Entity enemy: enemies) {
            for (Entity asteroid : world.getEntities(Asteroid.class)) {
                boolean collision = testNormalsCollision(enemy, asteroid) && testNormalsCollision(asteroid, enemy);
                if (collision) {
                    //System.out.println("Collision " + asteroid.getID() + LocalDateTime.now());
                    world.removeEntity(enemy);
                    return;
                }
            }
        }
    }
    
    private void playerCollision(GameData gameData, World world) {
        // Player and asteroids collision
        List<Entity> players = world.getEntities(Player.class);
        if (players.size() > 0) {
            Entity player = players.get(0);
            for (Entity asteroid : world.getEntities(Asteroid.class)) {
                boolean collision = testNormalsCollision(player, asteroid) && testNormalsCollision(asteroid, player);
                if (collision) {
                    //System.out.println("Collision " + asteroid.getID() + LocalDateTime.now());
                    world.removeEntity(player);
                    return;
                }
            }
        }
    }
    
    /**
     * SAT collision implementation (Separating Axis Theorem)
     * @param entityX This is the shape that is used to create normal vectors used for collision checking
     * @param entityY The other entity that is checked collision with
     * @return 
     */
    private boolean testNormalsCollision(Entity entityX, Entity entityY) {
        
        float[] shapexEntityX = entityX.getShapeX();
        float[] shapeyEntityX = entityX.getShapeY();

        List<Vector2D> entityXVectors = Vector2D.getVectors(shapexEntityX, shapeyEntityX); // Get edges
        List<Vector2D> entityXNormalVectors = Vector2D.getNormals(entityXVectors); // Get normal vectors

        // For each normal
        for (int i = 0; i < entityXNormalVectors.size(); i++) {

            // Project origin entityX vectors onto normal and find min, max value
            List<Vector2D> originEntityXVectors = Vector2D.getOriginVectors(shapexEntityX, shapeyEntityX);
            Point[] minMaxEntityX = Vector2D.projectAndGetMinMax(entityXNormalVectors.get(i), originEntityXVectors);

            // Project origin entityY vectors onto normal and find min, max value
            float[] shapexEntityY = entityY.getShapeX();
            float[] shapeyEntityY = entityY.getShapeY();

            List<Vector2D> originEntityYVectors = Vector2D.getOriginVectors(shapexEntityY, shapeyEntityY);
            Point[] minMaxEntityY = Vector2D.projectAndGetMinMax(entityXNormalVectors.get(i), originEntityYVectors);

            
            Point entXMin = minMaxEntityX[0];
            Point entXMax = minMaxEntityX[1];
            Point entYMin = minMaxEntityY[0];
            Point entYMax = minMaxEntityY[1];
            
            boolean seperate = entXMax.compareTo(entYMin) < 0 || entYMax.compareTo(entXMin) < 0;
            
            if (seperate) {
                return false; // No need to process further if 1 axis doesn't overlap.
            }

        }
        
        return true; // All axis overlap
    }    

    private void destroyAsteroid(Asteroid parentAsteroid, World world) {
        
        Random random = new Random();
        PositionPart posPart = parentAsteroid.getPart(PositionPart.class);
        int newSize = 0;
        world.removeEntity(parentAsteroid);
        
        if (parentAsteroid.getSize() >= Asteroid.SIZE_BIG) {
            newSize = Asteroid.SIZE_MEDIUM;
        } else if (parentAsteroid.getSize() >= Asteroid.SIZE_MEDIUM) {
            newSize = Asteroid.SIZE_SMALL;
        } else
        {
            return; // Don't spawn new asteroids
        }
        
        for (int i = 0; i < 2; i++) {
            Asteroid a = new Asteroid();
            
            float deacceleration = 0; // Never stop moving
            float acceleration = 100000000; // Accelerate like a fucking rocket
            float maxSpeed = 50;
            float rotationSpeed = 5;
            float x = posPart.getX();
            float y = posPart.getY();
            // Set a random radians/direction/orientation for this asteroid
            float radians = random.nextFloat() * 3.1415f;
            
            a.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
            a.add(new PositionPart(x, y, radians));
            a.setSize(newSize);
            
            world.addEntity(a);
        }
    }
}
